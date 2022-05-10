package Threads;

import model.enums.Status;
import model.Job;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Semaphore;

public class JobHandler extends Thread {

    private final Job job;
    private int progressCounter;
    private final Semaphore semaphore;
    private final List<Job> workingJobs;

    public JobHandler(Job job, Semaphore semaphore, List<Job> workingJobs) {
        this.job = job;
        this.workingJobs = workingJobs;
        this.semaphore = semaphore;
        this.progressCounter = job.getType().getComplexity();
    }

    @Override
    public void run() {
        try {
            waiting();
            semaphore.acquire();
            boolean failed = false;
            while (progressCounter != 0) {
                job.setStatus(Status.IN_PROGRESS);
                Thread.sleep(200);
                if (checkFail(job)) {
                    job.setStatus(Status.FAILED);
                    failed = true;
                    semaphore.release();
                    break;
                }
                progressCounter--;
                if (job.isCancel()) {
                    job.setCancel(false);
                    job.setStatus(Status.TERMINATED);
                    throw new RuntimeException();
                }
            }
            if (!failed) {
                job.setCompleted(LocalDateTime.now());
                job.setStatus(Status.DONE);
                semaphore.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            job.setStatus(Status.FAILED);
            semaphore.release();
            Thread.currentThread().interrupt();
        } catch (RuntimeException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void waiting() {
        while (job.isWaiting()) {
            boolean continueWait = workingJobs.stream()
                    .filter(job -> job.getStatus().equals(Status.IN_PROGRESS))
                    .anyMatch(job -> job.getId().equals(this.job.getId()) || job.getType().equals(this.job.getType()));
            if(!continueWait) {
                this.job.setWaiting(false);
            }
        }
    }

    public boolean checkFail(Job job) {
        int avgRangeFail = (int) (job.getType().getComplexity() * job.getSafety());
        int randomTry = (int) (Math.random() * job.getType().getComplexity()) / 10;
        return randomTry == avgRangeFail;
    }
}
