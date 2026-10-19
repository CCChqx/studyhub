package com.studyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyhub.pojo.dto.SubjectCreateDTO;
import com.studyhub.pojo.dto.SubjectUpdateDTO;
import com.studyhub.pojo.entity.Subject;
import com.studyhub.pojo.vo.SubjectVO;

import java.util.List;

public interface SubjectService extends IService<Subject> {

    SubjectVO createSubject(SubjectCreateDTO dto);

    List<SubjectVO> listSubjects();

    SubjectVO getSubjectById(Long id);

    SubjectVO updateSubject(SubjectUpdateDTO dto);

    void deleteSubjectById(Long id);
}
