package com.georgeCross.george.service.apiTests.rest;

import com.georgeCross.george.models.Category;
import com.georgeCross.george.models.Product;
import com.georgeCross.george.repositories.ProductRepository;
import com.georgeCross.george.service.ImageService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductImageRestAssuredTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductRepository productRepository;

    @MockBean
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("REST Assured: Успешное удаление изображения")
    void shouldDeleteProductImageViaRestAssured() {

        String imgUrl = "http://cloud.com";
        String imgDelete = "http://cloud.com";

        Product product = new Product();
        product.setSlug("product");
        product.setTitle("novi");
        product.setImageUrls(new ArrayList<>(List.of(imgUrl, imgDelete)));

        Product saveProduct = productRepository.save(product);
        Long productId = saveProduct.getId();

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", productId)
                .queryParam("imgUrl", imgDelete)
                .when()
                .delete("/api/products/{id}/images")
                .then()
                .statusCode(200)
                .body("id", equalTo(productId.intValue()))
                .body("imageUrls", hasSize(1))
                .body("imageUrls[0]", equalTo(imgUrl));

        verify(imageService).deleteProductImageFromCloud(imgDelete);
    }

    @Test
    @DisplayName("REST Assured: Продукт не найден")
    void productNotFound() {

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 3L)
                .queryParam("imgUrl", "http://cloud.com")
                .when()
                .delete("/api/products/{id}/images")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("REST Assured: Редактирование продукта")
    void editProduct() {

        String imgUrl = "http://cloud.com";
        String addUrl = "http://cloud.com";

        Product product = new Product();
        product.setSlug("product");
        product.setTitle("novi");
        product.setImageUrls(new ArrayList<>(List.of(imgUrl)));

        Product saveProduct = productRepository.save(product);
        Long productId = saveProduct.getId();

        when(imageService.uploadProductImage(any(), eq(productId))).thenReturn((List.of(addUrl)));

        given()
                .pathParam("id", productId)
                .formParam("title", "new")
                .formParam("slug", "new")
                .multiPart("file", "image1.jpg", "bytes1".getBytes(), "image/jpeg")
                .when()
                .put("/api/products/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(productId.intValue()))
                .body("title", equalTo("new"))
                .body("slug", equalTo("new"))
                .body("imageUrls", hasSize(2))
                .body("imageUrls[1]", equalTo("http://cloud.com"));

    }

    @Test
    @DisplayName("REST Assured: возврат продуктов по слагу")
    void getProductBySlug() {

        Product product = new Product();
        product.setSlug("product");
        product.setTitle("novi");

        Product saveProduct = productRepository.save(product);


        given()
                .pathParam("slug", saveProduct.getSlug())
                .get("/api/products/{slug}")
                .then()
                .statusCode(200)
                .body("title", equalTo(saveProduct.getTitle()));
    }

    @Test
    @DisplayName("REST Assured: не найдено продуктов по слагу")
    void getProductNotFoundBySlug() {

        String slug = "moneta";

        given()
                .pathParam("slug", slug)
                .get("/api/products/{slug}")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("REST Assured: возврат всех продуктов")
    void getProductByCategory() {

        Product product = new Product();
        product.setSlug("product");
        product.setTitle("novi");
        product.setCategory(Category.VOSTOK);

        Product saveProduct = productRepository.save(product);

        given()
                .contentType(ContentType.JSON)
                .queryParam("category", saveProduct.getCategory())
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("title[0]", equalTo(saveProduct.getTitle()))
                .body("$", hasSize(1))
                .body("category[0]", equalTo(saveProduct.getCategory().toString()));
    }
//    @Test
//    @DirtiesContext
//    @DisplayName("REST Assured: Создание продукта")
//    void postProduct() {
//
//        Product product = new Product();
//        product.setSlug("product");
//        product.setTitle("novi");
//        String imgUrl = "http://cloud.com";
//        product.setImageUrls(new ArrayList<>(List.of(imgUrl)));
//
//        Product saveProduct = productRepository.save(product);
//        Long productsId = saveProduct.getId();
//
//        when(imageService.uploadProductImage(any(), any(Long.class))).thenReturn(List.of(imgUrl));
//
//        given()
//                .log().all()
//                .formParam("title", "novi")
//                .formParam("slug", "product")
//                .multiPart("file", "image1.jpg", "bytes1".getBytes(), "image/jpeg")
//                .when()
//                .post("/api/products")
//                .then()
//                .log().all()
//                .statusCode(200)
//                .body("id", notNullValue())
//                .body("title", equalTo("novi"))
//                .body("slug", equalTo("product"));
////                .body("imageUrls", hasSize(1))
////                .body("imageUrls[0]", equalTo("http://cloud.com")).log();
//    }

}
