package com.sox.webapp.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;



import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@Validated
@AllArgsConstructor
public class Anime {

    @TableId
    private String animeId;

    @Length(min=1,max=64)
    @NotNull
    private String chineseName;

    @Length(min=1,max=64)
    @NotNull
    private String originalName;

    @NotNull
    private Date releasedDate;

    @TableField(fill = FieldFill.INSERT)
    private Date uploadedDate;

    @TableField(fill = FieldFill.UPDATE)
    private Date updatedDate;

    @TableField(exist = false)
    private List<String> types;

    @Length(min=1,max=512)
    @NotNull
    private String introduction;

    @Length(min=1,max=5)
    @NotNull
    private String status;

    private String coverUrl;

    @TableField(exist = false)
    private String episodeTag;

    private int popularity;

    private int locked;
}

