package be.bittich.website.conf;

import org.apache.camel.component.jms.JmsComponent;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.sql.DataSource;

/**
 * Created by Nordine on 07-11-15.
 */
@Configuration
@EnableCaching
public class ContextConfiguration {

    @Bean
    @Inject
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("bittich_be");
    }

    @Bean
    @Inject
    public JmsComponent activeMQComponent(ConnectionFactory connectionFactory) {
        JmsComponent component = JmsComponent.jmsComponentTransacted(connectionFactory);
        component.setConcurrentConsumers(1);
        return component;
    }

}
