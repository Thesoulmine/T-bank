package ru.tbank.restful.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.tbank.restful.dto.CategoryRequestDTO;
import ru.tbank.restful.entity.Category;
import ru.tbank.restful.mapper.CategoryMapper;

import java.util.List;

@Component
public class CategoryKudaGoClient implements CategoryClient {

    private final RestClient restClient;
    private final CategoryMapper categoryMapper;

    public CategoryKudaGoClient(@Qualifier("kudaGoRestClient") RestClient restClient,
                                CategoryMapper categoryMapper) {
        this.restClient = restClient;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.toEntity(
                restClient
                        .get()
                        .uri("place-categories/")
                        .retrieve()
                        .body(new ParameterizedTypeReference<List<CategoryRequestDTO>>() {}));
    }
}
