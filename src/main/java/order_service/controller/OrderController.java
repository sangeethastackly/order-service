package order_service.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

import order_service.dto.OrderDto;
import order_service.dto.UserDto;
import order_service.service.OrderService;
import order_service.service.UserClient;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserClient userClient;

    public OrderController(OrderService orderService,
                           UserClient userClient) {
        this.orderService = orderService;
        this.userClient = userClient;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getOrder(@PathVariable Long id) {

        OrderDto order = orderService.getOrderById(id);

        UserDto user = userClient.getUser(order.getUserId());

        return Map.of(
                "orderId", order.getOrderId(),
                "userId", user.getId(),
                "userName", user.getName(),
                "userEmail", user.getEmail()
        );
    }
}