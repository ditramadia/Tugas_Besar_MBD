package task;

public class Task {
    private final int schedule;
    private final String task;
    private final String taskName;

    public Task(){
        this.schedule = 0;
        this.taskName = "";
        this.task = "";
    }

    public Task(String task, String taskName, int schedule){
        this.schedule = schedule;
        this.taskName = taskName;
        this.task = task;
    }

    public String getTask(){ return this.task;}
    public String getTaskName(){ return this.taskName;}
    public int getSchedule(){ return this.schedule;}


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
