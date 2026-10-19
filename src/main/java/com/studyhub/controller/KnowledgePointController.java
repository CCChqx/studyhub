package com.studyhub.controller;

import com.studyhub.common.PageResult;
import com.studyhub.common.Result;
import com.studyhub.pojo.query.KnowledgePointQuery;
import com.studyhub.pojo.vo.KnowledgePointVO;
import com.studyhub.service.KnowledgePointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "知识点管理")
@RestController
@RequestMapping("/api/knowledgepoint")
@RequiredArgsConstructor
public class KnowledgePointController {

    private final KnowledgePointService knowledgePointService;

    @Operation(summary = "知识点分页查询")
    @GetMapping
    public Result<PageResult<KnowledgePointVO>> knowledgePointQuery(KnowledgePointQuery query) {
        return Result.success(knowledgePointService.pageKnowledgePoints(query));
    }

    @Operation(summary = "根据id查询单个知识点")
    @GetMapping("/{id}")
    public Result<KnowledgePointVO> getKnowledgePointById(@PathVariable Long id) {
        return Result.success(knowledgePointService.getKnowledgePointById(id));
    }
}
