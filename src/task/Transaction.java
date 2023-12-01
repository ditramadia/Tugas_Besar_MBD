package task;

import utils.Utils;

import java.util.*;

/**
 * Transaction class
 * <p>Merepresentasikan schedule yang dilakukan oleh beberapa transaksi
 * <p>transaction : Transaksi-transaksi yang terlibat dan runtutan operasi yang dilakukan
 * <p>waitingList : Operasi-operasi yang menunggu untuk dilakukan
 */
public class Transaction {
    private Map<Integer, List<Task>> transaction = new HashMap<>();
    private List<Task> waitingList;

    /**
     * Contructor <p>
     * Ketika suatu task dilakukan oleh transaksi yang belum ada di schedule,
     * akan dimap sebagai key baru pada transaction.
     * Jika sudah ada, aksi ditambahkan ke key yang sudah ada
     * Setelah melakukan inisiasi transaction, juga melakukan inisiasi waitinglist
     * @param tasks list of task requests
     */
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

    /**
     * Melakukan commit untuk operasi-operasi yang sudah ada
     * transaksi dan operasi yang ditentukan
     * serta membuka lock yang berkaitan dengan operasi tersebut. <p>
     * Setelah melakukan commit, method melanjutkan dengan 
     * memasukkan operasi selanjutnya yang ada di waitinglist
     * 
     * @param schedule transaction yang ingin commit
     * @param idx operasi dari transaksi yang ingin dicommit
     * @param lock lock yang ingin diunlock
     */
    public void committed(int schedule, int idx, Lock lock){
        for (int i = idx; i>=0; i--){
            this.transaction.get(schedule).get(i).setStatus("COMMITTED");
            lock.unlock(this.transaction.get(schedule).get(i).getResource());
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

    /**
     * Mencari operasi yang dilakukan transaksi
     * dengan posisinya di dalam urutan schedule
     * @param queue posisi i dari task-task di dalam schedule
     * @return task ke-i
     */
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
