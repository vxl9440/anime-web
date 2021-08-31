package com.sox.webapp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @TableId(type = IdType.AUTO)
    private int commentId;

    private String username;

    @NotNull
    private String animeId;

    @NotNull
    @Length(min=1,max=256,message = "评论长度在1至256之间")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "UTC-4")
    private Date postDate;
}
