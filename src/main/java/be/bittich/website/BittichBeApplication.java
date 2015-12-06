package be.bittich.website;

import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BittichBeApplication extends FatJarRouter {


    public void configure() throws Exception {

    }
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BittichBeApplication.class, args);

    }
}
