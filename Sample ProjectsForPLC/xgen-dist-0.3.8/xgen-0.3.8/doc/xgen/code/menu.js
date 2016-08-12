/* 
 * code menu.js 
 * Brad Matlack 4-2004
 */


var menu = new Menu("Code Examples");

menu.addMenuItem("Code Examples","code/examples.html");
menu.addMenuItem("&nbsp;");

menu.addMenuItem("Prisistable class");
menu.addMenuItem("Blog.java", "code/Blog.java.html");
menu.addMenuItem("BlogCategory.java", "code/BlogCategory.java.html");
menu.addMenuItem("&nbsp;");

menu.addMenuItem("Persistence table");
menu.addMenuItem("BlogTable.java", "code/BlogTable.java.html");
menu.addMenuItem("BlogCategoryTable.java", "code/BlogCategoryTable.java.html");
menu.addMenuItem("&nbsp;");

menu.addMenuItem("Persistence manager");
menu.addMenuItem("BlogManager.java", "code/BlogManager.java.html");
menu.addMenuItem("BlogCategoryManager.java", "code/BlogCategoryManager.java.html");
menu.addMenuItem("&nbsp;");

document.write(getMenuHtml(menu));
