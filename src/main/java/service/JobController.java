package service;

import Threads.JobHandler;
import Threads.Scheduler;
import model.Job;
import model.enums.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class JobController {

    private final static int PARALLEL_THREADS = 3;
    private final static int THREAD_POOL = 10;
    private final static String EXCEPTION_MESSAGE = "Job with this title not exists";
    private final JobGenerator jobGenerator;
    private List<Job> jobs;

    public JobController() {
        jobGenerator = new JobGenerator();
        jobs = new ArrayList<>();
    }

    public void startWork() {
        Semaphore semaphore = new Semaphore(PARALLEL_THREADS);
        Scanner scan = new Scanner(System.in);
        Scheduler scheduler = new Scheduler(jobs, semaphore);
        scheduler.setDaemon(true);
        scheduler.start();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL);
        int i;
        do {
            PrettyPrinter.printMainMenu();
            System.out.print("write number = ");
            i = scan.nextInt();
            System.out.println();
            switch (i) {
                case 1 : {
                    Job job = jobGenerator.generateJob(scan);
                    executeJob(job, semaphore, executorService);
                    break;
                }
                case 2 : {
                    Job job = jobGenerator.createRandomJob();
                    executeJob(job, semaphore, executorService);
                    break;
                }
                case 3 : {
                    getJobStatusByTitle(scan);
                    break;
                }
                case 4 : {
                    getAllJobs();
                    break;
                }
                case 5 : {
                    terminateJob(scan);
                    break;
                }
                default: {
                    i = -1;
                    System.out.println("unexpected number");
                    break;
                }
            }
        } while (i > 0);
        executorService.shutdown();
        System.out.println("end");
    }

    public void terminateJob(Scanner scan) {
        System.out.println("Job title = ");
        String title = scan.next();
        Job searchedJob = jobs.stream()
                .filter(job -> job.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(EXCEPTION_MESSAGE));
        searchedJob.setCancel(true);
    }

    public void getAllJobs() {
        jobs.forEach(System.out::println);
    }

    public void getJobStatusByTitle(Scanner scan) {
        System.out.println("Job title = ");
        String title = scan.next();
        Job searchedJob = jobs.stream()
                .filter(job -> job.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(EXCEPTION_MESSAGE));
        System.out.println(searchedJob.getTitle() + " - " + searchedJob.getStatus());
    }

    public void executeJob(Job job, Semaphore semaphore, ExecutorService executorService) {
        List<Job> workingJobs = jobs.stream()
                .filter(job1 -> job1.getStatus().equals(Status.IN_PROGRESS))
                .collect(Collectors.toList());
        job = JobUtility.checkJobWaiting(job, workingJobs);
        jobs.add(job);
        System.out.println("create and start handling Job - " + job.getId());
        executorService.execute(new JobHandler(job, semaphore, workingJobs));
    }
}
