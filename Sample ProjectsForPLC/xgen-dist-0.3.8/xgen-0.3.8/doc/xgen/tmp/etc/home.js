/* 
 * home.js 
 * Brad Matlack 4-2004
 */

var home = "";
home += getItem("Home","index.html");
home += getItem("License","GPL_license.html");
home += getItem("About","about.html");
home += getItem("Download","download.html");
home += getItem("QuickStart", "quickstart.html");

var menu = getMenu("Home",home);

document.write(menu);
