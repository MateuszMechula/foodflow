package pl.foodflow.domain;


import lombok.*;
import pl.foodflow.infrastructure.database.entity.MenuItemEntity;

@With
@Value
@Builder
@EqualsAndHashCode(of = "itemImageId")
@ToString(of = {"itemImageId", "imageUrl"})
public class ItemImage {

    Long itemImageId;
    String imageUrl;
    MenuItemEntity menuItem;
}
