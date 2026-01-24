package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.CheckoutRequestDto;
import at.technikum.springrestbackend.dto.OrderResponseDto;
import at.technikum.springrestbackend.service.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/checkout")
    public OrderResponseDto checkout(@Valid @RequestBody CheckoutRequestDto dto) {
        return checkoutService.checkout(dto);
    }
}
