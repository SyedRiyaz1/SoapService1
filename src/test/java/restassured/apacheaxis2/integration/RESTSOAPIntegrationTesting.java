package restassured.apacheaxis2.integration;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import restassured.apacheaxis2.integration.EnricoStub.DateType;
import restassured.apacheaxis2.integration.EnricoStub.GetHolidaysForYear;
import restassured.apacheaxis2.integration.EnricoStub.GetHolidaysForYearResponse;
import restassured.apacheaxis2.integration.EnricoStub.HolidayCollectionType;
import restassured.apacheaxis2.integration.EnricoStub.HolidayType;

public class RESTSOAPIntegrationTesting
{
	public static void main(String[] args) throws Exception
	{
		//Soap Service
		//Create object to stub class
		EnricoStub stub=new EnricoStub();
		//Create request to soap based service
		GetHolidaysForYear soapreq=new GetHolidaysForYear();
		BigInteger bi=BigInteger.valueOf(2020);
		soapreq.setYear(bi);
		soapreq.setCountry("usa");
		soapreq.setRegion("mn");
		soapreq.setHolidayType("public_holiday");
		//Get Response
		GetHolidaysForYearResponse soapres=stub.getHolidaysForYear(soapreq);
		HolidayCollectionType hct=soapres.getHolidays();
		ArrayList<String> soapresult = null;
		try
		{
			HolidayType[] ht=hct.getHoliday();
			soapresult=new ArrayList<String>();
			System.out.println("SOAP result is:");
			for(int i=0;i<ht.length;i++)
			{
				DateType dt=ht[i].getDate();
				if(dt.getMonth()<10 && dt.getDay()<10)
				{
					soapresult.add(dt.getYear()+"-0"+dt.getMonth()+"-0"+dt.getDay());
				}
				else if(dt.getMonth()>10 && dt.getDay()<10)
				{
					soapresult.add(dt.getYear()+"-"+dt.getMonth()+"-0"+dt.getDay());
				}
				else if(dt.getMonth()<10 && dt.getDay()>10)
				{
					soapresult.add(dt.getYear()+"-0"+dt.getMonth()+"-"+dt.getDay());
				}
				else
				{
					soapresult.add(dt.getYear()+"-"+dt.getMonth()+"-"+dt.getDay());
				}
				System.out.println(soapresult.get(i));
			}	
		}
		catch(Exception ex)
		{
			System.out.println("No Holiday Type");
		}
		
		//Restful service
		//Register end point of restful webservice
		RestAssured.baseURI="https://date.nager.at/Api/v2/PublicHolidays";
		//Create HTTP request and get response
		RequestSpecification restreq=RestAssured.given();
		Response restres=restreq.request(Method.GET,"/2020/us");
		JsonPath jp=restres.jsonPath();
		List<String> restresult=jp.getList("date");
		System.out.println("Rest result is:");
		for(int i=0;i<restresult.size();i++)
		{
			System.out.println(restresult.get(i));
		}
		
		//Validations
		//Way 1
		if(soapresult.equals(restresult))
		{
			System.out.println("Integration Test Passed");
		}
		else
		{
			System.out.println("Integration Test Failed");
		}
		
		//Way 2
		/*if(soapresult.size()==restresult.size())
		{
			int flag=0;
			for(int i=0;i<soapresult.size();i++)
			{
				if(soapresult.get(i).equals(restresult.get(i)))
				{
					continue;
				}
				else
				{
					flag=1;
				}
			}
			if(flag==0)
			{
				System.out.println("Integration Test Passed");
			}
			else
			{
				System.out.println("Integration Test Failed");
			}
		}*/
	}
}
