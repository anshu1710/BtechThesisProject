/* 
 * header.js 
 * Brad Matlack 4-2004
 */

var webroot = "/D:/Project/workzen.xgen/doc/xgen/";
//var webroot = "/xgen/";

var isNav, isIE, news, links, images; 
var isNav = (navigator.appName == "Netscape");
var isIE  = (navigator.appName.indexOf("Microsoft") != -1);
var version = navigator.appVersion;

/* link object */
function Link(link_name, link_address){
  this.name = link_name;
  this.address = link_address;
}

/* tab bar object */
function TabBar(){
  this.tabs = new Array();
  this.addTab = addTabLink;
}

/* menu object */
function Menu(menu_title){
 this.title = menu_title;
 this.links = new Array();
 this.addMenuItem = addMenuItemLink;
}

/* menu method */
function addMenuItemLink(name, address){
  var link = new Link(name, address);
  index = this.links.length;
  this.links[index] = link;
}

/* tab bar method */
function addTabLink(name, address){
  var link = new Link(name, address);
  index = this.tabs.length;
  this.tabs[index] = link;
}

/* html function */
function getImage(address){
  return "<img src='" + webroot + "images/" + address + "'/>";
}

/* html function */
function getImageLink(link, gif){
 image = "<img src='" + webroot + "images/" + gif + "'/>";
 html = "";
 if( link.address ){
   html += "<div class='menuitem'>" + image + "<a href='" + webroot + link.address + "'>" + link.name + "</a></div>";
 }else{
 	 html += "<div class='menuitem'>" + link.name + "</div>";
 }
 return html;
}

/* html function */
function getLink(link){
 html = "";
 if( link.address ){
   html += "<div class='menuitem'><a href='" + webroot + link.address + "'>" + link.name + "</a></div>";
 }else{
 	 html += "<div class='menuitem'>" + link.name + "</div>";
 }
 return html;
}

/* html function */
function getTabBarDown(bar){
 html = "";
 html += "<div class='bar'></div>";
 for( var i=0; i < bar.tabs.length; i++ ){
  link = bar.tabs[i];
  html += "<span class='downtab'><a href='" + webroot + link.address + "'>" + link.name + "</a></span>";
 }
 return html;
}

/* html function */
function getTabBarUp(bar){
 html = "";
 for( var i=0; i < bar.tabs.length; i++ ){
  link = bar.tabs[i];
  html += "<span class='uptab'><a href='" + webroot + link.address + "'>" + link.name + "</a></span>";
 }
 html += "<div class='bar'></div>";
 return html;
}

/* html function */
function getTabBarMiddle(bar){
 html = "";
 html += "<div class='bar'>";
 for( var i=0; i < bar.tabs.length; i++ ){
  link = bar.tabs[i];
  html += "<span class='notab'><a href='" + webroot + link.address + "'>" + link.name + "</a></span>";
 }
 html += "</div>";
 return html;
}

/* html function */
function getTitledMenu(menu){
 html  = "<div class='menux'>";
 html += "<div class='box-title'>" + menu.title + "</div>";
 html += "<div class='box'>";
 html += "<div class='box-body'>";
 for( var i=0; i < menu.links.length; i++ ){
  link = menu.links[i];
  html += getImageLink(link, "navArrowRight.gif");
 }
 html += "</div>";
 html += "</div>";
 html += "</div>";
 return html;
}

/* html function */
function getTabbedMenu(menu){
  var html = "";
  html += "<div class='menux'>";
  //html += "<div>"; 
  html += "<span class='uptab'>" + menu.title + "</span>";
  //html += "</div>";
  html += "<div class='box'>";
  html += "<div class='box-body'>";
  for( var i=0; i < menu.links.length; i++ ){
   link = menu.links[i];
   html += getImageLink(link, "navArrowRight.gif");
  }
  html += "</div>";
  html += "</div>";
  html += "</div>";
  return html;
}

/* html function */
function getTabbedMenuDown(menu){
  var html = "";
  html  = "<div class='menux'>";
  html += "<div class='box'>";
  html += "<div class='box-body'>";
  for( var i=0; i < menu.links.length; i++ ){
   link = menu.links[i];
   html += getImageLink(link, "navArrowRight.gif");
  }
  html += "</div>";
  html += "</div>";
  html += "<div class=''>&nbsp;"; // microsoft bug
  html += "<span class='downtab'>" + menu.title + "</span>";
  html += "</div>";
  html += "</div>";
  return html;
}

/* html function */
function getExtras(){
	 html = "<p>";
	 html += sfimage;
	 html += "</p>";
	 return html;
}

/*
 * callback method for sub-javascript menus.
 */
function getMenuHtml2(menu){
  html = "";
  html += "<div class='menu'>";
  html += getTabbedMenu(menu);
  //html += getTitledMenu(menu);
  html += "</div>";
  html += "<table cellpadding='15'><tr><td>";
  return html;
}

/*
 * callback method for sub-javascript menus.
 */
function getMenuHtml(menu){
  html = "";
  html += "<table cellpadding='10'><tr><td valign='top'>";
  html += getTabbedMenu(menu);
  //html += getTitledMenu(menu);
  html += getExtras();
  html += "</td><td valign='top'>";
  return html;
}

/*
 * callback method for sub-javascript menus.
 */
function printMenu(menu){
  document.write(getMenuHtml(menu));
}

var head = "";
head += "<head>";
head += "<title>xgen</title>";
head += "<link type='text/css' href='" + webroot + "print.css' rel='stylesheet' media='print'/>";
head += "<link type='text/css' href='" + webroot + "style.css' rel='stylesheet' media='screen'/>";
head += "<link type='text/css' href='" + webroot + "box.css'   rel='stylesheet' media='screen'/>";
head += "<link type='text/css' href='" + webroot + "box.css'   rel='stylesheet' media='print'/>";
head += "</head>";
head += "<body>";
head += "<div class=headerTop>&nbsp;</div>";
head += "<div class=headerTitle>&nbsp; &lt;xgen&gt;</div>";

var sfimage = "";
sfimage += "<div><a href='http://sourceforge.net/projects/xgen'>";
sfimage += "<img src='http://sourceforge.net/sflogo.php?group_id=87769&amp;type=2' />";
sfimage += "</a></div>";

var testMenu = new Menu("test menu");
testMenu.addMenuItem("one","one.html");
testMenu.addMenuItem("two","two.html");

var bar = new TabBar();
bar.addTab("Home","index.html");
bar.addTab("Code Examples","code/examples.html");
bar.addTab("Xml Schema","xml/schema.html");

document.write(head);
//document.write(getTabBarUp(bar));
//document.write(getTabBarDown(bar));
document.write(getTabBarMiddle(bar));
//document.write(getHtml(testMenu));
