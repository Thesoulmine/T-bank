package ru.tbank.restful.listener.repository.initialization.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import ru.tbank.restful.client.CategoryClient;
import ru.tbank.restful.entity.Category;
import ru.tbank.restful.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@Component
public class CategoryRepositoryInitializationCommandImpl implements RepositoryInitializationCommand {

    private final CategoryClient categoryClient;
    private final CategoryService categoryService;
    private final ExecutorService fixedRepositoryInitializationExecutorService;

    public CategoryRepositoryInitializationCommandImpl(
            CategoryClient categoryClient,
            CategoryService categoryService,
            @Qualifier("fixedRepositoryInitializationExecutorService")
            ExecutorService fixedRepositoryInitializationExecutorService) {
        this.categoryClient = categoryClient;
        this.categoryService = categoryService;
        this.fixedRepositoryInitializationExecutorService = fixedRepositoryInitializationExecutorService;
    }

    @Override
    public void execute() {
        log.info("Category repository initialization start");
        List<Category> categories = new ArrayList<>();
        List<Future<?>> futures = new ArrayList<>();

        try {
            categories = categoryClient.getAllCategories();
        } catch (HttpClientErrorException exception) {
            log.error("{} in category client", exception.getStatusCode());
        }

        for (int i = 0; i < categories.size(); i += 5) {
            int toIndex = Math.min(i + 5, categories.size());

            List<Category> batch = categories.subList(i, toIndex);

            futures.add(fixedRepositoryInitializationExecutorService.submit(() ->
                    batch.forEach(category -> {
                        log.info("Saving category {}", category);
                        categoryService.saveCategory(category);
                        log.info("Saving category was successful");
                    })
            ));
        }

        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        log.info("Category repository initialization was successful");
    }
}
