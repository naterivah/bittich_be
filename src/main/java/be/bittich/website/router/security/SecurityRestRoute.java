package be.bittich.website.router.security;

import be.bittich.website.domain.security.User;
import be.bittich.website.repository.security.UserRepository;
import be.bittich.website.util.RestMessage;
import org.apache.camel.Exchange;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.cache.CacheManager;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

/**
 * Created by Nordine on 10-11-15.
 */
@Named
public class SecurityRestRoute extends SpringRouteBuilder {

    public static final String DOMAIN = "SecurityRest";

    private final UserRepository userRepository;
    private final CacheManager cacheManager;


    @Inject
    public SecurityRestRoute(UserRepository userRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.cacheManager = cacheManager;
    }

    @Override
    public void configure() throws Exception {
        rest("/auth")
                .post("/login").type(Credential.class).to("direct:login")
                .get("/logout").to("direct:logout")

        ;

        from("direct:login")
                .routeId("SecurityRestRoute.Login")
                .setHeader("username", simple("${body.name}"))
                .setHeader("password", simple("${body.password}"))
                .to("bean:userRepository?method=findOneByEmailOrUsername(${body.name},${body.name})")
                .choice()
                .when(simple("${body.password} == ${headers.password}"))
                    .process(e -> {
                        User body = e.getIn().getBody(User.class);
                        String token = body.getUsername() + ":" + body.getPassword();
                        cacheManager.getCache("tokenStore").put(token, body);
                        e.getIn().setHeader("AuthorizationToken", token);
                    })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .transform(header("AuthorizationToken"))
                .otherwise()
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .transform(constant(RestMessage.builder().message("Login failure").build()))
                .endChoice()


        ;
    }
}
