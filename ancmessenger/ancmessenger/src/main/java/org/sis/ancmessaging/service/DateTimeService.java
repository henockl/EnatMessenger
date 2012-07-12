package org.sis.ancmessaging.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.chrono.EthiopicChronology;
import org.joda.time.chrono.GregorianChronology;
import org.springframework.stereotype.Service;

@Service
public class DateTimeService {
	public Date parseAmharicDateString(String dateString) {
		int start;
		int index = dateString.indexOf('/');
		
		int date = Integer.parseInt(dateString.substring(0, index));
		start = index + 1;
		index = dateString.indexOf('/', start);
		int month = Integer.parseInt(dateString.substring(start, index));
		
		start = index + 1;
		int year = Integer.parseInt(dateString.substring(start));
		
		DateTime eddEthiopicDate = new DateTime(year, month, date, 12, 0, EthiopicChronology.getInstance());
		DateTime eddDate = eddEthiopicDate.withChronology(GregorianChronology.getInstance());
		
		return eddDate.toDate();
	}
	
		
	public String convertToEthiopicString(Date gregDate) {
		DateTime dateInGreg = new DateTime(gregDate);
		DateTime dateInEthiopic = dateInGreg.withChronology(EthiopicChronology.getInstance());
		return dateInEthiopic.getDayOfMonth() + "/" + dateInEthiopic.getMonthOfYear() 
				+ "/" + dateInEthiopic.getYear();
	}
}
