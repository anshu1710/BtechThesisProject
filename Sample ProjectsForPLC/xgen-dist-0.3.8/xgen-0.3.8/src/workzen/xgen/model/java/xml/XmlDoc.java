/*
 * Copyright (c) Apr 18, 2004 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.model.java.xml;

import org.jdom.Document;
import org.jdom.output.XMLOutputter;


/**
 * @author matlackb
 */
public class XmlDoc {
	
	private Document doc;
	
	public XmlDoc(Document doc){
		this.doc = doc;
	}
	
	/** 
	 * Get String output, use standard JDom formatter
	 * 
	 * @param Document
	 */
	public String getHtmlOutput() {
		String html = "undefined";
		try {
			XMLOutputter out = new XMLOutputter("  ", true);
			out.setTextNormalize(true);
			String output = out.outputString(doc);
			html = out.escapeAttributeEntities(output);
			//System.out.println(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}
	
	public Document getDocument(){
		return doc;
	}
	
}
