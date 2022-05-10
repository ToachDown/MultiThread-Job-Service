package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.RepeatTime;
import model.enums.Status;
import model.enums.Type;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {

    private UUID id;
    private Type type;
    private String title;
    private Status status;
    private double safety;
    private LocalDateTime created;
    private LocalDateTime completed;
    private Set<RepeatTime> repeatTime;
    private boolean waiting;
    private boolean cancel;

    @Override
    public String toString() {
        return "Job: {\n title: " + title + ", \n " +
                "status: " + status + ", \n " +
                "created: " + created.toLocalTime() + ", \n " +
                "repeatTime: " + repeatTime + ", \n " +
                (completed == null ? "" : "completed: " + completed.toLocalTime() + ", \n ") +
                "id: " + id + ", \n " +
                "type: " + type + "\n},";
    }
}
