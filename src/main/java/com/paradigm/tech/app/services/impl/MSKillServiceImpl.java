package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.M_Skill;
import com.paradigm.tech.app.model.entityDTO.response.Enums.ResponseMSkillDTO;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class MSKillServiceImpl implements MSkillService {
    private final MSkillRepository mSkillRepository;

//    @Override
//    public MSkill saveMSkill(String skill) {
//        try {
//            // Convert string to enum constant
//            ESkill eSkill = ESkill.valueOf(skill.toUpperCase());
//
//            // Create MSkill object
//            MSkill mSkill = MSkill.builder()
//                    .skill_name(eSkill)
//                    .build();
//
//            // Save and return the MSkill object
//            return mSkillRepository.save(mSkill);
//        } catch (IllegalArgumentException e) {
//            throw new RuntimeException("Invalid skill type provided", e);
//        }
//    }

    @Override
    public List<ResponseMSkillDTO> getAllMSkill() {
        try {
            List<M_Skill> mSkills = mSkillRepository.findAll();
            List<ResponseMSkillDTO> mSkillDTOList = new ArrayList<>();
            for (M_Skill mSkill : mSkills) {
                ResponseMSkillDTO mSkillDTO = ResponseMSkillDTO.builder()
                        .id(mSkill.getId())
                        .skillName(mSkill.getSkillName().getSkillName())
                        .build();
                mSkillDTOList.add(mSkillDTO);
            }
            return mSkillDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseMSkillDTO getMSkillById(String id) {
        try {
            M_Skill mSkill = mSkillRepository.findById(id).get();
            return ResponseMSkillDTO.builder()
                    .id(mSkill.getId())
                    .skillName(mSkill.getSkillName().getSkillName())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }
}
