package task;

public class Task {
    private static int _queue = 1;
    private final int schedule;
    private final String task;
    private final String taskName;
    private final int queue;
    private String status;

    public Task(){
        this.schedule = 0;
        this.taskName = "";
        this.task = "";
        this.status = "UNCOMMITTED";
        this.queue = _queue;
        _queue++;
    }

    public Task(String task, String taskName, int schedule){
        this.schedule = schedule;
        this.taskName = taskName;
        this.task = task;
        this.status = "UNCOMMITTED";
        this.queue = _queue;
        _queue++;
    }

    public Task(String task, int schedule){
        this.schedule = schedule;
        this.task = task;
        this.taskName = "";
        this.status = "UNCOMMITTED";
        this.queue = _queue;
        _queue++;
    }

    public String getTask(){ return this.task;}
    public String getTaskName(){ return this.taskName;}
    public int getSchedule(){ return this.schedule;}
    public String getStatus(){ return this.status;}
    public int getQueue(){return this.queue;}

    public void setStatus(String status){ this.status = status;}


    @Override
    public String toString() {
        switch (this.task) {
            case "W" -> {
                return "Write task " + this.taskName + " in Time " + this.schedule;
            }
            case "R" -> {
                return "Read task " + this.taskName + " in Time " + this.schedule;
            }
            case "C" -> {
                return "Committed Time "+this.schedule;
            }
            default -> {
                return "Nothing to do";
            }
        }
    }
}
