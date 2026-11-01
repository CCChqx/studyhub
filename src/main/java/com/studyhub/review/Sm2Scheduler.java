package com.studyhub.review;

/*
*  sm2 间隔重复算法计算下一次复习时间
*  */

import java.time.LocalDate;

public class Sm2Scheduler implements Scheduler{

    private final static double MIN_EF = 1.3;
    private final static int MASTERED_INTERVAL_DAYS = 30;
    private final static int MASTERED_REPETITIONS = 3;


    @Override
    public ScheduleResult schedule(ReviewSnapshot current, int quality, LocalDate today) {
        validateQuality(quality);

        double newEf = updateEf(current.ef(), quality);

        int nextRepetitions = quality < 3 ? 0 : current.repetitions() + 1;

        int nextIntervalDays = updateInterval(
                current.intervalDays(),
                current.ef(),
                current.repetitions(),
                quality
        );

        boolean mastered = isMastered(nextIntervalDays, nextRepetitions);

        return new ScheduleResult(
                newEf,
                nextIntervalDays,
                nextRepetitions,
                today.plusDays(nextIntervalDays),
                mastered
        );
    }


    /*
     *   重新计算 EF 难度系数
     * */
    private static double updateEf(double ef,int quality) {
        double newEf = ef + 0.1 -(5 - quality) * (0.08 + (5 - quality) * 0.02);
        return Math.max(MIN_EF, newEf);
    }

    //  更新下一次复习天数
    private static int updateInterval(int previousInterval,double previousEf,
                                       int previousRepetitions,int quality){
        if (quality < 3) return 1;

        if (previousRepetitions == 0){
            return 1;
        }

        if (previousRepetitions == 1){
            return 6;
        }
        return Math.max(1,(int)Math.round(previousInterval * previousEf));
    }

    //  判断知识点是否熟练掌握
    private static boolean isMastered(int interval,int repetitions){
        return interval >= MASTERED_INTERVAL_DAYS && repetitions >= MASTERED_REPETITIONS;
    }

    //  校验自测打分数据是否合法
    private static void validateQuality(int quality){
        if (quality < 0 || quality >5){
            throw new IllegalArgumentException("quality must be between 0 and 5");
        }
    }
}
