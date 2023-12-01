package task;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Schedule_OCC extends Schedule {
    private Map<Integer, Timestamp> TransactionsTimestamp = new HashMap<>();
    private Map<Integer, Set<Task>> TransactionsReadSet = new HashMap<>();
    private Map<Integer, Set<Task>> TransactionsWriteSet = new HashMap<>();

    public Schedule_OCC(List<Task> tasks) {
        super(tasks);
        for (Integer keyInteger : getTransactionNames()) {
            TransactionsTimestamp.put(keyInteger, new Timestamp());
            TransactionsReadSet.put(keyInteger, new HashSet<>());
            TransactionsWriteSet.put(keyInteger, new HashSet<>());
        }
    }

    public void clearTransactionRWSet(int transaction) {
        TransactionsReadSet.get(transaction).clear();
    }

    public boolean validate(int transaction1, int transaction2) {
        Timestamp T1TS = TransactionsTimestamp.get(transaction1);
        Timestamp T2TS = TransactionsTimestamp.get(transaction2);
        Set<Task> T1ReadSet = TransactionsReadSet.get(transaction1);
        // Set<Task> T2ReadSet = TransactionsReadSet.get(transaction2);
        // Set<Task> T1WriteSet = TransactionsWriteSet.get(transaction1);
        Set<Task> T2WriteSet = TransactionsWriteSet.get(transaction2);
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

    private class Timestamp {
        Integer startTime = 0;
        Integer validTime;
        Integer finishTime;
        Timestamp() {
            startTime = 0;
            validTime = 0;
            finishTime = 0;
        }
        public Integer getValidTime() {
            return validTime;
        }
        public Integer getFinishTime() {
            return finishTime;
        }
        public void setValidTime(Integer validTime) {
            this.validTime = validTime;
        }
        public void setFinishTime(Integer finishTime) {
            this.finishTime = finishTime;
        }
    }
    
}
