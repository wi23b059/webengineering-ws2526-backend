package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.OrderItemRequestDto;
import at.technikum.springrestbackend.dto.OrderItemResponseDto;
import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.entity.OrderItem;
import at.technikum.springrestbackend.exception.OrderItemNotFoundException;
import at.technikum.springrestbackend.mapper.OrderItemMapper;
import at.technikum.springrestbackend.repository.OrderItemRepository;
import at.technikum.springrestbackend.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderItemResponseDto> getAllOrderItems(Integer orderId) {
        return orderItemRepository.findByOrder_Id(orderId)
                .stream()
                .map(OrderItemMapper::toResponseDto)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public OrderItemResponseDto getOrderItem(Integer orderId, Integer itemId) {
        OrderItem orderItem = orderItemRepository
                .findByOrder_IdAndId(orderId, itemId)
                .orElseThrow(() -> new OrderItemNotFoundException(itemId));
        return OrderItemMapper.toResponseDto(orderItem);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public OrderItemResponseDto addOrderItem(Integer orderId, OrderItemRequestDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        OrderItem orderItem = OrderItemMapper.toEntity(order, dto);
        OrderItem saved = orderItemRepository.save(orderItem);
        return OrderItemMapper.toResponseDto(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public OrderItemResponseDto updateOrderItem(Integer orderId, Integer itemId, OrderItemRequestDto dto) {
        OrderItem existing = orderItemRepository.findByOrder_IdAndId(orderId, itemId)
                .orElseThrow(() -> new OrderItemNotFoundException(itemId));

        existing.setProductId(dto.getProductId());
        existing.setQuantity(dto.getQuantity());
        existing.setPrice(dto.getPrice());

        OrderItem updated = orderItemRepository.save(existing);
        return OrderItemMapper.toResponseDto(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteOrderItem(Integer orderId, Integer itemId) {
        orderItemRepository.deleteByOrder_IdAndId(orderId, itemId);
    }
}
