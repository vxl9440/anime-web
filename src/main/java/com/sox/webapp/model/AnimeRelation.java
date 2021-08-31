package com.sox.webapp.model;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeRelation {
    @TableId
    @NotNull
    private Integer relationId;

    @TableId
    @NotNull
    @Length(min = 1,max = 40)
    private String animeId;
}
