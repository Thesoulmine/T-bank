package ru.tbank.restful.client;

import ru.tbank.restful.entity.Category;

import java.util.List;

public interface CategoryClient {

    List<Category> getAllCategories();
}
