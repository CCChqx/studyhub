package com.studyhub.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("review_state")
public class ReviewState {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;
    private Long knowledgePointId;
    private double ef;
    private int intervalDays;
    private LocalDate dueAt;
    private int reviewCount;
    private int lapseCount;
    private boolean mastered;
    private String algorithmVersion;

    @Version
    private Integer version;
}
