package task;


/** Timestamped task <p> Task yang ditambah dengan keterangan timestamp operasi dari 
 * Timestamp dari transaksi yang merepresentasikan <p>
 * startTime = waktu mulai transaksi (default 0)
 * validTime = waktu mulai validasi
 * finishTime = waktu transaksi selesai
 */
public class TimestampedTask extends Task {
    private int timestamp;
    public TimestampedTask(String operation, int transaction, int timestamp){
        super(operation, transaction);
        this.timestamp=timestamp;
    }
    public TimestampedTask(String operation, String resource, int transaction, int timestamp){
        super(operation, resource, transaction);
        this.timestamp=timestamp;
    }
    public TimestampedTask(int timestamp){
        super();
        this.timestamp=timestamp;
    }
    public int getTimestamp() {
        return timestamp;
    }
}
