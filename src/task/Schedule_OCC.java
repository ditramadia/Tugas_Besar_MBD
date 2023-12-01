package task;

import static task.Schedule.waitingList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Schedule_OCC extends Schedule {
    private Map<Integer, Timestamp> TransactionsTimestamps = new HashMap<>();
    private Map<Integer, Set<Task>> TransactionsReadSets = new HashMap<>();
    private Map<Integer, Set<Task>> TransactionsWriteSets = new HashMap<>();

    public Schedule_OCC(List<Task> tasks) {
        super(tasks);
        for (Integer keyInteger : getTransactionNames()) {
            TransactionsTimestamps.put(keyInteger, new Timestamp(
                this.getScheduleMap().get(keyInteger).get(0).getQueue() 
                //mengambil task paling awal dari transaction, dan mengambil nilai queue
            ));
            TransactionsReadSets.put(keyInteger, new HashSet<>());
            TransactionsWriteSets.put(keyInteger, new HashSet<>());
        }
    }

    public void clearTransactionRWSet(int transaction) {
        TransactionsReadSets.get(transaction).clear();
        TransactionsWriteSets.get(transaction).clear();
    }

    public boolean validate(int transaction1, int transaction2) {
        Timestamp T1TS = TransactionsTimestamps.get(transaction1);
        Timestamp T2TS = TransactionsTimestamps.get(transaction2);
        Set<Task> T1ReadSet = TransactionsReadSets.get(transaction1);
        // Set<Task> T2ReadSet = TransactionsReadSet.get(transaction2);
        // Set<Task> T1WriteSet = TransactionsWriteSet.get(transaction1);
        Set<Task> T2WriteSet = TransactionsWriteSets.get(transaction2);
        if (T2TS.finishTime < T1TS.startTime){
            // Finish(T2)<Starts(T1)
            return true;
        } else if (T1TS.startTime < T2TS.finishTime 
                    && T2TS.finishTime < T1TS.validTime 
                    && Collections.disjoint(T2WriteSet, T1ReadSet)){
            // Starts(T1) < Finish(T2) < Valid(T1) and T2WriteSet disjoint from T1ReadSet
            return true;
        }
        return false;
    }

    public void committed(int schedule) {
        for (Task task : this.getScheduleMap().get(schedule)) {
            task.setStatus("COMMITTED");
        }
        clearTransactionRWSet(schedule);
    }
    
    public void addRead(Task t){
        TransactionsReadSets.get(t.getSchedule()).add(t);
        TransactionsTimestamps.get(t.getSchedule()).addTaskOnReadPhase(t);
    }
    public void addWrite(Task t){
        TransactionsWriteSets.get(t.getSchedule()).add(t);
        TransactionsTimestamps.get(t.getSchedule()).addTaskOnReadPhase(t);
    }

    public void rollbackTransaction(Integer schedule){
        // setting rolled back tasks queues to be latest and add to waiting list
        for (Task task : getScheduleMap().get(schedule)) {
            task.setQueue();
            addWaitingList(task);
        }
        // adding the waiting list back to new requests and empty waiting list
        for (Task task : waitingList) {
            if (this.getScheduleMap().containsKey(task.getSchedule())) {
                this.getScheduleMap().get(task.getSchedule()).add(task);
            } else {
                this.getScheduleMap().put(task.getSchedule(), new ArrayList<>());
                this.getScheduleMap().get(task.getSchedule()).add(task);
            }
        }
        waitingList = new ArrayList<>();
    }

    private class Timestamp {
        Integer startTime;
        Integer validTime;
        Integer finishTime;
        Timestamp() {
            startTime = 0;
            validTime=startTime;
            finishTime=validTime;
        }
        Timestamp(int queue_number) {
            startTime = queue_number;
            validTime=startTime;
            finishTime=validTime;
        }
        void addTaskOnReadPhase(Task t){
            validTime++;
            finishTime++;
        }
        Integer getValidTime() {
            return validTime;
        }
        Integer getFinishTime() {
            return finishTime;
        }
        void setValidTime(Integer validTime) {
            this.validTime = validTime;
        }
        void setFinishTime(Integer finishTime) {
            this.finishTime = finishTime;
        }
    }
    
}
