/**
 * EnricoCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT)
 */
package restassured.apacheaxis2.integration;


/**
 *  EnricoCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class EnricoCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public EnricoCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public EnricoCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for getHolidaysForDateRange method
     * override this method for handling normal response from getHolidaysForDateRange operation
     */
    public void receiveResultgetHolidaysForDateRange(
        restassured.apacheaxis2.integration.EnricoStub.GetHolidaysForDateRangeResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getHolidaysForDateRange operation
     */
    public void receiveErrorgetHolidaysForDateRange(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for isWorkDay method
     * override this method for handling normal response from isWorkDay operation
     */
    public void receiveResultisWorkDay(
        restassured.apacheaxis2.integration.EnricoStub.IsWorkDayResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from isWorkDay operation
     */
    public void receiveErrorisWorkDay(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getSupportedCountries method
     * override this method for handling normal response from getSupportedCountries operation
     */
    public void receiveResultgetSupportedCountries(
        restassured.apacheaxis2.integration.EnricoStub.GetSupportedCountriesResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getSupportedCountries operation
     */
    public void receiveErrorgetSupportedCountries(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for isPublicHoliday method
     * override this method for handling normal response from isPublicHoliday operation
     */
    public void receiveResultisPublicHoliday(
        restassured.apacheaxis2.integration.EnricoStub.IsPublicHolidayResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from isPublicHoliday operation
     */
    public void receiveErrorisPublicHoliday(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for whereIsPublicHoliday method
     * override this method for handling normal response from whereIsPublicHoliday operation
     */
    public void receiveResultwhereIsPublicHoliday(
        restassured.apacheaxis2.integration.EnricoStub.WhereIsPublicHolidayResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from whereIsPublicHoliday operation
     */
    public void receiveErrorwhereIsPublicHoliday(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getHolidaysForYear method
     * override this method for handling normal response from getHolidaysForYear operation
     */
    public void receiveResultgetHolidaysForYear(
        restassured.apacheaxis2.integration.EnricoStub.GetHolidaysForYearResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getHolidaysForYear operation
     */
    public void receiveErrorgetHolidaysForYear(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getHolidaysForMonth method
     * override this method for handling normal response from getHolidaysForMonth operation
     */
    public void receiveResultgetHolidaysForMonth(
        restassured.apacheaxis2.integration.EnricoStub.GetHolidaysForMonthResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getHolidaysForMonth operation
     */
    public void receiveErrorgetHolidaysForMonth(java.lang.Exception e) {
    }
}
