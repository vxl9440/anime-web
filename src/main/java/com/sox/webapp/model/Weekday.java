package com.sox.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weekday {
    private String weekday;
    private String ref;
    private List<Anime> animeList;
}
