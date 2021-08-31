/*
 * @version: V1.0
 * @author: Miaoxin Li
 * @className: AdminRelationController
 * @packageName: com.sox.webapp.controller.admin
 * @description: A class to handle http requests and responses for admin relation service
 * @date: 2021-07-21
 */
package com.sox.webapp.controller.admin;

import com.sox.webapp.model.AnimeRelation;
import com.sox.webapp.model.Relation;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.service.impl.RelationServiceImpl;
import com.sox.webapp.validate.RelationValidator;
import com.sox.webapp.validate.Validator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AdminRelationController {

    private final RelationServiceImpl relationService;

    public AdminRelationController(RelationServiceImpl relationService) {
        this.relationService = relationService;
    }

    /**
     * do relation creation service
     * @param relation from user, contains relation name
     * @param result validation result for relation
     * @return a set of data the tell user whether operation success or fail
     */
    @RequestMapping("/admin/createRelation")
    public ResponseDataSet<Object> createRelation(@RequestBody @Valid Relation relation, BindingResult result) {
        Validator validator = new RelationValidator(result,relation);
        return relationService.insertRelation(validator.getResult());
    }

    /**
     * do anime relation creation service
     * @param animeRelation from user, contains relation id and anime id
     * @param result validation result for animeRelation
     * @return a set of data the tell user whether operation success or fail
     */
    @RequestMapping("/admin/createRelationAnime")
    public ResponseDataSet<Object> createAnimeRelation(@RequestBody @Valid AnimeRelation animeRelation, BindingResult result) {
        Validator validator = new RelationValidator(result,animeRelation);
        return relationService.insertAnimeRelation(validator.getResult());
    }

    /**
     * do relation creation service
     * @param relation from user, contains relation new name
     * @param result validation result for relation
     * @return a set of data the tell user whether operation success or fail
     */
    @RequestMapping("/admin/changeRelationName")
    public ResponseDataSet<Object> changeRelationName(@RequestBody @Valid Relation relation, BindingResult result) {
        Validator validator = new RelationValidator(result,relation);
        return relationService.updateRelation(validator.getResult());
    }

    /**
     * do anime relation deletion service
     * @param animeRelation from user, contains relation id and anime id
     * @param result validation result for animeRelation
     * @return a set of data the tell user whether operation success or fail
     */
    @RequestMapping("/admin/deleteRelationAnime")
    public ResponseDataSet<Object> deleteAnimeRelation(@RequestBody @Valid AnimeRelation animeRelation, BindingResult result) {
        Validator validator = new RelationValidator(result,animeRelation);
        return relationService.deleteAnimeRelation(validator.getResult());
    }

    /**
     * do relation deletion service
     * @param relation from user, contains relation id
     * @param result validation result for relation
     * @return a set of data the tell user whether operation success or fail
     */
    @RequestMapping("/admin/deleteRelation")
    public ResponseDataSet<Object> deleteRelation(@RequestBody @Valid Relation relation, BindingResult result) {
        Validator validator = new RelationValidator(result,relation);
        return relationService.deleteRelation(validator.getResult());
    }
}
