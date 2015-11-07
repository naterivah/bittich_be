package be.bittich.website.router.personal;

import be.bittich.website.domain.personal.AboutMe;
import org.apache.camel.spring.SpringRouteBuilder;

import javax.inject.Named;

import static be.bittich.website.util.RouterConstants.*;


/**
 * Created by Nordine on 18-10-15.
 */
@Named
public class AboutMeRouter extends SpringRouteBuilder {

    public static final String DOMAIN = "AboutMe";


    @Override
    public void configure() throws Exception {

        rest("/aboutme")
                .description("AboutMe Api")
                .produces(MEDIA_TYPE)

                .get()
                    .id("AboutMeRouter.Home")
                    .route()
                        .setHeader(HEADER_DOMAIN, constant(DOMAIN))
                        .setHeader(HEADER_IP_ADDRESS, simple(NETTY_HEADER, String.class))
                        .setHeader(HEADER_ACTION, constant(LIST_ACTION))
                        .inOnly(DISPATCHER_ENDPOINT)
                        .to("bean:aboutMeRepository?method=findAll()")
                .endRest()

                .get("/{id}")
                    .id("AboutMeRouter.GetById")
                    .route()
                        .setHeader(HEADER_DOMAIN, constant(DOMAIN))
                        .setHeader(HEADER_IP_ADDRESS, simple(NETTY_HEADER, String.class))
                        .setHeader(HEADER_ACTION, constant(GET_BY_ID_ACTION))
                        .inOnly(DISPATCHER_ENDPOINT)
                        .setBody(simple(HEADER_ID, Long.class))
                        .to("bean:aboutMeRepository?method=findOne")
                        .choice()
                            .when(body().isNull())
                            .to(NOT_FOUND)
                        .endChoice()
                .endRest()

                .put("/add")
                .id("AboutMeRouter.Add")
                .type(AboutMe.class)
                .route()
                    .setHeader(HEADER_DOMAIN, constant(DOMAIN))
                    .setHeader(HEADER_IP_ADDRESS, simple(NETTY_HEADER, String.class))
                    .setHeader(HEADER_ACTION, constant(ADD_ACTION))
                    .inOnly(DISPATCHER_ENDPOINT)
                    .to(ACKNOWLEDGMENT_OK)
                .endRest()

                .post("/edit")
                .id("AboutMeRouter.Edit")
                .type(AboutMe.class)
                .route()
                    .filter().simple("${body.id} == null").to(NOT_FOUND).end()
                    .setHeader(HEADER_DOMAIN, constant(DOMAIN))
                    .setHeader(HEADER_IP_ADDRESS, simple(NETTY_HEADER, String.class))
                    .setHeader(HEADER_ACTION, constant(EDIT_ACTION))
                    .inOnly(DISPATCHER_ENDPOINT)
                    .to(ACKNOWLEDGMENT_OK)
                .endRest()

                .delete("/delete/{id}")
                .id("AboutMeRouter.Delete")
                .route()
                    .filter().simple("${headers.id} == null").to(NOT_FOUND).end()
                    .setHeader(HEADER_DOMAIN, constant(DOMAIN))
                     .setHeader(HEADER_IP_ADDRESS, simple(NETTY_HEADER, String.class))
                    .setHeader(HEADER_ACTION, constant(DELETE_ACTION))
                    .inOnly(DISPATCHER_ENDPOINT)
                    .to(ACKNOWLEDGMENT_OK)
                .endRest()
        ;
    }
}
