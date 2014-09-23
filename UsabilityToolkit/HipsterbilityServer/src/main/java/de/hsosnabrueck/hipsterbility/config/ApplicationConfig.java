package de.hsosnabrueck.hipsterbility.config;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by Albert on 15.09.2014.
 */
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
//        register(new ApplicationBinder());
        packages(true, "de.hsosnabrueck.hipsterbility.rest.api");
    }
}
