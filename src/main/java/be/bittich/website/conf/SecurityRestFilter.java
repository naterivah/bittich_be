package be.bittich.website.conf;

import be.bittich.website.domain.security.User;
import be.bittich.website.repository.security.UserRepository;
import be.bittich.website.router.personal.AboutMeRouter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;
import java.util.UUID;

import static spark.Spark.*;

/**
 * Created by Nordine on 10-11-15.
 */
@Slf4j
public class SecurityRestFilter  {

    public static final String DOMAIN = "SecurityRest";

    private final UserRepository userRepository;
    private final CacheManager cacheManager;


    public SecurityRestFilter(UserRepository userRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.cacheManager = cacheManager;
    }

    public void configure(){

        log.info("Configuring the security context of the application...");


        before("/aboutme", (request, response) -> {

            String token = request.headers("X-Auth-Token");

            if (Objects.isNull(token)) halt(401, "Unauthorized");

            User user = cacheManager.getCache("tokenStore").get(token, User.class);

            if (Objects.isNull(user)) halt(401, "Unauthorized");

        });

        post("/login", (request, response) -> {
            for (String attr : request.attributes()) {
                System.out.println("attr: " + attr);
            }
            String name = request.queryParams("name");
            String password = request.queryParams("password");
            User user = userRepository.findOneByEmailOrUsername(name, name);

            if (Objects.isNull(user)) halt(403, "User not found");

            if (!user.getPassword().equals(password)) halt(403, "Password incorrect");

            String uuid = UUID.randomUUID().toString();

            log.info("User {} just logged in and receive the uuid {}", user.getUsername(), uuid);

            cacheManager.getCache("tokenStore").put(uuid, user);

            response.status(200);

            return uuid;
        });

    }
}
