package be.bittich.website.event.personal;

import org.apache.camel.spring.SpringRouteBuilder;
import static be.bittich.website.util.RouterConstants.*;

import javax.inject.Named;

@Named
public class AboutMeEventConsumer extends SpringRouteBuilder {

    public static final String ABOUTME_DOMAIN = "AboutMe";
    public static final String ABOUTME_DEAD_LETTER_QUEUE = "jms:queue:AboutMe.DLQ";
    public static final String ABOUT_ME_ADD_ACTION = String.format("jms:queue:%s.%s", ABOUTME_DOMAIN,ADD_ACTION);
    public static final String ABOUT_ME_EDIT_ACTION = String.format("jms:queue:%s.%s",ABOUTME_DOMAIN, EDIT_ACTION);
    public static final String ABOUT_ME_DELETE_ACTION = String.format("jms:queue:%s.%s",ABOUTME_DOMAIN, DELETE_ACTION);

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
                .log(" An exception occured. Exception:\n${exceptionMessage}\nHeaders:${headers}\nPayload: ${body}")
                .inOnly(ABOUTME_DEAD_LETTER_QUEUE)
        ;

        from("jms:topic:AboutMe")
                .routeId("AboutMeEventConsumer.CBR")
                .filter(header(HEADER_DOMAIN).isEqualTo(constant(ABOUTME_DOMAIN)))
                    .toD(String.format("jms:queue:${headers.%s}.${headers.%s}", HEADER_DOMAIN, HEADER_ACTION),true)
                .end()

        ;



        from(ABOUT_ME_ADD_ACTION)
            .routeId("AboutMeEventConsumer.AddAction")
            .to("bean:aboutMeRepository?method=save")
        ;

        from(ABOUT_ME_EDIT_ACTION)
                .routeId("AboutMeEventConsumer.Edit")
                .to("bean:aboutMeRepository?method=save")

        ;

        from(ABOUT_ME_DELETE_ACTION)
                .routeId("AboutMeEventConsumer.Delete")
                .setBody(simple(HEADER_ID, Long.class))
                .to("bean:aboutMeRepository?method=findOne")
                .choice()
                    .when(body().isNotNull())
                        .to("bean:aboutMeRepository?method=delete")
                    .otherwise()
                        .throwException(new RuntimeException("Id doesn't exist!"))
                .endChoice()
        ;


    }
}
