package ru.tbank.restful.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tbank.restful.dto.CategoryRequestDTO;
import ru.tbank.restful.dto.CategoryResponseDTO;
import ru.tbank.restful.dto.ExceptionMessageResponseDTO;
import ru.tbank.restful.mapper.CategoryMapper;
import ru.tbank.restful.service.CategoryService;
import ru.tbank.timedstarter.annotation.Timed;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Timed
@RequestMapping("/api/v1/places/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService,
                              CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryMapper.toResponseDTO(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getCategory(@PathVariable Long id) {
        return categoryMapper.toResponseDTO(categoryService.getCategoryBy(id));
    }

    @PostMapping
    public CategoryResponseDTO createCategory(
            @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return categoryMapper.toResponseDTO(
                categoryService.saveCategory(categoryMapper.toEntity(categoryRequestDTO)));
    }

    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return categoryMapper.toResponseDTO(
                categoryService.updateCategory(id, categoryMapper.toEntity(categoryRequestDTO)));
    }

    @DeleteMapping("/{id}")
    public CategoryResponseDTO deleteCategory(@PathVariable Long id) {
        return categoryMapper.toResponseDTO(categoryService.deleteCategoryBy(id));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionMessageResponseDTO> handleNoSuchElementException() {
        return new ResponseEntity<>(
                new ExceptionMessageResponseDTO("Category not found"),
                HttpStatus.NOT_FOUND);
    }
}
