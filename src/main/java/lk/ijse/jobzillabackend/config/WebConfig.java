package lk.ijse.jobzillabackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/C:/Users/kesha/Documents/IJSE%20GDES%2069/Advanced-Api-Development/final-project/JobZilla-WebProject-Backend/uploads/");
    }

}


