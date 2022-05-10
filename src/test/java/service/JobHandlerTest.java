package service;

import Threads.JobHandler;
import model.Job;
import model.enums.Type;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JobHandlerTest {

    private static JobHandler jobHandler;
    private static List<Job> jobs;
    private static Job job;

    @BeforeAll
    public static void init() {
        jobs = List.of(
                Job.builder()
                        .type(Type.ANALYTICS)
                        .safety(0.1123)
                        .build(),
                Job.builder()
                        .type(Type.BUSINESS)
                        .safety(0.6542)
                        .build()
        );
        job = Job.builder()
                .type(Type.DESIGN)
                .safety(0.2234)
                .build();

        jobHandler = new JobHandler(job, new Semaphore(3), jobs);
    }

    @Test
    public void checkFailTest() {
        List<Boolean> results = IntStream.range(0, 1000)
                .boxed()
                .map(i -> jobHandler.checkFail(job))
                .collect(Collectors.toList());
        int trueRes = (int) results.stream().filter(res -> res).count();
        int falseRes = 1000 - trueRes;
        assertTrue(trueRes < 5);
    }
}
