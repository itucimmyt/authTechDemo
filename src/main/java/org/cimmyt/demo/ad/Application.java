package org.cimmyt.demo.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Harness for bootstrapping the web application and declare MVC mappings.
 * @author cimmyt-siu
 * @since 1.0.0
 */
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter{

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Mappings for URL requests. Relies on naming conventions to avoid the need of
     * {@link Controller Controller} beans. 
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
        	.setViewName("home");
        registry.addViewController("/home");
        registry.addViewController("/secured");
        registry.addViewController("/login");
    }
}
