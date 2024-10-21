package ru.tbank.restful.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import ru.tbank.restful.client.CategoryClient;
import ru.tbank.restful.client.LocationClient;
import ru.tbank.restful.entity.Category;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.service.CategoryService;
import ru.tbank.restful.service.LocationService;
import ru.tbank.timedstarter.annotation.Timed;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
@Slf4j
public class RepositoryInitializationEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryClient categoryClient;
    private final CategoryService categoryService;
    private final LocationClient locationClient;
    private final LocationService locationService;
    private final ExecutorService fixedRepositoryInitializationExecutorService;
    private final ScheduledExecutorService scheduledRepositoryInitializationExecutorService;
    private final Duration refreshInterval;

    public RepositoryInitializationEventListener(
            CategoryClient categoryClient,
            CategoryService categoryService,
            LocationClient locationClient,
            LocationService locationService,
            @Qualifier("fixedRepositoryInitializationExecutorService")
            ExecutorService fixedRepositoryInitializationExecutorService,
            @Qualifier("scheduledRepositoryInitializationExecutorService")
            ScheduledExecutorService scheduledRepositoryInitializationExecutorService,
            @Value("${executor-service.scheduled-repository-initialization.refresh-interval}")
            Duration refreshInterval) {
        this.categoryClient = categoryClient;
        this.categoryService = categoryService;
        this.locationClient = locationClient;
        this.locationService = locationService;
        this.fixedRepositoryInitializationExecutorService = fixedRepositoryInitializationExecutorService;
        this.scheduledRepositoryInitializationExecutorService = scheduledRepositoryInitializationExecutorService;
        this.refreshInterval = refreshInterval;
    }

    @Timed
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        scheduledRepositoryInitializationExecutorService.scheduleAtFixedRate(
                this::initializeData,
                0,
                refreshInterval.toMinutes(),
                TimeUnit.MINUTES);
    }

    private void initializeData() {
        Instant methodStartTime = Instant.now();
        log.info("Repositories initialization start");
        initializeCategories();
        initializeLocations();

        log.info("Repositories initialization was successful");

        Instant methodEndTime = Instant.now();
        System.out.println(Duration.between(methodStartTime, methodEndTime).getNano());
    }

    private void initializeCategories() {
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

    private void initializeLocations() {
        log.info("Location repository initialization start");
        List<Location> locations = new ArrayList<>();
        List<Future<?>> futures = new ArrayList<>();

        try {
            locations = locationClient.getAllLocations();
        } catch (HttpClientErrorException exception) {
            log.error("{} in location client", exception.getStatusCode());
        }

        for (int i = 0; i < locations.size(); i += 5) {
            int toIndex = Math.min(i + 5, locations.size());

            List<Location> batch = locations.subList(i, toIndex);

            futures.add(fixedRepositoryInitializationExecutorService.submit(() ->
                    batch.forEach(location -> {
                        log.info("Saving location {}", location);
                        locationService.saveLocation(location);
                        log.info("Saving location was successful");
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

        log.info("Location repository initialization was successful");
    }
}
