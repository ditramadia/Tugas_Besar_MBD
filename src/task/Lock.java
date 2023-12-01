package task;

import java.util.HashMap;
import java.util.Map;

public class Lock {
    private static Map<String, TypeLock> lock;

    public Lock(){
        lock = new HashMap<>();
    }

    public void addLock(String resource, String type, int schedule){
        System.out.println("Grant access "+type+" "+resource+" in schedule "+ schedule);
        lock.put(resource, new TypeLock(type, schedule));
    }

    public void unlock(String resource){
        if (!lock.containsKey(resource))
            return;

        TypeLock temp = lock.get(resource);
        lock.remove(resource);

        System.out.println("Unlock "+ resource +" in schedule "+ temp.getSchedule());
    }

    public boolean isLockEmpty(){
        return lock.isEmpty();
    }

    public boolean checkPermission(Task task, String type){
        TypeLock typeLock = lock.get(task.getResource());

        if (typeLock == null)
            return true;

        if (task.getTransaction() == typeLock.schedule)
            return true;

        if (typeLock.getType().equals("Exclusive"))
            return false;

        return !type.equals("Exclusive");
    }

    public boolean checkPrivileged(Task task){
        TypeLock typeLock = lock.get(task.getResource());

        if (typeLock == null)
            return false;

        if (task.getTransaction() == typeLock.schedule)
            return true;

        return false;
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

