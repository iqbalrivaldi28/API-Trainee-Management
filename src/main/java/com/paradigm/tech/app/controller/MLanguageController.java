package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.model.entityDTO.response.Enums.ResponseMLanguageDTO;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION)
@RequiredArgsConstructor
public class MLanguageController {
    private final MLanguageService mLanguageService;

//    @PostMapping("/mlanguage")
//    public ResponseEntity<MLanguage> saveMLanguage(@RequestBody Map<String,String> request){
//        String language = request.get("language");
//        MLanguage mLanguage = mLanguageService.saveMLanguage(language);
//        return new ResponseEntity<>(mLanguage, HttpStatus.CREATED);
//    }

    @GetMapping(ApiPathConstant.M_LANGUAGE)
    public ResponseEntity<CustomResponseDTO<?>> getAllMLanguage(){
        List<ResponseMLanguageDTO> mLanguage = mLanguageService.getAllMLanguage();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All languages fetched", mLanguage));
    }
    @GetMapping(ApiPathConstant.M_LANGUAGE + "/{id}")
    public ResponseEntity<CustomResponseDTO<?>> getMLanguageById(@PathVariable String id){
        ResponseMLanguageDTO mLanguage = mLanguageService.getMLanguageById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "Language fetched", mLanguage));
    }
}
