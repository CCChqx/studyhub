package com.studyhub.controller;

import com.studyhub.common.PageResult;
import com.studyhub.common.Result;
import com.studyhub.pojo.dto.KnowledgePointCreateDTO;
import com.studyhub.pojo.dto.KnowledgePointUpdateDTO;
import com.studyhub.pojo.query.KnowledgePointQuery;
import com.studyhub.pojo.vo.KnowledgePointVO;
import com.studyhub.service.KnowledgePointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "知识点管理")
@RestController
@RequestMapping("/api/knowledgepoint")
@RequiredArgsConstructor
public class KnowledgePointController {

    private final KnowledgePointService knowledgePointService;

    @Operation(summary = "知识点分页查询")
    @GetMapping
    public Result<PageResult<KnowledgePointVO>> listKnowledgePoint(KnowledgePointQuery query) {
        return Result.success(knowledgePointService.pageKnowledgePoints(query));
    }

    @Operation(summary = "根据id查询单个知识点")
    @GetMapping("/{id}")
    public Result<KnowledgePointVO> getKnowledgePointById(@PathVariable Long id) {
        return Result.success(knowledgePointService.getKnowledgePointById(id));
    }

    @Operation(summary = "新增知识点")
    @PostMapping
    public Result<KnowledgePointVO> createKnowledgePoint(@Valid @RequestBody KnowledgePointCreateDTO dto){
        return Result.success(knowledgePointService.createKnowledgePoint(dto));
    }

    @Operation(summary = "修改知识点")
    @PutMapping
    public Result<KnowledgePointVO> updateKnowledgePoint(@Valid @RequestBody KnowledgePointUpdateDTO dto){
        return Result.success(knowledgePointService.updateKnowledgePoint(dto));
    }

    @Operation(summary = "删除知识点")
    @DeleteMapping("/{id}")
    public Result<Void> deleteKnowledgePoint(@PathVariable Long id){
        knowledgePointService.deleteKnowledgePointById(id);
        return Result.success();
    }
}
