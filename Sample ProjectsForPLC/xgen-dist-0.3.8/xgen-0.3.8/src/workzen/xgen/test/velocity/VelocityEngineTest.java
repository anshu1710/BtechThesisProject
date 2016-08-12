/*
 * VelocityEngineTest.java
 * Copyright (c) 2000-2001 Brad D Matlack.  
 * License http://www.gnu.org/copyleft/gpl.html
 */

package workzen.xgen.test.velocity;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import workzen.xgen.loader.ILoader;
import workzen.xgen.loader.XGenLoader;
import workzen.xgen.model.java.Package;

/**
 * Just a bunch of tests to see how Velocity works.
 */
public class VelocityEngineTest
{
  public static void main( String[] args )	throws Exception
  {
    String xml = "file:///D:/project/xit/work/pojo/input/typebean.xml";
    String templatePath = "D:/project/xit/templates/pojo";
    String template = "control.vm";
    
    Properties p = new Properties();
    p.setProperty("file.resource.loader.path", templatePath);
    Package pkg = null;
    
    try{
      java.net.URL url = new java.net.URL(xml);
      ILoader loader = new XGenLoader();
      pkg = (Package)loader.load(url,false);
    }catch(Exception e){
      e.printStackTrace();
    }

    BasicConfigurator.configure();
    
    VelocityEngine ve = new VelocityEngine();
    ve.init(p);
    Template t = ve.getTemplate( template );
    VelocityContext context = new VelocityContext();
    
    context.put("model", pkg);
    
    StringWriter writer = new StringWriter();		
    t.merge( context, writer );
    System.out.println( writer.toString() );     
  }
}

