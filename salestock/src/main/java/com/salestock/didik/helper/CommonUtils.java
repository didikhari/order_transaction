package com.salestock.didik.helper;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class CommonUtils {

	public static String generateUUID(){
		return UUID.randomUUID().toString();
	}
	
	public static Timestamp getCurrentDateTime() {
		Calendar calendar = getJakartaCalendar();
		return new Timestamp(calendar.getTimeInMillis());
	}

	private static Calendar getJakartaCalendar() {
		TimeZone zone = TimeZone.getTimeZone("Asia/Jakarta");
		Calendar calendar = Calendar.getInstance(zone , new Locale("in", "ID"));
		return calendar;
	}

	public static Date getNextMinute(Long minute){
		return new Date(System.currentTimeMillis() + (minute * 60 * 1000));
	}
	
	public static Date getCurrentDateWithCustomTime(int hour, int minute, int second){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}
	
}
