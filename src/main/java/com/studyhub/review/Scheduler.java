package com.studyhub.review;

import java.time.LocalDate;

public interface Scheduler {

    ScheduleResult schedule(ReviewSnapshot current, int quality, LocalDate today);

}
