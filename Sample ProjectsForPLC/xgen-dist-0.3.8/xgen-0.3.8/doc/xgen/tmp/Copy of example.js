/* 
 * example.js 
 * Brad Matlack 4-2004
 */

var example = "";
example += getTitle("Schema");
example += getItem("Schema", "xml/blog_schema_1.html");
example += getItem("Ant build", "xml/build_blog.html");

example += getTitle("Persistable class");
example += getItem("Blog.java", "code/Blog.java.html");
example += getItem("BlogCategory.java", "code/BlogCategory.java.html");

example += getTitle("Persistence table");
example += getItem("BlogTable.java", "code/BlogTable.java.html");
example += getItem("BlogCategoryTable.java", "code/BlogCategoryTable.java.html");

example += getTitle("Persistence manager");
example += getItem("BlogManager.java", "code/BlogManager.java.html");
example += getItem("BlogCategoryManager.java", "code/BlogCategoryManager.java.html");

var menu = getMenu("Examples", example);

document.write(menu);
