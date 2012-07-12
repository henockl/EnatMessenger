package org.sis.ancmessaging.enums;

import java.util.ArrayList;
import java.util.List;

public class EthiopianMonth {
	private String name;
	private int number;
	
	public EthiopianMonth() {
		
	}
	
	private EthiopianMonth(String name, int number) {
		this.name = name;
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	public static List<EthiopianMonth> getEthiopianMonths() {
		List<EthiopianMonth> months = new ArrayList<EthiopianMonth>();
		months.add(new EthiopianMonth("Meskerem", 1));
		months.add(new EthiopianMonth("Tikimt", 2));
		months.add(new EthiopianMonth("Hidar", 3));
		months.add(new EthiopianMonth("Tahsas", 4));
		months.add(new EthiopianMonth("Tir", 5));
		months.add(new EthiopianMonth("Yekatit", 6));
		months.add(new EthiopianMonth("Megabit", 7));
		months.add(new EthiopianMonth("Miazia", 8));
		months.add(new EthiopianMonth("Ginbot", 9));
		months.add(new EthiopianMonth("Sene", 10));
		months.add(new EthiopianMonth("Hamle", 11));
		months.add(new EthiopianMonth("Nehase", 12));
		months.add(new EthiopianMonth("Pagume", 13));
		
		return months;
	}
}
