package pl.foodflow.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.foodflow.business.ItemImageService;

@Controller
@AllArgsConstructor
public class ImageController {

    private final ItemImageService itemImageService;

//    @GetMapping("/upload-form")
//    public String showUploadForm(Model model) {
//        model.addAttribute("categoryItems", categoryItemRepository.findAll()); // Załadowanie dostępnych kategorii
//        return "upload-form"; // Thymeleaf template
//    }
//
//    @PostMapping("/upload")
//    public String uploadImage(@RequestParam Long categoryId,
//                              @RequestParam("image") MultipartFile imageFile) {
//        try {
//            itemImageService.uploadImage(categoryId, imageFile);
//            return "redirect:/images/upload-form?success"; // Przekierowanie po pomyślnym uploadzie
//        } catch (IOException e) {
//            return "redirect:/images/upload-form?error=" + e.getMessage(); // Przekierowanie w razie błędu
//        }
//    }
}
