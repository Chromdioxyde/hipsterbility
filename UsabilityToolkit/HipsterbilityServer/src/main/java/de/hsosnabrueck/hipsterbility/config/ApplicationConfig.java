package de.hsosnabrueck.hipsterbility.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by Albert on 15.09.2014.
 */
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
//        register(new ApplicationBinder());

        register(JacksonFeature.class); // Enable Jackson instead of Moxy
        packages(true, "de.hsosnabrueck.hipsterbility.rest.api"); // Register REST resources
        register(RolesAllowedDynamicFeature.class); // Use security annotations in JAX-RS
//        register(MultiPartFeature.class); // Multipart feature for file upload

    }

}
