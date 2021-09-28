package sourcecode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

public class MyClassLoader extends ClassLoader {


    public MyClassLoader() {
        super(null);

    }

    @Override
    protected Class findClass (String name) throws ClassNotFoundException {

        String classPath = name.replace(".", "/")+".class";
        InputStream classInputStream = getSystemClassLoader().getResourceAsStream(classPath);
        try {
            byte[] classBytes = new byte[classInputStream.available()];
            classInputStream.read(classBytes);
            Class clazz = defineClass(name, classBytes, 0, classBytes.length);
            resolveClass(clazz);
            return clazz;
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        MyClassLoader classLoader = new MyClassLoader();
        Class<?> clazz = null;
        try {
            clazz = classLoader.loadClass("com.example.myspring.common.Result");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(clazz.getConstructor().newInstance());
    }


}