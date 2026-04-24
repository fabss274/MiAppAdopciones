package adopciones.v01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private static final String API_URL = "https://analisis.datosabiertos.jcyl.es/api/explore/v2.1";
    @Bean
    public RestClient nucleoZooWebClient() {
        return RestClient.builder()
                .baseUrl(API_URL)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
