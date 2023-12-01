package task;

/**
 * Task class <p>
 * Merepresentasikan task yang dilakukan di dalam schedule <p>
 * Biasanya task ditulis dengan notasi XY(Z) dengan <p>
 * operation = X = jenis operasi yang dilakukan (R)ead, (W)rite, atau (C)ommit <p>
 * transaction = Y = nama transaksi (1,2,...,n) dengan n adalah bilangan asli sembarang <p>
 * resource = Z = resource yang diakses dalam operasi <p>
 * queue = nomor urut task yang akan dilakukan dalam schedule <p>
 * status = status task (uncommitted, committed, aborted, finished) 
 * (dapat berubah tergantung metode concurrency control) <p>
 */
public class Task {
    private static int _queue = 0;
    private final int transaction;
    private final String operation;
    private final String resource;
    private final int queue;
    private String status;

    public Task(){
        this.transaction = 0;
        this.resource = "";
        this.operation = "";
        this.status = "UNCOMMITTED";
        this.queue = _queue;
        _queue++;
    }

    public Task(String operation, String resource, int transaction){
        this.transaction = transaction;
        this.resource = resource;
        this.operation = operation;
        this.status = "UNCOMMITTED";
        this.queue = _queue;
        _queue++;
    }

    public Task(String operation, int transaction){
        this.transaction = transaction;
        this.operation = operation;
        this.resource = "";
        this.status = "UNCOMMITTED";
        this.queue = _queue;
        _queue++;
    }

    public String getOperation(){ return this.operation;}
    public String getResource(){ return this.resource;}
    public int getTransaction(){ return this.transaction;}
    public String getStatus(){ return this.status;}
    public int getQueue(){return this.queue;}

    public void setStatus(String status){ this.status = status;}


    @Override
    public String toString() {
        switch (this.operation) {
            case "W" -> {
                return "Write task " + this.resource + " in Time " + this.transaction;
            }
            case "R" -> {
                return "Read task " + this.resource + " in Time " + this.transaction;
            }
            case "C" -> {
                return "Committed Time "+this.transaction;
            }
            default -> {
                return "Nothing to do";
            }
        }
    }
}
