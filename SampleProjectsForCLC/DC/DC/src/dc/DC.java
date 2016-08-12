/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dc;
/**
 *
 * @author Dell
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import javax.swing.JFileChooser;


class Frame1 extends Frame  
{
     Thread th; 
    BufferedReader br;
    String s1;
MenuBar mb=new MenuBar();
	Menu m1=new Menu("File");
        MenuItem Item1=new MenuItem("New");
        MenuItem Item2=new MenuItem("Exit");
        MenuItem Item3=new MenuItem("Query");
        TextField tx=new TextField();
 Socket s;
 PrintWriter pw;
 TextField tf;
Frame1()
  {     
        
            addWindowListener( new java.awt.event.WindowAdapter()
             { @Override
   public void windowClosing( java.awt.event.WindowEvent e )   
           {  if(pw!=null)
               pw.println("delete");
               System.exit( 0 );  
              }  
         }  
       );
           setMenuBar(mb);
           //setLayout();//no set layout in this so textarea willoccupy whole frame
           setSize(400,400);
           setTitle("select");
           tf=new TextField();
   mb.add(m1);
   m1.add(Item1);
   m1.add(Item3);
   m1.add(Item2);
   add(tf);
   Item1.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){Upload();}});
           Item2.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){Exit();}});
        Item3.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){Query();}});   

        try {
        s=new Socket("127.0.0.7",1241);
    pw=new PrintWriter(s.getOutputStream(),true);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Frame1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Frame1.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
void Query()
{
    String h,q;   
    try {
           if(pw!=null)
            {
                
                pw.println("query");
                q=tf.getText();
                pw.println(q);
            
            br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            if((h=br.readLine())!=null)
            tf.setText(h);
            ClientServer.Qu(h,q);
     
            }
    } catch (IOException ex) {
            Logger.getLogger(Frame1.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
 
 void Exit()
{ 
    if(pw!=null)
    pw.println("delete");  
    System.exit(0);
}

void Upload()
{
JFileChooser chooser = new JFileChooser();
chooser.setDialogTitle("Select target directory");
chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
int returnVal = chooser.showOpenDialog(this);
if(returnVal == JFileChooser.APPROVE_OPTION) {
File a=chooser.getSelectedFile();
final String d=a.getAbsolutePath();
File file = new File(d);  
 File[] files = file.listFiles();  
 try
{ pw.println("update");
for (int fileInList = 0; fileInList < files.length; fileInList++)  
 {  
 pw.println(files[fileInList].getName());  
 }  
pw.println("end");
 th=new Thread() {@Override
 public void run()
 { ClientServer a=new ClientServer(d); } };
  th.setDaemon(true);
 th.start();
}
catch(Exception E)
{
}
}
}
}

class DC
{	
	public static void main(String[] ar)
	{
	Frame1 a=new Frame1();
	a.setVisible(true);
	}
}