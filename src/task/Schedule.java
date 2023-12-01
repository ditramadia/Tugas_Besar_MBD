package task;

import utils.Utils;

import java.util.*;

public class Schedule {
    private final Map<Integer, List<Task>> schedule = new HashMap<>();
    private static List<Task> waitingList;

    public Schedule(List<Task> tasks){
        for (Task task : tasks){
            if (this.schedule.containsKey(task.getSchedule())){
                this.schedule.get(task.getSchedule()).add(task);
            }else {
                this.schedule.put(task.getSchedule(), new ArrayList<>());
                this.schedule.get(task.getSchedule()).add(task);
            }
        }

        waitingList = new ArrayList<>();
    }

    public void committed(int schedule, int idx, Lock lock, List<Task> _task){
        for (int i = idx-1; i>=0; i--){
            try {
                    this.schedule.get(schedule).get(i).setStatus("COMMITTED");
                    lock.unlock(this.schedule.get(schedule).get(i).getResource(), schedule);
            }catch (Exception e){
                System.out.print("");
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
                    lock.addLock(currentTask.getResource(), keyType, currentTask.getSchedule());
                    System.out.println(currentTask);
                    iterator.remove();
                }
            }
        }
    }

    public void addWaitingList(Task task){
        waitingList.add(task);
    }
}
