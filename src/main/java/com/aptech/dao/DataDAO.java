package com.aptech.dao;

import com.aptech.models.LinkData;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface DataDAO {
    public Optional<LinkData> createNewLink(String link, String content);
    public Optional<LinkData> findLinkByID(Integer ID);
    public Optional<LinkData> findLinkByName(String link);
    public void updateLink(LinkData linkData, String link, String content);
    public void deleteLink(LinkData linkData);
    public List<LinkData> returnAllLink();
}
