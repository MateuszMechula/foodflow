package pl.foodflow.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "itemImageId")
@ToString(of = {"itemImageId", "imageUrl"})
public class ItemImage {

    Long itemImageId;
    String imageUrl;
    CategoryItem categoryItem;
}
