package task;

import java.util.HashMap;
import java.util.Map;

public class Lock {
    private static Map<String, TypeLock> lock;

    public Lock(){
        lock = new HashMap<>();
    }

    public void addLock(String taskName, String type, int schedule){
        System.out.println("Grant access "+type+" "+taskName+" in schedule "+ schedule);
        lock.put(taskName, new TypeLock(type, schedule));
    }

    public void unlock(String taskName){
        if (!lock.containsKey(taskName))
            return;

        TypeLock temp = lock.get(taskName);
        lock.remove(taskName);

        System.out.println("Unlock "+ taskName +" in schedule "+ temp.getSchedule());
    }

    public boolean checkPermission(Task task, String type){
        TypeLock typeLock = lock.get(task.getTaskName());

        if (typeLock == null)
            return true;

        if (task.getSchedule() == typeLock.schedule)
            return true;

        if (typeLock.getType().equals("Exclusive"))
            return false;

        return !type.equals("Exclusive");
    }

    private class TypeLock{
        private String type;
        private int schedule;

        public TypeLock(String type, int schedule){
            this.type = type;
            this.schedule = schedule;
        }

        public String getType(){return type;}
        public int getSchedule(){return schedule;}
    }
}

