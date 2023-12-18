package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import task.Task;
import task.Schedule_OCC;

/**
 * OptimisticConcurrencyControl (OCC) class
 * <p>
 * 
 */
public class OptimisticConcurrencyControl implements Algorithm {
    private List<Task> tasks = new ArrayList<>();
    private Schedule_OCC localSchedule;
    private List<Task> schedule = new ArrayList<>();

    /**
     * Constructor
     * <p>
     * Melakukan parsing string dan memetakan task-task yang diparsing menjadi
     * schedule
     * 
     * @param schedule 1 string berisi task-task contohnya (R1(A) R2(B) W1(A) C1
     *                 ...)
     *                 <p>
     */
    public OptimisticConcurrencyControl(String schedule) {
        String[] _temp = schedule.split(" ");
        // Matching pattern XY(Z)
        Pattern pattern1 = Pattern.compile("([A-Za-z])(\\d+)\\(([^)]+)\\)");
        // Matching pattern XY
        Pattern pattern2 = Pattern.compile("([A-Za-z])(\\d+)$");
        for (String temp : _temp) {
            Matcher match1 = pattern1.matcher(temp);
            while (match1.find()) {
                this.tasks.add(
                        new Task(
                                match1.group(1),
                                match1.group(3),
                                Integer.parseInt(match1.group(2))));
            }
            Matcher match2 = pattern2.matcher(temp);
            while (match2.find()) {
                this.tasks.add(
                        new Task(
                                match2.group(1),
                                Integer.parseInt(match2.group(2))));
            }
        }
        this.localSchedule = new Schedule_OCC(this.tasks);
    }

    // at this point, schedule has mapped out tasks, to each transactions
    // R1(A) R2(A) W2(A) C1 R3(A) C2 W3(A) W3(B) C3 given this list of requests
    // in schedule :
    // R1(A),1<1,0,0> C1,4<1,2,0>
    // R2(A),2<2,2,2> W2(A),3<2,3,0> C2,7
    // R3(A),5<5,0,0> W3(A),6 W3(B),8 C3,9
    // example is formatted as [task],[queue_number]
    // note: timestamp has already been had on queue attr
    @Override
    public void execute() {
        // action in localSchedule
        for (Task task : this.tasks) {
            System.out.println(task);
            // if request is a commit request, goes to validation phase
            if (task.getOperation().equals("C")) {
                // validate
                for (Integer key : localSchedule.getTransactionNames()) {
                    if (key != task.getSchedule()){ // tidak perlu validasi dengan diri sendiri
                        if (!localSchedule.validate(task.getSchedule(), key)) {
                            System.out.println("Commit failed! Rolling back");
                            this.localSchedule.rollbackTransaction(key);
                        }
                    }
                } 
                // validation phase succeeded, move to writing phase
                // operations either have been validated, 
                // or worst case scenario, all transaction is rolled back
                this.localSchedule.committed(task.getSchedule());
                for (Task t : this.localSchedule.getTransactionSchedule(task.getSchedule())) {
                    this.schedule.add(t);
                }
            } else {
                // not commit request, thus in reading phase
                // reading phase put operations into rw sets
                if (task.getOperation().equals("R")) {
                    System.out.println("Received Read Operation");
                    localSchedule.addRead(task);
                } else if (task.getOperation().equals("W")) {
                    System.out.println("Received Write Operation");
                    localSchedule.addWrite(task);
                }
            }
        }
        // System.out.println(Arrays.toString(this.schedule.toArray()));
    }
}
