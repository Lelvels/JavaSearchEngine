package com.aptech.dao;

import com.aptech.database.*;
import com.aptech.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDaoImpl implements CategoryDAO{
    ConnectionHandler connectionHandler = ConnectionHandler.getInstance();

    @Override
    public Optional<Category> createNewCategory(String categoryName, String newDescription) {
        try
        {
            Connection conn = connectionHandler.getConnection().get();
            if(!findCategoryByName(categoryName).isPresent()){
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO category(NAME, DESCRIPTION) "
                        + "VALUES (?, ?)");
                stmt.setString(1, categoryName);
                stmt.setString(2, newDescription);
                int i = stmt.executeUpdate();
                System.out.println(i + " category records inserted!");
                Category category = findCategoryByName(categoryName).get();
                conn.close();
                return Optional.of(category);
            } else {
                System.out.println("Record already existed in database!");
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Category> findCategoryByID(Integer categoryID) {
        try
        {
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM category where ID = ?;");
            stmt.setInt(1, categoryID);
            Category category = new Category();
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    Integer id = rs.getInt("ID");
                    String name = rs.getString("NAME");
                    String description = rs.getString("DESCRIPTION");
                    category.setCategoryID(id);
                    category.setName(name);
                    category.setDescription(description);
                    conn.close();
                    return Optional.of(category);
                }
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Category> findCategoryByName(String CatName) {
        try
        {
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM category where NAME = ?;");
            stmt.setString(1, CatName);
            Category category = new Category();
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    Integer id = rs.getInt("ID");
                    String name = rs.getString("NAME");
                    String description = rs.getString("DESCRIPTION");
                    category.setCategoryID(id);
                    category.setName(name);
                    category.setDescription(description);
                    conn.close();
                    return Optional.of(category);
                }
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void updateCategory(Category category, String newCategoryName, String newDescription) {
        try {
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("UPDATE category " +
                    "SET NAME = ?, DESCRIPTION = ? WHERE ID = ?");
            Integer categoryID = category.getCategoryID();
            stmt.setString(1, newCategoryName);
            stmt.setString(2, newDescription);
            stmt.setInt(3, categoryID);
            int i = stmt.executeUpdate();
            System.out.println("Category id: " + categoryID + " updated!");
            conn.close();
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategory(Category category) {
        try{
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM category WHERE ID = ?");
            Integer categoryID = category.getCategoryID();
            stmt.setInt(1, categoryID);
            int i = stmt.executeUpdate();
            System.out.println("Category id: " + categoryID + " record deleted!");
            conn.close();
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> returnAllCategory() {
        List<Category> categoryList = new ArrayList<>();
        try {
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Category;");
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    Category category = new Category();
                    Integer categoryID  = rs.getInt("ID");
                    String categoryName = rs.getString("NAME");
                    String description = rs.getString("DESCRIPTION");
                    category.setCategoryID(categoryID);
                    category.setName(categoryName);
                    category.setDescription(description);
                    categoryList.add(category);
                }
                conn.close();
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return categoryList;
    }
}
