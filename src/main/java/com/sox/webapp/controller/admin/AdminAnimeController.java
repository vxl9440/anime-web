package com.sox.webapp.controller.admin;

import com.sox.webapp.exception.DataFormatInvalidException;
import com.sox.webapp.factory.ReturnResultFactory;
import com.sox.webapp.model.Anime;
import com.sox.webapp.model.AnimeEpisode;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.service.impl.AnimeEpisodeServiceImpl;
import com.sox.webapp.service.impl.AnimeServiceImpl;
import com.sox.webapp.util.Constant;
import com.sox.webapp.validate.AnimeInfoValidator;
import com.sox.webapp.validate.ImageValidator;
import com.sox.webapp.validate.Validator;
import com.sox.webapp.validate.VideoValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
public class AdminAnimeController {

    private final AnimeServiceImpl animeService;
    private final AnimeEpisodeServiceImpl animeEpisodeService;

    public AdminAnimeController(AnimeServiceImpl animeService, AnimeEpisodeServiceImpl animeEpisodeService) {
        this.animeService = animeService;
        this.animeEpisodeService = animeEpisodeService;
    }


    @RequestMapping("/admin/createAnime")
    @ResponseBody
    public ResponseDataSet<Object> createAnime(@RequestBody @Valid Anime anime, BindingResult result){
        Validator validator = new AnimeInfoValidator(anime,result);
        return animeService.insertAnime(validator.getResult());
    }

    @RequestMapping("/admin/deleteAnime/{animeId}")
    public String deleteAnime(@PathVariable("animeId") String animeId){
        if(animeService.deleteAnime(animeId) >= 1){
            return "redirect:/admin/adminAnimeCategory/all";
        }else{
            return "/error/404.html";
        }
    }

    @RequestMapping("/admin/updateAnimeLockStatus")
    @ResponseBody
    public ResponseDataSet<Object> updateAnimeLockStatus(@RequestParam("animeId") String animeId,
                                                         @RequestParam("lockStatus") Integer lockStatus){
        return animeService.updateAnimeLockStatus(animeId,lockStatus);
    }


    @RequestMapping("/admin/updateAnimeInfo")
    @ResponseBody
    public ResponseDataSet<Object> updateAnimeInfo(@RequestBody @Valid Anime anime, BindingResult result){
        Validator validator = new AnimeInfoValidator(anime,result);
        return animeService.updateAnimeInfo(validator.getResult());
    }


    @RequestMapping("/admin/uploadCover")
    @ResponseBody
    public ResponseDataSet<Object> uploadAnimeCover(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("animeId") String animeId) {
        Validator validator = new ImageValidator(null,file,animeId);
        return animeService.updateAnimeCover(validator.getResult());
    }

    @RequestMapping("/admin/uploadVideo")
    @ResponseBody
    public ResponseDataSet<Object> uploadVideo(@RequestParam("file") MultipartFile file,
                                               @RequestParam("animeId") String animeId,
                                               @RequestParam("episodeTitle") String episodeTitle,
                                               @RequestParam("episodeNumber") String episodeNumber){
        AnimeEpisode animeEpisode = new AnimeEpisode(file,animeId,null,episodeNumber,episodeTitle,"mp4");
        Validator validator = new VideoValidator(null,animeEpisode);
        return animeEpisodeService.insertAnimeEpisode(validator.getResult());
    }

    @RequestMapping("/admin/updateVideoInfo")
    @ResponseBody
    public ResponseDataSet<Object> uploadVideoInfo(@RequestBody @Valid AnimeEpisode animeEpisode, BindingResult result) {
        if(result.hasErrors()){
            throw new DataFormatInvalidException(result.getAllErrors().get(0).getDefaultMessage());
        }
        return animeEpisodeService.updateAnimeEpisode(ReturnResultFactory.build(Constant.SUCCESS_CODE,"SUCCESS",animeEpisode));
    }

    @RequestMapping("/admin/deleteVideo")
    @ResponseBody
    public ResponseDataSet<Object> deleteVideo(@RequestBody @Valid AnimeEpisode animeEpisode, BindingResult result) {
        if(result.hasErrors()){
            throw new DataFormatInvalidException(result.getAllErrors().get(0).getDefaultMessage());
        }
        return animeEpisodeService.deleteAnimeEpisode(ReturnResultFactory.build(Constant.SUCCESS_CODE,"SUCCESS",animeEpisode));
    }

    @RequestMapping("/admin/insertSlide")
    @ResponseBody
    public ResponseDataSet<Object> insertSlide(@RequestParam("animeId") String animeId,
                                               @RequestParam("file") MultipartFile file) {
        return animeService.insertSlide(animeId,file);
    }

    @RequestMapping("/admin/deleteSlide")
    @ResponseBody
    public ResponseDataSet<Object> deleteSlide(@RequestParam("animeId") String animeId){
        return animeService.deleteSlide(animeId);
    }
}
