package be.bittich.website.conf;

import be.bittich.website.domain.personal.AboutMe;
import be.bittich.website.domain.security.Role;
import be.bittich.website.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.annotation.EnableJms;
import be.bittich.website.repository.security.UserRepository;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import be.bittich.website.repository.personal.AboutMeRepository;
/**
 * Created by Nordine on 07-11-15.
 */
@EnableCaching
@EnableJms
@Slf4j
@Configuration
public class ContextConfiguration implements CommandLineRunner {

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
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }



    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }
    @Bean
    @Inject
    public JmsComponent activeMQComponent(ConnectionFactory connectionFactory) {
        JmsComponent component = JmsComponent.jmsComponentTransacted(connectionFactory);
        component.setConcurrentConsumers(1);
        return component;
    }

    @Override
    public void run(String... strings) throws Exception {

        AboutMe aboutMe = AboutMe.builder()
                .aboutMe("Je m'appelle J.F Cope et j'adore les pains au chocolat")
                .build();



        User user = User.builder()
                .username("nbittich")
                .email("nordine1@hotmail.com")
                .password("kikoolol")
                .role(Role.ROLE_ADMIN)
                .role(Role.ROLE_MODERATOR)
                .role(Role.ROLE_USER)
                .role(Role.ROLE_SUPER_ADMIN)
                .build();

        log.info("User me has been saved with id {}",userRepository.save(user).getId());
        log.info("About me has been saved with id {}",aboutMeRepository.save(aboutMe).getId());
    }
}
