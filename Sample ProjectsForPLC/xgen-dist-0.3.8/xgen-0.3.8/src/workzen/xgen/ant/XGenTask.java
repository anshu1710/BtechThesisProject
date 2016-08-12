/*
 * XGenTask.java
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
package workzen.xgen.ant;

import java.net.URL;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import workzen.xgen.loader.ILoader;
import workzen.xgen.loader.XGenLoader;
import workzen.xgen.model.java.XGenException;

/**
 * This class exends VETexenTask and initializes the Velocity context.
 * The meta-data loader initializes the model, which gets placed 
 * into the context, for translation into code.
 * 
 * <pre>
 * loader = workzen.xgen.util.XGenLoader
 * inputFile = d:/project/test/input/blog.xml
 * resourceTemplate = templates/persistenceMgr/control.vm
 * templatePath = d:/project/test
 * outputDir = d:/project/test/output/blog
 * outputFile = "some_output_file"
 * </pre>
 * 
 * The <b>loader</b> is <i>optional</i>. It is used to define the meta-data loader. 
 * XGen automatically defines this to be "workzen.xgen.loader.XGenLoader", which
 * loads the xgen schema. For instance, you could write a loader for any type of
 * structured data, and then transform that data using velocity macros.
 * <p>
 * The <b>inputFile</b> is <i>required</i>. It defines the path of the 
 * input file used by the meta-data loader.
 * <p>
 * The <b>resourceTemplate</b> is <i>required</i>. It is an alias for the 
 * <b>controlTemplate</b> Velocity parameter. Specify a "resource" path 
 * to the template. This way, we can search both jar files, and the filesystem. 
 * <p>
 * The <b>templatePath</b> is <i>required</i> for filesystem lookups only.
 * The template path is used by the file resource loader to locate the template directory.
 * By default, this can be set to the root path of your project. 
 * Adding custom templates using the same resource path (templates/dir/template.vm), 
 * the filesystem template will be used first.
 * <p>
 * The <b>outputDir</b> is <i>required</i>. It defines the base directory of your output. 
 * <p>
 * The <b>outputFile</b> is <i>required</i> for standalone templates only.  
 * The output file is written to whenever the generator parses a template.
 * However, in the case of "control" templates, the generator is used internally
 * to write to a number of output files. In this case, the outputFile does not
 * need to be specified. XGen will automatically define a default output file.
 * 
 * @author <a href="mailto:bmatlack@workzen.userworld.com">Brad Matlack</a>
 */
public class XGenTask extends VETexenTask {

	public static final String DEFAULT_OUTPUT_FILE = "default.output";

	private String inputFile = "foo";
	private String resourceTemplate = "foo.vm";
	
	private ILoader loader = new XGenLoader();
	
	private Logger logger = Logger.getLogger(XGenTask.class);

	public XGenTask() {
		super();
		// initialize log4j
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		logger.debug("XGenTask()");
		super.outputFile = DEFAULT_OUTPUT_FILE;
	}

	/**
	 * Set the input file for the xml loader.
	 * <i>Required</i>
	 * Example: inputFile = d:/project/input/jesso.xml
	 * 
	 * @param xmlfile  to load.		
	 */
	public void setInputFile(String val) {
		logger.debug("setInputFile() " + val);
		if (isNull(val))
			return;
		this.inputFile = val;
	}

	/**
	 * Initialize the loader.
	 * This setter is optional. Default is XGenLoader.
	 * <i>Optional</i>
	 * Example: loader=com.loader.MyLoader
	 * @param val
	 */
	public void setLoader(String val) throws XGenException {
		logger.debug("setLoaderClassname() " + val);
		if (isNull(val))
			return;
		this.loader = getLoaderInstance(val);
	}
	
	/**
	 * Convience method for setting the controlTemplate.
	 * This is a reminder to use the naming convention: "templates/pojo/controlvm".
	 */
	public void setResourceTemplate(String val){
		logger.debug("setResourceTemplate() " + val);
		if (isNull(val))
			return;
		this.resourceTemplate = val;
		super.controlTemplate = resourceTemplate;
	}
	
	/**
	 * Override the default method, and call setResourceTemplate()
	 * @see org.apache.velocity.texen.ant.TexenTask#setControlTemplate(java.lang.String)
	 */
	public void setControlTemplate(String val){
		setResourceTemplate(val);
	}

	/**
	 * <i>Optional</i> Required for standalone templates.
	 * @see org.apache.velocity.texen.ant.TexenTask#setOutputFile(java.lang.String)
	 */
	public void setOutputFile(String outputFile) {
		logger.debug("setOutputFile() " + outputFile);
		if (isNull(outputFile))
			return;
		super.setOutputFile(outputFile);
	}
	
	/**
	 * Override and initialize the Velocity context.
	 * @see org.apache.velocity.texen.ant.TexenTask#initControlContext()
	 */
	public Context initControlContext() throws Exception {
		super.initControlContext();
		logger.debug("initControlContext()");
		
		// put the model into the context
		VelocityContext context = new VelocityContext();
		Object model = loadModel(inputFile);
		context.put("model", model);
		
		context.put("resourcePath", getResourcePath(controlTemplate));
		return context;
	}

	/** 
	 * Load meta-data using the loader instance.
	 */
	private Object loadModel(String xmlFile) {
		logger.debug("loadModel() " + xmlFile);
		Object obj = null;
		try {
			URL url = new java.net.URL("file:///" + xmlFile);
			obj = loader.load(url, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	
	/**
	 * The resource path is used in control templates to 
	 * setup child template names.
	 * If the controlTemplate = templates/pojo/control.vm, 
	 * the resourcePath = templates/pojo/
	 * 
	 * @param res
	 * @return
	 */
	private String getResourcePath(String res){
		int index = res.lastIndexOf("/");
		String path = res;	
		if( index > 0 ){
			path = res.substring(0, index + 1);
		}
		logger.debug("resourcePath: " + path);
		return path;
	}

	/** */
	private boolean isNull(String str) {
		if (str == null || str.equals(""))
			return true;
		return false;
	}
	
	/** */
	private boolean notNull(String str){
		return ! isNull(str);	
	}

	/** 
	 * Instantiate the ILoader class.
	 * 
	 * @param classname
	 */
	private ILoader getLoaderInstance(String loaderClassname)
		throws XGenException {
		logger.debug("getLoaderInstance() " + loaderClassname);

		Class c = null;
		try {
			c = Class.forName(loaderClassname);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new XGenException(e);
		}

		Object obj = null;
		try {
			obj = c.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
			throw new XGenException(e1);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
			throw new XGenException(e1);
		}
		return (ILoader) obj;
	}

}
