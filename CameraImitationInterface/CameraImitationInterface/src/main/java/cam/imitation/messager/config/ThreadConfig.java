package cam.imitation.messager.config;

import cam.imitation.messager.service.MessagerService;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
@ComponentScan(
    basePackages = "cam.imitation.messager.service",
    basePackageClasses={MessagerService.class}
)
public class ThreadConfig {
    @Bean
    public ThreadPoolTaskScheduler taskExecutor(){
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(10);
        executor.setThreadNamePrefix("ThreadPoolTaskScheduler");
        executor.setRemoveOnCancelPolicy(true);
        executor.initialize();
        return executor;
    }
}
