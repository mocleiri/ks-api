package org.kuali.student.common.util;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.beanutils.PropertyUtils;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javadoc.*;

public class DataDictionaryUtil {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SecurityException, NoSuchFieldException, IOException {
		
		String dictPackageString="org.kuali.student.core.organization.dto";
		String outputFileName="org-dict.xml";
		
		if(args!=null&&args.length>0&&args[0]!=null){
			outputFileName = args[0];
		}
		if(args!=null&&args.length>1&&args[1]!=null){
			dictPackageString = args[1];
		}

		
		StringBuilder sb = new StringBuilder();
		
		//XML Header
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<beans xmlns=\"http://www.springframework.org/schema/beans\" "+
	"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:dict=\"http://student.kuali.org/xsd/dictionary-extension\" "+
	"xsi:schemaLocation=\" "+
	"http://student.kuali.org/xsd/dictionary-extension http://student.kuali.org/xsd/dictionary-extension/dictionary-extension.xsd "+
	"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd"+
	"\">");
		
		//Repeating/singl constraints
		sb.append("\n<dict:constraint key=\"repeating\" id=\"repeating\"");
		sb.append("\nserverSide=\"false\">");
		sb.append("\n\t<!-- Repeating -->");
		sb.append("\n\t<!-- Nine 9's get translated as \"(unbounded)\" -->");
		sb.append("\n\t<dict:maxOccurs>(unbounded)</dict:maxOccurs>");
		sb.append("\n</dict:constraint>");

		sb.append("\n<dict:constraint key=\"single\" id=\"single\" serverSide=\"false\">");
		sb.append("\n\t<!-- Single valued (non-repeating) -->");
		sb.append("\n\t<!-- Used to override a repeating constraint -->");
		sb.append("\n\t<dict:maxOccurs>1</dict:maxOccurs>");
		sb.append("\n</dict:constraint>");
		
		//output object structures
		Set<Class<?>> processedClasses = new HashSet<Class<?>>();
		for(Class<?> c:getClasses(dictPackageString, true)){
			processObjectStructure(c, sb, processedClasses);
		}
		//Close
		sb.append("\n</beans>");
		
