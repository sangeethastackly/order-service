package order_service.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import order_service.dto.UserDto;

@Service
public class UserClient {

    private final RestClient restClient;

    public UserClient(RestClient userRestClient) {
        this.restClient = userRestClient;
    }

    @Retry(name = "userService")
    @CircuitBreaker(
            name = "userService",
            fallbackMethod = "userServiceFallback"
    )
    @TimeLimiter(name = "userService")
    public CompletableFuture<UserDto> getUser(Long userId) {

        System.out.println("Calling User Service...");

        return CompletableFuture.supplyAsync(() ->
                restClient.get()
                        .uri("/api/users/{id}", userId)
                        .retrieve()
                        .body(UserDto.class)
        );
    }

    public CompletableFuture<UserDto> userServiceFallback(
            Long userId,
            Throwable throwable) {

        System.out.println("Fallback triggered because User Service is unavailable");

        return CompletableFuture.failedFuture(
                new ResponseStatusException(
                        HttpStatus.SERVICE_UNAVAILABLE,
                        "User Service is temporarily unavailable"
                )
        );
    }
}