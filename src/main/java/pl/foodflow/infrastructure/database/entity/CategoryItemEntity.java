package pl.foodflow.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "categoryItemId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category_item")
public class CategoryItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_item_id")
    private Long categoryItemId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_category_id")
    private MenuCategoryEntity menuCategory;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "categoryItem")
    private ItemImageEntity itemImage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryItem")
    private Set<OrderItemEntity> orderItems;

}
