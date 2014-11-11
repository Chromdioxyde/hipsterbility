package de.hsosnabrueck.hipsterbility.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.Serializable;
import java.util.logging.Logger;


/**
 * Created by Albert on 10.11.2014.
 * Provides injectable loggers.
 */
@ApplicationScoped
public class LogProvider implements Serializable {

    @Produces public Logger createLogger(InjectionPoint ip) {
        return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
    }
}