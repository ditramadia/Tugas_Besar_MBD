package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import task.Task;
import task.TimestampedTask;
import task.Transaction;

/**
 * OptimisticConcurrencyControl (OCC) class <p>
 * 
 */
public class OptimisticConcurrencyControl {
    private List<Task> tasks = new ArrayList<>();
    private Transaction transaction;
    static int scheduleTime = 0;
    /**
     * Constructor <p>
     * Melakukan parsing string dan memetakan task-task yang diparsing menjadi schedule
     * @param transaction 1 string berisi task-task contohnya (R1(A) R2(B) W1(A) C1 ...) <p>
     */
    public OptimisticConcurrencyControl(String transaction){
        String[] _temp = transaction.split(" ");
        // Matching pattern XY(Z)
        Pattern pattern1 = Pattern.compile("([A-Za-z])(\\d+)\\(([^)]+)\\)");
        // Matching pattern XY
        Pattern pattern2 = Pattern.compile("([A-Za-z])(\\d+)$");
        for (String temp : _temp){
            Matcher match1 = pattern1.matcher(temp);
            while (match1.find()) {
                this.tasks.add(
                    new TimestampedTask(
                        match1.group(1), 
                        match1.group(3), 
                        Integer.parseInt(match1.group(2)),
                        scheduleTime));
                scheduleTime++;
            }
            Matcher match2 = pattern2.matcher(temp);
            while (match2.find()) {
                this.tasks.add(
                    new TimestampedTask(
                        match2.group(1), 
                        Integer.parseInt(match2.group(2)),
                        scheduleTime));
                scheduleTime++;
            }
        }
        this.transaction = new Transaction((Task[]) this.tasks.toArray());
    }
}
