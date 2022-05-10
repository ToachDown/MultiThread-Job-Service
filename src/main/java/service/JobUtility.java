package service;

import model.Job;
import model.enums.Status;

import java.util.List;
import java.util.stream.Collectors;

public class JobUtility {

    public static Job checkJobWaiting(Job currentJob, List<Job> workingJobs) {
        boolean waiting = workingJobs.stream()
                .anyMatch(job -> job.getId().equals(currentJob.getId()) || job.getType().equals(currentJob.getType()));
        currentJob.setWaiting(waiting);
        return currentJob;
    }
}
