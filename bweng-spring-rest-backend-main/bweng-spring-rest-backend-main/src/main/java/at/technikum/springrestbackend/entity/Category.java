package at.technikum.springrestbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entity representing a product category.
 * A category groups multiple products under a common label and is stored in the {@code categories} table.
 * It is the parent side of the relationship with {@link Product}.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 90)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
