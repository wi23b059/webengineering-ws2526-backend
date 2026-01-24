package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.*;
import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.entity.OrderItem;
import at.technikum.springrestbackend.mapper.OrderItemMapper;
import at.technikum.springrestbackend.mapper.OrderMapper;
import at.technikum.springrestbackend.repository.OrderItemRepository;
import at.technikum.springrestbackend.repository.OrderRepository;
import at.technikum.springrestbackend.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private  final CartService cartService;

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public OrderResponseDto checkout(CheckoutRequestDto dto) {

        // 1️⃣ Gesamtpreis serverseitig berechnen
        BigDecimal calculatedTotal = dto.getItems().stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (calculatedTotal.compareTo(dto.getOrder().getTotalPrice()) != 0) {
            throw new IllegalArgumentException("Total price mismatch");
        }

        // 2️⃣ Prepare Order
        Order order = OrderMapper.toEntity(dto.getOrder());

        // 🔹 Standard payment, if empty
        if (order.getPaymentMethod() == null) {
            order.setPaymentMethod("INVOICE");
        }

        // 🔹 Generate Rechnungsnummer
        order.setInvoiceNumber(generateInvoiceNumber());

        // 3️⃣ Save Order & OrderItems
        List<OrderItem> items = dto.getItems().stream()
                .map(i -> OrderItemMapper.toEntity(order, i))
                .toList();

        order.setOrderItems(items);

        Order savedOrder = orderRepository.save(order);

        // ✅ Clear DB cart
        cartService.clearCart(savedOrder.getUserId());

        return OrderMapper.toResponseDto(savedOrder);
    }

    // Help method
    private String generateInvoiceNumber() {
        // Example: "INV" + year + 6-digit random number
        int random = (int) (Math.random() * 900000) + 100000;
        return "INV" + java.time.Year.now().getValue() + random;
    }
}
