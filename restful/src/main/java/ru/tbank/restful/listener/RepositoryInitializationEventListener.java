package ru.tbank.restful.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import ru.tbank.restful.client.CategoryClient;
import ru.tbank.restful.client.LocationClient;
import ru.tbank.restful.service.CategoryService;
import ru.tbank.restful.service.LocationService;
import ru.tbank.timedstarter.annotation.Timed;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
@Slf4j
public class RepositoryInitializationEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryClient categoryClient;
    private final CategoryService categoryService;
    private final LocationClient locationClient;
    private final LocationService locationService;
    private final ExecutorService executorService;

    public RepositoryInitializationEventListener(CategoryClient categoryClient,
                                                 CategoryService categoryService,
                                                 LocationClient locationClient,
                                                 LocationService locationService) {
        this.categoryClient = categoryClient;
        this.categoryService = categoryService;
        this.locationClient = locationClient;
        this.locationService = locationService;
        executorService = Executors.newFixedThreadPool(2);
    }

    @Timed
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Repositories initialization start");
        Future<?> result1 = executorService.submit(this::initializeCategories);
        Future<?> result2 = executorService.submit(this::initializeLocations);

        try {
            result1.get();
            result2.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();
        log.info("Repositories initialization was successful");
    }

    private void initializeCategories() {
        log.info("Category repository initialization start");

        try {
            categoryClient.getAllCategories().forEach(category -> {
                log.info("Saving category {}", category);
                categoryService.saveCategory(category);
                log.info("Saving category was successful");
            });
            log.info("Category repository initialization was successful");
        } catch (HttpClientErrorException exception) {
            log.error("{} in category client. Attempt again initialize category repository in 500 seconds",
                    exception.getStatusCode());
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            initializeCategories();
        }
    }

    private void initializeLocations() {
        log.info("Location repository initialization start");

        try {
            locationClient.getAllLocations().forEach(location -> {
                log.info("Saving location {}", location);
                locationService.saveLocation(location);
                log.info("Saving location was successful");
            });
            log.info("Location repository initialization was successful");
        } catch (HttpClientErrorException exception) {
            log.error("{} in location client. Attempt again initialize location repository in 500 seconds",
                    exception.getStatusCode());
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            initializeLocations();
        }
    }
}
