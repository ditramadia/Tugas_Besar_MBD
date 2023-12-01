import algorithm.Algorithm;
import algorithm.TwoPhaseLocking;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Algorithm algorithm;
    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning){
            System.out.println("============================ CHOOSE YOUR ALGORITHM ============================");
            System.out.println("1. Two Phase Locking\n2. Optimistic Concurrency Control\n3. Exit");
            System.out.print("input: ");
            String input = scanner.nextLine();

            switch (input){
                case "1" ->{
                    System.out.println("========================= TWO PHASE LOCKING =========================");
                    System.out.print("Insert tasks: ");
                    String tasks = scanner.nextLine();
                    algorithm = new TwoPhaseLocking(tasks);
                    algorithm.execute();
                }
                case "2" ->{
                    System.out.println("========================= OPTIMISTIC CONCURRENCY CONTROL =========================");
                    System.out.print("Insert tasks: ");
                    String tasks = scanner.nextLine();
                    algorithm = new TwoPhaseLocking(tasks); //TODO: change to OCC
                    algorithm.execute();
                }
                default -> {
                    isRunning = false;
                }
            }
        }
        Algorithm two = new TwoPhaseLocking("R1(A) R2(A) W2(A) C1 R3(A) C2 W3(A) W3(B) C3");
        two.execute();
    }
}