package be.bittich.website.event;

import org.apache.camel.spring.SpringRouteBuilder;
import static be.bittich.website.util.RouterConstants.*;

import javax.inject.Named;
import static be.bittich.website.util.RouterConstants.*;

/**
 * Created by Nordine on 07-11-15.
 */

@Named
public class EventDispatcher extends SpringRouteBuilder {


    @Override
    public void configure() throws Exception {

        from(DISPATCHER_ENDPOINT)
                .id("EventDispatcher.dispatch")
                .toD(String.format("jms:topic:${headers.%s}", HEADER_DOMAIN), true) //ignore invalid endpoint
                ;

    }
}
