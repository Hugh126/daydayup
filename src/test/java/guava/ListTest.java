package guava;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @Author hugh
 * @Description
 * @Date 2022/1/20
 */
public class ListTest {

    @Test
    void test1() {
        ArrayList<String> names = Lists.newArrayList("John", "Adam", "Jane");
        List<String> reverse = Lists.reverse(names);
        assertThat(reverse, hasItems("Jane", "Adam", "John"));
        List<List<String>> result = Lists.partition(names, 2);

        List<Character> chars = Lists.charactersOf("hello");
        assertEquals(5, chars.size());
        List<Character> result2 = ImmutableSet.copyOf(chars).asList();
        System.out.println(result2.toString());


    }
}
