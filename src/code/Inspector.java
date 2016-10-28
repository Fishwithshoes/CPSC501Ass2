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
		displayClassFieldInfo(classObject, obj);
		displaySuperClass(obj, classObject, recurseMap);
		if (recursive == true){
			recurseOnFieldObjects(obj, recursive, recurseMap);
		}
	}
	
	public void displayClassIterInfo(Class<?> currObject){
		String className = currObject.getName();
		String superClassName = null;
		if (currObject.getSuperclass() != null)
			superClassName = currObject.getSuperclass().getName();
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
	
	public void displayClassFieldInfo(Class<?> currClass, Object currObject) {
		Field [] fields = currClass.getDeclaredFields();
		System.out.println("***Fields***");
		System.out.println();
		for (int i = 0; i < fields.length; i++) {
			Class <?> fieldType = fields[i].getType();
			String fieldName = fields[i].getName();
			String fieldTypeString = fieldType.getName();
			String modifiers = Modifier.toString(fields[i].getModifiers());
			String arrayCompType = null;
			int arrayFieldLength = 0;
			String valueString = new String();
			if (fieldType.isPrimitive()) {
				valueString = primitiveHandler(fields[i], currObject);
			}
			else if (fieldType.isArray()){
				Object arrayObject = new Object();
				try {
					fields[i].setAccessible(true);
					arrayObject = fields[i].get(currObject);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (arrayObject != null) {
					arrayFieldLength = Array.getLength(arrayObject);
					valueString = arrayHandler(arrayObject, currObject);
				}
				else {
					valueString = null;
				}
				arrayCompType = fieldType.getComponentType().toString();

			}
			else if (fieldType.isEnum()) {
				
			}
			else {
				valueString = objectHandler(fields[i], currObject);
			}

			System.out.println("Field Name: " + fieldName);
			System.out.println("Field Type: " + fieldTypeString);
			System.out.println("Field Modifiers: " + modifiers);
			if (fieldType.isArray()) {
				System.out.println("Array Field Component Type: " + arrayCompType);
				System.out.println("Array Field Length: " + Integer.toString(arrayFieldLength));
			}
			System.out.println("Field Value: " + valueString);
		}
		System.out.println();
	}
	
	public String primitiveHandler(Field currField, Object currObject) {
		String resultString = new String();
		Class <?> fieldType = currField.getType();
		if (fieldType == Boolean.TYPE) {
			resultString = boolHandler(currField, currObject);	
		}
		else if (fieldType == Character.TYPE) {
			resultString = charHandler(currField, currObject);				
		}
		else if (fieldType == Byte.TYPE) {
			resultString = byteHandler(currField, currObject);		
		}
		else if (fieldType == Short.TYPE) {
			resultString = shortHandler(currField, currObject);			
		}
		else if (fieldType == Integer.TYPE) {
			resultString = intHandler(currField, currObject);			
		}
		else if (fieldType == Long.TYPE) {
			resultString = longHandler(currField, currObject);			
		}
		else if (fieldType == Float.TYPE) {
			resultString = floatHandler(currField, currObject);
		}
		else if (fieldType == Double.TYPE) {
			resultString = doubleHandler(currField, currObject);
		}
		else { //void
			resultString = "";		
		}

		return resultString;
		
	}
	
	public String arrayHandler(Object array, Object currObject) {
		String resultString = "";
		for (int i = 0; i < Array.getLength(array); i++) {
			Object valueAtI = Array.get(array, i);
			if (valueAtI == null)
				resultString = resultString + "null ";
			else
				resultString = resultString + valueAtI.toString() + " ";
		}
		
		return resultString;
	}
	
	public String objectHandler(Field currField, Object currObject) {
		String valueString = new String();
		Object fieldObject = new Object();	
		try {
			currField.setAccessible(true);
			fieldObject = currField.get(currObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (fieldObject == null)
			return null;
		else
			valueString = fieldObject.toString();
		
		return valueString;
	}
	
	public String boolHandler(Field currField, Object currObject) {
		boolean returnValue = new Boolean(false);
		currField.setAccessible(true);
		try {
			returnValue = currField.getBoolean(currObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String boolString = Boolean.toString(returnValue);
		return boolString;
	}
	
	public String charHandler(Field currField, Object currObject) {
		char returnValue = new Character('e');
		currField.setAccessible(true);
		try {
			returnValue = currField.getChar(currObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String charString = Character.toString(returnValue);
		return charString;
	}
	
	public String byteHandler(Field currField, Object currObject) {
		byte returnValue = new Byte((byte)0xff);
		currField.setAccessible(true);
		try {
			returnValue = currField.getByte(currObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String byteString = Byte.toString(returnValue);
		return byteString;
	}
	
	public String shortHandler(Field currField, Object currObject) {
		short returnValue = new Short((short)-1);
		currField.setAccessible(true);
		try {
			returnValue = currField.getShort(currObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String shortString = Short.toString(returnValue);
		return shortString;
	}
	
	public String intHandler(Field currField, Object currObject) {
		int returnValue = new Integer(-1);
		currField.setAccessible(true);
		try {
			returnValue = currField.getInt(currObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String intString = Integer.toString(returnValue);
		return intString;
	}
	
	public String longHandler(Field currField, Object currObject) {
		long returnValue = new Long(-1);
		currField.setAccessible(true);
		try {
			returnValue = currField.getLong(currObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String longString = Long.toString(returnValue);
		return longString;
	}
	
	public String floatHandler(Field currField, Object currObject) {
		float returnValue = new Float(-1.0);
		currField.setAccessible(true);
		try {
			returnValue = currField.getFloat(currObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String floatString = Float.toString(returnValue);
		return floatString;
	}
	
	public String doubleHandler(Field currField, Object currObject) {
		double returnValue = new Double(-1.0);
		currField.setAccessible(true);
		try {
			returnValue = currField.getDouble(currObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String doubleString = Double.toString(returnValue);
		return doubleString;
	}
	
	public void displaySuperClass(Object currObject, Class<?> currClass, HashMap<Class<?>, Integer> currMap) {
		Class <?> superClass = currClass.getSuperclass();
		while (superClass != null && currMap.containsKey(superClass) == false) {
			recurseMap.put(superClass, superClass.hashCode());
			displayClassIterInfo(superClass);
			displayClassMethodInfo(superClass);
			displayClassConstructorInfo(superClass);
			displayClassFieldInfo(superClass, currObject);
			displaySuperClass(currObject, superClass, recurseMap);
		}
	}
	
	public void recurseOnFieldObjects(Object currObject, boolean recursive, HashMap<Class<?>, Integer> currMap) {
		Class <?> currClass = currObject.getClass();
		Object newObject = new Object();
		Field [] fields = currClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			Class<?> currType = fields[i].getType();
			if (currType.isPrimitive() == true){
				//System.out.println("is Prim");
			}
			else if (currType.isArray() == true){
				Object arrayObject = new Object();
				try {
					arrayObject = fields[i].get(currObject);
					if (arrayObject != null) {
						for (int j = 0; j < Array.getLength(arrayObject); j++) {
							newObject = Array.get(arrayObject, j);
							if (newObject != null && newObject.getClass().isPrimitive() == false) {
								if (currMap.containsKey(newObject.getClass()) == false){
									inspect(newObject, recursive);
							}
						}
					}
				}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (currType.isEnum() == true) {
				
			}
			else {
				try {
					newObject = fields[i].get(currObject);
					if (newObject != null) {
						if (currMap.containsKey(newObject.getClass()) == false){
							inspect(newObject, recursive);
						}
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
