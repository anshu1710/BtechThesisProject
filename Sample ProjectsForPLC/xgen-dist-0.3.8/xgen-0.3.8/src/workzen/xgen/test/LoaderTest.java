/*
 * LoadTest.java
 * Copyright (c) 2000-2001 Brad D Matlack.  
 * License http://www.gnu.org/copyleft
 */

package workzen.xgen.test;

import java.net.URL;

import org.apache.log4j.BasicConfigurator;

import workzen.xgen.loader.XGenLoader;
import workzen.xgen.model.java.Package;

/**
 * This is a test of the xml loader. It loads the document and returns
 * the package which gets printed to standard out.
 * @author <a href="mailto:bmatlack@workzen.us">Brad Matlack</a>
 */
public class LoaderTest {
	Package pkg = null;

	/** */
	public static void main(String args[]) throws Exception {

		String usage = "usage: LoadTest [xmlfile]";
		String xmlFile =
			"D:/Project/workzen.xgen/etc/input/types.xml";

		if (args.length > 0) {
			if (args[0].equals("help")) {
				System.out.println(usage);
				System.exit(1);
			} else {
				xmlFile = args[0];
			}
		}
		BasicConfigurator.configure();

		java.net.URL url = new java.net.URL("file:///" + xmlFile);
		LoaderTest test = new LoaderTest(url);
		test.run();

	}

	/** constructor */
	public LoaderTest(URL url) {
		try {
			XGenLoader loader = new XGenLoader();
			pkg = (Package)loader.load(url,false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** run the test */
	public void run() {
		try {
			System.out.println(pkg.toString("\n"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
