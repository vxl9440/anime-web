package com.sox.webapp.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Slf4j
public class FileUtil {

    // File to md5
    public static String getMD5(MultipartFile file){
        BigInteger bi;
        try {
            byte[] buffer = new byte[8192];
            int len;
            MessageDigest md = MessageDigest.getInstance("MD5");
            InputStream inputStream = file.getInputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            inputStream.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.println(e.toString());
            return null;
        }

        return bi.toString(16);
    }

    public static boolean write(MultipartFile file, String path,String fileName){
        try{
            File file1 = new File(path);
            if(!file1.exists()){
                file1.mkdir();
            }
            file.transferTo(new File(path+"\\"+fileName));
        }catch(IOException ioe){
            return false;
        }
        return true;
    }

    public static void deleteDirectory(String dirPath){
        boolean flag = false;
        File file = new File(dirPath);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files != null){
                for (File file1 : files) {
                    if(!file1.delete()){
                        flag = true;
                    }
                }
            }
        }else{
            flag = true;
        }
        if(!flag){
            file.delete();
        }else{
            log.error("Directory ["+dirPath+"] failed to be deleted");
        }
    }

}
