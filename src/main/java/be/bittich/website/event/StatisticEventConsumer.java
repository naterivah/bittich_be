package be.bittich.website.event;

import org.apache.camel.Exchange;
import org.apache.camel.spring.SpringRouteBuilder;

import javax.inject.Named;
import static be.bittich.website.util.RouterConstants.*;
import static org.apache.camel.Exchange.*;

/**
 * Created by Nordine on 07-11-15.
 */
@Named
public class StatisticEventConsumer extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {

        from(DISPATCHER_ENDPOINT)
                .id("StatisticEventConsumer.statistics")
                .log(String.format("\nDomain:${headers.%s}\nAction:${headers.%s}\nIpAddress:${headers.%s}\nMethod:${headers.%s}",
                        HEADER_DOMAIN,
                        HEADER_ACTION,
                        HEADER_IP_ADDRESS,
                        HTTP_METHOD
                ))
        ;
    }

}
