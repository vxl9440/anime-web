package com.sox.webapp.validate;

import com.sox.webapp.exception.DataFormatInvalidException;
import com.sox.webapp.exception.VideoFormatNotSupportException;
import com.sox.webapp.factory.ReturnResultFactory;
import com.sox.webapp.model.AnimeEpisode;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.util.Constant;
import org.springframework.validation.BindingResult;

import java.util.Objects;


public class VideoValidator extends Validator{

    private final AnimeEpisode animeEpisode;

    public VideoValidator(BindingResult result,AnimeEpisode animeEpisode) {
        super(result);
        this.animeEpisode = animeEpisode;
    }

    @Override
    public ResponseDataSet<Object> getResult() {
        if(animeEpisode.getFile().isEmpty()){
            throw new VideoFormatNotSupportException("文件不能为空");
        }

        String[] fileToken = Objects.requireNonNull(animeEpisode.getFile().getOriginalFilename()).split("\\.");
        if(fileToken.length < 2){
            throw new VideoFormatNotSupportException("只支持MP4文件");
        }

        if(!"MP4".equalsIgnoreCase(fileToken[fileToken.length - 1])){
            throw new VideoFormatNotSupportException("只支持MP4文件");
        }

        if(animeEpisode.getAnimeId().length() > 40 ||
                animeEpisode.getEpisodeTitle().length() > 36 ||
                animeEpisode.getEpisodeNumber().length() > 10){
            throw new DataFormatInvalidException("不支持该数据格式");
        }

        return ReturnResultFactory.build(Constant.SUCCESS_CODE,"SUCCESS",animeEpisode);
    }

}
