package ru.tbank.restful.service;

import org.springframework.stereotype.Service;
import ru.tbank.restful.entity.Category;
import ru.tbank.restful.enums.RepositoryActionType;
import ru.tbank.restful.listener.repository.RepositorySaveEventListener;
import ru.tbank.restful.publisher.RepositorySaveEventPublisher;
import ru.tbank.restful.repository.Repository;
import ru.tbank.restful.snapshot.CategorySnapshot;
import ru.tbank.restful.snapshot.ChangesHistory;

import java.util.*;

@Service
public class CategoryServiceImpl implements
        CategoryService, RepositorySaveEventPublisher<Category>, ChangesHistory<CategorySnapshot> {

    private final Repository<Category> categoryRepository;
    private final Set<RepositorySaveEventListener<Category>> listeners = new HashSet<>();
    private final Deque<CategorySnapshot> categoryHistory = new ArrayDeque<>();

    public CategoryServiceImpl(Repository<Category> categoryRepository) {
        this.categoryRepository = categoryRepository;
        addListener(categoryRepository);
    }

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
        Category savedCategory = saveEntity(category);

        addToHistory(new CategorySnapshot(
                savedCategory.getId(),
                savedCategory.getKudaGoId(),
                savedCategory.getSlug(),
                savedCategory.getName(),
                RepositoryActionType.SAVE));

        return savedCategory;
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category originCategory = getCategoryBy(id);

        addToHistory(new CategorySnapshot(
                originCategory.getId(),
                originCategory.getKudaGoId(),
                originCategory.getSlug(),
                originCategory.getName(),
                RepositoryActionType.UPDATE));

        return categoryRepository.update(id, category);
    }

    @Override
    public Category deleteCategoryBy(Long id) {
        Category deletedCategory = categoryRepository.delete(id);

        addToHistory(new CategorySnapshot(
                deletedCategory.getId(),
                deletedCategory.getKudaGoId(),
                deletedCategory.getSlug(),
                deletedCategory.getName(),
                RepositoryActionType.DELETE));

        return deletedCategory;
    }

    @Override
    public void addToHistory(CategorySnapshot snapshot) {
        categoryHistory.addFirst(snapshot);
    }

    @Override
    public void undoHistory() {
        CategorySnapshot snapshot = categoryHistory.getFirst();

        switch (snapshot.getActionType()) {
            case SAVE -> {
                deleteCategoryBy(snapshot.getId());
            }
            case UPDATE -> {
                Category category = new Category();
                category.setId(snapshot.getId());
                category.setSlug(snapshot.getSlug());
                category.setName(snapshot.getName());
                categoryRepository.update(category.getId(), category);
            }
            case DELETE -> {
                Category category = new Category();
                category.setId(snapshot.getId());
                category.setSlug(snapshot.getSlug());
                category.setName(snapshot.getName());
                saveCategory(category);
            }
        }
    }
}
