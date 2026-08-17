package order_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import order_service.dto.UserDto;

@Service
public class UserClient {

    private final RestClient restClient;

    public UserClient(RestClient userRestClient) {
        this.restClient = userRestClient;
    }

    @CircuitBreaker(
            name = "userService",
            fallbackMethod = "userServiceFallback"
    )
    public UserDto getUser(Long userId) {

        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            try {

                System.out.println("Attempt " + attempt);

                return restClient.get()
                        .uri("/api/users/{id}", userId)
                        .retrieve()
                        .body(UserDto.class);

            } catch (Exception e) {

                System.out.println(
                        "Attempt " + attempt + " failed"
                );

                if (attempt == maxAttempts) {

                    throw new ResponseStatusException(
                            HttpStatus.SERVICE_UNAVAILABLE,
                            "User Service is unavailable after retries"
                    );
                }
            }
        }

        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "User Service is unavailable"
        );
    }

    public UserDto userServiceFallback(
            Long userId,
            Throwable throwable) {

        System.out.println(
                "Circuit Breaker fallback triggered"
        );

        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "User Service is temporarily unavailable"
        );
    }
}