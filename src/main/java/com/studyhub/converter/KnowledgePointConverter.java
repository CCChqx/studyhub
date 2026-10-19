package com.studyhub.converter;

import com.studyhub.pojo.dto.KnowledgePointCreateDTO;
import com.studyhub.pojo.entity.KnowledgePoint;
import com.studyhub.pojo.vo.KnowledgePointVO;

/**
 * KnowledgePoint entity <-> VO 转换。
 */
public final class KnowledgePointConverter {

    private KnowledgePointConverter() {
    }

    public static KnowledgePointVO toVO(KnowledgePoint entity) {
        return KnowledgePointVO.builder()
                .id(entity.getId())
                .subjectId(entity.getSubjectId())
                .categoryId(entity.getCategoryId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .tags(entity.getTags())
                .importance(entity.getImportance())
                .build();
    }

    public static KnowledgePoint toEntity(KnowledgePointCreateDTO dto) {
        KnowledgePoint entity = new KnowledgePoint();
        entity.setSubjectId(dto.getSubjectId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setTags(dto.getTags());
        entity.setImportance(dto.getImportance());
        return entity;
    }
}
