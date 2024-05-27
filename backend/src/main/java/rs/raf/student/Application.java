package rs.raf.student;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

@ApplicationPath("/api/v1")
public class Application extends ResourceConfig {

    public Application() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        register(new InjectionBinder());

        packages("rs.raf.student.resource");
        packages("rs.raf.student.filter");
    }

}
