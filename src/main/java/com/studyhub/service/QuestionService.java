package com.studyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyhub.common.PageResult;
import com.studyhub.pojo.dto.QuestionCreateDTO;
import com.studyhub.pojo.dto.QuestionUpdateDTO;
import com.studyhub.pojo.entity.Question;
import com.studyhub.pojo.query.QuestionQuery;
import com.studyhub.pojo.vo.QuestionVO;

public interface QuestionService extends IService<Question> {

    PageResult<QuestionVO> pageQuestions(QuestionQuery query);

    QuestionVO getQuestionById(Long id);

    QuestionVO createQuestion(QuestionCreateDTO dto);

    QuestionVO updateQuestion(QuestionUpdateDTO dto);

    void deleteQuestionById(Long id);
}
