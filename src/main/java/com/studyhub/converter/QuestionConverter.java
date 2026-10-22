package com.studyhub.converter;

import com.studyhub.pojo.dto.QuestionCreateDTO;
import com.studyhub.pojo.entity.Question;
import com.studyhub.pojo.vo.QuestionVO;

/**
 * Question entity <-> VO 转换。
 */
public final class QuestionConverter {

    private QuestionConverter() {
    }

    public static Question toEntity(QuestionCreateDTO dto) {
        Question entity = new Question();
        entity.setSubjectId(dto.getSubjectId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setKpId(dto.getKpId());
        entity.setType(dto.getType());
        entity.setStem(dto.getStem());
        entity.setOptions(dto.getOptions());
        entity.setAnswer(dto.getAnswer());
        entity.setAnalysis(dto.getAnalysis());
        entity.setStatus(1);
        return entity;
    }

    public static QuestionVO toVO(Question entity) {
        return QuestionVO.builder()
                .id(entity.getId())
                .subjectId(entity.getSubjectId())
                .categoryId(entity.getCategoryId())
                .kpId(entity.getKpId())
                .type(entity.getType())
                .stem(entity.getStem())
                .options(entity.getOptions())
                .answer(entity.getAnswer())
                .analysis(entity.getAnalysis())
                .status(entity.getStatus())
                .createTime(entity.getCreateTime())
                .build();
    }
}
