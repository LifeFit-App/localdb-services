package com.lifefit.soap.ws;

import java.util.List;

import com.lifefit.soap.model.Goal;
import com.lifefit.soap.model.HealthMeasureHistory;
import com.lifefit.soap.model.LifeStatus;
import com.lifefit.soap.model.Measure;
import com.lifefit.soap.model.Person;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface LifeFit {
	@WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") int personId);
	
	@WebMethod(operationName="authenticateUser")
	@WebResult(name="person")
	public Person getPersonByEmailPass(@WebParam(name="email") String email,
			@WebParam(name="pass") String pass);
			
	@WebMethod(operationName="getPeopleList")
    @WebResult(name="people") 
    public List<Person> getPeople();
	
	@WebMethod(operationName="getMeasureTypeList")
	@WebResult(name="measureTypes")
	public List<Measure> readMeasureTypes();
	
	@WebMethod(operationName="getMeasureByName")
	@WebResult(name="measure")
	public Measure getMeasureByName(@WebParam(name="measureName")String measureName);
	
	@WebMethod(operationName="getPersonGoal")
	@WebResult(name="goal")
	public Goal getPersonGoal(@WebParam(name="personId") int personId);
	
	@WebMethod(operationName="updatePersonGoal")
	@WebResult(name="status")
	public boolean updatePersonGoal(@WebParam(name="goal") Goal goal,
			@WebParam(name="personId") int personId);
	
	@WebMethod(operationName="savePersonHealthMeasure")
	@WebResult(name="status")
	public boolean savePersonHealthMeasure(@WebParam(name="lifeStatus") LifeStatus lifeStatus,
			@WebParam(name="personId") int personId);
	
	@WebMethod(operationName="updatePersonHealthMeasure")
	@WebResult(name="status")
	public boolean updatePersonHealthMeasure(@WebParam(name="lifeStatus") LifeStatus lifeStatus,
			@WebParam(name="personId") int personId);
	
	@WebMethod(operationName="deletePersonHealthMeasure")
	@WebResult(name="status")
	public boolean deletePersonHealthMeasure(@WebParam(name="lifeStatus") LifeStatus lifeStatus,
			@WebParam(name="personId") int personId);	
	
	@WebMethod(operationName="getHealthMeasureHistory")
	@WebResult(name="healthMeasureHistory")
	public List<HealthMeasureHistory> getPersonHealthMeasureHistory(@WebParam(name="personId") int personId,
			@WebParam(name="measureId") int measureId);
	
	@WebMethod(operationName="saveHealthMeasureHistory")
	@WebResult(name="status")
	public boolean savePersonHealthMeasureHistory(@WebParam(name="healthMeasureHistory") 
			HealthMeasureHistory healthMeasureHistory);
	
	@WebMethod(operationName="getPersonHealthMeasureById")
	@WebResult(name="healthMeasure")
	public LifeStatus getPersonHealthMeasureById(@WebParam(name="personId") int personId,
			@WebParam(name="measureId") int measureId);
}
