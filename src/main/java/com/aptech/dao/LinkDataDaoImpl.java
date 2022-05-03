package com.aptech.dao;

import com.aptech.database.ConnectionHandler;
import com.aptech.database.DataAccessException;
import com.aptech.models.FirstLink;
import com.aptech.models.LinkData;
import com.aptech.models.LinkDataSetSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LinkDataDaoImpl implements LinkDataDao {
    ConnectionHandler connectionHandler = ConnectionHandler.getInstance();

    @Override
    public void createNewLink(LinkData linkData) {
        try
        {
            Connection conn = connectionHandler.getConnection().get();
            if(!findLink(conn, linkData.getLink()).isPresent()){
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO data(LINK, TITLE, CONTENT) "
                        + "VALUES (?, ?, ?)");
                stmt.setString(1, linkData.getLink());
                stmt.setString(2, linkData.getTitle());
                stmt.setString(3, linkData.getContent());
                int i = stmt.executeUpdate();
                System.out.println(i + " link data records inserted!");
                conn.close();
            } else {
                conn.close();
                System.out.println("Record already existed in database!");
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void insertMultiLinks(Connection connection, List<LinkData> linkDataList) {
        PreparedStatement ps = null;
        String query = "INSERT INTO data(LINK, TITLE, CONTENT) " + "VALUES (?, ?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            long start = System.currentTimeMillis();

            for(LinkData linkData : linkDataList){
                ps.setString(1, linkData.getLink());
                ps.setString(2, linkData.getTitle());
                ps.setString(3, linkData.getContent());
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> returnLinkSet() {
        Set<String> linkDataSet = ConcurrentHashMap.newKeySet();
        try {
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("SELECT Link FROM data;");
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    String link = rs.getString("LINK");
                    linkDataSet.add(link);
                }
                conn.close();
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return linkDataSet;
    }

    @Override
    public Optional<LinkData> findLinkByID(Integer ID) {
        try
        {
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM data where ID = ?;");
            stmt.setInt(1, ID);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    Integer id = rs.getInt("ID");
                    String link = rs.getString("LINK");
                    String content = rs.getString("CONTENT");
                    LinkData linkData = new LinkData();
                    linkData.setID(id);
                    linkData.setLink(link);
                    linkData.setContent(content);
                    conn.close();
                    return Optional.of(linkData);
                }
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<LinkData> findLink(Connection conn, String link) {
        try
        {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM data where link = ?;");
            stmt.setString(1, link);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    Integer id = rs.getInt("ID");
                    String content = rs.getString("CONTENT");
                    LinkData linkData = new LinkData();
                    linkData.setID(id);
                    linkData.setLink(link);
                    linkData.setContent(content);
                    return Optional.of(linkData);
                }
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void updateLinkData(LinkData linkData, String link, String title, String content) {
        try {
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("UPDATE data " +
                    "SET LINK = ?, TITLE = ? ,CONTENT = ? WHERE ID = ?");
            Integer ID = linkData.getID();
            stmt.setString(1, link);
            stmt.setString(2, title);
            stmt.setString(3, content);
            stmt.setInt(4, ID);
            int i = stmt.executeUpdate();
            System.out.println("Link data id: " + ID + " updated!");
            conn.close();
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLinkData(LinkData linkData) {
        try{
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM data WHERE ID = ?");
            Integer ID = linkData.getID();
            stmt.setInt(1, ID);
            int i = stmt.executeUpdate();
            System.out.println("Link data id: " + ID + " deleted!");
            conn.close();
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<LinkData> returnAllLink() {
        List<LinkData> linkDataList = new ArrayList<>();
        try {
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM data LIMIT 12;");
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    LinkData linkData = new LinkData();
                    Integer ID = rs.getInt("ID");
                    String link = rs.getString("LINK");
                    String content = rs.getString("CONTENT");
                    String title = rs.getString("TITLE");
                    linkData.setID(ID);
                    linkData.setLink(link);
                    linkData.setTitle(title);
                    linkData.setContent(content);
                    linkDataList.add(linkData);
                }
                conn.close();
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return linkDataList;
    }

    @Override
    public List<LinkData> findContent(String searchWord) {
        List<LinkData> linkDataList = new ArrayList<>();
        Connection conn = connectionHandler.getConnection().get();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM data WHERE CONTENT LIKE ? " +
                            " OR TITLE LIKE ? LIMIT 50;");
            stmt.setString(1, "%" + searchWord + "%");
            stmt.setString(2, "%" + searchWord + "%");
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    LinkData linkData = new LinkData();
                    Integer ID = rs.getInt("ID");
                    String link = rs.getString("LINK");
                    String content = rs.getString("CONTENT");
                    String title = rs.getString("TITLE");
                    linkData.setID(ID);
                    linkData.setLink(link);
                    linkData.setContent(content);
                    linkData.setTitle(title);
                    linkDataList.add(linkData);
                }
                conn.close();
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return linkDataList;
    }
}
