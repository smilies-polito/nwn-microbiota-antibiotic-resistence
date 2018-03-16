package src.model.application_example;
import java.util.concurrent.Callable;
import java.util.NoSuchElementException; 
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.Set;
import java.util.Collection;
import java.util.Arrays;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.renew.engine.simulator.SimulationThreadPool;
import de.renew.net.NetInstance;
import de.renew.net.Place;
import de.renew.net.PlaceInstance;
import de.renew.engine.searchqueue.SearchQueue;

 
 /**  
  * Thread safe fixed size circular buffers implementation. Backed by an array.  
  *   
  * @author brad  feat. roberta feat. anonymous
  */  
 
public class tracker {  
      
      // accessible data storages  
      public Integer[][] data;
      public Integer[] insertLocation;
      public Integer[] sizebuf;
      
      public int bufferSize;
        /**  
       * Creating one File Writer based on a buffer, that is unique for all cells
       *          
       */    
      public FileWriter fw;
      public BufferedWriter bw;
      
      

      /**  
       * Creating a circular buffer with the specified uniform size for each pnp cell.   
       *   
       * @param bufferSize  
       *      - the maximum size of the buffers , unique for all pnp cells      
       */  
      
      public tracker(int bsize) {
            
            bufferSize=bsize;
            insertLocation = new Integer[6];
            sizebuf = new Integer[6];
            data= (Integer[][]) new Integer[6][bufferSize];
            for (int i=0; i<6; i++) {
                insertLocation[i]=0;
                sizebuf[i]=0;
            }  
      }  
      
      
      /**  
       * Separately and independently for each cell, this group of methods
       * inserts items at the end of the queues. If a queue is full, the oldest  
       * value will be removed and head of the queue will become the second oldest  
       * value.  
       *   
       * @param item  
       *      - the item to be inserted  
       */  
      
      public void insert (int item, int cell) {
            data[cell][insertLocation[cell]]=item;
            insertLocation[cell] = (insertLocation[cell]+1) % bufferSize;
            if (sizebuf[cell] < bufferSize) {
                sizebuf[cell]++;
            }
      }
      
      
      public double average (int cell) {
            int sum =0;
            for (int i =0; i< sizebuf[cell]; i++) {
                sum += data[cell][i];
            }    
            return (double) sum / sizebuf[cell];
      
      }
   
      
      public int getSize(int cell) {
            return sizebuf[cell];
      }
      
      public int getLastElement (int cell) {
            int i;
            if (insertLocation[cell]==0) {
                i = bufferSize -1;
            } else {
                i = insertLocation[cell]-1;
            }
      
            return data[cell][i];
      }      
      
         
          
      /**  
       * Appends to a file a timestamp, and for P3p, P4p, P5p, P6p, P7p, P8p cells: current avg of data array, plus some relevant statistics
       *   
       *  
       */     
      public void write_to_file(String filename){
      
        try {
           			
			String dat = System.currentTimeMillis()+"";
			
			for(int i=0; i<6; i++){
			    
			    dat+="\t" + average(i);
			}
			
			dat+="\n";

           
             
			File file = new File(filename);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(dat);

			//System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

	   }
    }      
  }  
}  
 
