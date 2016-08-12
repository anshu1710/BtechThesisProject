/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package acm_table;

/**
 *
 * @author Prashant
 */
public class Threadclass implements Runnable{

    Thread t1;
     base b;

    public Threadclass(base b)

    {
         this.b=b;
        t1=new Thread(this,"clipboardThread");
        t1.start();

        
  
    }

    public void run()
    {
       
            while(true)
            {
                 try {
                    Thread.sleep(800);
                    } catch (InterruptedException ex) {
                  
                    }
          b. checker();
  
        }
    }

}




