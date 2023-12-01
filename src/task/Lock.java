package task;

import java.util.*;

public class Lock {
    private static Map<String, List<TypeLock>> lock;

    public Lock(){
        lock = new HashMap<>();
    }

    public void addLock(String resource, String type, int schedule){
        System.out.println("Grant access "+type+" "+resource+" in Schedule "+ schedule);
        if (lock.containsKey(resource)){
            lock.get(resource).add(new TypeLock(type, schedule));
        }else {
            lock.put(resource, new ArrayList<>());
            lock.get(resource).add(new TypeLock(type, schedule));
        }
    }

    public void unlock(String resource){
        if (!lock.containsKey(resource))
            return;

        List<TypeLock> temp = lock.get(resource);
        lock.remove(resource);
        Iterator<TypeLock> iterator = temp.listIterator();
        while (iterator.hasNext()){
            TypeLock _temp = iterator.next();
            System.out.println("Unlock resource "+ resource +" in Schedule "+ _temp.getSchedule());
        }
    }

    public boolean isLockEmpty(){
        return lock.isEmpty();
    }

    public boolean checkPermission(Task task, String type){
        List<TypeLock> typeLocks = lock.get(task.getResource());

        if (typeLocks == null)
            return true;

        for (TypeLock typeLock: typeLocks) {
            if (task.getSchedule() == typeLock.schedule)
                return true;

            if (typeLock.getType().equals("Exclusive"))
                return false;
        }

        return !type.equals("Exclusive");
    }

    public boolean checkPrivileged(Task task){
        List<TypeLock> typeLocks = lock.get(task.getResource());

        if (typeLocks == null)
            return false;

        for (TypeLock typeLock: typeLocks) {
            if (task.getSchedule() == typeLock.schedule)
                return true;
        }

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

