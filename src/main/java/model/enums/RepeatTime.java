package model.enums;

import lombok.Getter;

@Getter
public enum RepeatTime {
    ONE(1,1),
    TWO(2,2),
    SIX(6,3),
    TWELVE(12,4);

    private final int hour;
    private final int number;

    RepeatTime(int hour, int number) {
        this.hour = hour;
        this.number = number;
    }


}
