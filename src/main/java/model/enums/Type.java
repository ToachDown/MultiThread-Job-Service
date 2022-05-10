package model.enums;

import lombok.Getter;

@Getter
public enum Type {
    BUSINESS(400, 1),
    ANALYTICS(700, 2),
    DEVELOPMENT(1600, 3),
    DESIGN(900, 4);

    private final int complexity;
    private final int number;

    Type(int complexity, int number) {
        this.complexity = complexity;
        this.number = number;
    }
}
