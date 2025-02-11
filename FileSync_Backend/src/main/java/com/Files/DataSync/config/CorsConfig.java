package com.Files.DataSync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    /**
     * Bean to define the CORS configuration for the application.
     * @return a CorsConfigurationSource to handle CORS requests
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        // Create a new CorsConfiguration instance to hold CORS settings
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Define allowed origins for CORS requests (e.g., React app running on localhost:3000)
//        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://192.168.1.9:3000"));


        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000","chrome-extension://ccaljandkoegdhicagpaccikgnadedcb"));

        // Allow specific HTTP methods for CORS requests
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

        // Allow all headers to be sent in CORS requests
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

        // Allow credentials (cookies, authorization headers, etc.) in CORS requests
        corsConfiguration.setAllowCredentials(true);

        // Expose specific headers to the frontend
        corsConfiguration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type","Content-Disposition"));

        // Create a source and register the configuration for all paths
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        // Return the configured CORS source
        return source;
    }
}
