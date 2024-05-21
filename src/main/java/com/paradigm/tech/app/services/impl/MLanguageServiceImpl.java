package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.M_Language;
import com.paradigm.tech.app.model.entityDTO.response.Enums.ResponseMLanguageDTO;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MLanguageServiceImpl implements MLanguageService {
    private final MLanguageRepository mLanguageRepository;

//    @Override
//    public MLanguage saveMLanguage(String language) {
//        try {
//            ELanguage eLanguage = ELanguage.valueOf(language.toUpperCase()); //dapatkan string utk di convert ke enum
//
//            //masukkan json nya ke data object
//            MLanguage mlanguage = MLanguage.builder().
//                    language_name(eLanguage).build();
//
//            //masukkan data obj ke tabel
//            return mLanguageRepository.save(mlanguage);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Invalid language type provided", e);
//        }
//
//    }

    @Override
    public List<ResponseMLanguageDTO> getAllMLanguage() {
        try {
            List<M_Language> mLanguages = mLanguageRepository.findAll();
            List<ResponseMLanguageDTO> mLanguageDTOList = new ArrayList<>();
            for (M_Language mLanguage : mLanguages) {
                ResponseMLanguageDTO mLanguageDTO = ResponseMLanguageDTO.builder()
                        .id(mLanguage.getId())
                        .languageName(mLanguage.getLanguageName().getLanguageName())
                        .build();
                mLanguageDTOList.add(mLanguageDTO);
            }
            return mLanguageDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseMLanguageDTO getMLanguageById(String id) {
        try {
            M_Language mLanguage = mLanguageRepository.findById(id).get();
            return ResponseMLanguageDTO.builder()
                    .id(mLanguage.getId())
                    .languageName(mLanguage.getLanguageName().getLanguageName())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }
}
