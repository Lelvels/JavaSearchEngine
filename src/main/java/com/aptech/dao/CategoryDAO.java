package com.aptech.dao;

import com.aptech.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {
    public Optional<Category> createNewCategory(String categoryName, String newDescription);
    public Optional<Category> findCategoryByID(Integer categoryID);
    public Optional<Category> findCategoryByName(String CatName);
    public void updateCategory(Category category, String newCategoryName, String newDescription);
    public void deleteCategory(Category category);
    public List<Category> returnAllCategory();
}
