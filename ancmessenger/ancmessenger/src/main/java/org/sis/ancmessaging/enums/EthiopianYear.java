package org.sis.ancmessaging.enums;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.chrono.EthiopicChronology;

public class EthiopianYear {
	private int year;
	public static List<Integer> getYears() {
		DateTime date = new DateTime();
		DateTime ethiopicDate = date.withChronology(EthiopicChronology.getInstance());
		int currentYear = ethiopicDate.getYear();
		List<Integer> years = new ArrayList<Integer>();
		years.add(currentYear - 1);
		years.add(currentYear);
		return years;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
}
