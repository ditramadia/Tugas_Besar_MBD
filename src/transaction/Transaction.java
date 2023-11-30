package transaction;

import java.util.ArrayList;

/**
 * Transaction adalah satu transaksi dalam operasi
 * yang nantinya akan berjalan konkuren dengan transaksi lain.
 * Transaction dalam kasus ini diimplementasikan dengan Thread
 */
public class Transaction implements Runnable{
    int startTime;
    int validTime;
    int finishTime;

    /**
     * schedule is the variable needing to be scheduled
     * TODO: implement scheduler
     */
    ArrayList<String> schedule = new ArrayList<>();

    public Transaction(int startTime){
        this.startTime = startTime;
    }

    @Override
    public void run() {
        // Untuk setiap dia melakukan operasi ke-i,
        // validTime = startTime + i
        
        // Untuk setiap dia melakukan validasi ke-j,
        // finishTime = validTime + j

        // Di akhir run Transaction, akan terbentuk startTime, validTime, dan finishTime yang sesuai protokol OCC
    }
}
