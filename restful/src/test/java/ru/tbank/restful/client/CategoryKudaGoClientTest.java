package ru.tbank.restful.client;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;
import ru.tbank.restful.entity.Category;
import ru.tbank.restful.mapper.CategoryMapperImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class CategoryKudaGoClientTest {

    @Container
    private static final WireMockContainer wireMockContainer = new WireMockContainer("wiremock/wiremock:3.6.0")
            .withMappingFromResource(
                    "category-client",
                    CategoryKudaGoClientTest.class,
                    "category-client-mock-config.json");

    private static CategoryKudaGoClient categoryClient;

    @BeforeAll
    public static void beforeAll() {
        RestClient restClient = RestClient.create(wireMockContainer.getBaseUrl());
        categoryClient = new CategoryKudaGoClient(restClient, new CategoryMapperImpl());
    }

    @Test
    public void getAllCategories_ReturnAllCategories() {
        Category category1 = new Category();
        category1.setKudaGoId(1L);
        category1.setSlug("qwe");
        category1.setName("qwe");

        Category category2 = new Category();
        category2.setKudaGoId(2L);
        category2.setSlug("asd");
        category2.setName("asd");

        List<Category> result = categoryClient.getAllCategories();

        assertEquals(List.of(category1, category2), result);
    }
}