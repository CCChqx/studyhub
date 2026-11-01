package com.studyhub.review;

import java.time.LocalDate;

/*
*  输出 ：新状态
* */


public record ScheduleResult(
        double newEf,
        int nextIntervalDays,
        int nextRepetitions,
        LocalDate dueAt,
        boolean mastered
){

}
