XGen Readme

OVERVIEW 

XGen source code generator creates Java source code from a simple xml document. 
Its primary function is to generate JDBC compliant beans that allow object level 
persistence to relational databases. It has full support for all JDBC 2.0 datatypes, 
including BLOB and CLOB.

REQUIREMENTS

Required libraries are included in the lib directory. Check build.properties 
for any additional libraries required to build the distribution.
XGen uses the Jakarta Velocity template engine.

LICENSE

This program is distributed under GPL. See license.txt or www.gnu.org for the 
license. This software does not come with a warranty of any kind. Use at you own
risk. You may modify and distribute the code as you wish, as long as you provide 
the source code and this copyright with it. 

AUTHOR

Brad Matlack
bmatlack@workzen.us
Copyright (c) Brad D Matlack 2-2003

CHANGE LOG

Build 1.0: Initial Release 2002-03-21

Build 1.1 2002-05-15
XGen is refactored into the Xml2Bean Module of XiT.

Build 0.2.0 2004-03-01
XGen is refactored into the Xml2Java Plugin of XiT.

Build 0.3.0 2004-03-10
XGen is refactored and re-released as a standalone app within workzen.xgen code tree.
Added VETexenTask

Build 0.3.1
Added associations: Reference and Collection to model

Build 0.3.2
Refactoring of model to specify Column and JavaClass

Build 0.3.3
Refactored TypeMap and schema to better handle primitive types.

Build 0.3.4-6
Added manager templates

Build 0.3.7
Bug fix in manager loadByWhere()

Build 0.3.8
Moved the java model into its own package "java"
