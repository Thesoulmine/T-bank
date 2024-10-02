package ru.tbank.restful.service;

import org.springframework.stereotype.Service;
import ru.tbank.restful.entity.Category;
import ru.tbank.restful.repository.Repository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final Repository<Category> categoryRepository;

    public CategoryServiceImpl(Repository<Category> categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryBy(Long id) {
        return categoryRepository.find(id).orElseThrow();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        return categoryRepository.update(id, category);
    }

    @Override
    public Category deleteCategoryBy(Long id) {
        return categoryRepository.delete(id);
    }
}
