import algorithm.Algorithm;
import algorithm.OptimisticConcurrencyControl;
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
                    algorithm = new TwoPhaseLocking(tasks.toUpperCase());
                    algorithm.execute();
                }
                case "2" ->{
                    System.out.println("========================= OPTIMISTIC CONCURRENCY CONTROL =========================");
                    System.out.print("Insert tasks: ");
                    String tasks = scanner.nextLine();
                    algorithm = new TwoPhaseLocking(tasks.toUpperCase()); //TODO: change to OCC
                    algorithm.execute();
                }
                default -> {
                    isRunning = false;
                }
            }
        }
    }
}