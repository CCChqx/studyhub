package com.studyhub.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("review_log")
public class ReviewLog {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;
    private Long knowledgePointId;
    private String requestId;
    private int quality;
    private double previousEf;
    private double nextEf;
    private int previousIntervalDays;
    private int nextIntervalDays;
    private LocalDate previousDueAt;
    private LocalDate nextDueAt;
    private LocalDateTime reviewedAt;
}
