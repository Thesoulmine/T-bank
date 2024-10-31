package ru.tbank.restful.service;

import org.springframework.stereotype.Service;
import ru.tbank.restful.entity.Category;
import ru.tbank.restful.listener.repository.RepositorySaveEventListener;
import ru.tbank.restful.publisher.RepositorySaveEventPublisher;
import ru.tbank.restful.repository.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService, RepositorySaveEventPublisher<Category> {

    private final Repository<Category> categoryRepository;
    private final Set<RepositorySaveEventListener<Category>> listeners = new HashSet<>();

    @Override
    public void addListener(RepositorySaveEventListener<Category> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(RepositorySaveEventListener<Category> listener) {
        listeners.remove(listener);
    }

    @Override
    public Category saveEntity(Category entity) {
        Category category = entity;

        for (RepositorySaveEventListener<Category> listener : listeners) {
            category = listener.save(entity);
        }

        return category;
    }

    public CategoryServiceImpl(Repository<Category> categoryRepository) {
        this.categoryRepository = categoryRepository;
        addListener(categoryRepository);
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
        return saveEntity(category);
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
