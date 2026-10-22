package com.studyhub.controller;

import com.studyhub.common.PageResult;
import com.studyhub.common.Result;
import com.studyhub.pojo.dto.QuestionCreateDTO;
import com.studyhub.pojo.dto.QuestionUpdateDTO;
import com.studyhub.pojo.query.QuestionQuery;
import com.studyhub.pojo.vo.QuestionVO;
import com.studyhub.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "题目管理")
@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "题目分页查询")
    @GetMapping
    public Result<PageResult<QuestionVO>> listQuestions(QuestionQuery query) {
        return Result.success(questionService.pageQuestions(query));
    }

    @Operation(summary = "根据 id 查询题目")
    @GetMapping("/{id}")
    public Result<QuestionVO> getQuestionById(@PathVariable Long id) {
        return Result.success(questionService.getQuestionById(id));
    }

    @Operation(summary = "新增题目")
    @PostMapping
    public Result<QuestionVO> createQuestion(@Valid @RequestBody QuestionCreateDTO dto) {
        return Result.success(questionService.createQuestion(dto));
    }

    @Operation(summary = "修改题目")
    @PutMapping
    public Result<QuestionVO> updateQuestion(@Valid @RequestBody QuestionUpdateDTO dto) {
        return Result.success(questionService.updateQuestion(dto));
    }

    @Operation(summary = "删除题目")
    @DeleteMapping("/{id}")
    public Result<Void> deleteQuestionById(@PathVariable Long id) {
        questionService.deleteQuestionById(id);
        return Result.success();
    }
}
