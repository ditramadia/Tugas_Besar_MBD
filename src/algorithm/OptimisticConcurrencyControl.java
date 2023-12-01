package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import task.Task;
import task.Schedule;

/**
 * OptimisticConcurrencyControl (OCC) class
 * <p>
 * 
 */
public class OptimisticConcurrencyControl implements Algorithm {
    private List<Task> tasks = new ArrayList<>();
    private Schedule localSchedule;
    private Schedule schedule;
    private Map<Integer, HashSet<Task>> readSetOfTransactions = new HashMap<>();
    private Map<Integer, HashSet<Task>> writeSetOfTransactions = new HashMap<>();

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
        this.localSchedule = new Schedule(this.tasks);
        this.instantiateRWSets();
    }

    // at this point, schedule has mapped out tasks, to each transactions
    // R1(A) R2(A) W2(A) C1 R3(A) C2 W3(A) W3(B) C3 given this list of requests
    // solve this in occ algo?
    // in schedule :
    // R1(A)1 C14
    // R2(A)2 W2(A)3 C27
    // R3(A)5 W3(A)6 W3(B)8 C39
    // timestamp has already been had on queue attr
    @Override
    public void execute() {
        // action in localSchedule
        for (Task task : this.tasks) {
            // if request is a commit request
            if (task.getOperation() == "C") {
                // validate
                // checking all transactions
                for (Integer keyInteger : this.localSchedule.getScheduleKeys()) {
                    // if finish time of Transaction(keyInteger) < task time
                    // if commit time of Transaction(keyInteger) < task commit time
                    // if last read time Transaction(keyInteger) < last read time task
                }
                // clear RW Set for transaction being validated
                clearRWSetOfTransaction(task.getSchedule());
            } else {
                // not commit request, thus in reading phase
                // reading phase put operations into rw sets
                if (task.getOperation() == "R") {
                    readSetOfTransactions.get(task.getSchedule()).add(task);
                } else if (task.getOperation() == "W") {
                    writeSetOfTransactions.get(task.getSchedule()).add(task);
                }
            }
        }
    }

    // counting how many transactions happening in schedule
    public void instantiateRWSets() {
        for (Task task : this.tasks) {
            readSetOfTransactions.putIfAbsent(task.getSchedule(), new HashSet<Task>());
            writeSetOfTransactions.putIfAbsent(task.getSchedule(), new HashSet<Task>());
        }
    }

    // clearing Read/Write set of transaction x
    public void clearRWSetOfTransaction(int schedule) {
        readSetOfTransactions.get(schedule).clear();
        writeSetOfTransactions.get(schedule).clear();
    }

    // rolling back for validation
    public void rollbackTransaction(int olderSchedule, int youngerSchedule) {

    }
}
