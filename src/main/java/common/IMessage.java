package common;

/**
 * @author hugh
 * @Title: IMessage
 * @ProjectName springboottest
 * @Description: 泛型接口及其两种实现方式
 * @date 2018/9/2713:23
 */
public interface IMessage<T> {

    public void printT(T t);

}


/**
 * type1
 */
class IMessageImpl<T> implements IMessage<T> {

    // 泛型方法
    public static <T> T[] fun(T ... args) {
        return args;
    }

    @Override
    public void printT(T t) {

    }
}

/**
 * type2
 */
class IMessageStringIMpl implements IMessage<String> {
    @Override
    public void printT(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        IMessage iMessage = new IMessageStringIMpl();
        iMessage.printT("hello");
    }

}

