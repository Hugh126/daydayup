package simple;


import com.example.myspring.entity.Stu;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 final 关键字理解
 1）修饰类，类不可被继承
 <!--所有成员方法被隐式声明为finnal，一般出于安全考虑，用于工具类-->
 2）修饰方法，不可被覆写
 <!--所有private方法被隐式声明为finnal-->
 3）修饰变量，a. 对基础类型及其封装类型，值不可被更改；b. 对于引用类型，引用指向对象不可被更改

 */
public class FinalTest {

    public static final List oriList = new ArrayList(){
    };

    static{
        oriList.add(1);
        oriList.add(2);
    }

    /**
     * final修饰的变量被编译器当作  编译期常量
     */
    @Test
    public void Test1() {
        String a = "hello2";
        final String b = "hello";
        String d = "hello";
        String c = b + 2;
        String e = d + 2;
        System.out.println((a == c));
        System.out.println((a == e));

        System.out.println(a.equals(e));
    }

    private void removeElem(final List list) {
        list.remove(0);
    }


    private void changeObj(List list) {
        list = new ArrayList();
    }

    private void changeObj2(final List list) {
        // 不可被修改
//        list = new ArrayList();
    }



    @Test
    public void Test2() {
        removeElem(oriList);
        System.out.println(oriList.size());

        /**
         * final 修饰变量在方法内修改其引用指向对象无效
         */
        changeObj(oriList);
        System.out.println(oriList.size());


    }

    @Test
    public void test3() {
        System.out.println(System.identityHashCode(127));
        System.out.println(System.identityHashCode(Integer.valueOf(127)));
        System.out.println(System.identityHashCode(new Stu()));
        System.out.println(System.identityHashCode(new Stu()));

    }
}
