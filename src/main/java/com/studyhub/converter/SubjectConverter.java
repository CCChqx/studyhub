package com.studyhub.converter;

import com.studyhub.pojo.dto.SubjectCreateDTO;
import com.studyhub.pojo.entity.Subject;
import com.studyhub.pojo.vo.SubjectVO;

/**
 * Subject entity <-> VO 转换。工具类，私有构造器禁止实例化。
 */
public final class SubjectConverter {

    private SubjectConverter() {
    }

    public static SubjectVO toVO(Subject entity) {
        return SubjectVO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .description(entity.getDescription())
                .build();
    }

    public static Subject toEntity(SubjectCreateDTO dto){
        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setCode(dto.getCode());
        subject.setDescription(dto.getDescription());
        return subject;
    }
}
