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
import ru.tbank.restful.dto.CategoryRequestDTO;
import ru.tbank.restful.dto.CategoryResponseDTO;
import ru.tbank.restful.entity.Category;
import ru.tbank.restful.mapper.CategoryMapper;
import ru.tbank.restful.mapper.CategoryMapperImpl;
import ru.tbank.restful.service.CategoryService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Import(CategoryMapperImpl.class)
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void getAllCategories_ReturnAllCategories() throws Exception {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("qwe");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("asd");

        CategoryResponseDTO resultCategory1 = new CategoryResponseDTO();
        resultCategory1.setId(1L);
        resultCategory1.setName("qwe");

        CategoryResponseDTO resultCategory2 = new CategoryResponseDTO();
        resultCategory2.setId(2L);
        resultCategory2.setName("asd");

        Mockito.when(categoryService.getAllCategories()).thenReturn(List.of(category1, category2));

        mockMvc.perform(get("/api/v1/places/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(List.of(resultCategory1, resultCategory2))));
    }

    @Test
    public void createCategory_SaveCategoryAndReturnSavedCategory() throws Exception {
        CategoryRequestDTO requestCategory = new CategoryRequestDTO();
        requestCategory.setId(1L);
        requestCategory.setName("qwe");

        Category category = new Category();
        category.setId(1L);
        category.setKudaGoId(1L);
        category.setName("qwe");

        CategoryResponseDTO resultCategory = new CategoryResponseDTO();
        resultCategory.setId(1L);
        resultCategory.setKudaGoId(1L);
        resultCategory.setName("qwe");

        Mockito.when(categoryService.saveCategory(Mockito.any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/api/v1/places/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCategory)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(resultCategory)));
    }
}