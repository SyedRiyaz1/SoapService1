package restassured.apacheaxis2.integration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

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

public class RESTSOAPIntegrationTestingExcel
{
	public static void main(String[] args) throws Exception
	{
		//Take date from excel
		File f=new File("webservicesintegrationtestdata.xlsx");
		FileInputStream fi=new FileInputStream(f);
		Workbook wb=WorkbookFactory.create(fi);
		Sheet sh=wb.getSheet("Sheet1");
		int nour=sh.getPhysicalNumberOfRows();
		int nouc=sh.getRow(0).getLastCellNum();
		//Create results column
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date date=new Date();
		String cname=sf.format(date);
		sh.getRow(0).createCell(nouc).setCellValue("Result on "+cname);
		//Font settings for Headings
		Font font1=wb.createFont();
		font1.setColor(IndexedColors.BLUE.getIndex());
	    font1.setItalic(true);
	    font1.setBold(true);
	    //Cell Style settings for Headings
		CellStyle cs1=wb.createCellStyle();
		cs1.setFont(font1);
		cs1.setAlignment(HorizontalAlignment.CENTER);
		sh.getRow(0).getCell(nouc).setCellStyle(cs1);
		//Data driven
		//Read data from excel(from row 2(index=1))
		for(int i=1;i<nour;i++)
		{
			DataFormatter df=new DataFormatter();
			int year=Integer.parseInt(df.formatCellValue(sh.getRow(i).getCell(0)));
			String scountry=df.formatCellValue(sh.getRow(i).getCell(1));
			String sregion=df.formatCellValue(sh.getRow(i).getCell(2));
			String sholidaytype=df.formatCellValue(sh.getRow(i).getCell(3));
			String rcountry=df.formatCellValue(sh.getRow(i).getCell(4));
			
			
			//Soap Service
			//Create object to stub class
			EnricoStub stub=new EnricoStub();
			//Create request to soap based service
			GetHolidaysForYear soapreq=new GetHolidaysForYear();
			BigInteger bi=BigInteger.valueOf(year);
			soapreq.setYear(bi);
			soapreq.setCountry(scountry);
			soapreq.setRegion(sregion);
			soapreq.setHolidayType(sholidaytype);
			//Get Response
			GetHolidaysForYearResponse soapres=stub.getHolidaysForYear(soapreq);
			HolidayCollectionType hct=soapres.getHolidays();
			ArrayList<String> soapresult = null;
			try
			{
				HolidayType[] ht=hct.getHoliday();
				soapresult=new ArrayList<String>();
				System.out.println("SOAP result is:");
				for(int j=0;j<ht.length;j++)
				{
					DateType dt=ht[j].getDate();
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
					System.out.println(soapresult.get(j));
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
			Response restres=restreq.request(Method.GET,"/"+year+"/"+rcountry);
			JsonPath jp=restres.jsonPath();
			List<String> restresult=jp.getList("date");
			System.out.println("Rest result is:");
			for(int k=0;k<restresult.size();k++)
			{
				System.out.println(restresult.get(k));
			}
			
			//Validations
			//Way 1
			if(soapresult.equals(restresult))
			{
				sh.getRow(i).createCell(nouc).setCellValue("Integration Test Passed");
				//Font settings for Test Result
				Font font2=wb.createFont();
				font2.setColor(IndexedColors.GREEN.getIndex());
			    font2.setItalic(true);
			    //Cell Style settings for Test Result
				CellStyle cs2=wb.createCellStyle();
				cs2.setFont(font2);
				cs2.setAlignment(HorizontalAlignment.CENTER);
				sh.getRow(i).getCell(nouc).setCellStyle(cs2);
			}
			else
			{
				sh.getRow(i).createCell(nouc).setCellValue("Integration Test Failed");
				//Font settings for Test Result
				Font font2=wb.createFont();
				font2.setColor(IndexedColors.RED.getIndex());
			    font2.setItalic(true);
			    //Cell Style settings for Test Result
				CellStyle cs2=wb.createCellStyle();
				cs2.setFont(font2);
				cs2.setAlignment(HorizontalAlignment.CENTER);
				sh.getRow(i).getCell(nouc).setCellStyle(cs2);
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
						break;
					}
				}
				if(flag==0)
				{
					sh.getRow(i).createCell(nouc).setCellValue("Integration Test Passed");
					//Font settings for Test Result
					Font font2=wb.createFont();
					font2.setColor(IndexedColors.GREEN.getIndex());
			    	font2.setItalic(true);
			    	//Cell Style settings for Test Result
					CellStyle cs2=wb.createCellStyle();
					cs2.setFont(font2);
					cs2.setAlignment(HorizontalAlignment.CENTER);
					sh.getRow(i).getCell(nouc).setCellStyle(cs2);	
				}
				else
				{
					sh.getRow(i).createCell(nouc).setCellValue("Integration Test Failed");
					//Font settings for Test Result
					Font font2=wb.createFont();
					font2.setColor(IndexedColors.RED.getIndex());
			    	font2.setItalic(true);
			    	//Cell Style settings for Test Result
					CellStyle cs2=wb.createCellStyle();
					cs2.setFont(font2);
					cs2.setAlignment(HorizontalAlignment.CENTER);
					sh.getRow(i).getCell(nouc).setCellStyle(cs2);
				}
			}
			else
			{
				sh.getRow(i).createCell(nouc).setCellValue("Integration Test Failed-->Array size is not matching");
				//Font settings for Test Result
				Font font2=wb.createFont();
				font2.setColor(IndexedColors.RED.getIndex());
			    font2.setItalic(true);
			    //Cell Style settings for Test Result
				CellStyle cs2=wb.createCellStyle();
				cs2.setFont(font2);
				cs2.setAlignment(HorizontalAlignment.CENTER);
				sh.getRow(i).getCell(nouc).setCellStyle(cs2);
			}*/
		}
		
		sh.autoSizeColumn(nouc);
		
		//Save and close excel
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		fo.close();
		fi.close();
		wb.close();
	}
}
