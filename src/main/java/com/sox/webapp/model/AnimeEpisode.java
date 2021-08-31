package com.sox.webapp.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Validated
@AllArgsConstructor
public class AnimeEpisode implements Comparable<AnimeEpisode>{

    @TableField(exist = false)
    private MultipartFile file;

    @NotNull
    @Length(min=1,max=40)
    @TableId
    private String animeId;

    @TableId
    @NotNull
    @Length(min=1,max = 64)
    private String md5;

    @NotNull
    @Length(min=1,max=10)
    private String episodeNumber;

    @NotNull
    @Length(min=1,max=36)
    private String episodeTitle;

    private String videoType;

    @Override
    public int compareTo(AnimeEpisode animeEpisode) {
        String tmp1 = this.episodeNumber.replace("第","").replace("集","");
        String tmp2 = animeEpisode.getEpisodeNumber().replace("第","").replace("集","");
        boolean b1 = true,b2 = true;
        try{
            Float.parseFloat(tmp1);
        }catch(NumberFormatException ne){
            b1 = false;
        }

        try{
            Float.parseFloat(tmp2);
        }catch(NumberFormatException ne){
            b2 = false;
        }

        if(b1 && b2){
            float n1 = Float.parseFloat(tmp1);
            float n2 = Float.parseFloat(tmp2);
            return n1 < n2?-1:1;
        }else if(b1){
            return -1;
        }else if(b2){
            return 1;
        }else{
            return tmp1.compareTo(tmp2);
        }
    }
}
