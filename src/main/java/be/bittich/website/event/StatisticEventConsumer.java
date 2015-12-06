package be.bittich.website.event;

import org.apache.camel.spring.SpringRouteBuilder;

import javax.inject.Named;

import static be.bittich.website.util.RouterConstants.*;
import static java.lang.String.format;
import static org.apache.camel.Exchange.HTTP_METHOD;

/**
 * Created by Nordine on 07-11-15.
 */
@Named
public class StatisticEventConsumer extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {

        from(DISPATCHER_ENDPOINT)
                .routeId("StatisticEventConsumer.statistics")
                .log(format("\nIp:${headers.%s}\nDomain:${headers.%s}\nAction:${headers.%s}\nMethod:${headers.%s}\nPayload:%s",
                        HEADER_IP_ADDRESS,
                        HEADER_DOMAIN,
                        HEADER_ACTION,
                        HTTP_METHOD,
                        CAMEL_BODY
                ))
        ;
    }

}
