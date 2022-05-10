package service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PrittyPrinterTest {

    @Test
    public void printMainMenuTest() {
        PrettyPrinter.printMainMenu();
    }

    @Test
    public void printTypeMenuTest() {
        PrettyPrinter.printTypeMenu();
    }

    @Test
    public void printRepeatTimeMenuTest() {
        PrettyPrinter.printRepeatTimeMenu();
    }
}
