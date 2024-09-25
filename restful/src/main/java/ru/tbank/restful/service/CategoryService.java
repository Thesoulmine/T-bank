package ru.tbank.restful.service;

import ru.tbank.restful.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryBy(Long id);

    Category saveCategory(Category category);

    Category updateCategory(Long id, Category category);

    Category deleteCategoryBy(Long id);
}
