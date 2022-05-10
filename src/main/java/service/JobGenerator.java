package service;

import model.enums.RepeatTime;
import model.enums.Status;
import model.Job;
import model.enums.Type;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JobGenerator {

    public Job generateJob(Scanner scan) {
        String title = inputJobTitle(scan);
        Type type = inputJobType(scan);
        Set<RepeatTime> times = inputRepeatPeriod(scan);
        return Job.builder()
                .title(title)
                .id(UUID.randomUUID())
                .status(Status.IDLE)
                .created(LocalDateTime.now())
                .safety(Math.random())
                .type(type)
                .repeatTime(times)
                .build();
    }

    public Job createRandomJob() {
        return Job.builder()
                .title(UUID.randomUUID().toString().substring(26))
                .id(UUID.randomUUID())
                .status(Status.IDLE)
                .created(LocalDateTime.now())
                .safety(Math.random())
                .type(Arrays.stream(Type.values())
                        .collect(Collectors.toList())
                        .get((int)(Math.random() * Type.values().length))
                )
                .repeatTime(generateRandomRepeatPeriods())
                .build();
    }

    public Set<RepeatTime> generateRandomRepeatPeriods() {
        return IntStream.range(0, (int)(Math.random() * RepeatTime.values().length))
                .boxed()
                .map(i -> Arrays.stream(RepeatTime.values())
                        .collect(Collectors.toList())
                        .get((int)(Math.random() * RepeatTime.values().length))
                ).collect(Collectors.toSet());
    }

    public Type inputJobType(Scanner scan) {
        PrettyPrinter.printTypeMenu();
        int typeNumber = scan.nextInt();
        return Arrays.stream(Type.values())
                .filter(typ -> typ.getNumber() == typeNumber)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found Type"));
    }

    public String inputJobTitle(Scanner scan) {
        System.out.println("write job title: ");
        return scan.next();
    }

    public Set<RepeatTime> inputRepeatPeriod(Scanner scan) {
        Set<RepeatTime> times = new HashSet<>();
        while (true) {
            System.out.println("write repeat job time: ");
            PrettyPrinter.printRepeatTimeMenu();
            int repeatNumber = scan.nextInt();
            if(repeatNumber == 0) {
                break;
            }
            RepeatTime time = Arrays.stream(RepeatTime.values())
                    .filter(typ -> typ.getNumber() == repeatNumber)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Not found repeat time"));
            times.add(time);
        }
        return times;
    }

}
