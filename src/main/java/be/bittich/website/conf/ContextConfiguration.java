package be.bittich.website.conf;

import be.bittich.website.domain.personal.AboutMe;
import be.bittich.website.domain.security.Role;
import be.bittich.website.domain.security.User;
import be.bittich.website.repository.personal.AboutMeRepository;
import be.bittich.website.repository.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.sql.DataSource;


@EnableCaching
@EnableJms
@Slf4j
@Configuration
public class ContextConfiguration implements CommandLineRunner {

    @Value("${camel.server.port}")
    private int port;
    @Value("${camel.server.host}")
    private String host;

    @Inject
    private AboutMeRepository aboutMeRepository;

    @Inject
    private UserRepository userRepository;

    @Bean
    @Inject
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean
    public RouteBuilder restConfig(){
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                restConfiguration()
                        .bindingMode(RestBindingMode.json)
                        .port(port)
                        .host(host)
                        .enableCORS(true)
                        .dataFormatProperty("prettyPrint", "true")
                        .component("netty4-http")
                ;

            }
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Inject
    private PasswordEncoder encoder;

    @Override
    public void run(String... strings) throws Exception {

        AboutMe aboutMe = AboutMe.builder()
                .aboutMe("Je m'appelle J.F Cope et j'adore les pains au chocolat")
                .build();



        User user = User.builder()
                .username("nbittich")
                .email("nordine1@hotmail.com")
                .password(encoder.encode("kikoololmdr"))
                .role(Role.ROLE_ADMIN)
                .role(Role.ROLE_MODERATOR)
                .role(Role.ROLE_USER)
                .role(Role.ROLE_SUPER_ADMIN)
                .build();

        log.info("User me has been saved with id {}",userRepository.save(user).getId());
        log.info("About me has been saved with id {}",aboutMeRepository.save(aboutMe).getId());
    }
}
