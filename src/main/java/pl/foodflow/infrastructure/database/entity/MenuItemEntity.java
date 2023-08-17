package pl.foodflow.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "menuItemId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_item")
public class MenuItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id")
    private Long menuItemId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_category_id")
    private MenuCategoryEntity menuCategory;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "menuItem")
    private ItemImageEntity itemImage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menuItem")
    private Set<OrderItemEntity> orderItems;

}
