package restassured.apacheaxis2.integration;

import java.math.BigInteger;
import java.util.ArrayList;

import restassured.apacheaxis2.integration.EnricoStub.DateType;
import restassured.apacheaxis2.integration.EnricoStub.GetHolidaysForYear;
import restassured.apacheaxis2.integration.EnricoStub.GetHolidaysForYearResponse;
import restassured.apacheaxis2.integration.EnricoStub.HolidayCollectionType;
import restassured.apacheaxis2.integration.EnricoStub.HolidayType;

public class SOAPServiceComponentTesting
{
	public static void main(String[] args) throws Exception
	{
		//Create object to stub class
		EnricoStub stub=new EnricoStub();
		//Create request to soap based service
		GetHolidaysForYear req=new GetHolidaysForYear();
		BigInteger bi=BigInteger.valueOf(2020);
		req.setYear(bi);
		req.setCountry("usa");
		req.setRegion("mn");
		req.setHolidayType("public_holiday");
		//Get Response
		GetHolidaysForYearResponse res=stub.getHolidaysForYear(req);
		HolidayCollectionType hct=res.getHolidays();
		try
		{
			HolidayType[] ht=hct.getHoliday();
			ArrayList<String> soapres=new ArrayList<String>();
			for(int i=0;i<ht.length;i++)
			{
				DateType dt=ht[i].getDate();
				if(dt.getMonth()<10 && dt.getDay()<10)
				{
					soapres.add(dt.getYear()+"-0"+dt.getMonth()+"-0"+dt.getDay());
				}
				else if(dt.getMonth()>10 && dt.getDay()<10)
				{
					soapres.add(dt.getYear()+"-"+dt.getMonth()+"-0"+dt.getDay());
				}
				else if(dt.getMonth()<10 && dt.getDay()>10)
				{
					soapres.add(dt.getYear()+"-0"+dt.getMonth()+"-"+dt.getDay());
				}
				else
				{
					soapres.add(dt.getYear()+"-"+dt.getMonth()+"-"+dt.getDay());
				}
				System.out.println(soapres.get(i));
			}	
		}
		catch(Exception ex)
		{
			System.out.println("No Holiday Type");
		}	
	}
}