		//Save to file
		saveToFile(outputFileName, sb);
		

	}
	
    private static void processObjectStructure(Class<?> beanClass, StringBuilder sb, Set<Class<?>> alreadyProcessed) throws ClassNotFoundException, IOException {
		if(!alreadyProcessed.contains(beanClass)){
			alreadyProcessed.add(beanClass);
			
			Map<String,String> methodComments;
			try{
				methodComments = getMethodDocComments(beanClass);
			}catch(Exception e){
				System.out.println("Warning, couldn't get javadocs for class:" + beanClass.getName());
				methodComments = new HashMap<String,String>();
			}
			
	    	Set<Class<?>> childStructures = new HashSet<Class<?>>();

	    	//output parent object structure and default type/state
	    	sb.append("\n<dict:objectStructure key=\""+beanClass.getSimpleName()+"-parent\" id=\""+beanClass.getSimpleName()+"-parent\" abstract=\"true\">");
			sb.append("\n\t<dict:type key=\"default\" id=\""+beanClass.getSimpleName()+".type.default\">");
			sb.append("\n\t\t<dict:state key=\"default\" id=\""+beanClass.getSimpleName()+".state.default\">");
	
			//Loop through properties and output fields
			for(PropertyDescriptor pd:PropertyUtils.getPropertyDescriptors(beanClass)){
				Class<?> type = pd.getPropertyType();
				String name = pd.getName();
				
				//Dont process if not a real field (type,state,id,metaInfo,attributes)
				if(isFieldDescriptor(type,name)){
					sb.append("\n\t\t\t<dict:field key=\""+name+"\">");
					sb.append("\n\t\t\t\t<dict:fieldDescriptor>");
					sb.append("\n\t\t\t\t\t<dict:name>"+name+"</dict:name>");
					String comment = methodComments.get(pd.getReadMethod().getName());
					if(comment!=null&&!"".equals(comment.trim())){
						sb.append("\n\t\t\t\t\t<dict:desc>"+comment+"</dict:desc>");
					}else{
						sb.append("\n\t\t\t\t\t<dict:desc>"+name+"</dict:desc>");
					}
					//Check if it's a list
					boolean isList = false;
					if(type.isAssignableFrom(java.util.List.class)){
						//Get generic Type
						Type[] actualTypeArguments =  ((ParameterizedType)pd.getReadMethod().getGenericReturnType()).getActualTypeArguments();
						type = (Class<?>) actualTypeArguments[0];
						isList = true;
					}
					
					//see if it's complex
					if(isComplex(type)){
						//Add a complex type and reference
						String complexRef = type.getSimpleName();
						sb.append("\n\t\t\t\t\t<dict:dataType>complex</dict:dataType>");
						sb.append("\n\t\t\t\t\t<dict:objectStructureRef bean=\""+complexRef+"\"/>");

						//Add this type to the list of child structures we need to add
						childStructures.add(type);
					}else{
						//Get regular type
						sb.append("\n\t\t\t\t\t<dict:dataType>"+getTypeString(type)+"</dict:dataType>");
					}
					
					//Close up field descriptor
					sb.append("\n\t\t\t\t</dict:fieldDescriptor>");
					
					
					//Add single/repeating constraints
					sb.append("\n\t\t\t\t<dict:constraintDescriptor>");
					if(isList){
						sb.append("\n\t\t\t\t\t<dict:constraintRef bean=\"repeating\"/>");
					}else{
						sb.append("\n\t\t\t\t\t<dict:constraintRef bean=\"single\"/>");
					}
					sb.append("\n\t\t\t\t</dict:constraintDescriptor>");
					
					sb.append("\n\t\t\t</dict:field>");
				}
			}
			sb.append("\n\t\t</dict:state>");
			sb.append("\n\t</dict:type>");
	    	sb.append("\n</dict:objectStructure>");
	    	
	    	//Output a child objectstructure
	    	sb.append("\n<dict:objectStructure key=\""+beanClass.getSimpleName()+"\" id=\""+beanClass.getSimpleName()+"\" parent=\""+beanClass.getSimpleName()+"-parent\"/>");
	    	
	    	//Process any child structures
	    	for(Class<?> childStructureClass:childStructures){
	    		processObjectStructure(childStructureClass, sb, alreadyProcessed);
	    	}
		}
	}
    
	/**
	 * Gets a map of method name to method javadoc comment (provided the sources are on the path)
	 * @param clazz
	 * @return map of method name to method javadoc comment
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	static Map<String,String> getMethodDocComments(Class<?> clazz) throws IOException, ClassNotFoundException{
		
		//Get the source .java file and write it to a temp location
		ClassLoader cld = Thread.currentThread().getContextClassLoader();
		if (cld == null) {
			throw new ClassNotFoundException("Can't get class loader.");
		}
		String sourceResourceName = clazz.getName().replace('.', '/')+".java";
		InputStream is= cld.getResourceAsStream(sourceResourceName);
		FileWriter os = new FileWriter(clazz.getSimpleName()+".java");
		int c;
		while((c=is.read())!=-1){
			os.write(c);
		}
		os.close();
		is.close();
		
		///The result map to return
		Map<String,String> results = new HashMap<String,String>();
		
		//Load the javadoc info
		Context ctx = new Context();
		@SuppressWarnings("unused")
		MyMessager messager = new MyMessager(ctx, null, new PrintWriter(System.err), 
				new PrintWriter(System.out), new PrintWriter(System.out));

		JavadocTool jdt = JavadocTool.make0(ctx);
		String docLocale="";
		String encoding = null;
		long defaultFilter = com.sun.tools.javac.code.Flags.PUBLIC | com.sun.tools.javac.code.Flags.PROTECTED | ModifierFilter.PACKAGE;
		ModifierFilter showAccess = new ModifierFilter(defaultFilter);
		ListBuffer<String> javaNames = new ListBuffer<String>();
		javaNames.append(clazz.getSimpleName()+".java");
		ListBuffer<String[]> options = new ListBuffer<String[]>();
		boolean breakiterator = false;
		ListBuffer<String> subPackages = new ListBuffer<String>();
		ListBuffer<String> excludedPackages = new ListBuffer<String>();
		boolean docClasses = false;
		boolean languageVersion = false;
		boolean quiet = false;

		RootDocImpl root = jdt.getRootDocImpl(docLocale, encoding, 
				showAccess, javaNames.toList(), options.toList(), 
				breakiterator, subPackages.toList(), excludedPackages
				.toList(), docClasses,
				languageVersion, quiet);
		
		
		//Loop through the methods in the javadoc and store the results
		for(ClassDoc d:root.classes()){
			for(MethodDoc md:d.methods()){
				results.put(md.name(), md.commentText());
			}
		}
		
		//delete the temp file
		File cleanupFile = new File(clazz.getSimpleName()+".java");
		cleanupFile.delete();
		
		return results;
	}
	
	//Needed because of protected access
	public static class MyMessager extends Messager{

		protected MyMessager(Context arg0, String arg1, PrintWriter arg2,
				PrintWriter arg3, PrintWriter arg4) {
			super(arg0, arg1, arg2, arg3, arg4);
			
		}
		
	}
	
	private static void saveToFile(String filename, StringBuilder sb) throws IOException {
    	PrintWriter out = new PrintWriter(new FileWriter(filename));
    	out.print(sb.toString());
    	out.close();
    }

	private static String getTypeString(Class<?> type) {
		return type.getSimpleName();
	}

	private static boolean isComplex(Class<?> type){
		if(type.isPrimitive()){
			return false;
		}
		if(type.isAssignableFrom(String.class)||
		   type.isAssignableFrom(Date.class)||
		   type.isAssignableFrom(Integer.class)||
		   type.isAssignableFrom(Boolean.class)){
			return false;
		}
		
		return true;
	}

	private static boolean isFieldDescriptor(Class<?> type, String name) {
		//System.out.println("Checking if Field Type:"+type.toString()+" Name:"+name);
		if("id".equals(name)||
		   "type".equals(name)||
		   "state".equals(name)||
		   "metaInfo".equals(name)||
		   "attributes".equals(name)||
		   "class".equals(name)){
			return false;
		}
		if(type.isAnnotation()){
			return false;
		}
		return true;
	}
	
	//Taken from http://forums.sun.com/thread.jspa?threadID=341935&start=15
	private static ArrayList<String> getClassesHelper(String pckgname)
		throws ClassNotFoundException, IOException {
		// Get a File object for the package
		ArrayList<String> directories = new ArrayList<String>();
		
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null) {
				throw new ClassNotFoundException("Can't get class loader.");
			}
			String path = pckgname.replace('.', '/');
			Enumeration<URL> resources = cld.getResources(path);
			if (resources == null) {
				throw new ClassNotFoundException("No resource for " + path);
			}
			while(resources.hasMoreElements()){
				URL resource = resources.nextElement();
				if ("jar".equals(resource.getProtocol())){
					JarFile directory = new JarFile(resource.getFile().substring(6, resource.getFile().indexOf('!')));
					for (Enumeration<JarEntry> e = directory.entries(); e.hasMoreElements();){
						JarEntry current = e.nextElement();
						if(current.getName().length() > path.length() && current.getName().substring(0, path.length()).equals(path) && current.getName().endsWith(".class")){
							directories.add(current.getName().replace("/", "."));
						}
					}
				}else{
					File directory = new File(resource.getFile());
					for(String current:directory.list()){
						directories.add(pckgname + '.' + current);
					}
				}
			}
			return directories;
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(pckgname + "does not appear to be a valid package");
		}
	}

	public static Class<?>[] getClasses(String pckgname,
			boolean skipDollarClasses) throws IOException {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		try {
			ArrayList<String> files = getClassesHelper(pckgname);

			if (files!=null&&!files.isEmpty()) {
				// Get the list of the files contained in the package
				for (String file:files) {
					// we are only interested in .class files
					if (file.endsWith(".class")) {
						// get rid of the ".class" at the end
						String withoutclass = file.substring(0, file.length() - ".class".length());

						// in case we don't want $1 $2 etc. endings (i.e. common
						// in GUI classes)
						if (skipDollarClasses) {
							int dollar_occurence = withoutclass.indexOf("$");
							if (dollar_occurence != -1) {
								withoutclass = withoutclass.substring(0,
										dollar_occurence);
							}
						}

						// add this class to our list but avoid duplicates
						boolean already_contained = false;
						for (Class<?> c : classes) {
							if (c.getCanonicalName().equals(withoutclass)) {
								already_contained = true;
							}
						}
						if (!already_contained) {
							classes.add(Class.forName(withoutclass));
						}
						// REMARK this kind of checking is quite slow using
						// reflection, it would be better
						// to do the class.forName(...) stuff outside of this
						// method and change the method
						// to only return an ArrayList with fqcn Strings. Also
						// in reality we have the $1 $2
						// etc. classes in our packages, so we are skipping some
						// "real" classes here
					}
				}
			} else {
				throw new ClassNotFoundException(pckgname
						+ " does not appear to be a valid package");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Class<?>[] classesA = new Class[classes.size()];
		classes.toArray(classesA);
		return classesA;
	}
}
