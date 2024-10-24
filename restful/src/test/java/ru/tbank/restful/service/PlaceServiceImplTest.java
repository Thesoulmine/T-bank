package ru.tbank.restful.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tbank.restful.entity.Place;
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
class PlaceServiceImplTest {

    @InjectMocks
    private PlaceServiceImpl categoryService;

    @Mock
    private Repository<Place> categoryRepository;

    @Test
    public void getAllCategories_ReturnAllCategories() {
        Place place1 = new Place();
        place1.setId(1L);
        place1.setName("qwe");

        Place place2 = new Place();
        place2.setId(2L);
        place2.setName("asd");

        Place resultPlace1 = new Place();
        resultPlace1.setId(1L);
        resultPlace1.setName("qwe");

        Place resultPlace2 = new Place();
        resultPlace2.setId(2L);
        resultPlace2.setName("asd");

        when(categoryRepository.findAll()).thenReturn(List.of(place1, place2));

        List<Place> result = categoryService.getAllCategories();

        verify(categoryRepository).findAll();
        assertEquals(List.of(resultPlace1, resultPlace2), result);
    }

    @Test
    public void getCategoryBy_ReturnCategory() {
        Place place = new Place();
        place.setId(1L);
        place.setName("qwe");

        Place resultPlace = new Place();
        resultPlace.setId(1L);
        resultPlace.setName("qwe");

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(categoryRepository.find(eq(place.getId()))).thenReturn(Optional.of(place));

        Place result = categoryService.getCategoryBy(place.getId());

        verify(categoryRepository).find(idCaptor.capture());
        assertEquals(place.getId(), idCaptor.getValue());
        assertEquals(resultPlace, result);
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
        Place place = new Place();
        place.setId(1L);
        place.setName("qwe");

        Place resultPlace = new Place();
        resultPlace.setId(1L);
        resultPlace.setName("qwe");

        ArgumentCaptor<Place> categoryCaptor = ArgumentCaptor.forClass(Place.class);

        when(categoryRepository.save(any(Place.class))).thenReturn(place);

        Place result = categoryService.saveCategory(place);

        verify(categoryRepository).save(categoryCaptor.capture());
        assertEquals(place, categoryCaptor.getValue());
        assertEquals(resultPlace, result);
    }

    @Test
    public void updateCategory_UpdateCategoryAndReturnUpdatedCategory() {
        Place place = new Place();
        place.setId(1L);
        place.setName("qwe");

        Place resultPlace = new Place();
        resultPlace.setId(1L);
        resultPlace.setName("qwe");

        ArgumentCaptor<Place> categoryCaptor = ArgumentCaptor.forClass(Place.class);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(categoryRepository.update(eq(place.getId()), any(Place.class))).thenReturn(place);

        Place result = categoryService.updateCategory(place.getId(), place);

        verify(categoryRepository).update(idCaptor.capture(), categoryCaptor.capture());
        assertEquals(place, categoryCaptor.getValue());
        assertEquals(place.getId(), idCaptor.getValue());
        assertEquals(resultPlace, result);
    }

    @Test
    public void updateCategory_ThrowsNoSuchElementException_IfElementNotExist() {
        Place place = new Place();
        place.setId(1L);
        place.setName("qwe");

        ArgumentCaptor<Place> categoryCaptor = ArgumentCaptor.forClass(Place.class);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(categoryRepository.update(eq(place.getId()), any(Place.class))).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> categoryService.updateCategory(place.getId(), place));
        verify(categoryRepository).update(idCaptor.capture(), categoryCaptor.capture());
        assertEquals(place.getId(), idCaptor.getValue());
        assertEquals(place, categoryCaptor.getValue());
    }

    @Test
    public void deleteCategoryBy_DeleteCategoryAndReturnDeletedCategory() {
        Place place = new Place();
        place.setId(1L);
        place.setName("qwe");

        Place resultPlace = new Place();
        resultPlace.setId(1L);
        resultPlace.setName("qwe");

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(categoryRepository.delete(eq(place.getId()))).thenReturn(place);

        Place result = categoryService.deleteCategoryBy(place.getId());

        verify(categoryRepository).delete(idCaptor.capture());
        assertEquals(place.getId(), idCaptor.getValue());
        assertEquals(resultPlace, result);
    }

    @Test
    public void deleteCategoryBy_ReturnNull_IfElementNotExist() {
        Long id = 1L;

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(categoryRepository.delete(eq(id))).thenReturn(null);

        Place result = categoryService.deleteCategoryBy(id);

        verify(categoryRepository).delete(idCaptor.capture());
        assertEquals(id, idCaptor.getValue());
        assertNull(result);
    }
}