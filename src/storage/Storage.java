package storage;

import java.util.HashMap;
import java.util.Map;

/**
 * Kelas untuk merepresentasikan basis data yang
 * diberi perlakuan operasi read/write.
 * Storage direpresentasikan dengan suatu map yang mempunyai id dan value
 * dengan operasi read adalah getter dari suatu id
 * dan operasi write adalah setter dari suatu id
 * 
 * Method yang diimplementasikan mengasumsikan operasi yang committed,
 * operasi yang belum commited seperti OCC pada fase executing akan 
 * perlu melakukan copy data terlebih dulu
 */
public class Storage {
    private Map<Integer,Integer> Data;
    public Storage(){
        this.Data = new HashMap<>();
    }
    public int read(int id){
        System.out.println("Read : ["+this.Data.get(id)+"]");
        return this.Data.get(id);
    }
    public void write(int id, int inputData){
        if(!this.Data.containsKey(id)){
            this.Data.put(id,inputData);
            System.out.println("Write : new data with id ["+id+"] and value ["+inputData+"]");
        }else{
            this.Data.replace(id,inputData);
            System.out.println("Write : ["+inputData+"] to ["+this.Data.get(id)+"]");
        }
    }
}