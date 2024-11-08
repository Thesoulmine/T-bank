package ru.tbank.restful.listener.repository.initialization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.tbank.restful.listener.repository.initialization.command.RepositoryInitializationCommand;
import ru.tbank.timedstarter.annotation.Timed;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RepositoryInitializationEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final List<RepositoryInitializationCommand> initializationCommands;
    private final ScheduledExecutorService scheduledRepositoryInitializationExecutorService;
    private final Duration refreshInterval;

    public RepositoryInitializationEventListener(
            List<RepositoryInitializationCommand> initializationCommands,
            @Qualifier("scheduledRepositoryInitializationExecutorService")
            ScheduledExecutorService scheduledRepositoryInitializationExecutorService,
            @Value("${executor-service.scheduled-repository-initialization.refresh-interval}")
            Duration refreshInterval) {
        this.initializationCommands = initializationCommands;
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
        log.info("Repositories initialization start");

        initializationCommands.forEach(RepositoryInitializationCommand::execute);

        log.info("Repositories initialization was successful");
    }
}
