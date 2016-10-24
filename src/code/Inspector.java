package code;
import java.lang.reflect.*;
import java.util.HashMap;

public class Inspector {
	HashMap<Class<?>, Integer> recurseMap = new HashMap<Class<?>, Integer>();

	public void inspect(Object obj, boolean recursive){
		Class<?> classObject = obj.getClass();
		recurseMap.put(classObject, classObject.hashCode());
		displayClassIterInfo(classObject);
		displayClassMethodInfo(classObject);
		displayClassConstructorInfo(classObject);
		displayClassFieldInfo(classObject);
		
		if (recursive == true){
			recurseOnFieldObjects(classObject, recursive, recurseMap);
		}		
	}
	
	public void displayClassIterInfo(Class<?> currObject){
		String className = currObject.getName();
		String superClassName = currObject.getSuperclass().getName();
		Class <?> [] interfaces = currObject.getInterfaces();
		String [] interfaceNames = new String[interfaces.length];
			for (int i = 0; i < interfaces.length; i++) {
				interfaceNames[i] = interfaces[i].getName();
			}
		System.out.println("******NEW CLASS******");
		System.out.println("Class: " + className);
		System.out.println("Direct Superclass: " + superClassName);
		System.out.println("Interfaces:");
		for (int i = 0; i < interfaceNames.length; i++) {
			System.out.println(interfaceNames[i]);
		}
		System.out.println();
	}
	
	public void displayClassMethodInfo(Class<?> currObject) {
		Method [] methods = currObject.getDeclaredMethods();
		System.out.println("***Methods***");
		System.out.println();
		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			Class<?> [] exceptions = methods[i].getExceptionTypes();
			String [] exceptionNames = new String[exceptions.length];
			for (int j = 0; j < exceptionNames.length; j++) {
				exceptionNames[j] = exceptions[j].getName();
			}
			Class<?> [] parameters = methods[i].getParameterTypes();
			String [] parameterNames = new String[parameters.length];
			for (int k = 0; k < parameterNames.length; k++) {
				parameterNames[k] = parameters[k].getName();
			}
			String returnTypeName = methods[i].getReturnType().getName();
			String modifiers = Modifier.toString(methods[i].getModifiers());
			
			System.out.println("Method Name: " + methodName);
			System.out.println("Exceptions: ");
			for (int j = 0; j < exceptionNames.length; j++) {
				System.out.println(exceptionNames[j]);
			}
			System.out.println("Parameter Types: ");
			for (int k = 0; k < parameterNames.length; k++){
				System.out.println(parameterNames[k]);
			}
			System.out.println("Return Type: " + returnTypeName);
			System.out.println("Modifiers: " + modifiers);
			
		}
		System.out.println();
	}
	
	public void displayClassConstructorInfo(Class <?> currObject) {
		Constructor <?> [] constructors = currObject.getConstructors();
		String [] constructorNames = new String [constructors.length];
		System.out.println("***Constructors***");
		System.out.println();
		for (int i = 0; i < constructors.length; i++) {
			constructorNames[i] = constructors[i].getName();
			Class<?> [] parameters = constructors[i].getParameterTypes();
			String [] parameterNames = new String[parameters.length];
			String modifiers = Modifier.toString(constructors[i].getModifiers());
			System.out.println("Parameter Types: ");
			for (int k = 0; k < parameterNames.length; k++){
				System.out.println(parameterNames[k]);
			}
			System.out.println("Modifiers: " + modifiers);
		}
		System.out.println();
	}
	
	public void displayClassFieldInfo(Class<?> currObject) {
		Field [] fields = currObject.getDeclaredFields();
		System.out.println("***Fields***");
		System.out.println();
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getType().getName();
			String modifiers = Modifier.toString(fields[i].getModifiers());
			String value = getFieldValue(fields[i], currObject);
			
			System.out.println("Field Type: " + fieldName);
			System.out.println("Field Modifiers: " + modifiers);
			System.out.println("Field Value: " + value);
		}
		System.out.println();
	}
	
	public String getFieldValue(Field currField, Class<?> currObject) {
		String valueString = new String();
		Class <?> fieldClass = currField.getType();
		try {
			Object fieldValue = currField.get(currObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (fieldClass.isPrimitive() == true) {
			try {
				if (fieldClass == Class.forName("Integer")) {
					valueString = Integer.toString(currField.getInt(currObject));
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return valueString;
	}
	
	public void recurseOnFieldObjects(Class<?> currObject, boolean recursive, HashMap<Class<?>, Integer> currMap) {	
		Field [] fields = currObject.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Class<?> currType = fields[i].getType();
			if (currType.isPrimitive() == true){
				//System.out.println("is Prim");
			}
			else if (currType.isArray() == true){
				//System.out.println("is Arr");
			}
			else {
				try {
					Object newObject = currType.newInstance();
					if (currMap.containsKey(newObject.getClass()) == false){
						inspect(newObject, recursive);
					}
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
