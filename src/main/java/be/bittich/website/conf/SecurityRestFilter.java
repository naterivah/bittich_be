package be.bittich.website.conf;

import be.bittich.website.domain.security.RemoteClient;
import be.bittich.website.domain.security.User;
import be.bittich.website.repository.security.RemoteClientRepository;
import be.bittich.website.repository.security.UserRepository;
import be.bittich.website.router.personal.AboutMeRouter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;
import java.util.UUID;

import static spark.Spark.*;

/**
 * Created by Nordine on 10-11-15.
 */
@Named
@Slf4j
public class SecurityRestFilter {


    private final UserRepository userRepository;
    private final RemoteClientRepository remoteClientRepository;
    private final CacheManager cacheManager;
    private final PasswordEncoder encoder;


    @Inject
    public SecurityRestFilter(UserRepository userRepository, RemoteClientRepository remoteClientRepository, CacheManager cacheManager, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.remoteClientRepository = remoteClientRepository;
        this.cacheManager = cacheManager;
        this.encoder = encoder;
    }

    public void configure() {

        log.info("Configuring the security context of the application...");


        before("*/protected/*", (request, response) -> {

            String token = request.headers("X-Auth-Token");
            final String ipAddress = request.ip();

            if (Objects.isNull(token)) halt(401, "Unauthorized");

            User user = cacheManager.getCache("tokenStore").get(token, User.class);

            if (Objects.isNull(user)) halt(401, "Unauthorized");
            if (!user.getRemoteClients().stream().anyMatch(r -> r.getIp().equals(ipAddress))) {
                log.info("Attempt to use a token generated with another ip address");
                halt(401, "Unauthorized");
            }


        });

        post("/login", (request, response) -> {

            final String name = request.queryParams("name");
            final String password = request.queryParams("password");
            final String ipAddress = request.ip();

            log.info("Attempt to log in with username {}, password {}, ip {}"
                    , name, password, ipAddress);

            RemoteClient remoteClient = remoteClientRepository.findOne(ipAddress);

            if (Objects.isNull(remoteClient)) {
                remoteClient =
                        remoteClientRepository.save(
                                RemoteClient.builder().ip(ipAddress).banned(false).build()
                        );
            } else if (remoteClient.isBanned()) {
                halt(403, String.format("Ip address %s is banned", ipAddress));
            }

            final User user = userRepository.findOneByEmailOrUsername(name, name);

            if (Objects.isNull(user)) halt(403, "User not found");

            if (!encoder.matches(password, user.getPassword())) {
                user.incrementFailedLogin();
                userRepository.save(user); // failed connection
                halt(403, "Password incorrect");
            }

            user.addRemoteClient(remoteClient);

            userRepository.save(user); // last connection TODO not working!

            String uuid = UUID.randomUUID().toString();

            log.info("User {} just logged in and receive the uuid {}", user.getUsername(), uuid);

            cacheManager.getCache("tokenStore").put(uuid, user);

            response.status(200);

            return uuid;
        });

    }
}
