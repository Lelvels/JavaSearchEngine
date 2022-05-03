package com.aptech.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Category {
    private Integer categoryID;
    private String name;
    private String description;

    public Category(Integer catID, String newName, String newDescription){
        this.categoryID = catID;
        this.name = newName;
        this.description = newDescription;
    }
}
