package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class GitHubRestClientConfig {

    @Bean
    public RestClient restClient() {
        final String githubToken = System.getenv("GITHUB_TOKEN");

        return RestClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeaders(headers -> headers.setBearerAuth(githubToken))
                .build();
    }
}
