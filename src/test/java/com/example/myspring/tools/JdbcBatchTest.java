package com.example.myspring.tools;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    String jdbcURL = "jdbc:mysql://localhost:3306/benchtest";
    String username = "root";
    String password = "123456";

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


}
