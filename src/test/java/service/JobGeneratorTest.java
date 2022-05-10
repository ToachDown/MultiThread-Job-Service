package service;

import model.Job;
import model.enums.RepeatTime;
import model.enums.Status;
import model.enums.Type;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class JobGeneratorTest {

    private static JobGenerator jobGenerator;

    @BeforeAll
    public static void init(){
        jobGenerator = new JobGenerator();
    }

    @Test
    public void createRandomJobTest() {
        IntStream.range(0, 10)
                .boxed()
                .forEach(i -> {
                    Job job = jobGenerator.createRandomJob();
                    assertNotNull(job);
                    assertEquals(10, job.getTitle().length());
                    assertTrue(job.getSafety() < 1);
                    assertTrue(List.of(Type.values()).contains(job.getType()));
                    assertTrue(List.of(RepeatTime.values()).containsAll(job.getRepeatTime()));
                    assertEquals(Status.IDLE, job.getStatus());
                    assertNotNull(job.getId());
                    assertNotNull(job.getCreated());
                });
    }
}
