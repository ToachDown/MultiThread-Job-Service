package service;

import model.Job;
import model.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JobUtilityTest {

    @Test
    public void checkJobWaitingTest_WhenAllJobDifferent() {
        UUID id = UUID.randomUUID();
        List<Job> jobs = List.of(
                Job.builder()
                        .id(id)
                        .type(Type.ANALYTICS)
                        .build(),
                Job.builder()
                        .id(UUID.randomUUID())
                        .type(Type.DEVELOPMENT)
                        .build()
        );
        Job job = Job.builder()
                .id(UUID.randomUUID())
                .type(Type.BUSINESS)
                .build();
        Job changedJob = JobUtility.checkJobWaiting(job, jobs);
        assertFalse(changedJob.isWaiting());
    }

    @Test
    public void checkJobWaitingTest_WhenJobWithSameId() {
        UUID id = UUID.randomUUID();
        List<Job> jobs = List.of(
                Job.builder()
                        .id(id)
                        .type(Type.ANALYTICS)
                        .build(),
                Job.builder()
                        .id(UUID.randomUUID())
                        .type(Type.DEVELOPMENT)
                        .build()
        );
        Job job = Job.builder()
                .id(id)
                .type(Type.BUSINESS)
                .build();
        Job changedJob = JobUtility.checkJobWaiting(job, jobs);
        assertTrue(changedJob.isWaiting());
    }

    @Test
    public void checkJobWaitingTest_WhenJobWithSameType() {
        UUID id = UUID.randomUUID();
        List<Job> jobs = List.of(
                Job.builder()
                        .id(id)
                        .type(Type.ANALYTICS)
                        .build(),
                Job.builder()
                        .id(UUID.randomUUID())
                        .type(Type.DEVELOPMENT)
                        .build()
        );
        Job job = Job.builder()
                .id(UUID.randomUUID())
                .type(Type.ANALYTICS)
                .build();
        Job changedJob = JobUtility.checkJobWaiting(job, jobs);
        assertTrue(changedJob.isWaiting());
    }

    @Test
    public void checkJobWaitingTest_WhenJobWithSameTypeAndId() {
        UUID id = UUID.randomUUID();
        List<Job> jobs = List.of(
                Job.builder()
                        .id(id)
                        .type(Type.ANALYTICS)
                        .build(),
                Job.builder()
                        .id(UUID.randomUUID())
                        .type(Type.DEVELOPMENT)
                        .build()
        );
        Job job = Job.builder()
                .id(id)
                .type(Type.ANALYTICS)
                .build();
        Job changedJob = JobUtility.checkJobWaiting(job, jobs);
        assertTrue(changedJob.isWaiting());
    }
}
