package ru.tbank.restful.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.tbank.restful.annotation.Timed;
import ru.tbank.restful.dto.CategoryRequestDTO;
import ru.tbank.restful.dto.CategoryResponseDTO;
import ru.tbank.restful.dto.ExceptionMessageResponseDTO;
import ru.tbank.restful.mapper.CategoryMapper;
import ru.tbank.restful.service.CategoryService;

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

    @GetMapping("")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return new ResponseEntity<>(
                categoryMapper.toResponseDTO(categoryService.getAllCategories()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable Long id) {
        return new ResponseEntity<>(
                categoryMapper.toResponseDTO(categoryService.getCategoryBy(id)),
                HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return new ResponseEntity<>(
                categoryMapper.toResponseDTO(
                        categoryService.saveCategory(categoryMapper.toEntity(categoryRequestDTO))),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return new ResponseEntity<>(
                categoryMapper.toResponseDTO(
                        categoryService.updateCategory(id, categoryMapper.toEntity(categoryRequestDTO))),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable Long id) {
        return new ResponseEntity<>(
                categoryMapper.toResponseDTO(categoryService.deleteCategoryBy(id)),
                HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionMessageResponseDTO> handleNoSuchElementException() {
        return new ResponseEntity<>(
                new ExceptionMessageResponseDTO("Category not found"),
                HttpStatus.NOT_FOUND);
    }
}
