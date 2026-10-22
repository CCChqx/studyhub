package com.studyhub.pojo.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyhub.pojo.entity.Question;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 题目分页查询条件。继承 Page 复用 pageNum/pageSize。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionQuery extends Page<Question> {

    private Long subjectId;

    private Long categoryId;

    private Long kpId;

    /** 1 单选 / 2 多选 / 3 简答 */
    private Integer type;

    /** 1 启用 / 0 停用 */
    private Integer status;

    /** 题干关键字 */
    private String keyword;
}
