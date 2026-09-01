package com.georgeCross.george.service;

import com.georgeCross.george.controllers.ShopController;
import com.georgeCross.george.models.Category;
import com.georgeCross.george.models.Product;
import com.georgeCross.george.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.georgeCross.george.models.Category.VOSTOK;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopController.class)
public class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ImageService imageService;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Название1");
        product1.setSlug("nazvanie1");

        Long product2Id = 2L;
        product2 = new Product();
        product2.setId(product2Id);
        product2.setTitle("Название2");
        product2.setSlug("nazvanie2");
        product2.setImageUrls(List.of("http://cloud.com"));
    }

    @Test
    @DisplayName("Возврат продуктов")
    void getAllProduct() throws Exception {

        Category category = VOSTOK;
        List<Product> expectedList = List.of(product1);

        when(productRepository.findByCategory(category)).thenReturn(expectedList);

        mockMvc.perform(get("/api/products")
                        .param("category", String.valueOf(category))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Название1"))
                .andExpect(jsonPath("$[0].slug").value("nazvanie1"));
    }

    @Test
    @DisplayName("Поиск по слагу")
    void getProductsBySlug() throws Exception {

        String slug = "nazvanie2";
        List<Product> expectedList = List.of(product1);

        when(productRepository.findBySlug(slug)).thenReturn(Optional.of(product1));

        mockMvc.perform(get("/api/products/{slug}", slug)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Название1"));
    }

    @Test
    @DisplayName("Post product")
    void postProduct() throws Exception {

        Product imgProduct = new Product();
        imgProduct.setId(2L);
        imgProduct.setImageUrls(List.of("http://cloud.com"));
        imgProduct.setTitle("nazvanie");

        MockMultipartFile fakeFile = new MockMultipartFile(
                "file",               // Имя параметра в контроллере (@RequestParam("file"))
                "test-image.jpg",      // Имя файла
                "image/jpeg",          // Тип контента
                "image-bytes".getBytes() // Содержимое файла
        );

        when(productRepository.save(any(Product.class)))
                .thenReturn(product2)
                .thenReturn(imgProduct);

        when(imageService.uploadProductImage(any(), eq(2L)))
                .thenReturn(List.of("http://cloud.com"));

        mockMvc.perform(multipart("/api/products")
                        .file(fakeFile)
                        .param("title", "nazvanie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("nazvanie"));
    }

    @Test
    @DisplayName("delete product")
    void deleteProductTest() throws Exception {

        Long productId = 99L;

        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    @DisplayName("Редактирование продукта")
    void putUpdateProductTest() throws Exception {


        Long productId = 1L;

        Product imgProduct = new Product();
        imgProduct.setId(productId);
        imgProduct.setImageUrls(new ArrayList<>(List.of("http://cloud.com")));
        imgProduct.setTitle("nazvanie");

        Product updateProduct = new Product();
        updateProduct.setTitle("nazvanie1");
        updateProduct.setImageUrls(List.of("http://cloud.com"));
        updateProduct.setId(productId);

        MockMultipartFile fakeFile = new MockMultipartFile(
                "file",               // Имя параметра в контроллере (@RequestParam("file"))
                "test-image.jpg",      // Имя файла
                "image/jpeg",          // Тип контента
                "image-bytes".getBytes() // Содержимое файла
        );

        when(productRepository.findById(productId)).thenReturn(Optional.of(imgProduct));
        when(imageService.uploadProductImage(any(), eq(productId))).thenReturn((List.of("http://cloud.com")));
        when(productRepository.save(imgProduct)).thenReturn(updateProduct);

        mockMvc.perform(multipart(HttpMethod.PUT, "/api/products/{id}", productId)
                        .file(fakeFile)
                        .param("title", "nazvanie1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("nazvanie1"));
    }

    @Test
    @DisplayName("Удаление картинки")
    void deleteImages() throws Exception {

        Long productId = 1L;
        String imageUrl = "https://cloud.com";

        Product deleteUrlImg = new Product();
        deleteUrlImg.setTitle("nazvanie1");
        deleteUrlImg.setImageUrls(new ArrayList<>(List.of("https://cloud.com")));
        deleteUrlImg.setId(productId);

        Product saveProduct = new Product();
        saveProduct.setTitle("nazvanie1");
        saveProduct.setImageUrls(List.of("https://cloud.com"));
        saveProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(deleteUrlImg));
        when(productRepository.save(any(Product.class))).thenReturn(saveProduct);



        mockMvc.perform(delete("/api/products/{id}/images", productId)
                        .param("imgUrl", imageUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.imageUrls.length()").value(1))
                .andExpect(jsonPath("$.imageUrls[0]").value("https://cloud.com"));

        verify(imageService).deleteProductImageFromCloud(imageUrl);
    }
}
