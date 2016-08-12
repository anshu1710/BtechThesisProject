package acm_table;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Toolkit;
import java.io.*;

public final class TextTransfer implements ClipboardOwner {
Clipboard clipboard ; Transferable contents ;

    public TextTransfer()
    { clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        contents = null;

    }

     public String result;
     

   public void lostOwnership( Clipboard aClipboard, Transferable aContents) {

      
     
   }

  /**

  * owner of thecontents.
  */
  public void setClipboardContents( String aString ){
    
    StringSelection stringSelection = new StringSelection( aString );
  //  Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents( stringSelection, this );
  }

  /**

  *
  * @return anoard; if none found, return any text found on t

  */
  public String getClipboardContents() {
    result = "";
    contents = null;
    //odd: the Object param of getContents is not currently used
   try{ contents = clipboard.getContents(this);
      }
   catch(Exception e){
       
   }


    boolean hasTransferableText =
      (contents != null) &&
      contents.isDataFlavorSupported(DataFlavor.stringFlavor);

    if ( hasTransferableText ) {
      try {
        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
      }
      catch (UnsupportedFlavorException ex){

        System.out.println(ex);
      }
      catch (IOException ex) {
        System.out.println(ex);
      }
    }
    return result;
  }
}
