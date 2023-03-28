package vojtech.kafkaconsumer.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import vojtech.kafkaconsumer.api.Controller;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(){
        register(Controller.class);
    }
}
