package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.Enums.ResponseMSkillDTO;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION)
@RequiredArgsConstructor
public class MSkillController {
    private final MSkillService mSkillService;

//    @PostMapping("/mskill")
//    public ResponseEntity<MSkill> saveMSkill(@RequestBody Map<String,String> request){
//        String skill = request.get("skill");//tarik dari request
//        MSkill savedSkill = mSkillService.saveMSkill(skill);//masukkan ke service trus ke entity
//        return new ResponseEntity<>(savedSkill, HttpStatus.CREATED);
//    }

    @GetMapping(ApiPathConstant.M_SKILL + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> getMSkillById(@PathVariable String id){
        ResponseMSkillDTO mSkill = mSkillService.getMSkillById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Skill fetched", mSkill));
    }

    @GetMapping(ApiPathConstant.M_SKILL)
    public ResponseEntity<CustomResponseDTO<?>> getAllMSkill(){
        List<ResponseMSkillDTO> mSkill = mSkillService.getAllMSkill();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All skills fetched", mSkill));
    }





}
