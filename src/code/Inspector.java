package code;
import java.lang.reflect.*;
import java.util.HashMap;

public class Inspector {
	HashMap<Class<?>, Integer> recurseMap = new HashMap<Class<?>, Integer>();

	public void inspect(Object obj, boolean recursive){
		System.out.println("size: " +recurseMap.size());
		Class<?> classObject = obj.getClass();
		recurseMap.put(classObject, classObject.hashCode());
		if (recursive == true){
			recurseOnFieldObjects(classObject, recursive, recurseMap);
		}
		System.out.println(classObject.getName());
		
		
	}
		
	public void recurseOnFieldObjects(Class<?> currObject, boolean recursive, HashMap<Class<?>, Integer> currMap) {	
		Field [] fields = currObject.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Class<?> currType = fields[i].getType();
			if (currType.isPrimitive() == true){
				System.out.println("is Prim");
			}
			else if (currType.isArray() == true){
				System.out.println("is Arr");
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
