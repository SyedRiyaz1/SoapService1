package restassured.apacheaxis2.integration;

import java.util.List;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RESTFulServiceComponentTesting
{
	public static void main(String[] args) throws Exception
	{
		//Register end point of restful webservice
		RestAssured.baseURI="https://date.nager.at/Api/v2/PublicHolidays";
		//Create HTTP request and get response
		RequestSpecification req=RestAssured.given();
		Response res=req.request(Method.GET,"/2020/us");
		String ctype=res.getHeader("Content-Type");
		if(ctype.contains("json"))
		{
			JsonPath jp=res.jsonPath();
			List<String> restres=jp.getList("date");
			for(int i=0;i<restres.size();i++)
			{
				System.out.println(restres.get(i));
			}
		}
	}
}
