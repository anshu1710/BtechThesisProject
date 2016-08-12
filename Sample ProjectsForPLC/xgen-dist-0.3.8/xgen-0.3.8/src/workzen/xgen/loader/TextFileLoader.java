/*
 * Copyright (c) Apr 18, 2004 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.loader;

import java.io.File;
import java.net.URL;

import workzen.common.util.ResourceLoader;
import workzen.xgen.model.java.XGenException;
import workzen.xgen.model.java.file.TextFile;


/**
 * Return a TextFile object.
 * 
 * @author matlackb
 */
public class TextFileLoader implements ILoader {

	/**
	 * @see workzen.xgen.loader.ILoader#load(java.net.URL, boolean)
	 */
	public Object load(URL url, boolean validate) throws XGenException {
		File file = ResourceLoader.urlToFile(url);
		if( file == null || file.exists() == false ){
			throw new XGenException("File does not exist " + url.toExternalForm() );
		}
		TextFile textFile = new TextFile(file);
		return textFile;
	}

}
