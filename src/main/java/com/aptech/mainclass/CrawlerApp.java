package com.aptech.mainclass;
import com.aptech.dao.LinkDataDao;
import com.aptech.dao.LinkDataDaoImpl;
import com.aptech.models.Crawler;
import com.aptech.models.LinkData;
import com.aptech.models.LinkDataSetSingleton;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrawlerApp {
    public static void main(String[] args){
        // Link crawling
        Crawler crawler = new Crawler();
        int choice;
        LinkDataDao linkDataDao = new LinkDataDaoImpl();
        Set<String> newLinkData = LinkDataSetSingleton.getLinkDataSet();

        Scanner s = new Scanner(System.in);
        do{
            System.out.println("Nhap 1 de crawl link!");
            System.out.println("Nhap 2 Insert link vao databse!");
            System.out.println("Nhap 3 de hien thi size cua set.");
            System.out.println("Enter choice: ");
            String txt = s.nextLine();
            choice = Integer.parseInt(txt);

            switch (choice){
                case 1:
                    newLinkData = crawler.linkCrawling(newLinkData);
                    System.out.println("Finished, your link data set size :"+newLinkData.size());
                    break;

                case 2:
                    crawler.insertLinkToDatabase(newLinkData);
                    System.out.println("Finished inserted into database !");
                    System.out.println("Updating set of link data!");
                    newLinkData = linkDataDao.returnLinkSet();
                    System.out.println("Your total link right now: " + newLinkData.size());
                    break;

                case 3:
                    System.out.println("Your link data set size :" + newLinkData.size());
                    break;

                default:
                    System.out.println("Xin moi nhap lai!");
                    break;
            }
        } while (choice!=0);
    }
}
