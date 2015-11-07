package be.bittich.website.event;

import org.apache.camel.spring.SpringRouteBuilder;
import static be.bittich.website.util.RouterConstants.*;

import javax.inject.Named;

/**
 * Created by Nordine on 07-11-15.
 */

@Named
public class EventDispatcher extends SpringRouteBuilder {


    @Override
    public void configure() throws Exception {

        from(DISPATCHER_ENDPOINT)
                .id("EventDispatcher.dispatch")
                .log("${body}")
                .log("${headers}")
                ;

    }
}
