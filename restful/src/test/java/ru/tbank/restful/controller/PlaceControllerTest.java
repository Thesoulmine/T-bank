package ru.tbank.restful.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.tbank.restful.dto.PlaceRequestDTO;
import ru.tbank.restful.dto.PlaceResponseDTO;
import ru.tbank.restful.dto.ExceptionMessageResponseDTO;
import ru.tbank.restful.entity.Place;
import ru.tbank.restful.mapper.PlaceMapperImpl;
import ru.tbank.restful.service.PlaceService;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Import(PlaceMapperImpl.class)
@WebMvcTest(PlaceController.class)
class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlaceService placeService;

    @Test
    public void getAllCategories_ReturnAllCategories_Ok() throws Exception {
        Place place1 = new Place();
        place1.setId(1L);
        place1.setName("qwe");

        Place place2 = new Place();
        place2.setId(2L);
        place2.setName("asd");

        PlaceResponseDTO resultCategory1 = new PlaceResponseDTO();
        resultCategory1.setId(1L);
        resultCategory1.setName("qwe");

        PlaceResponseDTO resultCategory2 = new PlaceResponseDTO();
        resultCategory2.setId(2L);
        resultCategory2.setName("asd");

        Mockito.when(placeService.getAllCategories()).thenReturn(List.of(place1, place2));

        mockMvc.perform(get("/api/v1/places/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(List.of(resultCategory1, resultCategory2))));
    }

    @Test
    public void getCategory_ReturnCategory_Ok() throws Exception {
        Place place = new Place();
        place.setId(1L);
        place.setName("qwe");

        PlaceResponseDTO resultCategory = new PlaceResponseDTO();
        resultCategory.setId(1L);
        resultCategory.setName("qwe");

        Mockito.when(placeService.getCategoryBy(Mockito.eq(place.getId()))).thenReturn(place);

        mockMvc.perform(get("/api/v1/places/categories/{id}", place.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(resultCategory)));
    }

    @Test
    public void getCategory_ThrowsNoSuchElementException_NotFound() throws Exception {
        Long id = 1L;

        ExceptionMessageResponseDTO result = new ExceptionMessageResponseDTO("Category not found");

        Mockito.when(placeService.getCategoryBy(Mockito.eq(id))).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/api/v1/places/categories/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(result)));

    }

    @Test
    public void createCategory_SaveCategoryAndReturnSavedCategory_Ok() throws Exception {
        PlaceRequestDTO requestCategory = new PlaceRequestDTO();
        requestCategory.setId(1L);
        requestCategory.setName("qwe");

        Place place = new Place();
        place.setId(1L);
        place.setKudaGoId(1L);
        place.setName("qwe");

        PlaceResponseDTO resultCategory = new PlaceResponseDTO();
        resultCategory.setId(1L);
        resultCategory.setKudaGoId(1L);
        resultCategory.setName("qwe");

        Mockito.when(placeService.saveCategory(Mockito.any(Place.class))).thenReturn(place);

        mockMvc.perform(post("/api/v1/places/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCategory)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(resultCategory)));
    }

    @Test
    public void updateCategory_UpdateCategoryAndReturnUpdatedCategory_Ok() throws Exception {
        PlaceRequestDTO requestCategory = new PlaceRequestDTO();
        requestCategory.setId(1L);
        requestCategory.setName("qwe");

        Place place = new Place();
        place.setId(1L);
        place.setKudaGoId(1L);
        place.setName("qwe");

        PlaceResponseDTO resultCategory = new PlaceResponseDTO();
        resultCategory.setId(1L);
        resultCategory.setKudaGoId(1L);
        resultCategory.setName("qwe");

        Mockito.when(placeService.updateCategory(Mockito.eq(place.getId()), Mockito.any(Place.class)))
                .thenReturn(place);

        mockMvc.perform(put("/api/v1/places/categories/{id}", place.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCategory)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(resultCategory)));
    }

    @Test
    public void updateCategory_ThrowsNoSuchElementException_NotFound() throws Exception {
        PlaceRequestDTO requestCategory = new PlaceRequestDTO();
        requestCategory.setId(1L);
        requestCategory.setName("qwe");

        Place place = new Place();
        place.setId(1L);
        place.setKudaGoId(1L);
        place.setName("qwe");

        ExceptionMessageResponseDTO result = new ExceptionMessageResponseDTO("Category not found");

        Mockito.when(placeService.updateCategory(Mockito.eq(place.getId()), Mockito.any(Place.class)))
                .thenThrow(NoSuchElementException.class);

        mockMvc.perform(put("/api/v1/places/categories/{id}", place.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCategory)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    public void deleteCategory_DeleteCategoryAndReturnDeletedCategory_Ok() throws Exception {
        Place place = new Place();
        place.setId(1L);
        place.setKudaGoId(1L);
        place.setName("qwe");

        PlaceResponseDTO resultCategory = new PlaceResponseDTO();
        resultCategory.setId(1L);
        resultCategory.setKudaGoId(1L);
        resultCategory.setName("qwe");

        Mockito.when(placeService.deleteCategoryBy(Mockito.eq(place.getId()))).thenReturn(place);

        mockMvc.perform(delete("/api/v1/places/categories/{id}", place.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(resultCategory)));
    }
}