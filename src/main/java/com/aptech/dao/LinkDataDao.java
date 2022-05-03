package com.aptech.dao;

import com.aptech.models.LinkData;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LinkDataDao {
    public void createNewLink(LinkData linkData);
    public void insertMultiLinks(Connection connection, List<LinkData> linkDataList);
    public Set<String> returnLinkSet();
    public Optional<LinkData> findLinkByID(Integer ID);
    public Optional<LinkData> findLink(Connection conn, String link);
    public void updateLinkData(LinkData linkData, String link, String title, String content);
    public void deleteLinkData(LinkData linkData);
    public List<LinkData> returnAllLink();
    public List<LinkData> findContent(String searchWord);
}
