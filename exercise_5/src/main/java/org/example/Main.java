package org.example;

import org.example.repositories.InMemoryPostRepository;
import org.example.repositories.PostRepository;
import org.example.service.PostService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class Main extends ResourceConfig  {

    public Main() {

        // Validation
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // Define implementations
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(InMemoryPostRepository.class).to(PostRepository.class).in(Singleton.class);
                bind(PostService.class).to(PostService.class).in(Singleton.class);
            }
        };
        register(binder);

        // Load resources
        packages("org.example.entities");
        packages("org.example.resources");
    }

}