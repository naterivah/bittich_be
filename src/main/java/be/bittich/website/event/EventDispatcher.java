package be.bittich.website.event;

import org.apache.camel.spring.SpringRouteBuilder;

import javax.inject.Named;

import static be.bittich.website.util.RouterConstants.DISPATCHER_ENDPOINT;
import static be.bittich.website.util.RouterConstants.HEADER_DOMAIN;

/**
 * Created by Nordine on 07-11-15.
 */

@Named
public class EventDispatcher extends SpringRouteBuilder {


    @Override
    public void configure() throws Exception {

        from(DISPATCHER_ENDPOINT)
                .routeId("EventDispatcher.dispatch")
                .toD(String.format("jms:topic:${headers.%s}", HEADER_DOMAIN)) 
                ;

    }
}
