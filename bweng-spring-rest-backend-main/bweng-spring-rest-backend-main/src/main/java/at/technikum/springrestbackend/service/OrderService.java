package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.OrderRequestDto;
import at.technikum.springrestbackend.dto.OrderResponseDto;
import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.exception.OrderNotFoundException;
import at.technikum.springrestbackend.mapper.OrderMapper;
import at.technikum.springrestbackend.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toResponseDto)
                .toList();
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public OrderResponseDto getOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return OrderMapper.toResponseDto(order);
    }

    @PreAuthorize("#dto.userId == authentication.principal.id or hasRole('ADMIN')")
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Order order = OrderMapper.toEntity(dto);
        Order saved = orderRepository.save(order);
        return OrderMapper.toResponseDto(saved);
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @Transactional
    public OrderResponseDto updateOrder(Integer id, OrderRequestDto dto) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        existing.setTotalPrice(dto.getTotalPrice());
        existing.setPaymentMethod(dto.getPaymentMethod());
        Order updated = orderRepository.save(existing);
        return OrderMapper.toResponseDto(updated);
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @Transactional
    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
    }
}
