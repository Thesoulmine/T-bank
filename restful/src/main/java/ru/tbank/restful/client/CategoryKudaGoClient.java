package ru.tbank.restful.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.tbank.restful.dto.CategoryRequestDTO;
import ru.tbank.restful.entity.Category;
import ru.tbank.restful.limiter.KudaGoRateLimiter;
import ru.tbank.restful.mapper.CategoryMapper;

import java.util.List;

@Component
public class CategoryKudaGoClient implements CategoryClient {

    private final RestClient restClient;
    private final CategoryMapper categoryMapper;
    private final KudaGoRateLimiter kudaGoRateLimiter;

    public CategoryKudaGoClient(@Qualifier("kudaGoRestClient") RestClient restClient,
                                CategoryMapper categoryMapper,
                                KudaGoRateLimiter kudaGoRateLimiter) {
        this.restClient = restClient;
        this.categoryMapper = categoryMapper;
        this.kudaGoRateLimiter = kudaGoRateLimiter;
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            kudaGoRateLimiter.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<CategoryRequestDTO> response;

        try {
            response = restClient
                    .get()
                    .uri("place-categories/")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } finally {
            kudaGoRateLimiter.release();
        }

        return categoryMapper.toEntity(response);
    }
}
