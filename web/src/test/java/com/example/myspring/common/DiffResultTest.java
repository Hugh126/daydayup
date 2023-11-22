package com.example.myspring.common;

import cn.hutool.json.JSONUtil;
import com.example.myspring.entity.ErpCity;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



@Slf4j
class DiffResultTest {

    @Test
    void showDiffsWithHeader() {
        Map map1 = ImmutableMap.of("id", 1, "provinceId", 10, "name", "武汉失去", "level", 1);
        Map map2 = ImmutableMap.of("provinceId", 11, "name", "武汉市", "abbr", "湖北省武汉市长江大桥", "level", 101);
        DiffResult diffResult = DiffResult.getInstance(map1, map2, ErpCity.class);
        ImmutableMap<String, String> header = ImmutableMap.of("仓库", "总部仓", "原材料", "花生");
        List<List<Object>> lists = diffResult.showDiffsWithHeader(new LinkedHashMap<>(header));
        log.warn(JSONUtil.toJsonPrettyStr(lists));
    }


}