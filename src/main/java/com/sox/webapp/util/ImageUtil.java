package com.sox.webapp.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageUtil {

    public static boolean resizeImage (MultipartFile file, String destPath, int newWith, int newHeight, boolean forceSize){
        try{
            if (forceSize) {
                Thumbnails.of(file.getInputStream()).forceSize(newWith, newHeight).toFile(destPath);
            } else {
                Thumbnails.of(file.getInputStream()).width(newWith).height(newHeight).toFile(destPath);
            }
        }catch(IOException ioe){
            return false;
        }
        return true;
    }

//    public static boolean resizeImage (String src, String destPath, int newWith, int newHeight, boolean forceSize){
//        try{
//            if (forceSize) {
//                Thumbnails.of(src).forceSize(newWith, newHeight).toFile(destPath);
//            } else {
//                Thumbnails.of(src).width(newWith).height(newHeight).toFile(destPath);
//            }
//        }catch(IOException ioe){
//            return false;
//        }
//        return true;
//    }
//
//    public static boolean resizeImage (File file, String destPath, int newWith, int newHeight, boolean forceSize){
//        try{
//            if (forceSize) {
//                Thumbnails.of(file).forceSize(newWith, newHeight).toFile(destPath);
//            } else {
//                Thumbnails.of(file).width(newWith).height(newHeight).toFile(destPath);
//            }
//        }catch(IOException ioe){
//            return false;
//        }
//        return true;
//    }
}
