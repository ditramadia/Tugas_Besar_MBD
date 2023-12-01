package algorithm;

import task.Lock;
import task.Task;
import task.Transaction;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwoPhaseLocking {
    private List<Task> tasks = new ArrayList<>();
    private Transaction transaction;
    private Lock lock;


    public TwoPhaseLocking(String transaction){
        String[] _temp = transaction.split(" ");
        Pattern pattern1 = Pattern.compile("([A-Za-z])(\\d+)\\(([^)]+)\\)");
        Pattern pattern2 = Pattern.compile("([A-Za-z])(\\d+)$");
        for (String temp : _temp){
            Matcher match1 = pattern1.matcher(temp);
            while (match1.find()) {
                this.tasks.add(new Task(match1.group(1),match1.group(3), Integer.parseInt(match1.group(2))));
            }
            Matcher match2 = pattern2.matcher(temp);
            while (match2.find()) {
                this.tasks.add(new Task(match2.group(1), Integer.parseInt(match2.group(2))));
            }
        }

        this.transaction = new Transaction(this.tasks);
        this.lock = new Lock();
    }


    public void execute(){
        for (Task task: this.tasks){
            if (task.getOperation().equals("C")){
                this.transaction.committed(task.getTransaction(), task.getQueue(), this.lock, this.tasks);
                continue;
            }
            if (this.lock.checkPrivileged(task)){
                System.out.println(task);
                continue;
            }
            if (this.lock.checkPermission(task, Utils.isKeyExclusive(this.tasks, task))){
                this.lock.addLock(task.getResource(), Utils.isKeyExclusive(this.tasks, task), task.getTransaction());
                System.out.println(task);
            }else{
                System.out.println("Abort "+task.getOperation()+""+task.getTransaction()+""+"("+task.getResource()+")");
                this.transaction.addWaitingList(task);
            }
        }
    }
}
