package guava;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * @author hugh
 */
@Data
public class DatePeriod implements Comparable<DatePeriod> {

    private Date start;
    private Date end;


    @Override
    public int compareTo(DatePeriod o) {
        return (int) (this.start.getTime() - o.getStart().getTime());
    }

    public  static DatePeriod getInstance(String startStr, String endStr) {
        DateTime startDate = DateUtil.parse(startStr, "yyyyMMdd");
        DateTime endDate = DateUtil.parse(endStr, "yyyyMMdd");
        DatePeriod period = new DatePeriod();
        period.setStart(startDate);
        period.setEnd(endDate);
        return period;
    }
}
