package service;

import Threads.Scheduler;
import model.Job;
import model.enums.RepeatTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SchedulerTest {

    private static Scheduler scheduler;

    @BeforeAll
    public static void init() {
        scheduler = new Scheduler(new ArrayList<>(), new Semaphore(1));
    }

    @Test
    public void checkRepeatTest_WhenOneHour() {
        Job job = Job.builder()
                .created(LocalDateTime.now().minusHours(1))
                .repeatTime(Set.of(RepeatTime.ONE))
                .build();
        boolean result = scheduler.checkRepeat(job);
        assertTrue(result);
    }

    @Test
    public void checkRepeatTest_WhenOneHourAndNotStart() {
        Job job = Job.builder()
                .created(LocalDateTime.now())
                .repeatTime(Set.of(RepeatTime.ONE))
                .build();
        boolean result = scheduler.checkRepeat(job);
        assertFalse(result);
    }

    @Test
    public void checkRepeatTest_WhenOneTwoHour() {
        Job job = Job.builder()
                .created(LocalDateTime.now().minusHours(2))
                .repeatTime(Set.of(RepeatTime.TWO))
                .build();
        boolean result = scheduler.checkRepeat(job);
        assertTrue(result);
    }

    @Test
    public void checkRepeatTest_WhenOneSixHour() {
        Job job = Job.builder()
                .created(LocalDateTime.now().minusHours(6))
                .repeatTime(Set.of(RepeatTime.SIX))
                .build();
        boolean result = scheduler.checkRepeat(job);
        assertTrue(result);
    }

    @Test
    public void checkRepeatTest_WhenOneTwelveHour() {
        Job job = Job.builder()
                .created(LocalDateTime.now().minusHours(12))
                .repeatTime(Set.of(RepeatTime.TWELVE))
                .build();
        boolean result = scheduler.checkRepeat(job);
        assertTrue(result);
    }

    @Test
    public void checkRepeatTest_WhenAllPeriods() {
        Job job = Job.builder()
                .created(LocalDateTime.now().minusHours(14))
                .repeatTime(Set.of(RepeatTime.values()))
                .build();
        boolean result = scheduler.checkRepeat(job);
        assertTrue(result);
    }

}
