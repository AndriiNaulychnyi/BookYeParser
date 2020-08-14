package project.parser.bookye;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import project.parser.bookye.service.MainService;

@SpringBootApplication
@EnableConfigurationProperties
public class SpringBootConsoleApplication implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(SpringBootConsoleApplication.class);

    @Autowired
    private MainService mainService;

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(SpringBootConsoleApplication.class, args);
        log.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws Exception {
        mainService.parser();
    }
}
