/*
 * Copyright (c) Mar 5, 2004 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.test.velocity;

import java.io.File;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import workzen.common.util.ResUtil;
import workzen.common.util.StringUtil;

import junit.framework.TestCase;

/**
 * NOTE: Must use VelocityEngine with multiple configurations!!!!!
 * 
 * @author matlackb
 */
public class TestCase1 extends TestCase {

	//private String templateName = "test.vm";
	//private String input = "et/input/typebean.xml";
	//private String output = "persistenceMgr/output";
	private String ISOEncoding = "ISO-8859-1";
	private String UTFEncoding = "UTF-8";

	private Logger logger = Logger.getLogger(TestCase1.class);

	/**
	 * Constructor for TestCase1.
	 * @param arg0
	 */
	public TestCase1(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestCase1.class);
	}

	public void setUp() {
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		logger.debug("setUp()");
	}

	/**
	 * push and pull from the context
	 */
	public void testEvaluate() {
		logger.debug("testEvaluate()");
		try {
			VelocityEngine engine = new VelocityEngine();
			engine.init();
			VelocityContext context = new VelocityContext();

			context.put("name", "Velocity Test 1");
			context.put("project", "Jakarta");

			StringWriter writer = new StringWriter();
			String s = "We are using $project $name to render this.";

			engine.evaluate(context, writer, "mystring", s);

			logger.debug("=>" + writer.toString());
			assertEquals(
				writer.toString(),
				"We are using Jakarta Velocity Test 1 to render this.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test the classloader. For testing in eclipse,
	 * this works only if xgen-x.x.x.jar is in the classpath!!!
	 * class.resource.loader.class = org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
	 * templateName = templates/test/test.vm
	 */
	public void testClassLoader() {
		logger.debug("testClassLoader()");
				
		try {
			Properties p = new Properties();
			p.setProperty(Velocity.RESOURCE_LOADER, "class");
			p.setProperty(
				"class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
								
			VelocityEngine engine = new VelocityEngine();
			engine.init(p);
	
			VelocityContext context = new VelocityContext();
			context.put("name", "Velocity Test 2");
			context.put("project", "Jakarta");
	
			StringWriter writer = new StringWriter();
	
			//String templateName = "workzen/xgen/templates/test/test.vm";
			String templateName = "templates/test/test.vm";
			//String templateName = "test.vm";
			logger.debug("template: " + templateName);
	
			engine.mergeTemplate(templateName, UTFEncoding, context, writer);
			logger.debug("=>" + writer.toString());
	
			assertEquals(
				writer.toString(),
				"We are using Jakarta Velocity Test 2 to render this.");
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test the jar loader
	 * <pre>
	 * jar.resource.loader.class = org.apache.velocity.runtime.resource.loader.JarResourceLoader
	 * jar.resource.loader.path = jar:file:/opt/myfiles/jar1.jar
	 * templateName = templates/test/test.vm
	 * </pre>
	 */
	public void testJarLoader() {
		logger.debug("testJarLoader()");
		//String jarPath = "jar:file:/project/workzen.xgen/xgen-0.3.5.jar!/templates/test/";
		String jarPath = "jar:file:/project/workzen.xgen/xgen-0.3.5.jar";

		try {
			Properties p = new Properties();
			p.setProperty("resource.loader", "jar");
			p.setProperty(
				"jar.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.JarResourceLoader");
			p.setProperty("jar.resource.loader.path", jarPath);
			logger.debug("setting=> jar.resource.loader.path: " + jarPath);
			
			VelocityEngine engine = new VelocityEngine();
			engine.init(p);

			VelocityContext context = new VelocityContext();
			context.put("name", "Velocity Test 3");
			context.put("project", "Jakarta");

			StringWriter writer = new StringWriter();

			String templateName = "templates/test/test.vm";
			//String templateName = "test.vm";
			logger.debug("template: " + templateName);

			engine.mergeTemplate(templateName, UTFEncoding, context, writer);
			logger.debug("=>" + writer.toString());

			assertEquals(
				writer.toString(),
				"We are using Jakarta Velocity Test 3 to render this.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * We'll find the file ourselves, and merge using the VelocityEngine.
	 * We set the file resource loader path first!
	 * file.resource.loader.path = d:/absolute/path
	 * templateName = templates/test/test.vm
	 */
	public void testFileLoader() {
		logger.debug("testFileLoader()");
		try {

			String templateName = "templates/test/test.vm";
			//String templateName = "test.vm";
			logger.debug("template: " + templateName);

			Properties p = new Properties();

			File file = ResUtil.getFile(templateName);
			String path = "/" + file.getParent();
			path = StringUtil.replaceAll(path, "\\", "/");
			
			path = "d:/project/workzen.xgen/src/workzen/xgen";
			p.setProperty("file.resource.loader.path", path);
			logger.debug("setting=> file.resource.loader.path: " + path);

			VelocityEngine engine = new VelocityEngine();
			engine.init(p);

			VelocityContext context = new VelocityContext();
			context.put("name", "Velocity Test 4");
			context.put("project", "Jakarta");

			StringWriter writer = new StringWriter();

			Template template = engine.getTemplate(templateName);
			//Template template = Velocity.getTemplate(fileName); 
			template.merge(context, writer);

			logger.debug("=>" + writer.toString());

			assertEquals(
				writer.toString(),
				"We are using Jakarta Velocity Test 4 to render this.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
