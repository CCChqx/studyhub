package com.studyhub.pojo.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyhub.pojo.entity.KnowledgePoint;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 知识点分页查询条件。继承 Page 直接复用 pageNum/pageSize，前端可绑定。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KnowledgePointQuery extends Page<KnowledgePoint> {

    /** 按学科过滤 */
    private Long subjectId;

    /** 按分类过滤 */
    private Long categoryId;

    /** 标题模糊匹配 */
    private String keyword;
}
