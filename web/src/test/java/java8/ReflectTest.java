package java8;

import cn.hutool.json.JSONUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author hugh
 * @version 1.0
 * @description:
 * @date 2019/11/23 0023
 */
public class ReflectTest {


	public static void setValue(Class clazz, Object obj, String filedName, Object value){
		String methodName = "set" + filedName.substring(0,1).toUpperCase()+filedName.substring(1);
		try{
			Method method =  clazz.getDeclaredMethod(methodName, clazz.getDeclaredField(filedName).getType());
			method.invoke(obj, value);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static Object getValue(Class clazz, Object obj, String filedName){
		String methodName = "get" + filedName.substring(0,1).toUpperCase()+filedName.substring(1);
		try{
			Method method =  clazz.getDeclaredMethod(methodName, new Class[]{clazz.getDeclaredField(filedName).getType()});
			return method.invoke(obj);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;

	}

	public static void foo(Class clazz, Object objFrom, Object objTo) {
		for(Field field : clazz.getDeclaredFields()) {
			boolean ogAccessible = field.isAccessible();
			field.setAccessible(true);
			Object fieldV = getValue(clazz, objFrom, field.getName());
			field.setAccessible(ogAccessible);
			if(fieldV != null) {
				setValue(clazz, objTo, field.getName(), fieldV);
			}

		}
	}




	static class Stu{

		private String sno;
		private String name;
		private Integer age;
		private double costMoney;

		public double getCostMoney() {
			return costMoney;
		}

		public void setCostMoney(double costMoney) {
			this.costMoney = costMoney;
		}

		public String getSno() {
			return sno;
		}

		public void setSno(String sno) {
			this.sno = sno;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public Stu() {
		}

		public Stu(String sno, String name, Integer age, double costMoney) {
			this.sno = sno;
			this.name = name;
			this.age = age;
			this.costMoney = costMoney;
		}
	}

	public static void main(String[] args) {
		Stu stu = new Stu();
		setValue(Stu.class, stu, "name", "abc");
		setValue(Stu.class, stu, "costMoney", 100.88);
		System.out.println(JSONUtil.toJsonStr(stu));

		System.out.println(getValue(Stu.class, stu, "name"));
		System.out.println("-------------");
//
//		Stu stu2 = new Stu("0002", "xxx", 18, 888);
//
//		foo(Stu.class, stu, stu2);
//
//		System.out.println(JSONUtil.toJsonStr(stu2));

	}


}
