package cam.imitation;

import cam.imitation.db.DbController;
import cam.imitation.storage.config.StorageProperties;
import cam.imitation.storage.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CameraImitationInterfaceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CameraImitationInterfaceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService, DbController dbController) {
		return (args) -> {
			dbController.deleteAllSnapshots();
			storageService.deleteAll();
			storageService.init();
		};
	}
}
