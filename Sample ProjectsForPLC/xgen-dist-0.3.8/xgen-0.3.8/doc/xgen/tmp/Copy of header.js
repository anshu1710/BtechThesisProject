/* 
 * header.js 
 * Brad Matlack 4-2004
 */

var webroot = "/D:/Project/workzen.xgen/doc/dev/";
//var webroot = "/xgen/";

var isNav, isIE, news, links, images; 
var isNav = (navigator.appName == "Netscape");
var isIE  = (navigator.appName.indexOf("Microsoft") != -1);
var version = navigator.appVersion;

/* box */
function getBox(title, str){
  var box = "";
  box += title;
  box += "<div class='box'>";
  box += "<div class='box-body'>";
  box += str;
  box += "</div>";
  box += "</div>";
  return box;
}

/* title box */
function getTitleBox(title, str){
  var box = "";
  box += "<div class='box'>";
  box += "<div class='box-header'>" + title + "</div>";
  box += "<div class='box-body'>";
  box += str;
  box += "</div>";
  box += "</div>";
  return box;
}

/* tab box */
function getTabBox(title, str){
  var box = "";
  box += "<div>";
  box += "<span class='tab'>" + title + "</span>";
  box += "</div>";
  box += "<div class='box'>";
  box += "<div class='box-body'>";
  box += str;
  box += "</div>";
  box += "</div>";
  return box;
}

/* get menu div */
function getTitle(text){
 return "<div class='menutitle'>" + text + "</div>";
}

/* get menu item linked via webroot */
function getItem(text, address){
  image = getImage("navArrowRight.gif");
  return "<div class='menuitem'>" + image + "<a href='" + webroot + address + "'>" + text + "</a></div>";
}

/* get an html link */
function getLink(text, address){
  image = getImage("navArrowRight.gif");
  return "<div>" + image + "<a href='" + webroot + address + "'>" + text + "</a></div>";
}

/* */
function getUpTab(text, address){
  return "<span class='uptab'><a href='" + webroot + address + "'>" + text + "</a></span>";
  //return "<a class='tab' href='" + webroot + address + "'>" + text + "</a>";
}

/* */
function getDownTab(text, address){
  return "<span class='downtab'><a href='" + webroot + address + "'>" + text + "</a></span>";
  //return "<a class='tab' href='" + webroot + address + "'>" + text + "</a>";
}

/* */
function getUpTabBar(tabs){
  bar = "";
  bar += tabs;
  bar += "<div class='bar'></div>";
  return bar;
}

/* */
function getDownTabBar(tabs){
  bar = "";
  bar += "<div class='bar'></div>";
  bar += tabs;
  return bar;
}

/* */
function getImage(address){
  return "<img src='" + webroot + "images/" + address + "'/>";
}

/* */
function getLine(){
  return "<div>&nbsp;</div>";
}

/* 
 * Generic callback for all sub-menus. Example:
 * var items = "";
 * items += getItem("Home","index.html");
 * items += getItem("License","GPL_license.html");
 * items += getItem("About","about.html");
 * document.write( getMenu("Home",items) );
 */
function getMenu2(title, menu){
  m = "<div class='menu'>&nbsp;";
  m += getTitleBox(title,menu);
  m += "</div>";
  return m;
}

function getMenu(title, menu){
  m = "<div class='menu'>&nbsp;";
  m += getTitleBox(title,menu);
  m += sfimage;
  m += "</div>";
  m += "<table cellpadding='15'><tr><td>";
  return m;
}

var sfimage = "";
sfimage += "<div><a href='http://sourceforge.net/projects/xgen'>";
sfimage += "<img src='http://sourceforge.net/sflogo.php?group_id=87769&amp;type=2' />";
sfimage += "</a></div>";


var page = "";
page += "<head>";
page += "<title>xgen</title>";
page += "<link type='text/css' href='" + webroot + "etc/print.css' rel='stylesheet' media='print'/>";
page += "<link type='text/css' href='" + webroot + "etc/style.css' rel='stylesheet' media='screen'/>";
page += "<link type='text/css' href='" + webroot + "etc/box.css'   rel='stylesheet' media='screen'/>";
page += "</head>";
page += "<body>";
page += "<div class=headerTop>&nbsp;</div>";
page += "<div class=headerTitle>&nbsp; &lt;xgen&gt;</div>";
//page += "<div class=tabBox>";
//page += "<div class=tabArea>";
//page += "<a class=tab href=index.html>Home</a>";
//page += "<a class=tab href=docs.html>Docs</a>";
//page += "<a class=tab href=examples.html>Examples</a>";
//page += "</div>";
//page += "</div>";

document.write(page);

var downTabs = "";
//tab += "<span style='margin-left:10px;'>";
downTabs += "<div style='height: 5px;  border-bottom:1px solid; background-color: #F5F5DC;'></div>";
downTabs += getDownTab("Home","index.html");
downTabs += getDownTab("Examples", "examples.html");
downTabs += getDownTab("Links","links.html");
//tab += "<div style='height: 5px;  border-top:2px solid; background-color: #F5F5DC;'></div>";
//tab += "</span>";

var upTabs = ""; 

upTabs += "<div>&nbsp;</div>";
upTabs += getUpTab("Home","index.html");
upTabs += getUpTab("Examples", "examples.html");
upTabs += getUpTab("Links","links.html");

document.write(getUpTabBar(upTabs));

// menus are written when called from submen
