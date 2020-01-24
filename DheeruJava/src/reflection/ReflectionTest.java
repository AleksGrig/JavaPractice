package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionTest {

	public static void main(String[] args) {
		User user = new User();
		Class<? extends User> clazz = user.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			System.out.println("type is " + field.getType() + " modifiers " + field.getModifiers());
		}

		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method.getName());
		}
	}

}
