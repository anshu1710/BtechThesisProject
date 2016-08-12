/* 
 * home_menu.js 
 * Brad Matlack 4-2004
 */

var menu = new Menu("Home");
menu.addMenuItem("Home","index.html");
menu.addMenuItem("License","home/GPL_license.html");
menu.addMenuItem("About","home/about.html");
menu.addMenuItem("Download","home/download.html");
menu.addMenuItem("QuickStart", "home/quickstart.html");

printMenu(menu);
