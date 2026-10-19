package com.studyhub.controller;

import com.studyhub.common.Result;
import com.studyhub.pojo.dto.SubjectCreateDTO;
import com.studyhub.pojo.dto.SubjectUpdateDTO;
import com.studyhub.service.SubjectService;
import com.studyhub.pojo.vo.SubjectVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "学科管理")
@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @Operation(summary = "新增学科")
    @PostMapping
    public Result<SubjectVO> createSubject(@Valid @RequestBody SubjectCreateDTO dto) {
        return Result.success(subjectService.createSubject(dto));
    }

    @Operation(summary = "查询全部学科")
    @GetMapping
    public Result<List<SubjectVO>> listSubjects() {
        return Result.success(subjectService.listSubjects());
    }

    @Operation(summary = "查询单个学科")
    @GetMapping("/{id}")
    public Result<SubjectVO> getSubjectById(@PathVariable Long id) {
        return Result.success(subjectService.getSubjectById(id));
    }

    @Operation(summary = "修改学科信息")
    @PutMapping
    public Result<SubjectVO> updateSubject(@Valid @RequestBody SubjectUpdateDTO dto) {
        return Result.success(subjectService.updateSubject(dto));
    }

    @Operation(summary = "删除学科")
    @DeleteMapping("/{id}")
    public Result<Void> deleteSubjectById(@PathVariable Long id) {
        subjectService.deleteSubjectById(id);
        return Result.success();
    }
}
