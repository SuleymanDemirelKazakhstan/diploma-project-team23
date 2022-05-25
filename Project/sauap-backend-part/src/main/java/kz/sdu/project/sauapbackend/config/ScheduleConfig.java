package kz.sdu.project.sauapbackend.config;

import kz.sdu.project.sauapbackend.service.UserMonthlyDonateService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.LocalDateTime;

@Configuration
@Slf4j
@EnableScheduling
@ConfigurationProperties(prefix = "scheduler")
public class ScheduleConfig implements SchedulingConfigurer {

    @Getter @Setter
    private String cronMonthlyExpression;

    private final UserMonthlyDonateService userMonthlyDonateService;

    public ScheduleConfig(UserMonthlyDonateService userMonthlyDonateService) {
        this.userMonthlyDonateService = userMonthlyDonateService;
    }


    @Bean
    public TaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("sauap-scheduler");
        scheduler.setPoolSize(1);
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        log.info("Starting scheduling 'scheduled messages processor' job (start time: {})", LocalDateTime.now());

        taskRegistrar.setScheduler(threadPoolTaskScheduler());
        taskRegistrar.addCronTask(this.userMonthlyDonateService::processMonthlyPayment, this.cronMonthlyExpression);

        log.info("Finished scheduling 'scheduled messages processor' job");
    }
}
