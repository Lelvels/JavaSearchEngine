package com.aptech.Task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class Task implements Runnable{
    private String link;
    private BlockingQueue<String> taskQueue;
    private Set<String> linkDataSet;

    public Task(String inLink, BlockingQueue<String> paraTaskQueue, Set<String> paraLinkDataSet){
        link = inLink;
        taskQueue = paraTaskQueue;
        linkDataSet = paraLinkDataSet;
    }

    public void run(){
        Document doc = null;
        try {
            if(linkDataSet.size() < DataConfig.LINK_DATA_SET_STOP_SIZE){
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                int code = connection.getResponseCode();

                if(code >= 200 && code <= 300){
                    doc = Jsoup.connect(link).get();
                    Elements crawledLinks = doc.select("a");
                    for (Element crawlingLink : crawledLinks) {
                        String linkInCrawling = crawlingLink.absUrl("href");
                        if(!linkInCrawling.isEmpty()){
                            if(linkDataSet.size() < DataConfig.LINK_DATA_SET_STOP_SIZE){
                                linkDataSet.add(linkInCrawling);
                                if(!taskQueue.contains(linkInCrawling)){
                                    if(taskQueue.size() < DataConfig.TASK_QUEUE_MAX_SIZE){
                                        taskQueue.add(linkInCrawling);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
