/*
 * To change this template, chTools | ates
 * and open the template in the editor.
 */

package acm_table;

/**
 *
 * @author Prashant
 */
public class Main {

    /**
     * @param args theand 
     */
    public static void main(String[] args) {
       
        base clip=new base();
       clip.setTitle("Clipboard");
 
       clip.setAlwaysOnTop(true);
       clip.setVisible(true);
     
       Threadclass t=new Threadclass(clip);
     
    }

}
