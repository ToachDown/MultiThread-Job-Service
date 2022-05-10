import service.JobController;

import java.io.IOException;

public class MainController {

    public static void main(String[] args) throws IOException {
        JobController jobController = new JobController();
        jobController.startWork();
    }
}
