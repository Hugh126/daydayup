package com.example.myspring.tools;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.*;

public class PgStreamFetchTest {

//    final  static private Map<Integer, MemInfo> memInfoStat1 = new HashMap<>(Maps.newHashMapWithExpectedSize(37493061));
//    final  static private Map<Integer, MemInfo> memInfoStat2 = new HashMap<>(Maps.newHashMapWithExpectedSize(31848989));
//    final  static private Map<Integer, MemInfo> memInfoStat3 = new HashMap<>(Maps.newHashMapWithExpectedSize(28159284));
    final  static private Map<Integer, MemInfo> memInfoStat4 = new HashMap<>(Maps.newHashMapWithExpectedSize(24033027));
    final  static String dfs = "yyyy-MM-dd HH:mm:ss";

    @BeforeClass
    public static void beforeClass() throws Exception {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        runtimeMxBean.getInputArguments().stream().forEach(System.out::println);
    }

    private Double objSize(Object obj) {
        return 0.0;
//        double size = ObjectSizeCalculator.getObjectSize(obj)/1024.0/1024.0;
//        return BigDecimal.valueOf(size).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private void batchInsert(int v, Collection<MemInfo> values){
        System.out.println("--------------------------------" + v + "--------------------------------");
        System.out.println("[TO Write Size=]" + values.size() + "size=" + objSize(values) + "M");
        System.out.println(LocalDateTime.now().toString() + "========= begin write");
        Iterator<MemInfo> iterator = values.iterator();
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/erp?rewriteBatchedStatements=true";
        try {
            connection = DriverManager.getConnection(url, "root", "123456");
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO erp_member_aggr (member_id, first_to_store_time, first_to_store_id, store_Id_Aggr, consume_date_aggr, consume_pay_aggr, coupon_seq_aggr) " +
                            " VALUES (?, ?, ?, ?, ?, ?, ?)");
            long index = 1;
            int batch = 1;
            while (iterator.hasNext()) {
                MemInfo next = iterator.next();
                statement.setInt(1, next.getMemberId());
                statement.setObject(2, next.getFirstToStoreTime());
                statement.setInt(3, next.getFirstToStoreId());
                statement.setString(4, next.getStoreIdAggr());
                statement.setString(5, next.getConsumeDateAggr());
                statement.setString(6, next.getConsumePayAggr());
                statement.setString(7, next.getCouponSeqAggr());
                statement.addBatch();
                if (index == 100000) {
                    long start = System.currentTimeMillis();
                    int[] updateCounts = statement.executeBatch();
                    connection.commit();
                    long end = System.currentTimeMillis();
                    System.out.println("[Batch version=]" + batch + " updateCounts=" + updateCounts.length + " cost=" + (end-start)/1000);
                    // clear
                    index=1;
                    batch ++;
                    statement.clearParameters();
                    statement.clearBatch();
                }else {
                    index++;
                }
            }
            // flush end
            long start2 = System.currentTimeMillis();
            int[] updateCounts = statement.executeBatch();
            connection.commit();
            long end2 = System.currentTimeMillis();
            System.out.println("[Batch version=]" + batch + " updateCounts=" + updateCounts.length + " cost=" + (end2-start2)/1000);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        System.out.println(LocalDateTime.now().toString() + "========= end write");
    }

    private GpMem buildMemInfo(ResultSet rs ) throws SQLException {
        GpMem mem = new GpMem();
        mem.setMemberId(rs.getInt("member_id"));
        mem.setCrtDate(rs.getString("crt_date"));
        mem.setBelongStoreId(rs.getInt("belong_store_id"));
        mem.setNetPayPrice(rs.getDouble("net_pay_price"));
        Object couponSeqObj = rs.getObject("coupon_seq");
        if (couponSeqObj != null) {
            mem.setCouponSeq(String.valueOf(couponSeqObj));
        }
        return mem;
    }

    private MemInfo convertWhenFirst(GpMem gpMem) {
        MemInfo info = new MemInfo();
        info.setMemberId(gpMem.getMemberId());
        info.setFirstToStoreTime(parseDate(gpMem.getCrtDate()));
        info.setFirstToStoreId(gpMem.getBelongStoreId());
        info.setConsumeDateAggr(gpMem.getCrtDate());
        Double netPayPrice = gpMem.getNetPayPrice();
        info.setConsumePayAggr(netPayPrice==null?"0.0":String.valueOf(netPayPrice));
        info.setStoreIdAggr(String.valueOf(gpMem.getBelongStoreId()));
        if (StrUtil.isNotEmpty(gpMem.getCouponSeq())) {
            info.setCouponSeqAggr(gpMem.getCouponSeq());
        }
        return info;
    }

