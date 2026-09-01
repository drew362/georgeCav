package com.georgeCross.george.service.apiTests;
import com.georgeCross.george.models.Product;
import com.georgeCross.george.repositories.ProductRepository;
import com.georgeCross.george.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

// Поднимаем приложение на случайном порту для честных HTTP-запросов
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductImageApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository; // Реальный репозиторий для подготовки данных

    @MockBean
    private ImageService imageService; // Облако изолируем моком

    private String baseUrl;

    @BeforeEach
    void setUp() {
        // Очищаем БД перед каждым тестом и формируем базовый URL
        productRepository.deleteAll();
        baseUrl = "http://localhost:" + port + "/api/products";
    }

    @Test
    @DisplayName("API: Успешное удаление изображения по HTTP DELETE")
    void shouldDeleteProductImageViaApi() {
        // 1. GIVEN — Создаем и сохраняем реальный продукт в тестовую базу данных
        String imageUrl = "https://cloud.com";

        Product product = new Product();
        product.setTitle("Тестовый продукт");
        product.setImageUrls(new ArrayList<>(List.of(imageUrl, "https://cloud.com")));
        product.setSlug("privet");

        Product savedProduct = productRepository.save(product);
        Long id = savedProduct.getId();

        // Формируем URL с Query-параметром: /products/{id}/images?imgUrl=...
        String requestUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .pathSegment(id.toString(), "images")
                .queryParam("imgUrl", imageUrl)
                .toUriString();

        // 2. WHEN — Отправляем реальный HTTP DELETE запрос через RestTemplate
        ResponseEntity<Product> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Product.class
        );

        // 3. THEN — Проверяем HTTP-ответ сервера
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);

        // Проверяем, что в теле ответа осталась только одна картинка
        assertThat(response.getBody().getImageUrls()).hasSize(1);
        assertThat(response.getBody().getImageUrls().get(0)).isEqualTo("https://cloud.com");

        // Проверяем, что в реальной базе данных изменения тоже применились
        Product productInDb = productRepository.findById(id).orElseThrow();
        assertThat(productInDb.getImageUrls()).hasSize(1);

        // Проверяем, что заглушка сервиса облака зафиксировала вызов удаления файла
        verify(imageService).deleteProductImageFromCloud(imageUrl);
    }

    @Test
    @DisplayName("API: Возврат 404 Not Found, если продукта не существует")
    void shouldReturn404WhenProductDoesNotExist() {
        // GIVEN
        Long nonExistingId = 999L;
        String requestUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .pathSegment(nonExistingId.toString(), "images")
                .queryParam("imgUrl", "https://any-url.com")
                .toUriString();

        // WHEN
        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                String.class
        );

        // THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}