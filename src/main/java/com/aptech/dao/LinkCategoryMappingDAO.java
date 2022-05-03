package com.aptech.dao;

import com.aptech.models.LinkCategoryMapping;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface LinkCategoryMappingDAO {
    public Optional<LinkCategoryMapping> createNew(Integer CatID, Integer LinkID);
    public List<LinkCategoryMapping> findByCatID(Integer CatID);
    public Optional<LinkCategoryMapping> findByLinkID(Integer LinkID);
    public void update(LinkCategoryMapping linkCategoryMapping, Integer CatID);
    public void delete(LinkCategoryMapping linkCategoryMapping);
    public List<LinkCategoryMapping> returnAll();
}
