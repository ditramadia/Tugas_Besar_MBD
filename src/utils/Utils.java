package utils;

import task.Task;

import java.util.List;

public class Utils {
    public static String isKeyExclusive(List<Task> tasks, Task task){
        if (task.getOperation().equals("W"))
            return "Exclusive";

        for (Task tsk: tasks){
            if (tsk.getResource().equals(task.getResource())  &&
                    tsk.getOperation().equals("W") &&
                    tsk.getTransaction()==task.getTransaction())
                return "Exclusive";
        }
        return "Shared";
    }
}
