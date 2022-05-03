package com.aptech.models;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LinkDataSetSingleton {
    //private static volatile LinkDataSetSingleton instance;
    private static volatile Set<String> linkDataSet;

    private LinkDataSetSingleton(){
        linkDataSet = ConcurrentHashMap.newKeySet();
    }

    public static Set<String> getLinkDataSet(){
        if(linkDataSet == null){
            synchronized (LinkDataSetSingleton.class){
                if(linkDataSet == null){
                    linkDataSet = ConcurrentHashMap.newKeySet();
                }
            }
        }
        return linkDataSet;
    }
}
