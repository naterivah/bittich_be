package be.bittich.website.util;


/**
 * Created by Nordine on 18-10-15.
 */
public final class RouterConstants {



    public static final  String LIST_ACTION = "listAction";
    public static final  String GET_BY_ID_ACTION = "getByIdAction";
    public static final  String ADD_ACTION = "addAction";
    public static final  String EDIT_ACTION = "editAction";
    public static final  String DELETE_ACTION = "deleteAction";

    public static final  String MEDIA_TYPE = "application/json";

    public static final String  CAMEL_BODY = "${body}";
    public static final String  HEADER_ACTION = "CamelHeaderAction";
    public static final String  HEADER_DOMAIN = "CamelHeaderDomain";
    public static final String  HEADER_ID = "${headers.id}";

    public static final String  HEADER_IP_ADDRESS = "CamelHeaderIpAddress";
    public static final String  HEADER_SECURITY_ROLE = "CamelHeaderSecurityRole";
    public static final  String NETTY_HEADER = "${headers.CamelNettyRemoteAddress}";

    public static final String DISPATCHER_ENDPOINT = "jms:topic:Dispatcher";

    public static String ACKNOWLEDGMENT_OK = "direct:ackOk";
    public static String AUTHORIZATION_CHECK = "direct:authorization";
    public static final String NOT_FOUND = "direct:notFound";




}
