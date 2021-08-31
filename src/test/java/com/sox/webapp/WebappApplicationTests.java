package com.sox.webapp;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sox.webapp.mapper.AnimeMapper;
import com.sox.webapp.mapper.RelationMapper;
import com.sox.webapp.model.Anime;
import com.sox.webapp.service.impl.AnimeServiceImpl;
import com.sox.webapp.util.ImageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@SpringBootTest
class WebappApplicationTests {

    @Autowired
    AnimeServiceImpl animeService;

    @Test
    void contextLoads() throws IOException {

        List<Anime> list = animeService.getAnimeByKeyword("to love rewrite fine",true,null);
        for (Anime anime : list) {
            System.out.println(anime.getChineseName());
        }
    }

}
