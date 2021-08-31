package com.sox.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sox.webapp.exception.*;
import com.sox.webapp.factory.DataFillerListFactory;
import com.sox.webapp.factory.ReturnResultFactory;
import com.sox.webapp.filler.BaseDataFiller;
import com.sox.webapp.mapper.AnimeMapper;
import com.sox.webapp.model.*;
import com.sox.webapp.service.AnimeService;
import com.sox.webapp.util.Constant;
import com.sox.webapp.util.FileUtil;
import com.sox.webapp.util.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


@Service
public class AnimeServiceImpl implements AnimeService {

    private final AnimeMapper animeMapper;
    private final AnimeTypeAssocServiceImpl animeTypeAssocService;

    public AnimeServiceImpl(AnimeMapper animeMapper,
                            AnimeTypeAssocServiceImpl typeAssocService) {
        this.animeMapper = animeMapper;
        this.animeTypeAssocService = typeAssocService;
    }

    @Override
    public Anime getAnimeById(String animeId, List<BaseDataFiller> fillerList) {
        QueryWrapper<Anime> wrapper = new QueryWrapper<>();
        wrapper.eq("animeId",animeId);
        List<Anime> animeList = animeMapper.selectList(wrapper);
        if(animeList.size() == 0){
            throw new DataNotFoundException("无法找到该动画");
        }
        return this.fillData(animeList,fillerList).get(0);
    }

    @Override
    public List<String> getAllAnimeId() {
        return animeMapper.getAllAnimeId();
    }

    @Override
    public List<Anime> getAnimeRandomly(int pageSize, List<BaseDataFiller> fillerList) {
        QueryWrapper<Anime> wrapper = new QueryWrapper<>();
        wrapper.eq("locked",0);
        Page<Anime> page = new Page<>(1,pageSize);
        return this.fillData(animeMapper.selectPage(page,wrapper).getRecords(),fillerList);
    }

    @Override
    public List<Anime> getAnimeByKeyword(String keyword,boolean includeLocked,List<BaseDataFiller> fillerList) {
        QueryWrapper<Anime> queryWrapper = buildQueryWrapperByKeyword(keyword,includeLocked);
        return this.fillData(animeMapper.selectList(queryWrapper),fillerList);
    }

    @Override
    public List<Anime> getAnimeByCategories(String categories,boolean includeLocked,List<BaseDataFiller> fillerList) {
        // 0 = year, 1 = season, 2 = type, 3 = status, 4 = sortBy， 5 = pageNumber
        String[] token = categories.split("-");

        int currentPage;
        try{
            currentPage = Integer.parseInt(token[5]);
        } catch (NumberFormatException e){
            return new ArrayList<>();
        }

        QueryWrapper<Anime> wrapper = new QueryWrapper<>();
        if(!includeLocked){
            wrapper.eq("locked",0);
        }
        this.buildQueryWrapperByCategory(token,wrapper);

        if ("名字".equals(token[4])) { // sortBy
            wrapper.orderByAsc("chineseName");
        } else {
            wrapper.orderByDesc("releasedDate");
        }

        Page<Anime> page = new Page<>(currentPage,Constant.PAGE_SIZE);
        return this.fillData(animeMapper.selectPage(page,wrapper).getRecords(),fillerList);
    }

    @Override
    public List<Anime> getAnimeByUsername(String username,int currentPage,List<BaseDataFiller> fillerList) {
        return this.fillData(animeMapper.getAnimeByUsername(username),fillerList);
    }

    @Override
    public List<Anime> getRecommendAnime(int size) {
        return this.fillData(animeMapper.getRecommendAnime(size),
                DataFillerListFactory.build(false,true,true));
    }

    @Override
    public List<Anime> getUpdatedAnime(int size) {
        return this.fillData(animeMapper.getUpdatedAnime(size),
                DataFillerListFactory.build(false,true,true));
    }

    @Override
    public List<Anime> getRelatedAnimeByAnimeId(String animeId, int size) {
        return this.fillData(animeMapper.getRelatedAnimeByAnimeId(animeId,size),
                DataFillerListFactory.build(false,true,true));
    }

    @Override
    public List<Anime> getAllLockedAnime() {
        QueryWrapper<Anime> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("locked",1);
        return fillData(animeMapper.selectList(queryWrapper),
                DataFillerListFactory.build(false,true,true));
    }

    @Override
    public List<Anime> getAllSlide() {
        List<Anime> animeList = animeMapper.getAllSlide();
        boolean flag = true;
        for (Anime anime : animeList) {
            if(flag){
                anime.setLocked(1);
                flag = false;
            }else{
                anime.setLocked(0);
            }
            anime.setCoverUrl(Constant.IMG_URL_ROOT+"\\slides\\"+anime.getCoverUrl());
        }
        return animeList;
    }

    @Override
    public List<Anime> getAnimeByWeekday(int weekday, String status) {
        return this.fillData(animeMapper.getAnimeByWeekday(weekday,status),
                DataFillerListFactory.build(false,true,true));
    }

