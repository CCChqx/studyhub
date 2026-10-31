package com.studyhub.review;

import lombok.Data;

/*
*   输入：旧状态
* */

public record ReviewSnapshot (

    double ef,
    int intervalDays,
    int repetitions
){
    public static ReviewSnapshot newCard(){
        return new ReviewSnapshot(2.5,0,0);
    }
}