    private java.util.Date parseDate(String str){
        SimpleDateFormat dateFormat = new SimpleDateFormat(dfs);
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    private MemInfo mergeGpMem(GpMem gpMem, MemInfo oldMem) {
        oldMem.setStoreIdAggr(oldMem.getStoreIdAggr() + "," + gpMem.getBelongStoreId());
        oldMem.setConsumePayAggr(oldMem.getConsumePayAggr() + "," + gpMem.getNetPayPrice());
        oldMem.setConsumeDateAggr(oldMem.getConsumeDateAggr() + "," + gpMem.getCrtDate());
        if (StrUtil.isNotEmpty(gpMem.getCouponSeq())) {
            String old = oldMem.getCouponSeqAggr();
            if (StringUtils.isEmpty(old)) {
                oldMem.setCouponSeqAggr(gpMem.getCouponSeq());
            }else{
                // coupon_seq 包含,
                oldMem.setCouponSeqAggr(old + "|" +gpMem.getCouponSeq());
            }
        }
        return oldMem;
    }

    /**
     * 1 分批防止OOM
     * 2 优化： 可以将memInfoStat4 简化为1个，及时clear即可
     * @throws SQLException
     */
    @Test
    public void test1() throws SQLException {
//        partFetch("member_id>80000000 and member_id <= 83000000", memInfoStat1);
//        batchInsert(1, memInfoStat1.values());
//        partFetch("member_id>83000000 and member_id <= 87000000", memInfoStat2);
//        batchInsert(2, memInfoStat2.values());
//        partFetch("member_id>87000000 and member_id <= 92000000", memInfoStat3);
//        batchInsert(3, memInfoStat3.values());
        partFetch("member_id>92000000", memInfoStat4);
        batchInsert(4, memInfoStat4.values());
    }

    private void partFetch(String sqlCondition, Map<Integer, MemInfo> statMap) {
        System.out.println(LocalDateTime.now().toString() +"--start-- sqlCondition=" + sqlCondition);
        long t1 = System.currentTimeMillis();
        String url = "jdbc:postgresql://xxx/dw?ssl=false";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, "dw", "DwHe1e3eureo");
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.FETCH_FORWARD);
            stmt.setFetchSize(100000);
            ResultSet rs = stmt.executeQuery("select member_id, to_char(crt_date, 'YYYY-MM-DD HH24:MI:SS') as crt_date, belong_store_id,  net_pay_price,coupon_seq from erp.erp_order " +
                    " where 1=1 " +
                    " and  " + sqlCondition +
                    " and status=2  order by crt_date");
            long x = 1;
            while (rs.next()) {
                int memberId = rs.getInt("member_id");
                GpMem gpMem = buildMemInfo(rs);
                if (!statMap.containsKey(memberId)) {
                    statMap.put(memberId, convertWhenFirst(gpMem));
                }else{
                    MemInfo oldMem= statMap.get(memberId);
                    statMap.put(memberId, mergeGpMem(gpMem, oldMem));
                }
                if (x%1000000 == 0){
                    System.out.println("[rate]=" + NumberUtil.round(x/37493061.0, 2).doubleValue()*100 + "%");
                }
                x++;
            }
            System.out.println("[Fetch End. Condition=]" + sqlCondition + " [Cost]=" + (System.currentTimeMillis()-t1)/1000 + "s");
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

    static class GpMem{
        private Integer memberId;
        private String crtDate;
        private Integer belongStoreId;
        private Double netPayPrice;
        private String couponSeq;

        public Integer getMemberId() {
            return memberId;
        }

        public void setMemberId(Integer memberId) {
            this.memberId = memberId;
        }

        public String getCrtDate() {
            return crtDate;
        }

        public void setCrtDate(String crtDate) {
            this.crtDate = crtDate;
        }

        public Integer getBelongStoreId() {
            return belongStoreId;
        }

        public void setBelongStoreId(Integer belongStoreId) {
            this.belongStoreId = belongStoreId;
        }

        public Double getNetPayPrice() {
            return netPayPrice;
        }

        public void setNetPayPrice(Double netPayPrice) {
            this.netPayPrice = netPayPrice;
        }

        public String getCouponSeq() {
            return couponSeq;
        }

        public void setCouponSeq(String couponSeq) {
            this.couponSeq = couponSeq;
        }
    }


    static class MemInfo{
        private Integer memberId;
        private Date firstToStoreTime;
        private Integer firstToStoreId;
        private String storeIdAggr;
        private String consumeDateAggr;
        private String consumePayAggr;
        private String couponSeqAggr;

        public Integer getMemberId() {
            return memberId;
        }

        public void setMemberId(Integer memberId) {
            this.memberId = memberId;
        }

        public Date getFirstToStoreTime() {
            return firstToStoreTime;
        }

        public void setFirstToStoreTime(Date firstToStoreTime) {
            this.firstToStoreTime = firstToStoreTime;
        }

        public Integer getFirstToStoreId() {
            return firstToStoreId;
        }

        public void setFirstToStoreId(Integer firstToStoreId) {
            this.firstToStoreId = firstToStoreId;
        }

        public String getStoreIdAggr() {
            return storeIdAggr;
        }

        public void setStoreIdAggr(String storeIdAggr) {
            this.storeIdAggr = storeIdAggr;
        }

        public String getConsumeDateAggr() {
            return consumeDateAggr;
        }

        public void setConsumeDateAggr(String consumeDateAggr) {
            this.consumeDateAggr = consumeDateAggr;
        }

        public String getConsumePayAggr() {
            return consumePayAggr;
        }

        public void setConsumePayAggr(String consumePayAggr) {
            this.consumePayAggr = consumePayAggr;
        }

        public String getCouponSeqAggr() {
            return couponSeqAggr;
        }

        public void setCouponSeqAggr(String couponSeqAggr) {
            this.couponSeqAggr = couponSeqAggr;
        }
    }
}
