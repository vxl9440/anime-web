package com.sox.webapp.util;


import com.sox.webapp.model.Anime;
import com.sox.webapp.model.Type;
import com.sox.webapp.model.Weekday;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Constant {
    public static final Integer SUCCESS_CODE = 1;
    public static final Integer FAIL_CODE = -1;
    public static String IMG_RESOURCE_ROOT;
    public static String VIDEO_RESOURCE_ROOT;
    public static String IMG_URL_ROOT;
    public static String VIDEO_URL_ROOT;
    public static String notificationMessage;
    public static long PAGE_SIZE;
    public static List<String> yearList;
    public static final List<String> seasonList;
    public static List<Type> typeList;
    public static List<String> statusList;
    public static List<String> sortLit;
    public static List<Weekday> weekdayList;
    public static List<Anime> recommendList,updateList;
    public static Map<String,List<Anime>> relatedMap;


    static{
        notificationMessage = null;
        yearList = new ArrayList<>();
        seasonList = new ArrayList<>(Arrays.asList("全部","1月","4月","7月","10月"));
        statusList = new ArrayList<>(Arrays.asList("全部","连载","完结","未播出"));
        sortLit = new ArrayList<>(Arrays.asList("时间","名字"));
        weekdayList = new ArrayList<>();
        relatedMap = new HashMap<>();
    }

    @Value("${constant.img-resource-path}")
    public void setDynamicResourceRoot(String imgResourceRoot) {
        IMG_RESOURCE_ROOT = imgResourceRoot;
    }

    @Value("${constant.video-resource-path}")
    public void setVideoResourceRoot(String videoResourceRoot) {
        VIDEO_RESOURCE_ROOT = videoResourceRoot;
    }

    @Value("${constant.img-url-path}")
    public void setImgUrlRoot(String imgUrlRoot) {
        IMG_URL_ROOT = imgUrlRoot;
    }

    @Value("${constant.video-url-path}")
    public void setVideoUrlRoot(String videoUrlRoot){
        VIDEO_URL_ROOT = videoUrlRoot;
    }

    @Value("${constant.page-size}")
    public void setPageSize(long pageSize){
        PAGE_SIZE = pageSize;
    }

}
