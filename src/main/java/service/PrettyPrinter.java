package service;

public class PrettyPrinter {

    public static void printMainMenu() {
        System.out.println("1: Create Job");
        System.out.println("2: Create random Job");
        System.out.println("3: Check Status by title");
        System.out.println("4: Get All Jobs");
        System.out.println("5: Terminate Job");
        System.out.println("0: exit");
    }

    public static void printTypeMenu() {
        System.out.println("1: BUSINESS ");
        System.out.println("2: ANALYTICS ");
        System.out.println("3: DEVELOPMENT ");
        System.out.println("4: DESIGN ");
    }

    public static void printRepeatTimeMenu() {
        System.out.println("1: one hour ");
        System.out.println("2: two hour ");
        System.out.println("3: six hour ");
        System.out.println("4: twelve hour ");
        System.out.println("0: its all ");
    }

}
