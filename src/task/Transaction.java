package task;

import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transaction {
    private Map<Integer, List<Task>> transaction = new HashMap<>();
    private List<Task> waitingList;

    public Transaction(Task[] tasks){
        for (Task task : tasks){
            if (this.transaction.containsKey(task.getTransaction())){
                this.transaction.get(task.getTransaction()).add(task);
            }else {
                this.transaction.put(task.getTransaction(), new ArrayList<>());
                this.transaction.get(task.getTransaction()).add(task);
            }
        }

        this.waitingList = new ArrayList<>();
    }

    public void committed(int schedule, int idx, Lock lock){
        for (int i = idx; i>=0; i--){
            this.transaction.get(schedule).get(i).setStatus("COMMITTED");
            lock.unlock(this.transaction.get(schedule).get(i).getResource());
            this.transaction.get(schedule).remove(i);
        }
        System.out.println("Committed schedule "+ schedule);
        for (Task task : this.waitingList){
            boolean isExclusive = Utils.isKeyExclusive(this.waitingList, task);
            String keyType = "";
            if (isExclusive){
                keyType = "Exclusive";
            }else {
                keyType = "Shared";
            }
            if (lock.checkPermission(task,keyType)){
                lock.addLock(task.getResource(),keyType,task.getTransaction());
                System.out.println(task.toString());
                this.waitingList.remove(task);
            }
        }
    }

    public boolean isWaiting(Task task){
        return this.waitingList.contains(task);
    }

    public boolean isWaitingEmpty(){
        return this.waitingList.isEmpty();
    }

    public void addWaitingList(Task task){
        this.waitingList.add(task);
    }
}
