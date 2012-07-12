function getEthiopianDaysForMonth(month, leapYear) {
	var i;
	var days = new Array();
    if (month >= 1 && month < 13) {
        for (i = 1; i <= 30; i++) {
            days.push(i);
        }
    } else if (month == 13) {
        for (i = 1; i <= 5; i++) {
            days.push(i);
        }
        if (leapYear) {
            days.push(6);
        }
    }
    return days;
}


function getGregorianDaysForMonth(month, leapYear) {
    var days = new Array();
    var i;
    if (month == 4 || month == 6 || month == 9 || month == 11) {
        for (i = 1; i <= 30; i++) {
            days.push(i);
        }
    } else if (month == 2) {
        for (i = 1; i <= 28; i++) {
            days.push(i);
        }
        if (leapYear) {
            days.push(29);
        }
    } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
               || month == 10 || month == 12) {
        for (i = 1; i <= 31; i++) {
            days.push(i);
        }
    }
    return days;
}


function getEthiopianMonths() {
    var months = { "EthiopianMonths": [
                                {
                                    "Name": "Meskerem",
                                    "Number": 1
                                },
                                {
                                    "Name": "Tikimt",
                                    "Number": 2
                                },
                                {
                                    "Name": "Hidar",
                                    "Number": 3
                                },
                                {
                                    "Name": "Tahsas",
                                    "Number": 4
                                },
                                {
                                    "Name": "Tir",
                                    "Number": 5
                                },
                                {
                                    "Name": "Yekatit",
                                    "Number": 6
                                },
                                {
                                    "Name": "Megabit",
                                    "Number": 7
                                },
                                {
                                    "Name": "Miazia",
                                    "Number": 8
                                },
                                {
                                    "Name": "Ginbot",
                                    "Number": 9
                                },
                                {
                                    "Name": "Sene",
                                    "Number": 10
                                },
                                {
                                    "Name": "Hamle",
                                    "Number": 11
                                },
                                {
                                    "Name": "Nehase",
                                    "Number": 12
                                },
                                {
                                    "Name": "Pagume",
                                    "Number": 13
                                }
                            ]
    };
    return months.EthiopianMonths;
}


function getGregorianMonths() {
    var months = { "GregorianMonths": [
                                {
                                    "Name": "January",
                                    "Number": 1
                                },
                                {
                                    "Name": "February",
                                    "Number": 2
                                },
                                {
                                    "Name": "March",
                                    "Number": 3
                                },
                                {
                                    "Name": "April",
                                    "Number": 4
                                },
                                {
                                    "Name": "May",
                                    "Number": 5
                                },
                                {
                                    "Name": "June",
                                    "Number": 6
                                },
                                {
                                    "Name": "July",
                                    "Number": 7
                                },
                                {
                                    "Name": "August",
                                    "Number": 8
                                },
                                {
                                    "Name": "September",
                                    "Number": 9
                                },
                                {
                                    "Name": "October",
                                    "Number": 10
                                },
                                {
                                    "Name": "November",
                                    "Number": 11
                                },
                                {
                                    "Name": "December",
                                    "Number": 12
                                }
                            ]
    };
    return months.GregorianMonths;
}


function getEthiopianYears() {
	var i;
    var years = new Array();
    for (i = 1900; i <= 2020; i++) {
        years.push(i);
    }
    return years;
}


function getGregorianYears() {
	var i;
    var years = new Array();
    for (i = 1907; i <= 2028; i++) {
        years.push(i);
    }
    return years;
}

function calculateExpectedDateOfDelivery(lmpDate, lmpMonth, lmpYear) {
	flagdate = 0;
	flagmonth = 0;
	var date;
	var month;
	var year = lmpYear;
	if(lmpMonth == 1 || lmpMonth == 2 || lmpMonth == 3) {
		if(lmpDate + 10 > 30){
			date = (lmpDate + 10 ) - 30;
			flagdate = 1;	
		} else {
			date = lmpDate + 10;
		}
		
		if (flagdate != 0) {
			month = lmpMonth + 10;
		} else {
			month = lmpMonth + 9;
		}
		
	} else {
		if (lmpDate + 5 > 30) {
			date = (lmpDate + 5) - 30;
			flagdate = 1;
		} else {
			date = lmpDate + 5;
		}
		if (flagdate != 0) {
			month = lmpMonth - 2;
		} else {
			month = lmpMonth - 3;
		}
		year = lmpYear + 1;
	}
	expectedDD = date + "/"+ month + "/" + year;

	return expectedDD;
}