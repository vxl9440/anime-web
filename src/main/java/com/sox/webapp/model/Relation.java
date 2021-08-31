package com.sox.webapp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relation {
    @TableId(type = IdType.AUTO)
    private int relationId;

    @Length(min = 1,max = 32)
    @NotNull
    private String relationName;

    @TableField(exist = false)
    private List<Anime> animeList;
}
