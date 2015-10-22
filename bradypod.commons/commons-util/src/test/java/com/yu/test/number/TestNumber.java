package com.yu.test.number;

import java.text.NumberFormat;
import java.util.Calendar;

public class TestNumber {

	public static void main(String[] args) {
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumIntegerDigits(20);
		formatter.setGroupingUsed(false);
		String sp = "/";
		String nstr = formatter.format(1000000000);
		System.out.println(nstr);
		nstr = nstr.substring(0, 2) + sp + nstr.substring(2, 5) + sp + nstr.substring(5, 8) + sp
				+ nstr.substring(8, 11) + sp + nstr.substring(11, 14) + sp + nstr.substring(14, 17)
				+ sp + nstr.substring(17, 20);
		System.out.println(nstr);
		
		String curDateStr;
		Calendar cal = Calendar.getInstance();
		int year = cal.get( Calendar.YEAR );
		int month = cal.get( Calendar.MONTH ) + 1;
		int day = cal.get( Calendar.DAY_OF_MONTH );
		curDateStr = String.valueOf( year ) + "/";
		curDateStr += ( ( month < 10 ) ? "0" + String.valueOf( month ) : String.valueOf( month ) ) + "/";
		curDateStr += ( ( day < 10 ) ? "0" + String.valueOf( day ) : String.valueOf( day ) );
		
		System.out.println(curDateStr);
	}

}
