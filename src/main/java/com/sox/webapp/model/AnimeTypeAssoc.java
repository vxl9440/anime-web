package com.sox.webapp.model;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimeTypeAssoc {
    @TableId
    private String animeId;
    @TableId
    private String typeName;
}
