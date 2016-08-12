package javaapplication1;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Coupling {
    Map<String, String> methods;
    Map<String,String> classes;
    Map<String,Integer> packages;
    Map <String , ArrayList<Integer> > methodBeginLines;
    Map <String, ArrayList<String> > classMethods,calls;
    Map<String,Integer> listOfMethods,listOfClasses;
    FileInputStream in;
    CompilationUnit cu;
    String currentClass,currentPackage;
    static int methodsCount,classCount,packageCount;
    String arrayOfMethods[],arrayOfClasses[],arrayOfPackages[];
    int outsidecalls;
    String clas;      
   Map<String,Integer> methodCalls;
   ArrayList cClass;
   
    public Coupling()
    {   methodCalls=new HashMap<String,Integer>();
        calls = new HashMap<String,ArrayList<String>>();
        methods = new HashMap<String, String>();
        classes = new HashMap<String, String>();
        packages = new HashMap<String, Integer>();
        methodBeginLines = new HashMap<String, ArrayList<Integer>>();
        classMethods = new HashMap<String, ArrayList<String>>();
        listOfMethods = new HashMap<String, Integer>();
        listOfClasses = new HashMap<String, Integer>();
        arrayOfMethods = new String[100000];
        arrayOfClasses = new String[100000];
        arrayOfPackages =  new String[100000];
        methodsCount = 0;
        classCount = 0;
        packageCount = 0;
    }   
    public void parseForDataExtraction(String projectPath)
    {
        String files;
        File folder = new File(projectPath);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) 
        {
            files = listOfFiles[i].getName();
            if (listOfFiles[i].isDirectory()) 
            {
                this.parseForDataExtraction(projectPath+"/"+listOfFiles[i].getName());
            }
            else
                if(listOfFiles[i].isFile() && listOfFiles[i].getName().toString().endsWith(".java"))
                {
                    try
                    { 
                        in = new FileInputStream(projectPath+"/"+listOfFiles[i].getName().toString());
                        cu = JavaParser.parse(in);
                       PackageVisitor p=new PackageVisitor();
                        p.visit(cu, null);
                        in.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
        }
    }
    private class PackageVisitor extends VoidVisitorAdapter
    {       @Override
        public void visit(PackageDeclaration n,Object args)
        {   currentPackage = n.getName().toString();
            if(!packages.containsKey(currentPackage))
                packages.put(currentPackage, packageCount++);
            new ClassVsitor().visit(cu, null);
        }
        private class ClassVsitor extends VoidVisitorAdapter
        {
            @Override
            public void visit(ClassOrInterfaceDeclaration n,Object args)
            {
               currentClass = n.getName().toString();
                
               classes.put(currentClass, currentPackage);
               arrayOfClasses[classCount++] = currentClass;
               new ConstructorVisitor().visit(cu, null);
               new MethodVsitor().visit(cu, null);
            }
            private class MethodVsitor extends VoidVisitorAdapter
            {  @Override
                public void visit(MethodDeclaration n,Object args)
                {   String methodName = n.getName();
      
                    methods.put(methodName,currentClass);
                    listOfMethods.put(methodName,methodsCount);
                    arrayOfMethods[methodsCount] = methodName;
                    ArrayList<String> x= new ArrayList<String>();
                    ArrayList<Integer> y = new ArrayList<Integer>();
                    if(classMethods.containsKey(currentClass))
                        x = classMethods.get(currentClass);
                    if(methodBeginLines.containsKey(currentClass))
                        y = methodBeginLines.get(currentClass);
                    x.add(methodName);
                    classMethods.put(currentClass, x);
              
                    y.add(n.getBeginLine());
                    methodBeginLines.put(currentClass, y);
                    methodsCount++;
                    
                }   
            }
            private class ConstructorVisitor extends VoidVisitorAdapter
            {  @Override
                public void visit(ConstructorDeclaration n,Object args)
                {
                    String methodName = n.getName();
                    methods.put(methodName,currentClass);
                    listOfMethods.put(methodName,methodsCount);
                    arrayOfMethods[methodsCount] = methodName;
                    ArrayList<String> x= new ArrayList<String>();
                    ArrayList<Integer> y = new ArrayList<Integer>();
                    if(classMethods.containsKey(currentClass))
                        x = classMethods.get(currentClass);
                    if(methodBeginLines.containsKey(currentClass))
                        y = methodBeginLines.get(currentClass);
                    x.add(methodName);
                    classMethods.put(currentClass, x);
                    y.add(n.getBeginLine());
                    methodBeginLines.put(currentClass, y);
                    methodsCount++;
                }   
            }
        }
    }
    private class callingDeclarationVisitor extends VoidVisitorAdapter
    { 
        
        @Override
        public void visit(PackageDeclaration n,Object args)
        {   currentPackage = n.getName().toString();
            
        new ClassVsitor().visit(cu, null);
            
        }
        private class ClassVsitor extends VoidVisitorAdapter
        {  @Override
            public void visit(ClassOrInterfaceDeclaration n,Object args)
            {
               currentClass = n.getName().toString();
               
               new MethodVisitor().visit(cu, null);
            }
            private class MethodVisitor extends VoidVisitorAdapter
            {   @Override
                public void visit(MethodCallExpr n,Object args)
                {   
                    
                    
                    String calledMethod = n.getName();
                    String callingMethod = getCallingMethod(calledMethod, n.getBeginLine());
                    List<Expression> arguments = new LinkedList<Expression>();
                    try{
                        String express = n.getArgs().get(0).toString();
                   if(express.contains("."))
                    {
                        if(classes.containsKey(express.substring(0, express.indexOf("."))))
                        {
                            String callingClass = currentClass;
                            String calledClass = express.substring(0, express.indexOf("."));
                        }
                    }
                    }catch(Exception e)
                    {
                       // System.out.println(e.getMessage());
                    }
                    if(!callingMethod.isEmpty())
                    {
                      if(!calledMethod.equals(callingMethod))
                        {
                            String callingClass = methods.get(callingMethod);
                            String calledClass = methods.get(calledMethod);
                            String callingPackage = classes.get(callingClass);
                            String calledPackage = classes.get(calledClass);
                       }
                    }
                }
            }
        }
    }
    private String getCallingMethod(String calledMethod, int beginLine)
    {
        if(!methods.containsKey(calledMethod))
            return "";
        else
        {
            String calling_class = currentClass;
            ArrayList<Integer> x = methodBeginLines.get(calling_class);
            ArrayList<String> y = classMethods.get(calling_class);
            Collections.sort(x);
          
        if(calling_class.equals(clas) && !clas.equals(getClassForMethod(calledMethod))) 
        {
            //System.out.println(calledMethod);
            String rClass = getClassForMethod(calledMethod);
            
            if(calls.containsKey(rClass))
                cClass=calls.get(rClass);
            else
                cClass = new ArrayList();
            if(!cClass.contains(calledMethod))
              cClass.add(calledMethod);
            calls.put(rClass,cClass);
            
           outsidecalls++;
            if(methodCalls.containsKey(calledMethod))
                methodCalls.put(calledMethod,methodCalls.get(calledMethod)+1);
            else
                methodCalls.put(calledMethod,new Integer(1));
         }
         
        //  System.out.println("calledMethod   "+calledMethod);
            int called=getMethodIndex(calledMethod);
            int index = binarySearch(x,0,x.size()-1,beginLine);
         //System.out.println("callingMethod "+ y.get(index));
            int calling =getMethodIndex(y.get(index));
            return y.get(index);
        }
    }
    private int binarySearch(ArrayList<Integer> x, int begin,int end,int key)
    {
        int mid = (begin+end)/2;
        while(true)
        {
            mid = (begin+end)/2;
            if(x.get(0) > key)
                return 0;
            if(x.get(mid) > key)
                end = mid-1;
            else if(x.get(mid)<key && mid==x.size()-1)
                return mid;
            else if(x.get(mid) <= key && x.get(mid+1) >= key)
                return mid;
            else
                begin = mid+1;
         }
    }
    public String getClassForMethod(String method)
    {
        return methods.get(method);
    }
    public int getMethodIndex(String methodName)
    {
        return listOfMethods.get(methodName);
    }
    public String getMethodAtIndex(int index)
    {
        return arrayOfMethods[index];
    }
    public String getClassAtIndex(int index)
    {
        return arrayOfClasses[index];
    }
     public void parseForMethodCalling(String projectPath)
    {       
        String files;
        File folder = new File(projectPath);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) 
        {
            files = listOfFiles[i].getName();
           // System.out.println(files);
            if (listOfFiles[i].isDirectory()) 
            { 
                this.parseForMethodCalling(projectPath+"/"+listOfFiles[i].getName());
            }
            else
                if(listOfFiles[i].isFile() && listOfFiles[i].getName().toString().endsWith(".java"))
                { try
                    {
                        in = new FileInputStream(projectPath+"/"+listOfFiles[i].getName().toString());
                        cu = JavaParser.parse(in);
                        new callingDeclarationVisitor().visit(cu, null);
                        in.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
        }
    }        
    public void setClassforCoupling(String clas)
    {this.clas=clas;
    }
     public int  methodCount()
    {    return methodsCount;
    }
     
     public int  getClassCount()
    {    return classCount;
    }
     
     public  void printClassesforMethods(Map<String,Integer> mp)
     {   //System.out.println("Function  Class");
         Iterator it = mp.entrySet().iterator();
         while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        //System.out.println(pairs.getKey()+"        "+getClassForMethod(pairs.getKey().toString()));
        it.remove(); // avoids a ConcurrentModificationException
    }
    }
     
     
      public double writeCoupling(Coupling coupling,FileWriter writer)
         {try {
            double totalCoupling=0.0;
             
              for(int i=0;i<coupling.getClassCount();i++)
              {
                  if(!coupling.clas.equals(coupling.arrayOfClasses[i])){
                      if(writer!=null)
                     writer.write("Coupling of Class "+coupling.clas+" with Class "+coupling.arrayOfClasses[i]+" is :");
                     if(coupling.calls.containsKey(coupling.arrayOfClasses[i]))
                     {
                     double coup=(double)coupling.calls.get(coupling.arrayOfClasses[i]).size();  
                     totalCoupling+=coup; 
                     if(writer!=null)
                     writer.write(coup+"\r\n");
                     }
                     else
                         if(writer!=null)
                     writer.write("0"+"\r\n");
                  }
              }
              if(writer!=null)
              writer.write("Total Coupling: "+totalCoupling/(coupling.getClassCount()-1)+"\r\n\r\n");
           return totalCoupling/(coupling.getClassCount()-1);     
        } catch (IOException ex) {
            Logger.getLogger(Coupling.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return 0;  
         }
     
     public static double getCoupling(String path,String Class,FileWriter writer)
    {   
        Coupling coupling=new Coupling();
        coupling.parseForDataExtraction(path);
        coupling.setClassforCoupling(Class);
        coupling.parseForMethodCalling(path);
        double coup=coupling.writeCoupling(coupling,writer);
        return coup;
    }
     
     
}  
  