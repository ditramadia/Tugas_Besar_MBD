package task;

import utils.Utils;

import java.util.*;

public class Transaction {
    private Map<Integer, List<Task>> transaction = new HashMap<>();
    private static List<Task> waitingList;

    public Transaction(List<Task> tasks){
        for (Task task : tasks){
            if (this.transaction.containsKey(task.getTransaction())){
                this.transaction.get(task.getTransaction()).add(task);
            }else {
                this.transaction.put(task.getTransaction(), new ArrayList<>());
                this.transaction.get(task.getTransaction()).add(task);
            }
        }

        waitingList = new ArrayList<>();
    }

    public void committed(int schedule, int idx, Lock lock, List<Task> _task){
        for (int i = idx-1; i>=0; i--){
            try {
                if (!this.transaction.get(schedule).get(i).getResource().equals("C")) {
                    this.transaction.get(schedule).get(i).setStatus("COMMITTED");
                    lock.unlock(this.transaction.get(schedule).get(i).getResource());
                }
            }catch (Exception e){
            }
        }
        System.out.println(_task.get(idx));
        if (!waitingList.isEmpty()){
            Iterator<Task> iterator = waitingList.iterator();
            while (iterator.hasNext()) {
                Task currentTask = iterator.next();
                String keyType = Utils.isKeyExclusive(_task, currentTask);

                if (lock.checkPrivileged(currentTask)) {
                    System.out.println(currentTask);
                    iterator.remove();
                    continue;
                }

                if (lock.checkPermission(currentTask, keyType)) {
                    lock.addLock(currentTask.getResource(), keyType, currentTask.getTransaction());
                    System.out.println(currentTask);
                    iterator.remove();
                }
            }
        }
    }

    public Task getTaskByQueueAtTransaction(int queue){
        Set<Integer> keys = this.transaction.keySet();
        for (int key : keys){
            List<Task> tasks = this.transaction.get(key);
            for (Task task: tasks){
                if (task.getQueue() == queue)
                    return task;
            }
        }
        return null;
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
