package order_service.dto;

public class OrderDto {

    private Long orderId;
    private Long userId;

    public OrderDto(Long orderId, Long userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getUserId() {
        return userId;
    }
}