package com.jpm.fx.tradereport.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DefaultCalendar extends BaseCalendar {

	/**
	 * Method to check holidays based on calendar
	 */
	@Override
	public boolean isHoliday(LocalDate currentDay) {
		return (currentDay.getDayOfWeek().name().equals(DayOfWeek.SUNDAY.name())  
				|| currentDay.getDayOfWeek().name().equals(DayOfWeek.SATURDAY.name()));
	}

}
