package ru.tbank.restful.listener.repository.initialization.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import ru.tbank.restful.client.LocationClient;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.service.LocationInMemoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@Component
public class LocationRepositoryInitializationCommandImpl implements RepositoryInitializationCommand {

    private final LocationClient locationClient;
    private final LocationInMemoryService locationInMemoryService;
    private final ExecutorService fixedRepositoryInitializationExecutorService;

    public LocationRepositoryInitializationCommandImpl(
            LocationClient locationClient,
            LocationInMemoryService locationInMemoryService,
            @Qualifier("fixedRepositoryInitializationExecutorService")
            ExecutorService fixedRepositoryInitializationExecutorService) {
        this.locationClient = locationClient;
        this.locationInMemoryService = locationInMemoryService;
        this.fixedRepositoryInitializationExecutorService = fixedRepositoryInitializationExecutorService;
    }

    @Override
    public void execute() {
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
                        locationInMemoryService.saveLocation(location);
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
