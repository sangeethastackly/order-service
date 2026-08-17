package order_service.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import order_service.dto.OrderDto;

@Service
public class OrderService {

    private final Map<Long, OrderDto> orders = Map.of(
            101L, new OrderDto(101L, 1L),
            102L, new OrderDto(102L, 2L)
    );

    public OrderDto getOrderById(Long id) {
        return orders.get(id);
    }
}