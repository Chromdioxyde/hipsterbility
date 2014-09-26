package de.hsosnabrueck.hipsterbility.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by Albert on 15.09.2014.
 */
@javax.ws.rs.ApplicationPath("resources")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
//        register(new ApplicationBinder());

        register(JacksonFeature.class); // Enable Jackson instead of Moxy
        register(RolesAllowedDynamicFeature.class); // Use security annotations in JAX-RS
        packages(true, "de.hsosnabrueck.hipsterbility.rest.api"); // Register REST resources
    }
}
