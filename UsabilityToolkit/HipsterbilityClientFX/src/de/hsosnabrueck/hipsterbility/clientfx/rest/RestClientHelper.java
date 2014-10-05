package de.hsosnabrueck.hipsterbility.clientfx.rest;

import de.hsosnabrueck.hipsterbility.clientfx.Settings;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by Albert on 02.10.2014.
 */
@Singleton
public class RestClientHelper {

    @Inject
    Settings settings;

    public static final String APP_PATH = "/hipsterbility/api";
    private Client client;
    private URI baseUri;


//    @PostConstruct
//    public void init(){
//        URI baseUri = UriBuilder.fromUri("http://localhost/hipsterbility/api").port(8080).build();
//        client  = ClientBuilder.newClient();
//
//        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "test123");
//        client.register(feature);
////        client.register(DeviceResource.class);
//        client.register(JacksonFeature.class);
//
//        WebTarget target = client.target(baseUri).path("users");
////        target.getUriBuilder().port(8080);
////        target.path("users");
////        target.path("1");
//        System.out.println(target);
//        Response response = target.request(MediaType.APPLICATION_JSON).get();
//        System.out.println(response.getStatus());
//        Collection<UserEntity> users = response.readEntity(new GenericType<List<UserEntity>>() {});
////        UserEntity users = response.readEntity(UserEntity.class);
//        System.out.println(users.size());
//        for(UserEntity user : users){
//            System.out.println(user.getUsername() + " - " +user.getFirstname() + " " + user.getLastname());
//        }
////                client.target(baseUri)
//////                .register(FilterForExampleCom.class)
////                .path("/users")
//////                .queryParam("greeting", "Hi World!")
////                .request(MediaType.APPLICATION_JSON)
//////                .header("some-header", "true")
////                .get(new GenericType<List<UserEntity>>() {});

//    }

    private void enableSsl(){
                SslConfigurator sslConfig = SslConfigurator.newInstance()
                .trustStoreFile("./truststore_client")
                .trustStorePassword("secret-password-for-truststore")
                .keyStoreFile("./keystore_client")
                .keyPassword("secret-password-for-keystore");
        SSLContext sslContext = sslConfig.createSSLContext();
        client = ClientBuilder.newBuilder().sslContext(sslContext).build();
    }

    private void configure(){

    }

    protected void closeClient(){
        if(null != client) client.close();
        baseUri = null;
        client = null;
    }

    public boolean pingServer() throws Exception{
        client = ClientBuilder.newClient();
        if(settings.getSsl()) enableSsl();
        try {
            // Ping resource with unrestricted access
            Response r = client.target(getBaseUri()).path("ping").request().get();
            System.out.println(r.getStatus());
            return 200 == r.getStatus(); // HTTP OK
        } finally{
            closeClient(); // close this client because we need one with credentials later
        }
    }

    private void addCredetials(){
        if(null == client) return;
        String user = settings.getUser();
        String password = settings.getPassword();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user, password);
        client.register(feature);
    }


    public boolean checkLogin() throws Exception{
        // TODO: SSL protocol prefix
        closeClient();
        client = ClientBuilder.newClient();
        addCredetials();
        if(settings.getSsl()) enableSsl();
        try {
            // Ping resource with unrestricted access
            Response r = client.target(getBaseUri()).path("users").request().head();
            System.out.println(r.getStatus());

            if (200 == r.getStatus()) {
                // if login is successful, the client can remain open
                return true;
            } else {
                closeClient();
                return false;
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            closeClient();
            throw e;
        }
    }

    private URI getBaseUri() {
        if(null != baseUri) return baseUri;
        String server = settings.getServer();
        int port = settings.getPort();
        boolean ssl = settings.getSsl();
        return UriBuilder.fromUri(ssl?"https://":"http://"+server+APP_PATH).port(port).build();
    }

//    public Client getClient() {
//        return client;
//    }

    public WebTarget getTarget(){
       return client.target(getBaseUri());
    }
}
