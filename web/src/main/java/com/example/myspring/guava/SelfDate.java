package com.example.myspring.guava;

/**
 * 日期类，存放年月日
 */
public class SelfDate implements Comparable<SelfDate> {
 
    private int year, month, day;
 
    public SelfDate(int ye, int mon, int da) {
        this.year = ye;
        this.month = mon;
        this.day = da;
    }
 
    public SelfDate() {
    }
 
    public void setItem(SelfDate item) {
        this.year = item.year;
        this.month = item.month;
        this.day = item.day;
    }
 
    @Override
    public int compareTo(SelfDate item) {
        if (this.year < item.year)
            return -1;
        else if (this.year == item.year) {
            if (this.month < item.month)
                return -1;
            else if (this.month == item.month) {
                if (this.day < item.day)
                    return -1;
                else if (this.day == item.day)
                    return 0;
                else
                    return 1;
            } else
                return 1;
        } else
            return 1;
    }
 
    public String toString() {
        String date = "";
        if (0 == this.month / 10)
            date = this.year + "-" + "0" + this.month;
        else date = this.year + "-" + this.month;
 
        if (0 == this.day / 10)
            date = date + "-" + "0" + this.day;
        else date = date + "-" + this.day;
        return date;
    }
}
