package com.aptech.models;

import com.aptech.Task.DataConfig;
import com.aptech.Task.InsertDatabaseTask;
import com.aptech.Task.Task;
import com.aptech.dao.FirstLinkDAO;
import com.aptech.dao.FirstLinkDaoImpl;
import com.aptech.dao.LinkDataDao;
import com.aptech.dao.LinkDataDaoImpl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Crawler {

    //Code for Multi Thread
    public Set<String> linkCrawling(Set<String> linkDataSet){
        Set<String> newLinkDataSet = ConcurrentHashMap.newKeySet();

        //Tao ThreadPool
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        //Tao Link Dao
        FirstLinkDAO firstLinkDAO = new FirstLinkDaoImpl();
        List<FirstLink> firstLinkList = firstLinkDAO.returnAllSuggestLink();

        //Tao Queue de them task
        BlockingQueue<String> taskQueue = new LinkedBlockingDeque<>(DataConfig.LINK_DATA_SET_STOP_SIZE);

        //Add origin link into the Set
        for (FirstLink firstLink : firstLinkList){
            linkDataSet.add(firstLink.getLink());
            taskQueue.add(firstLink.getLink());
        }

        //Cho chay multithread để crawl links
            while (newLinkDataSet.size() < DataConfig.LINK_DATA_SET_STOP_SIZE){
            try {
                String link = taskQueue.take();
                Task task = new Task(link, taskQueue, newLinkDataSet);
                executorService.execute(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        //thêm cái sets mới vào set cũ
        linkDataSet.addAll(newLinkDataSet);

        return linkDataSet;
    }

    //Code for multi thread
    public void insertLinkToDatabase(Set<String> linkDataSet){
        //Tao ThreadPool
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        //Tao Queue de them task
        BlockingQueue<String> taskQueue = new LinkedBlockingDeque<>(linkDataSet.size());

        //Xoa cac phan tu cu di
        LinkDataDao linkDataDao = new LinkDataDaoImpl();
        Set<String> oldLinkDataSet = linkDataDao.returnLinkSet();
        linkDataSet.removeAll(oldLinkDataSet);

        //Them set vao queue de chay multi thread
        taskQueue.addAll(linkDataSet);

        //Vong lap lam viec
        while(!taskQueue.isEmpty()){
            InsertDatabaseTask insertDatabaseTask = new InsertDatabaseTask(taskQueue);
            executorService.execute(insertDatabaseTask);
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    // Đầu tiên là lấy link ra sau đó lấy content trong html của trang web đó rồi quăng sạch lên databse limit lấy 2000 từ
}
