package ru.tbank.restful.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.tbank.restful.dto.CategoryRequestDTO;
import ru.tbank.restful.entity.Category;
import ru.tbank.restful.limiter.RateLimiter;
import ru.tbank.restful.mapper.CategoryMapper;

import java.util.List;

@Component
public class CategoryKudaGoClient implements CategoryClient {

    private final RestClient restClient;
    private final CategoryMapper categoryMapper;
    private final RateLimiter rateLimiter;

    public CategoryKudaGoClient(@Qualifier("kudaGoRestClient") RestClient restClient,
                                CategoryMapper categoryMapper,
                                @Qualifier("KudaGoRateLimiter") RateLimiter rateLimiter) {
        this.restClient = restClient;
        this.categoryMapper = categoryMapper;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            rateLimiter.acquire();
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
            rateLimiter.release();
        }

        return categoryMapper.toEntity(response);
    }
}
