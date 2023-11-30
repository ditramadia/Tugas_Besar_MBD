package task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
    private Map<Integer, List<Task>> schedule = new HashMap<>();

    public Schedule(Task[] tasks){
        for (Task task : tasks){
            if (this.schedule.containsKey(task.getSchedule())){
                this.schedule.get(task.getSchedule()).add(task);
            }else {
                this.schedule.put(task.getSchedule(), new ArrayList<>());
                this.schedule.get(task.getSchedule()).add(task);
            }
        }
    }

    public void committed(int schedule, int idx){
        for (int i = idx; i>=0; i--){
            this.schedule.get(schedule).get(i).setStatus("COMMITTED");
        }
        System.out.println("Committed schedule "+ schedule);
    }
}
