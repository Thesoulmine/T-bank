package ru.tbank.restful.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tbank.restful.entity.Category;
import ru.tbank.restful.repository.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private Repository<Category> categoryRepository;

    @Test
    public void getAllCategories_ReturnAllCategories() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("qwe");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("asd");

        Category resultCategory1 = new Category();
        resultCategory1.setId(1L);
        resultCategory1.setName("qwe");

        Category resultCategory2 = new Category();
        resultCategory2.setId(2L);
        resultCategory2.setName("asd");

        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        List<Category> result = categoryService.getAllCategories();

        verify(categoryRepository).findAll();
        assertEquals(List.of(resultCategory1, resultCategory2), result);
    }

    @Test
    public void getCategoryBy_ReturnCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("qwe");

        Category resultCategory = new Category();
        resultCategory.setId(1L);
        resultCategory.setName("qwe");

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(categoryRepository.find(eq(category.getId()))).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryBy(category.getId());

        verify(categoryRepository).find(idCaptor.capture());
        assertEquals(category.getId(), idCaptor.getValue());
        assertEquals(resultCategory, result);
    }

    @Test
    public void getCategoryBy_ThrowsNoSuchElementException_IfElementNotExist() {
        Long id = 1L;

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(categoryRepository.find(eq(id))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> categoryService.getCategoryBy(1L));
        verify(categoryRepository).find(idCaptor.capture());
        assertEquals(id, idCaptor.getValue());
    }

    @Test
    public void saveCategory_SaveCategoryAndReturnSavedCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("qwe");

        Category resultCategory = new Category();
        resultCategory.setId(1L);
        resultCategory.setName("qwe");

        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.saveCategory(category);

        verify(categoryRepository).save(categoryCaptor.capture());
        assertEquals(category, categoryCaptor.getValue());
        assertEquals(resultCategory, result);
    }

    @Test
    public void updateCategory_UpdateCategoryAndReturnUpdatedCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("qwe");

        Category resultCategory = new Category();
        resultCategory.setId(1L);
        resultCategory.setName("qwe");

        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(categoryRepository.update(eq(category.getId()), any(Category.class))).thenReturn(category);

        Category result = categoryService.updateCategory(category.getId(), category);

        verify(categoryRepository).update(idCaptor.capture(), categoryCaptor.capture());
        assertEquals(category, categoryCaptor.getValue());
        assertEquals(category.getId(), idCaptor.getValue());
        assertEquals(resultCategory, result);
    }

    @Test
    public void updateCategory_ThrowsNoSuchElementException_IfElementNotExist() {
        Category category = new Category();
        category.setId(1L);
        category.setName("qwe");

        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(categoryRepository.update(eq(category.getId()), any(Category.class))).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> categoryService.updateCategory(category.getId(), category));
        verify(categoryRepository).update(idCaptor.capture(), categoryCaptor.capture());
        assertEquals(category.getId(), idCaptor.getValue());
        assertEquals(category, categoryCaptor.getValue());
    }

    @Test
    public void deleteCategoryBy_DeleteCategoryAndReturnDeletedCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("qwe");

        Category resultCategory = new Category();
        resultCategory.setId(1L);
        resultCategory.setName("qwe");

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(categoryRepository.delete(eq(category.getId()))).thenReturn(category);

        Category result = categoryService.deleteCategoryBy(category.getId());

        verify(categoryRepository).delete(idCaptor.capture());
        assertEquals(category.getId(), idCaptor.getValue());
        assertEquals(resultCategory, result);
    }

    @Test
    public void deleteCategoryBy_ReturnNull_IfElementNotExist() {
        Long id = 1L;

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(categoryRepository.delete(eq(id))).thenReturn(null);

        Category result = categoryService.deleteCategoryBy(id);

        verify(categoryRepository).delete(idCaptor.capture());
        assertEquals(id, idCaptor.getValue());
        assertNull(result);
    }
}