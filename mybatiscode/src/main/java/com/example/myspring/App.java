package com.example.myspring;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        // 加载主配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        // 创建SqlSessionFactory对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // 创建SqlSession对象
        SqlSession session = factory.openSession();
        // 获取mapper
        CityMapper mapper = session.getMapper(CityMapper.class);

        // 查询
        // List<ErpCity> list = mapper.queryByName("武汉");

        // 分页查询
        PageHelper.startPage(1, 3);
        Page<ErpCity> page = (Page<ErpCity>) mapper.queryByLevel(1);
        System.out.println(page.toString());

        //释放资源
        session.close();
        in.close();
    }
}
