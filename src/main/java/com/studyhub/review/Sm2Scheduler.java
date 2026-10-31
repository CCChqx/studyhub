package com.studyhub.review;

/*
*  sm2 间隔重复算法计算下一次复习时间
*  */

import java.time.LocalDate;

public class Sm2Scheduler implements Scheduler{

    private final static double MIN_EF = 1.3;
    private final static double MAX_EF = 2.5;
    private final static int MASTERED_INTERVAL_DAYS = 30;
    private final static int MASTERED_REPETIONS = 3;


    @Override
    public ScheduleResult schedule(ReviewSnapshot current, int quality, LocalDate today) {
        return null;
    }


    /*
     *   重新计算 EF 难度系数
     * */
    private static double updateEf(double ef,int quality) {
        double newEf = ef + 0.1 -(5 - quality) * (0.08 + (5 - quality) * 0.02);
        return Math.min(Math.max(MIN_EF, newEf),MAX_EF);
    }

    private static int updateInterval(int previousInterval,double previousEf,
                                       int previousRepetitions,int quality){
        if (previousRepetitions == 0){
            return 1;
        }
        if (previousRepetitions == 1){
            return 6;
        }
        return Math.max(1,(int)Math.round(previousInterval * previousEf));
    }

    private static boolean isMastered(int interval,int repetitions){
        return interval >= MASTERED_INTERVAL_DAYS && repetitions >= MASTERED_REPETIONS;
    }

    private static void validateQuailty(int quality){
        if (quality < 0 || quality >5){
            throw new IllegalArgumentException("quality must be between 0 and 5");
        }
    }
}
