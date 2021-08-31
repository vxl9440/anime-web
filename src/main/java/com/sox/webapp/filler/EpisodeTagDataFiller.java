package com.sox.webapp.filler;

import com.sox.webapp.model.Anime;
import com.sox.webapp.service.impl.AnimeEpisodeServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EpisodeTagDataFiller implements BaseDataFiller{

    private final AnimeEpisodeServiceImpl animeEpisodeService;

    public EpisodeTagDataFiller(AnimeEpisodeServiceImpl animeEpisodeService){
        this.animeEpisodeService = animeEpisodeService;
    }

    @Override
    public void fill(List<Anime> animeList) {
        for (Anime anime : animeList) {
            float max = 0.0f;
            List<String> episodeNumbers = animeEpisodeService.getAllEpisodeByAnimeId(anime.getAnimeId());
            if(episodeNumbers.size() == 1 && "全集".equals(episodeNumbers.get(0))){
                anime.setEpisodeTag(episodeNumbers.get(0));
                continue;
            }
            for (String episodeNumber : episodeNumbers) {
                try{
                    float tmp = Float.parseFloat(episodeNumber.replace("第","").
                            replace("集",""));
                    max = Math.max(max,tmp);
                }catch(NumberFormatException ne){
                    //nothing
                }
            }

            String f;
            if(Math.ceil(max) == Math.floor(max)){
                f = Integer.toString((int)max);
            }else{
                f = max+"";
            }

            switch(anime.getStatus()){
                case "完结":
                    anime.setEpisodeTag("全"+f+"集");
                    break;
                case "连载":
                    anime.setEpisodeTag("更新至"+f+"集");
                    break;
                default:
                    anime.setEpisodeTag("未开播");
                    break;
            }
        }
    }
}
