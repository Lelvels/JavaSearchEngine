package com.aptech.dao;

import com.aptech.database.ConnectionHandler;
import com.aptech.database.DataAccessException;
import com.aptech.models.FirstLink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FirstLinkDaoImpl implements FirstLinkDAO {
    ConnectionHandler connectionHandler = ConnectionHandler.getInstance();

    @Override
    public Optional<FirstLink> createNewSuggestLink(String link, String description) {
        Connection conn = connectionHandler.getConnection().get();
        try
        {
            if(!findSuggestLinkByLink(link).isPresent()){
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO FirstLink(LINK, DESCRIPTION) "
                        + "VALUES (?, ?)");
                stmt.setString(1, link);
                stmt.setString(2, description);
                int i = stmt.executeUpdate();
                System.out.println(i + " first link records inserted!");
                FirstLink firstLink = findSuggestLinkByLink(link).get();
                return Optional.of(firstLink);
            } else {
                System.out.println("Record already existed in database!");
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<FirstLink> findSuggestLinkByID(Integer ID) {
        Connection conn = connectionHandler.getConnection().get();
        try
        {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM firstLink where ID = ?;");
            stmt.setInt(1, ID);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    Integer id = rs.getInt("ID");
                    String link = rs.getString("LINK");
                    String description = rs.getString("DESCRIPTION");
                    FirstLink firstLink = new FirstLink();
                    firstLink.setID(id);
                    firstLink.setLink(link);
                    firstLink.setDescription(description);
                    conn.close();
                    return Optional.of(firstLink);
                }
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<FirstLink> findSuggestLinkByLink(String link) {
        Connection conn = connectionHandler.getConnection().get();
        try
        {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM firstLink where link = ?;");
            stmt.setString(1, link);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    Integer id = rs.getInt("ID");
                    String rsLink = rs.getString("LINK");
                    String description = rs.getString("DESCRIPTION");
                    FirstLink firstLink = new FirstLink();
                    firstLink.setID(id);
                    firstLink.setLink(rsLink);
                    firstLink.setDescription(description);
                    conn.close();
                    return Optional.of(firstLink);
                }
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void updateSuggestLink(FirstLink firstLink, String link, String description) {
        try {
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("UPDATE firstLink " +
                    "SET LINK = ?, DESCRIPTION = ? WHERE ID = ?");
            Integer ID = firstLink.getID();
            stmt.setString(1, link);
            stmt.setString(2, description);
            stmt.setInt(3, ID);
            int i = stmt.executeUpdate();
            System.out.println("Link id: " + ID + " updated!");
            conn.close();
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSuggestLink(FirstLink firstLink) {
        try{
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM firstLink WHERE ID = ?");
            Integer ID = firstLink.getID();
            stmt.setInt(1, ID);
            int i = stmt.executeUpdate();
            System.out.println("Link id: " + ID + " deleted!");
            conn.close();
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<FirstLink> returnAllSuggestLink() {
        List<FirstLink> firstLinkList = new ArrayList<>();
        Connection conn = connectionHandler.getConnection().get();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM firstLink;");
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    FirstLink firstLink = new FirstLink();
                    Integer ID = rs.getInt("ID");
                    String link = rs.getString("LINK");
                    String description = rs.getString("DESCRIPTION");
                    firstLink.setID(ID);
                    firstLink.setLink(link);
                    firstLink.setDescription(description);
                    firstLinkList.add(firstLink);
                }
                conn.close();
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return firstLinkList;
    }
}
