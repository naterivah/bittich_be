package be.bittich.website.conf;

import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;

/**
 * Created by Nordine on 17-10-15.
 */
@Named
public class RestConfig extends SpringRouteBuilder {

    @Value("${camel.netty.port}")
    private int port;
    @Value("${camel.netty.host}")
    private String host;


    @Override
    public void configure() throws Exception {

        // rest configuration
        restConfiguration()
                .bindingMode(RestBindingMode.json)
                .enableCORS(true)
                .host(host)
                .dataFormatProperty("prettyPrint", "true")
                .component("netty4-http")
                .port(port)

        ;






    }

}
