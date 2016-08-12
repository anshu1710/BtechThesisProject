/* 
 * schema menu.js 
 * Brad Matlack 4-2004
 */


var menu = new Menu("Xml Schema");

menu.addMenuItem("Schema","xml/schema.html");
menu.addMenuItem("Blob schema", "xml/blog_schema_1.html");
menu.addMenuItem("Ant schema", "xml/build_blog.html");
menu.addMenuItem("&nbsp;");

document.write(getMenuHtml(menu));
