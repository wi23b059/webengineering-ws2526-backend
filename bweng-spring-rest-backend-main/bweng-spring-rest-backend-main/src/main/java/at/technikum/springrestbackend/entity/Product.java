package at.technikum.springrestbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entity representing a product in the system.
 * A product contains basic descriptive information, pricing details, and belongs to a single {@link Category}.
 * It is stored in the {@code products} table and references its category through a foreign key.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 90)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "rating", nullable = true, length = 120)
    private String rating;

    @Column(name = "image_path", nullable = true, length = 120)
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
