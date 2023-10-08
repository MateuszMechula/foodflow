package pl.foodflow.api.controller.rest.owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.foodflow.api.dto.MenuDTO;
import pl.foodflow.api.dto.mapper.MenuMapper;
import pl.foodflow.business.MenuService;
import pl.foodflow.business.OwnerService;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.Owner;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = OwnerMenuRestController.MENUS)
public class OwnerMenuRestController {
    public static final String MENUS = "/api/v1/owner/menus";
    public static final String MENU_ID = "/{menuId}";
    public static final String OWNER_ID = "/{ownerId}";
    private final MenuMapper menuMapper;
    private final MenuService menuService;
    private final OwnerService ownerService;

    @PostMapping(value = OWNER_ID)
    public ResponseEntity<Void> addMenu(
            @Valid @RequestBody MenuDTO menuDTO,
            @PathVariable Long ownerId
    ) {
        Owner owner = ownerService.findOwnerById(ownerId);
        Menu menu = menuMapper.map(menuDTO);
        menuService.createMenuForRestaurant(owner, menu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = MENU_ID)
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
