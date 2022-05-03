package com.aptech.Task;

import com.aptech.dao.LinkDataDao;
import com.aptech.dao.LinkDataDaoImpl;
import com.aptech.database.C3p0DataSource;
import com.aptech.models.LinkData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class InsertDatabaseTask implements Runnable{
    BlockingQueue<String> taskQueue;

    public InsertDatabaseTask(BlockingQueue<String> queue){
        taskQueue = queue;
    }

    private void insertIntoDatabase(){
        LinkDataDao linkDataDao = new LinkDataDaoImpl();
        List<LinkData> linkDataList = new LinkedList<>();
        Document doc = null;
        int linkDataListMaxSize = 5;

        try{
            while (linkDataList.size() <= linkDataListMaxSize && !(taskQueue.isEmpty())){
                // Taking link from the queue
                String link = taskQueue.take();
                LinkData linkData = new LinkData();

                //Checking the response code to see if the website work ?
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                int code = connection.getResponseCode();

                if(code >= 200 && code <= 300) {
                    doc = Jsoup.connect(link).get();
                    String content = doc.body().text();
                    String title = doc.title();
                    if (!title.isEmpty()) {
                        if (!content.isEmpty()) {
                            linkData.setLink(link);
                            linkData.setTitle(title);
                            linkData.setContent(content);
                            linkDataList.add(linkData);
                        }
                    }
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if(linkDataList.size() >= linkDataListMaxSize){
            Connection connection = null;
            try{
                connection = C3p0DataSource.getConnection();
                linkDataDao.insertMultiLinks(connection, linkDataList);
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                try{
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void run(){
        insertIntoDatabase();
    }
}
