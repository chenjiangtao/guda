/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * 
 * @author gag
 * @version $Id: ZoneDateUtils.java, v 0.1 2012-5-13 下午1:51:37 gag Exp $
 */
public class DateHelper {

    //    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    //    public static SimpleDateFormat monFormat  = new SimpleDateFormat("yyyyMM");

    //    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public static final Logger logger = Logger.getLogger(DateHelper.class);

    public static String getDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
        //
        return simpleDateFormat.format(new Date());
    }

    public static String getStrDateTime() {
        return getStrDateTime(new Date());
    }

    public static String getStrCurrentDate(String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(new Date());
    }

    public static String getStrDateByFormat(Date time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        if (time == null) {
            return "";
        }

        if (format == null || format.equals("")) {
            return dateFormat.format(time);
        } else {
            SimpleDateFormat f = new SimpleDateFormat(format);
            return f.format(time);
        }
    }

    /**
     * 按指定格式输出指定日期前一天的日期时间
     * 
     * @param time
     * @param format
     * @return
     */
    public static String getPreStrDateByFormat(Date time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);// 前一天的日期时间
        time = calendar.getTime();
        if (time == null) {
            return "";
        }

        if (format == null || format.equals("")) {
            return dateFormat.format(time);
        } else {
            SimpleDateFormat f = new SimpleDateFormat(format);
            return f.format(time);
        }
    }

    /**
     * 按指定格式输出指定日期30天前的日期时间
     * 
     * @param time
     * @param format
     * @return
     */
    public static String getPreThirtyDayStrDateByFormat(Date time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -30);// 30天前的日期时间
        time = calendar.getTime();
        if (time == null) {
            return "";
        }

        if (format == null || format.equals("")) {
            return dateFormat.format(time);
        } else {
            SimpleDateFormat f = new SimpleDateFormat(format);
            return f.format(time);
        }
    }

    /**
     * 按指定格式输出指定日期60天前的日期时间
     * 
     * @param time
     * @param format
     * @return
     */
    public static String getPreSixtyDayStrDateByFormat(Date time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -60);// 60天前的日期时间
        time = calendar.getTime();
        if (time == null) {
            return "";
        }

        if (format == null || format.equals("")) {
            return dateFormat.format(time);
        } else {
            SimpleDateFormat f = new SimpleDateFormat(format);
            return f.format(time);
        }
    }

    /**
     * 按指定格式输出指定日期10天前的日期时间
     * 
     * @param time
     * @param string
     * @return
     */
    public static String getPreTenDayStrDateByFormat(Date time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -10);// 10天前的日期时间
        time = calendar.getTime();
        if (time == null) {
            return "";
        }

        if (format == null || format.equals("")) {
            return dateFormat.format(time);
        } else {
            SimpleDateFormat f = new SimpleDateFormat(format);
            return f.format(time);
        }
    }

    public static String getStrDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(date);
    }

    public static boolean isHolidayToday(String[] excludes, String[] includes) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String today = simpleDateFormat.format(calendar.getTime());
        if (week == 1 || week == 7) {
            if (excludes != null) {
                for (int i = 0, len = excludes.length; i < len; ++i) {
                    if (today.equals(excludes[i])) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            if (includes != null) {
                for (int i = 0, len = includes.length; i < len; ++i) {
                    if (today.equals(includes[i])) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static boolean isHoliday(Date date, String[] excludes, String[] includes) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String today = simpleDateFormat.format(calendar.getTime());
        if (week == 1 || week == 7) {
            if (excludes != null) {
                for (int i = 0, len = excludes.length; i < len; ++i) {
                    if (today.equals(excludes[i])) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            if (includes != null) {
                for (int i = 0, len = includes.length; i < len; ++i) {
                    if (today.equals(includes[i])) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static boolean isWeekendToday() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return week == 1 || week == 7;
    }

    public static Date getSendTime(Date sendDate, Date start, Date end) {
        Calendar calendar = Calendar.getInstance();
        if (sendDate != null) {
            calendar.setTime(sendDate);
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        startCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        endCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        startCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        endCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        startCalendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
        endCalendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
        if (calendar.after(startCalendar) && calendar.before(endCalendar)) {
            return sendDate;
        } else if (calendar.before(startCalendar)) {
            return startCalendar.getTime();
        } else {
            startCalendar.add(Calendar.DATE, 1);
            return startCalendar.getTime();
        }

    }

    public static boolean withIn(Date start, Date end) {
        if (start == null && end == null) {
            return true;
        }
        Calendar calendar = Calendar.getInstance();
        if (start == null) {
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(end);
            if (calendar.before(endCalendar)) {
                return true;
            } else {
                return false;
            }
        }
        if (end == null) {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(start);
            if (calendar.after(startCalendar)) {
                return true;
            } else {
                return false;
            }
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        int compare = calendar.compareTo(startCalendar);
        if (compare >= 0 && calendar.before(endCalendar)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前时间是否在有效的时间内，在则立即发送。
     * 否则发送时间为第二天
     * @param timeStr 格式为8:00:00-15:00:00
     * @return
     * @throws ParseException 
     */
    public static Date getSendDate(Date sendDate, String timeStr) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        if (timeStr == null) {
            return null;
        }
        String[] temp = timeStr.split("-");
        if (temp.length != 2) {
            return null;
        }
        Date start = null;
        Date end = null;
        Date dayEnd = null;
        try {
            start = timeFormat.parse(temp[0]);
            end = timeFormat.parse(temp[1]);
            dayEnd = timeFormat.parse("23:59:59");
        } catch (ParseException e) {
            logger.info("时间转化有问题！", e);
        }
        if (start != null && end != null) {
            Date date = new Date();
            if (sendDate != null) {
                date = sendDate;
            }
            if (withIn(date, start, end) && withIn(new Date(), start, end)) {
                logger.info("模版有效时间范围之内！");
                return null;
            }
            logger.info("模版有效时间范围之外！");
            Calendar calendar = Calendar.getInstance();
            if (sendDate != null) {
                if (new Date().after(sendDate)) {
                    date = new Date();
                    calendar.setTime(date);
                } else {
                    calendar.setTime(sendDate);
                    date = sendDate;
                }
            }
            Calendar tempcalendar = Calendar.getInstance();
            tempcalendar.setTime(start);
            if (withIn(date, end, dayEnd)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, tempcalendar.get(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, tempcalendar.get(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, tempcalendar.get(Calendar.SECOND));
            } else {
                calendar.set(Calendar.HOUR_OF_DAY, tempcalendar.get(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, tempcalendar.get(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, tempcalendar.get(Calendar.SECOND));
            }
            return calendar.getTime();
        }
        return null;
    }

    public static boolean withIn(Date date, Date start, Date end) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            if (date.after(new Date())) {
                calendar.setTime(date);
            }
        }
        if (start == null || end == null) {
            return true;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        startCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        endCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));

        startCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        endCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));

        startCalendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
        endCalendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
        endCalendar.getTime();
        int result = calendar.compareTo(startCalendar);
        if (result >= 0 && calendar.before(endCalendar)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取第二天的发送时间，如果开始时间为Null则默认是早上8点
     * 
     * @param start
     * @return
     */
    public static Date getNextSendTime(Date sendDate, Date start) {
        Calendar calendar = Calendar.getInstance();
        if (sendDate != null) {
            calendar.setTime(sendDate);
        }
        Calendar startCalendar = Calendar.getInstance();
        if (start == null) {
            startCalendar.set(Calendar.HOUR_OF_DAY, 8);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.SECOND, 0);
        } else {
            startCalendar.setTime(start);
        }
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, startCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, startCalendar.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, startCalendar.get(Calendar.SECOND));
        return calendar.getTime();

    }

    /**
     * 是否同一天
     * 
     * @param date
     * @return
     */
    public static boolean isSameDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        return calendar.get(Calendar.DATE) == startCalendar.get(Calendar.DATE);
    }

    /**
     * 是否同一月
     * 
     * @param date
     * @return
     */
    public static boolean isSameMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        return calendar.get(Calendar.MONTH) == startCalendar.get(Calendar.MONTH);
    }

    /**
     * 获取日期为几号
     * 
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        return startCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 设置日子为1号
     * 
     * @param date
     * @return
     */
    public static Date setFirstDay(Date date) {

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return startCalendar.getTime();
    }

    /**
     * 获取Date所在月份的起始时间
     * 
     * @param date
     * @return
     */
    public static Date getStartOfMonth(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, -12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        date = cal.getTime();
        return date;
    }

    /**
     * 获取当天的起始时间
     * 
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR, -12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        date = cal.getTime();
        return date;
    }

    /**
     * 获取date所在天的最后时间
     * 
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        date = cal.getTime();
        return date;
    }

    /**
     * 获取date所在月份最后的时间
     * 
     * @param date
     * @return
     */
    public static Date getEndOfMonth(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);// 精确到毫秒
        date = cal.getTime();
        return date;
    }

    /**
     * 按照给定的日期字符串及格式生成一个Calendar类型的日期对象
     * 
     * @param date
     *            日期字符串 如：2010-07-10 18:00:00
     * @param format
     *            日期格式 如：yyyy-MM-dd HH:mm:ss
     */
    public static Calendar gc(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar d = Calendar.getInstance();
        d.setTime(sdf.parse(date));
        return d;
    }

    /**
     * 比较两个时间，精确到分
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        calendar1.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);
        long diff = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
        int minute = (int) (diff / (1000 * 60));
        if (minute > 0) {
            return 1;
        } else if (minute == 0) {
            return 0;
        }
        return -1;
    }

    public static Date getLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);// 7天前的日期时间
        return calendar.getTime();
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        Date date = dateFormat.parse("20130108080000");

        //        System.out.println(date1);
        //        //Date date2 = gc("2012-09-05 11:10:02", "yyyy-MM-dd hh:mm:ss").getTime();
        //        System.out.println(getEndOfMonth(date1));
        System.out.println(getSendDate(date, "08:00:00-18:00:00"));
        System.out.println(date);
        System.out.println(getSendDate(date, "08:00:00-18:00:00"));
        System.out.println(date);
    }
}
