package task;

import java.util.HashMap;
import java.util.Map;

public class Lock {
    private static Map<String, TypeLock> lock = new HashMap<>();

    public Lock(String taskName, String type, int time){
        lock.put(taskName, new TypeLock(type, time));
    }

    public boolean checkPermission(Task task, String type){
        TypeLock typeLock = lock.get(task.getTaskName());

        if (typeLock == null)
            return true;

        if (task.getTime() == typeLock.time)
            return true;

        if (typeLock.getType().equals("Exclusive"))
            return false;

        return !type.equals("Exclusive");
    }

    private class TypeLock{
        private String type;
        private int time;

        public TypeLock(String type, int time){
            this.type = type;
            this.time = time;
        }

        public String getType(){return type;}
        public int getTime(){return time;}
    }
}

