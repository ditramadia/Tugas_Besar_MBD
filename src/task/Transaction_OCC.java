package task;

import java.util.HashSet;
import java.util.Set;

public class Transaction_OCC {
    private TimeStamp ts;
    private Set<Task> readSet;
    private Set<Task> writeSet;

    public Transaction_OCC(){
        this.ts = new TimeStamp();
        this.readSet = new HashSet<>();
        this.writeSet = new HashSet<>();
    }
    /**
     * Timestamp dari transaksi yang merepresentasikan <p>
     * startTime = waktu mulai transaksi (default 0)
     * validTime = waktu mulai validasi
     * finishTime = waktu transaksi selesai
     */
    private class TimeStamp {
        int startTime = 0;
        int validTime;
        int finishTime;
        TimeStamp(){
            this.startTime=0;
            this.validTime=0;
            this.finishTime=0;
        }
        int getValidTime() {
            return validTime;
        }
        void setValidTime(int validTime) {
            this.validTime = validTime;
        }
        int getFinishTime(){
            return finishTime;
        }
        void setFinishTime(int finishTime) {
            this.finishTime = finishTime;
        }
    }
}
