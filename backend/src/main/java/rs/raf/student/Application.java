package rs.raf.student;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.LoggerFactory;
import rs.raf.student.exception.TGException;
import rs.raf.student.resource.ResourceRoot;

@ApplicationPath("/api/v1")
public class Application extends ResourceConfig {

    public Application() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        register(new InjectionBinder());

        packages(ResourceRoot.class.getPackageName());

        TGException.setLogger(LoggerFactory.getLogger(Application.class));
    }

}
