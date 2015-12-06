package be.bittich.website.router;

import be.bittich.website.util.RestMessage;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;

import javax.inject.Named;

import static be.bittich.website.util.RouterConstants.ACKNOWLEDGMENT_OK;
import static be.bittich.website.util.RouterConstants.AUTHORIZATION_CHECK;
import static be.bittich.website.util.RouterConstants.NOT_FOUND;

/**
 * Created by Nordine on 20-10-15.
 */
@Named
public class GlobalRouter extends SpringRouteBuilder {


    @Override
    public void configure() throws Exception {

        from(ACKNOWLEDGMENT_OK)
                .routeId("Ack.OK")
                .description("sends an ack ok as a response for asynchronous command")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(202))
                .transform(constant(RestMessage.builder().message("Will be processed soon.").build()))

        ;
        from(NOT_FOUND)
                .routeId("Ack.NotFound")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .transform(constant(RestMessage.builder().message("Resource Not Found.").build()))
                .marshal().json(JsonLibrary.Jackson)
        ;

        from(AUTHORIZATION_CHECK)
                .routeId("Authorization.Check")
                .log("Hello")
        ;

    }
}
