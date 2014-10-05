package de.hsosnabrueck.hipsterbility.config;

import de.hsosnabrueck.hipsterbility.rest.api.DeviceResource;
import de.hsosnabrueck.hipsterbility.rest.api.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Albert on 15.09.2014.
 */
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
//        register(new ApplicationBinder());

        register(JacksonFeature.class); // Enable Jackson instead of Moxy
        packages(true, "de.hsosnabrueck.hipsterbility.rest.api"); // Register REST resources
        register(RolesAllowedDynamicFeature.class); // Use security annotations in JAX-RS
    }

}
