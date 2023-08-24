package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.ItemImageDAO;
import pl.foodflow.domain.ItemImage;
import pl.foodflow.infrastructure.database.entity.ItemImageEntity;
import pl.foodflow.infrastructure.database.repository.jpa.ItemImageJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.ItemImageEntityMapper;

@Repository
@AllArgsConstructor
public class ItemImageRepository implements ItemImageDAO {

    private final ItemImageJpaRepository itemImageJpaRepository;
    private final ItemImageEntityMapper itemImageEntityMapper;


    @Override
    public ItemImage save(ItemImage itemImage) {
        ItemImageEntity toSave = itemImageEntityMapper.mapToEntity(itemImage);
        ItemImageEntity saved = itemImageJpaRepository.saveAndFlush(toSave);

        return itemImageEntityMapper.mapFromEntity(saved);
    }
}
