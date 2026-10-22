package com.studyhub.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分类表（学科下的章节/专题，parent_id 支持多级）。对应表 category。
 */
@Data
@TableName("category")
public class Category {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 所属学科，对应 subject.id */
    private Long subjectId;

    /** 父分类 id，0 表示顶级 */
    private Long parentId;

    private String name;

    /** 排序值，越小越靠前 */
    private Integer sort;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
