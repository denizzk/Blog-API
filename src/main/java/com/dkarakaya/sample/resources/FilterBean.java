package com.dkarakaya.sample.resources;

import javax.ws.rs.QueryParam;

public class FilterBean {

	private @QueryParam("day") int day;
	private @QueryParam("year") int year;
	private @QueryParam("month") int month;
	private @QueryParam("postedToday") boolean postedToday;
	private @QueryParam("start") int start;
	private @QueryParam("size") int size;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public boolean isPostedToday() {
		if (postedToday)
			return true;
		return false;
	}

	public void setPostedToday(boolean postedToday) {
		this.postedToday = postedToday;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
