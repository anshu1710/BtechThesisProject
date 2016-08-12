/*
 * DocLoader.java
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
package workzen.xgen.loader;

import java.io.IOException;
import java.net.URL;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import workzen.xgen.model.java.XGenException;
import workzen.xgen.model.java.xml.XmlDoc;

/**
 * Load the xml schema into a JDom Document 
 * 
 * @author <a href="mailto:bmatlack@workzen.us">Brad Matlack</a>
 */
public class XmlDocLoader implements ILoader {

	/**
	 * Load the JDom Document into an XmlDoc.
	 * 
	 * @see workzen.xgen.loader.ILoader#load(java.net.URL, boolean)
	 */
	public Object load(URL url, boolean validate) throws XGenException {
		Document doc = null;
		try {
			doc = loadDocument(url, validate);
		} catch (IOException e) {
			throw new XGenException(e);
		}
		XmlDoc xmlDoc = new XmlDoc(doc);
		return xmlDoc;
	}

	/**
	 * @param url
	 * @param validate
	 * @return
	 * @throws IOException
	 */
	private Document loadDocument(URL url, boolean validate)
		throws IOException {
		SAXBuilder builder = new SAXBuilder(validate);
		Document doc = null;
		try {
			doc = builder.build(url);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return doc;
	}
}
