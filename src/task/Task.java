package task;

public class Task {
    private final int time;
    private final String task;
    private final String taskName;

    public Task(){
        this.time = 0;
        this.taskName = "";
        this.task = "";
    }

    public Task(String task, String taskName, int time){
        this.time = time;
        this.taskName = taskName;
        this.task = task;
    }

    public String getTask(){ return this.task;}
    public String getTaskName(){ return this.taskName;}
    public int getTime(){ return this.time;}


    @Override
    public String toString() {
        switch (this.task) {
            case "W" -> {
                return "Write task " + this.taskName + " in Time " + this.time;
            }
            case "R" -> {
                return "Read task " + this.taskName + " in Time " + this.time;
            }
            case "C" -> {
                return "Committed Time "+this.time;
            }
            default -> {
                return "Nothing to do";
            }
        }
    }
}
