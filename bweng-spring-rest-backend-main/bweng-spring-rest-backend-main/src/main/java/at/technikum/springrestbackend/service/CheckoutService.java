package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.*;
import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.entity.OrderItem;
import at.technikum.springrestbackend.mapper.OrderItemMapper;
import at.technikum.springrestbackend.mapper.OrderMapper;
import at.technikum.springrestbackend.repository.OrderItemRepository;
import at.technikum.springrestbackend.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @PreAuthorize("#dto.order.userId == authentication.principal.id")
    @Transactional
    public OrderResponseDto checkout(CheckoutRequestDto dto) {

        // 🔒 1. Gesamtpreis serverseitig berechnen
        BigDecimal calculatedTotal = dto.getItems().stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (calculatedTotal.compareTo(dto.getOrder().getTotalPrice()) != 0) {
            throw new IllegalArgumentException("Total price mismatch");
        }

        // 🧾 2. Order speichern
        Order order = OrderMapper.toEntity(dto.getOrder());
        Order savedOrder = orderRepository.save(order);

        // 📦 3. OrderItems speichern
        for (OrderItemRequestDto itemDto : dto.getItems()) {
            OrderItem item = OrderItemMapper.toEntity(savedOrder.getId(), itemDto);
            orderItemRepository.save(item);
        }

        return OrderMapper.toResponseDto(savedOrder);
    }
}
