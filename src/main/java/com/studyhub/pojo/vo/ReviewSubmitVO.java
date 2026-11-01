package com.studyhub.pojo.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReviewSubmitVO {

    private int quality;

    private double newEf;

    private int nextIntervalDays;

    private LocalDate dueAt;

    private boolean mastered;
}
