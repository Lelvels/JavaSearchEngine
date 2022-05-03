package com.aptech.dao;

import com.aptech.database.ConnectionHandler;
import com.aptech.database.DataAccessException;
import com.aptech.models.LinkCategoryMapping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinkCategoryMappingDaoImpl implements LinkCategoryMappingDAO {
    ConnectionHandler connectionHandler = ConnectionHandler.getInstance();

    @Override
    public Optional<LinkCategoryMapping> createNew(Integer CatID, Integer LinkID) {
        try
        {
            Connection conn = connectionHandler.getConnection().get();
            if(!findByLinkID(LinkID).isPresent()){
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO CategoryLinkMapping(CAT_ID, LINK_ID) "
                        + "VALUES (?, ?)");
                stmt.setInt(1, CatID);
                stmt.setInt(2, LinkID);
                int i = stmt.executeUpdate();
                System.out.println(i + " link and category relation inserted!");
                LinkCategoryMapping linkCategoryMapping = findByLinkID(LinkID).get();
                conn.close();
                return Optional.of(linkCategoryMapping);
            } else {
                System.out.println("An error have occur, cannot found the link in database.");
                conn.close();
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<LinkCategoryMapping> findByCatID(Integer CatID) {
        Connection conn = connectionHandler.getConnection().get();
        List<LinkCategoryMapping> linkCategoryMappings = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CategoryLinkMapping where CAT_ID = ?;");
            stmt.setInt(1, CatID);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    LinkCategoryMapping linkCategoryMapping = new LinkCategoryMapping();
                    Integer LinkID = rs.getInt("LINK_ID");
                    Integer rsCatID = rs.getInt("CAT_ID");
                    linkCategoryMapping.setLinkID(LinkID);
                    linkCategoryMapping.setCatID(rsCatID);
                    linkCategoryMappings.add(linkCategoryMapping);
                }
            }
            conn.close();
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return linkCategoryMappings;
    }


    @Override
    public Optional<LinkCategoryMapping> findByLinkID(Integer LinkID) {
        Connection conn = connectionHandler.getConnection().get();
        try
        {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CategoryLinkMapping where ID = ?;");
            stmt.setInt(1, LinkID);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    Integer CatID = rs.getInt("CAT_ID");
                    LinkCategoryMapping linkCategoryMapping = new LinkCategoryMapping();
                    linkCategoryMapping.setLinkID(LinkID);
                    linkCategoryMapping.setCatID(CatID);
                    conn.close();
                    return Optional.of(linkCategoryMapping);
                }
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(LinkCategoryMapping linkCategoryMapping, Integer CatID) {
        try {
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("UPDATE CategoryLinkMapping " +
                    "SET CAT_ID = ? WHERE LINK_ID = ?");
            Integer LinkID = linkCategoryMapping.getLinkID();
            stmt.setInt(1, CatID);
            stmt.setInt(2, LinkID);
            int i = stmt.executeUpdate();
            System.out.println("Link id: " + LinkID + " in mapping table updated with CatID: " + CatID);
            conn.close();
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(LinkCategoryMapping linkCategoryMapping) {
        try{
            Connection conn = connectionHandler.getConnection().get();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM CategoryLinkMapping WHERE LINK_ID = ?");
            Integer ID = linkCategoryMapping.getLinkID();
            stmt.setInt(1, ID);
            int i = stmt.executeUpdate();
            System.out.println("Link id: " + ID + " in mapping table deleted!");
            conn.close();
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<LinkCategoryMapping> returnAll() {
        Connection conn = connectionHandler.getConnection().get();
        List<LinkCategoryMapping> linkCategoryMappings = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CategoryLinkMapping;");
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //Retrieve by column name
                    LinkCategoryMapping linkCategoryMapping = new LinkCategoryMapping();
                    Integer LinkID = rs.getInt("LINK_ID");
                    Integer CatID = rs.getInt("CAT_ID");
                    linkCategoryMapping.setCatID(CatID);
                    linkCategoryMapping.setLinkID(LinkID);
                    linkCategoryMappings.add(linkCategoryMapping);
                }
                conn.close();
            }
        } catch (DataAccessException | SQLException e){
            e.printStackTrace();
        }
        return linkCategoryMappings;
    }
}
