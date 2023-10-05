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
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.security.user.UserService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = OwnerMenuRestController.MENUS)
public class OwnerMenuRestController {
    public static final String MENUS = "/owner/api/v1/menus";
    public static final String MENU_ID = "/{menuId}";
    private final MenuMapper menuMapper;
    private final MenuService menuService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> addMenu(
            @Valid @RequestBody MenuDTO menuDTO
    ) {
        Owner owner = userService.getCurrentOwner();
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
