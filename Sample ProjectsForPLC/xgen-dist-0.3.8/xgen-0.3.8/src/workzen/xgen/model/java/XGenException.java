/*
 * Xml2JavaException.java
 * Copyright (c) Brad D Matlack 2002 - 2003
 * License: http://www.gnu.org/gpl
 *
 * This program is free software.
 *
 * You may redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation.
 * Version 2 of the license should be included with this distribution in
 * the file LICENSE, as well as License.html. If the license is not
 * included with this distribution, you may find a copy at the FSF web
 * site at 'www.gnu.org' or 'www.fsf.org', or you may write to the
 * Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139 USA.
 *
 * THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND,
 * NOT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR
 * OF THIS SOFTWARE, ASSUMES _NO_ bundlePONSIBILITY FOR ANY
 * CONSEQUENCE bundleULTING FROM THE USE, MODIFICATION, OR
 * REDISTRIBUTION OF THIS SOFTWARE.
 */
package workzen.xgen.model.java;

/**
 *   
 */
public class XGenException extends Exception { 
  
  private Throwable cause = null;

  public XGenException() {
    super();
  }

  public XGenException(java.lang.String message) { 
    super(message);
  }

  public XGenException(java.lang.String message, Throwable cause) {
    super(message);
    this.cause = cause;
  }
  
  public XGenException(Throwable cause) {
    super(cause.getMessage());
    this.cause = cause;
  }

  public Throwable getCause() {
    return cause;
  }

  public void printStackTrace() {
    super.printStackTrace();
    if (cause != null) {
      cause.printStackTrace();
    }
  }

  public void printStackTrace(java.io.PrintStream ps) {
    super.printStackTrace(ps);
    if (cause != null) {
      ps.println("Caused by:");
      cause.printStackTrace(ps);
    }
  }

  public void printStackTrace(java.io.PrintWriter pw) {
    super.printStackTrace(pw);
    if (cause != null) {
      pw.println("Caused by:");
      cause.printStackTrace(pw);
    }
  }
}


