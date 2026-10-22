package com.studyhub.controller;

import com.studyhub.common.Result;
import com.studyhub.pojo.dto.CategoryCreateDTO;
import com.studyhub.pojo.dto.CategoryUpdateDTO;
import com.studyhub.pojo.vo.CategoryVO;
import com.studyhub.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "学科下章节/专题管理")
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "根据id查询单个章节")
    @GetMapping("/{id}")
    public Result<CategoryVO> getCategoryById(@PathVariable Long id) {
        return Result.success(categoryService.getCategoryById(id));
    }

    @Operation(summary = "查询单个学科全部章节")
    @GetMapping
    public Result<List<CategoryVO>> listCategories(@RequestParam Long subjectId) {
        return Result.success(categoryService.listCategories(subjectId));
    }

    @Operation(summary = "新增学科下章节")
    @PostMapping
    public Result<CategoryVO> createCategory(@Valid @RequestBody CategoryCreateDTO dto) {
        return Result.success(categoryService.createCategory(dto));
    }

    @Operation(summary = "修改章节信息")
    @PutMapping
    public Result<CategoryVO> updateCategory(@Valid @RequestBody CategoryUpdateDTO dto){
        return Result.success(categoryService.updateCategory(dto));
    }

    @Operation(summary = "删除某个学科下的章节")
    @DeleteMapping("/{id}")
    public Result<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return Result.success();
    }
}
