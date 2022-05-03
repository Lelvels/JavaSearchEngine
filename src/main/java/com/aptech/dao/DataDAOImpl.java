package com.aptech.dao;

import com.aptech.database.ConnectionHandler;
import com.aptech.database.DataAccessException;
import com.aptech.models.FirstLink;
import com.aptech.models.LinkData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataDAOImpl implements DataDAO{
    ConnectionHandler connectionHandler = ConnectionHandler.getInstance();

    @Override
    public Optional<LinkData> createNewLink(String link, String content) {
        try
        {
            Connection conn = connectionHandler.getConnection().get();
            if(!findLinkByName(link).isPresent()){
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO data(LINK, CONTENT) "
                        + "VALUES (?, ?)");
                stmt.setString(1, link);
                stmt.setString(2, content);
                int i = stmt.executeUpdate();
                System.out.println(i + " data record inserted!");
                LinkData linkData = findLinkByName(link).get();
                conn.close();
                return Optional.of(linkData);
            } else {
                System.out.println("Record already existed in database!");
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
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
    public Optional<LinkData> findLinkByName(String link) {
        try
        {
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM data where link = ?;");
            stmt.setString(1, link);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    Integer id = rs.getInt("ID");
                    String rsLink = rs.getString("LINK");
                    String content = rs.getString("CONTENT");
                    LinkData linkData = new LinkData();
                    linkData.setID(id);
                    linkData.setLink(rsLink);
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
    public void updateLink(LinkData linkData, String link, String content) {
        try {
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("UPDATE data " +
                    "SET LINK = ?, CONTENT = ? WHERE ID = ?");
            Integer ID = linkData.getID();
            stmt.setString(1, link);
            stmt.setString(2, content);
            stmt.setInt(3, ID);
            int i = stmt.executeUpdate();
            System.out.println("Link id: " + ID + " updated!");
            conn.close();
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLink(LinkData linkData) {
        try{
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM data WHERE ID = ?");
            Integer ID = linkData.getID();
            stmt.setInt(1, ID);
            int i = stmt.executeUpdate();
            System.out.println("Link id: " + ID + " deleted!");
            conn.close();
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<LinkData> returnAllLink() {
        Connection conn = connectionHandler.getConnection().get();
        List<LinkData> linkDataList = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM data;");
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    LinkData linkData = new LinkData();
                    Integer ID = rs.getInt("ID");
                    String link = rs.getString("LINK");
                    String content = rs.getString("CONTENT");
                    linkData.setID(ID);
                    linkData.setLink(link);
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
}
