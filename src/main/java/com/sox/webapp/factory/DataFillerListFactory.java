package com.sox.webapp.factory;

import com.sox.webapp.filler.BaseDataFiller;
import com.sox.webapp.filler.EpisodeTagDataFiller;
import com.sox.webapp.filler.ImageDataFiller;
import com.sox.webapp.filler.TypeDataFiller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataFillerListFactory {

    private static TypeDataFiller typeDataFiller;
    private static EpisodeTagDataFiller episodeTagDataFiller;
    private static ImageDataFiller imageDataFiller;

    @Autowired
    public void setTypeDataFiller(TypeDataFiller typeDataFiller){
        DataFillerListFactory.typeDataFiller = typeDataFiller;
    }

    @Autowired
    public void setEpisodeTagDataFiller(EpisodeTagDataFiller episodeTagDataFiller) {
        DataFillerListFactory.episodeTagDataFiller = episodeTagDataFiller;
    }

    @Autowired
    public void setImageDataFiller(ImageDataFiller imageDataFiller) {
        DataFillerListFactory.imageDataFiller = imageDataFiller;
    }

    public static List<BaseDataFiller> build(boolean type, boolean img, boolean epiTag){
        List<BaseDataFiller> list = new ArrayList<>();
        if(type) list.add(typeDataFiller);
        if(img) list.add(imageDataFiller);
        if(epiTag) list.add(episodeTagDataFiller);
        return list;
    }
}
