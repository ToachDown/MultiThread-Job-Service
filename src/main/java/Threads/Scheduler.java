package Threads;

import model.Job;
import model.enums.Status;
import service.JobUtility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class Scheduler extends Thread {

    private final List<Job> jobs;
    private final Semaphore semaphore;


    public Scheduler(List<Job> jobs, Semaphore semaphore) {
        this.jobs = jobs;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        ExecutorService service = Executors.newFixedThreadPool(4);
        while (true) {
            try {
                Thread.sleep(45000);
                jobs.stream()
                        .filter(job -> !job.getStatus().equals(Status.IN_PROGRESS))
                        .filter(this::checkRepeat)
                        .forEach(job -> {
                            List<Job> workingJobs = jobs.stream()
                                    .filter(job1 -> job1.getStatus().equals(Status.IN_PROGRESS))
                                    .collect(Collectors.toList());
                            job = JobUtility.checkJobWaiting(job, workingJobs);
                            service.execute(new JobHandler(job, semaphore, workingJobs));
                        });
            } catch (InterruptedException e) {
                service.shutdown();
                throw new RuntimeException(e);
            }
        }
    }

    public boolean checkRepeat(Job job) {
        int minutes = job.getCreated().getMinute() + job.getCreated().getHour() * 60 + job.getCreated().getDayOfMonth() * 60 * 24;
        LocalDateTime time = LocalDateTime.now();
        int now = time.getMinute() + time.getHour() * 60 + time.getDayOfMonth() * 60 * 24;
        int difference = now - minutes;
        if(difference <= 0) {
            return false;
        }
        return job.getRepeatTime().stream()
                .anyMatch(t -> difference % (t.getHour() * 60) == 0);
    }
}
