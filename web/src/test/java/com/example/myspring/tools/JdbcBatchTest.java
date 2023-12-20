package com.example.myspring.tools;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * mvn dependency:tree
 * 找对应maven依赖
 *
 * jdbc batch的四种姿势
 * https://www.codejava.net/java-se/jdbc/jdbc-batch-update-examples
 */

@Slf4j
public class JdbcBatchTest {

    // rewriteBatchedStatements 指定是否将批量插入语句重写为多行插入语句
    String jdbcURL = "jdbc:mysql://localhost:3306/benchtest?rewriteBatchedStatements=true";
    String username = "root";
    String password = "123456";

    @BeforeClass
    public static void beforeClass() throws Exception {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        runtimeMxBean.getInputArguments().stream().forEach(System.out::println);
    }

    @Test
    public void test1() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.addBatch("INSERT INTO user (email, pass, name) VALUES ('email1', 'pass1', 'Name 1')");
            statement.addBatch("INSERT INTO user (email, pass, name) VALUES ('email2', 'pass2', 'Name 2')");
            statement.addBatch("INSERT INTO user (email, pass, name) VALUES ('email3', 'pass3', 'Name 3')");
            statement.addBatch("INSERT INTO user (email, pass, name) VALUES ('email4', 'pass4', 'Name 4')");
            statement.addBatch("INSERT INTO user (email, pass, name) VALUES ('email5', 'pass5', 'Name 5')");
            int[] updateCounts = statement.executeBatch();
            log.warn("updateInsertResult = " + IntStream.of(updateCounts).mapToObj(Integer::toString).collect(Collectors.joining(",")));
            connection.commit();
            connection.close();
        } catch ( SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ex.printStackTrace();
        }
    }


    @Test
    public void test2() {
        TEntry u1 = TEntry.create("name1", "pass1", "email1");
        TEntry u2 = TEntry.create("name2", "pass2", "email2");
        TEntry u3 = TEntry.create("name3", "pass3", "email3");
        ArrayList<TEntry> list = Lists.newArrayList(u1, u2, u3);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user (email, pass, name) VALUES (?, ?, ?)");
            for (TEntry t : list) {
                statement.setString(1, t.getEmail());
                statement.setString(2, t.getPass());
                statement.setString(3, t.getName());
                statement.addBatch();
            }
            int[] updateCounts = statement.executeBatch();
            log.warn("updateInsertResult = " + IntStream.of(updateCounts).mapToObj(Integer::toString).collect(Collectors.joining(",")));
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * 客户端流式读取
     * 测试时候设置  -Xmx5m -Xms5m， 然后对比 enableStreamingResults 差异
     *
     * 结果： 如果不设置流式读取，会OOM
     *
     * [原理] 据说客户端和服务端两个buffer相互阻塞发力。无法验证，看看就行
     * https://zhuanlan.zhihu.com/p/47060032
     */
    @Test
    public void streamFetch() {
        Connection connection = null;
        try {
            String erpUrl = "jdbc:mysql://localhost:3306/erp";
            connection = DriverManager.getConnection(erpUrl, username, password);
            com.mysql.jdbc.PreparedStatement preparedStatement  = (com.mysql.jdbc.PreparedStatement)
                    connection.prepareStatement("select * from erp_member_tag");
            preparedStatement.enableStreamingResults();
            ResultSet rs = preparedStatement.executeQuery();
            int i=1;
            while (rs.next()) {
                if (i%10000==0) {
                    System.out.println((i++) + "--" +rs.getString("id") + "--" + rs.getString("member_id"));
                }
                i++;
            }
            System.err.println("end=" + i);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * [无论是设置setFetchSize(Integer.MIN_VALUE)或是URL中制定，就可以直接使用流式结果集， 很多blog中说的模拟两可]
     *
     * 如果使用RowMapper，则会将每一行读入内存，将其转换为对象，最终仍会OOM
     *
     * 解决办法： 使用RowCallbackHandler 自己处理结果集
     */
    @Test
    public void streamFetchByTemplate() {
        String url = "jdbc:mysql://localhost:3306/erp";
//        String url = "jdbc:mysql://localhost:3306/erp?defaultfetchsize=-214783648";
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        jdbcTemplate.setFetchSize(Integer.MIN_VALUE);
        AtomicInteger i = new AtomicInteger();
        jdbcTemplate.query("select * from erp_member_tag", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                int x = i.getAndIncrement();
                if (x%10000==0) {
                    System.out.println(x + "--" +rs.getString("id") + "--" + rs.getString("member_id"));
                }
            }
        });
        System.err.println("end=" + i);
    }


}
