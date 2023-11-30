package utils;

import task.Task;

import java.util.List;

public class Utils {
    public static boolean isKeyExclusive(List<Task> tasks, Task task){
        if (task.getOperation().equals("W"))
            return true;

        for (Task tsk: tasks){
            if (tsk.getResource().equals(task.getResource())  && tsk.getOperation().equals("W"))
                return true;
        }
        return false;
    }
}
