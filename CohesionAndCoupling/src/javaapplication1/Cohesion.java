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

public class Cohesion {
    Map<String, String> methods;
    Map<String,String> classes;
    Map<String,Integer> packages;
    Map <String , ArrayList<Integer> > methodBeginLines;
    Map <String, ArrayList<String> > classMethods;
    Map<String,Integer> listOfMethods,listOfClasses;
    FileInputStream in;
    CompilationUnit cu;
    String currentClass,currentPackage;
    static int methodsCount,classCount,packageCount;
    String arrayOfMethods[],arrayOfClasses[],arrayOfPackages[];
    int matrix[][];
    String clas;      
    public Cohesion()
    {   methods = new HashMap<String, String>();
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
                { //System.out.println("ss"+listOfFiles[i].getName().toString());
                    try
                    { 
                        in = new FileInputStream(projectPath+"/"+listOfFiles[i].getName().toString());
                        cu = JavaParser.parse(in);
                       PackageVisitor p=new PackageVisitor();
                    //                              System.out.println("hi "+p);               
                               p.visit(cu, null);
                               
                        in.close();
                     }
                    catch(Exception e)
                    {
                  //    System.out.println("hello " +e.getMessage());
                    }
                }
        }
    }
    private class PackageVisitor extends VoidVisitorAdapter
    {       @Override
        public void visit(PackageDeclaration n,Object args)
        {            currentPackage = n.getName().toString();
               // System.out.println("adads"+currentPackage);
            if(!packages.containsKey(currentPackage))
                packages.put(currentPackage, packageCount++);
            new ClassVsitor().visit(cu, null);
        }
        private class ClassVsitor extends VoidVisitorAdapter
        {
            @Override
            public void visit(ClassOrInterfaceDeclaration n,Object args)
            {  currentClass = n.getName().toString();
              //System.out.println("aaa"+currentClass);
               classes.put(currentClass, currentPackage);
               arrayOfClasses[classCount++] = currentClass;
               new ConstructorVisitor().visit(cu, null);
               new MethodVsitor().visit(cu, null);
            }
            private class MethodVsitor extends VoidVisitorAdapter
            {
                @Override
                public void visit(MethodDeclaration n,Object args)
                {   
                    
                    String methodName = n.getName();
                  
                    //System.out.println("dfdfdgfd" + methodName);
                   if(currentClass.equals(clas))
                    {
                       
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
            private class ConstructorVisitor extends VoidVisitorAdapter
            {
                @Override
                public void visit(ConstructorDeclaration n,Object args)
                {      
                    String methodName = n.getName();
                    if(currentClass.equals(clas))
                    { 
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
            {  currentClass = n.getName().toString();
               new MethodVisitor().visit(cu, null);
            }
            private class MethodVisitor extends VoidVisitorAdapter
            {  @Override
                public void visit(MethodCallExpr n,Object args)
                {
                    String calledMethod = n.getName();
                    //System.out.println(calledMethod);
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
    public String getCalledClass(String methodName,String callingMethod)
    {
        for(int i=0;i<classCount;i++)
        {
            if(getPackageForMethod(callingMethod).equals(getPackageForClass(getClassAtIndex(i))))
            {
                if(classMethods.get(getClassAtIndex(i)).contains(methodName))
                    return getClassAtIndex(i);
            }
        }
        return "";
    }
    private String getCallingMethod(String calledMethod, int beginLine)
    {
        if(!methods.containsKey(calledMethod))
            return "";
        else
        {   String calling_class = currentClass;
            ArrayList<Integer> x = methodBeginLines.get(calling_class);
            ArrayList<String> y = classMethods.get(calling_class);
            Collections.sort(x);
           //System.out.println("calledMethod   "+calledMethod);
            int called=getMethodIndex(calledMethod);
            int index = binarySearch(x,0,x.size()-1,beginLine);
            //System.out.println("callingMethod "+ y.get(index));
            int calling =getMethodIndex(y.get(index));
            matrix[called][calling]=1;
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
    public String getPackageForMethod(String method)
    {
        return classes.get(methods.get(method));
    }
    public String getPackageForClass(String className)
    {
        return classes.get(className);
    }
    public int getMethodIndex(String methodName)
    {
        return listOfMethods.get(methodName);
    }
    public int getClassIndex(String className)
    {
        return listOfClasses.get(className);
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
    { int method=methodCount();
        String files;
        File folder = new File(projectPath);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) 
        {
            files = listOfFiles[i].getName();
            if (listOfFiles[i].isDirectory()) 
            {
                this.parseForMethodCalling(projectPath+"/"+listOfFiles[i].getName());
            }
            else
                if(listOfFiles[i].isFile() && listOfFiles[i].getName().toString().endsWith(".java"))
                {
                    try
                    {
                        in = new FileInputStream(projectPath+"/"+listOfFiles[i].getName().toString());
                        cu = JavaParser.parse(in);
                        new callingDeclarationVisitor().visit(cu, null);
                        in.close();
                        
                    }
                    catch(Exception e)
                    {
                      // System.out.println(e.getMessage());
                    }
                }
        }
    }        
    public int  methodCount()
    {    return methodsCount;
    }
    public void setClassforCohesion(String clas)
    {        this.clas=clas;
    }
    public double getCohesion()
    {  
        double calls=0;
       int method=methodCount();
       for(int i=0;i<method;i++)
       {
       for(int j=0;j<method;j++)
        calls=calls+matrix[i][j];
       }
       //calls=calls/method*(method-1);
       return calls;
    }
    public static double calculateCohesion(String path,String Class)
    {
         Cohesion parser = new Cohesion();
         parser.setClassforCohesion(Class);
         parser.parseForDataExtraction(path);
        int method=parser.methodCount();
       // System.out.print(parser.classes);
        //System.out.println(method);
        parser.matrix=new int[method][method];
        parser.parseForMethodCalling(path);
      /* System.out.println("Methods and their index in the matrix:");
       System.out.println(parser.listOfMethods);
       System.out.println("Matrix[CalledFunc][CallingFunc]:");
      for(int i=0;i<method;i++)
       
      for(int j=0;j<method;j++)
      System.out.print(parser.matrix[i][j]+"    ");
      System.out.println("");
             
       //System.out.println("Cohesion is: "+parser.getCohesion());
        */   
    return parser.getCohesion();
    }
    
    /*public static void main(String a[])
    {
       
        System.out.println(Cohesion.calculateCohesion("C:\\Users\\Dell\\Desktop\\withTest\\JavaOperationsNew", "Compute"));
    }*/
}