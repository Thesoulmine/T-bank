package ru.tbank.restful.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tbank.restful.dto.CategoryRequestDTO;
import ru.tbank.restful.dto.CategoryResponseDTO;
import ru.tbank.restful.entity.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryResponseDTO categoryResponseDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kudaGoId", source = "id")
    Category toEntity(CategoryRequestDTO categoryRequestDTO);

    List<Category> toEntity(List<CategoryRequestDTO> categoryRequestDTOList);

    CategoryResponseDTO toResponseDTO(Category category);

    List<CategoryResponseDTO> toResponseDTO(List<Category> categoryList);

    CategoryRequestDTO toRequestDTO(Category category);
}