    @Override
    public int getCountByCategory(String categories,boolean includeLocked) {
        QueryWrapper<Anime> wrapper = new QueryWrapper<>();
        String[] token = categories.split("-");
        if(token.length != 6){ // must be 5
            return 0;
        }
        if(!includeLocked){
            wrapper.eq("locked",0);
        }
        this.buildQueryWrapperByCategory(token,wrapper);
        return animeMapper.selectCount(wrapper);
    }

    @Override
    public int getCountByKeyword(String keyword,boolean includeLocked) {
        QueryWrapper<Anime> queryWrapper = buildQueryWrapperByKeyword(keyword,includeLocked);
        return animeMapper.selectCount(queryWrapper);
    }

    @Override
    public int getCountByLocked() {
        QueryWrapper<Anime> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("locked",1);
        return animeMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional
    public int deleteAnime(String animeId) {
        FileUtil.deleteDirectory(Constant.VIDEO_RESOURCE_ROOT+animeId); // delete video file
        Anime anime = this.getAnimeById(animeId,null);
        if(!new File(Constant.IMG_RESOURCE_ROOT+anime.getCoverUrl()).delete()){
            // delete image file
            throw new FileFailOperationException("服务器错误");
        }
        return animeMapper.deleteById(animeId); // delete anime from DB
    }

    @Override
    public int likeAnimeExist(String username, String animeId) {
        return animeMapper.likeAnimeExist(username,animeId);
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> insertSlide(String animeId, MultipartFile imageFile) {
        try{
            BufferedImage image = ImageIO.read(imageFile.getInputStream());
            if(image == null){
                throw new ImageFormatNotSupportException("文件格式不支持");
            }
            int height = image.getHeight();
            int width = image.getWidth();
            if(width < 980 || width > 1180 || height < 316 || height > 386){
                throw new ImageFormatNotSupportException("图片宽高必须最多大于或小于其自身的10%");
            }
        }catch (IOException e){
            throw new FileFailOperationException("服务器错误");
        }
        String uuid = UUID.randomUUID().toString();
        String [] token = Objects.requireNonNull(imageFile.getOriginalFilename()).split("\\.");
        String imageType = token[token.length - 1];
        String dest = Constant.IMG_RESOURCE_ROOT+"\\slides\\"+uuid+"."+imageType;
        if(!ImageUtil.resizeImage(imageFile,dest,1080,352,true)){
            throw new FileFailOperationException("服务器出错,请稍后重试");
        }
        if(animeMapper.insertAnimeSlide(animeId,uuid+"."+imageType) == 0){
            throw new SqlFailOperationException("Oops 系统错误，请稍后重试");
        }
        return ReturnResultFactory.build(Constant.SUCCESS_CODE,"SUCCESS",null);
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> deleteSlide(String animeId) {
        String coverUrl = animeMapper.getSlideByAnimeId(animeId);
        if(coverUrl!= null){
            File file1 = new File(Constant.IMG_RESOURCE_ROOT+"\\slides\\"+coverUrl);
            if(!file1.delete()){
                System.out.println("IGNORE 文件不存在");
            }
            if(animeMapper.deleteSlide(animeId) == 0){
                throw new SqlFailOperationException("Oops 系统错误，请稍后重试");
            }
        }else{
            throw new DataFormatInvalidException("数据格式不匹配");
        }
        return ReturnResultFactory.build(Constant.SUCCESS_CODE,"SUCCESS",null);
    }

    private List<Anime> fillData(List<Anime> animeList,List<BaseDataFiller> fillerList){
        if(fillerList != null){
            for (BaseDataFiller baseDataFiller : fillerList) {
                baseDataFiller.fill(animeList);
            }
        }
        return animeList;
    }

    // construct QueryWrapper
    private void buildQueryWrapperByCategory(String[] token,QueryWrapper<Anime> wrapper){
        if(!"全部".equals(token[0])){ // year
            wrapper.likeRight("releasedDate",token[0]);
        }

        if(!"全部".equals(token[1])){ // season
            token[1] = token[1].substring(0,token[1].length() - 1); // get month
            if(token[1].length() == 1) token[1] = "0"+token[1];
            wrapper.like("releasedDate",token[1]);
        }

        if(!"全部".equals(token[2])){ // type
            wrapper.inSql("animeId","select animeId from anime_type_assoc where typename = '"+token[2]+"'");
        }

        if(!"全部".equals(token[3])){ // status
            wrapper.eq("status",token[3]);
        }

        if ("名字".equals(token[4])) { // sortBy
            wrapper.orderByAsc("chineseName");
        } else {
            wrapper.orderByDesc("releasedDate");
        }
    }

    private QueryWrapper<Anime> buildQueryWrapperByKeyword(String keyword,boolean includeLocked){
        QueryWrapper<Anime> queryWrapper = new QueryWrapper<>();
        if(!includeLocked){
            queryWrapper.eq("locked",0);
        }

        String[] keys = keyword.split("[\\s-,]");
        queryWrapper.and(wrapper ->{
            QueryWrapper<Anime> temp = wrapper;
            for (String key : keys){
                if(key.trim().length() > 0){
                    temp = temp.like("chineseName", key).or().like("originalName",key).or();
                }
            }
        });
        return queryWrapper;
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> insertAnime(ResponseDataSet<Object> dataSet) {
        Anime anime = (Anime) dataSet.getData();
        String uuid = UUID.randomUUID().toString();
        anime.setAnimeId(uuid);
        Date date = new Date(System.currentTimeMillis());
        anime.setUploadedDate(date);
        anime.setUpdatedDate(date);
        anime.setLocked(1);
        if(animeMapper.insert(anime) == 0){
            throw new SqlFailOperationException("Oops 系统错误，请稍后重试");
        }
        for (String type : anime.getTypes()) {
            if(animeTypeAssocService.insertAnimeTypeAssoc(new AnimeTypeAssoc(uuid,type)) == 0){
                throw new SqlFailOperationException("Oops 系统错误，请稍后重试");
            }
        }
        dataSet.setData(uuid);
        return dataSet;
    }

    @Override
    public ResponseDataSet<Object> insertLikeAnime(String username, String animeId) {
        if(animeId != null){
            if(animeMapper.insertLikeAnime(username,animeId) == 0){
                throw new SqlFailOperationException("Oops 系统错误，请稍后重试");
            }
            return ReturnResultFactory.build(Constant.SUCCESS_CODE,"SUCCESS",null);
        }else{
            return ReturnResultFactory.build(Constant.FAIL_CODE,"账号ID不能为空",null);
        }
    }

    @Override
    public ResponseDataSet<Object> deleteLikeAnime(String username, String animeId) {
        if(animeId != null){
            if(animeMapper.deleteLikeAnime(username,animeId) == 0){
                throw new SqlFailOperationException("Oops 系统错误，请稍后重试");
            }
            return ReturnResultFactory.build(Constant.SUCCESS_CODE,"SUCCESS",null);
        }else{
            return ReturnResultFactory.build(Constant.FAIL_CODE,"账号ID不能为空",null);
        }
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> updateAnimeCover(ResponseDataSet<Object> dataSet) {
        ImageEntity imageEntity = (ImageEntity) dataSet.getData();
        String uuid = UUID.randomUUID().toString();
        Anime anime = this.getAnimeById(imageEntity.getAnimeId(),null);
        if(anime.getCoverUrl() != null){
            File file1 = new File(Constant.IMG_RESOURCE_ROOT+anime.getCoverUrl());
            if(!file1.delete()){
                System.out.println("IGNORE 文件不存在");
            }
        }

        String dest = Constant.IMG_RESOURCE_ROOT+"\\"+uuid+"."+imageEntity.getFileType();

        if(!ImageUtil.resizeImage(imageEntity.getFile(),dest,252,352,true)){
            throw new FileFailOperationException("服务器出错,请稍后重试");
        }

        anime.setUpdatedDate(new Date(System.currentTimeMillis()));
        anime.setCoverUrl(uuid+"."+imageEntity.getFileType());
        if(animeMapper.updateById(anime) == 0){
            throw new SqlFailOperationException("服务器出错,请稍后重试");
        }
        dataSet.setData(null);
        return dataSet;
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> updateAnimeInfo(ResponseDataSet<Object> dataSet) {
        Anime anime = (Anime) dataSet.getData();
        UpdateWrapper<Anime> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("animeId",anime.getAnimeId())
                .set("chineseName",anime.getChineseName())
                .set("originalName",anime.getOriginalName())
                .set("releasedDate",anime.getReleasedDate())
                .set("status",anime.getStatus())
                .set("introduction",anime.getIntroduction());
        if(animeMapper.update(null,updateWrapper) == 0){
            throw new SqlFailOperationException("服务器出错,请稍后重试"); //update basic info
        }
        animeTypeAssocService.deleteTypesByAnimeId(anime.getAnimeId()); // delete old types
        for (String type : anime.getTypes()) { // insert new type
            if(animeTypeAssocService.insertAnimeTypeAssoc(new AnimeTypeAssoc(anime.getAnimeId(),type)) == 0){
                throw new SqlFailOperationException("Oops 系统错误，请稍后重试");
            }
        }
        dataSet.setData(null);
        return dataSet;
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> updateAnimeLockStatus(String animeId,Integer lockStatus) {
        UpdateWrapper<Anime> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("animeId",animeId).set("locked",lockStatus);
        if(animeMapper.update(null,updateWrapper) == 0){
            throw new SqlFailOperationException("Oops 系统错误，请稍后重试");
        }
        Constant.relatedMap.put(animeId,this.getRelatedAnimeByAnimeId(animeId,8));
        return ReturnResultFactory.build(Constant.SUCCESS_CODE,"SUCCESS",null);
    }
}
