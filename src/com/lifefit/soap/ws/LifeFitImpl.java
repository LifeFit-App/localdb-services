package com.lifefit.soap.ws;

import java.util.List;

import com.lifefit.soap.model.Goal;
import com.lifefit.soap.model.HealthMeasureHistory;
import com.lifefit.soap.model.LifeStatus;
import com.lifefit.soap.model.Measure;
import com.lifefit.soap.model.Person;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface = "com.lifefit.soap.ws.LifeFit",
serviceName="LifeFitService")
public class LifeFitImpl implements LifeFit {

	@Override
	public Person readPerson(int id) {
		System.out.println("---> Reading Person by id = "+id);
		Person person = Person.getPersonById(id);
		if (person!=null) {
			System.out.println("---> Found Person by id = "+id+" => "+person.getFirstname()
					+" "+person.getLastname());
		} else {
			System.out.println("---> Didn't find any Person with  id = "+id);
		}		
		return person;
	}
	
	@Override
	public List<Person> getPeople() {
		System.out.println("---> Getting list of people...");
		return Person.getAll();
	}
	
	@Override
	public List<Measure> readMeasureTypes(){
		System.out.println("---> Getting list of measureTypes...");
        List<Measure> measureType = Measure.getAll();
        return measureType;
	}
	
	@Override
	public Measure getMeasureByName(@WebParam(name="measureName")String measureName){
		System.out.println("---> Get measureTypes with name = "+ measureName);
		
		Measure measure = Measure.getMeasureByName(measureName);
		return measure;
	}
	
	@Override
	public Goal getPersonGoal(int personId){
		System.out.println("---> Getting Goal of a person with personId = "+personId);
		Goal goal = Goal.getGoalByPersonId(personId);
		return goal;
	}
	
	@Override
	public boolean updatePersonGoal(Goal goal, int personId){
		System.out.println("---> Updating goal with goalId = "+goal.getIdGoal());
		
		Goal existingGoal = Goal.getGoalById(goal.getIdGoal());
		if(existingGoal == null){
			return false;
		}
		else {
			Person person = Person.getPersonById(personId);
			Measure measure = Measure.getMeasureById(goal.getMeasure().getIdMeasure());
			goal.setPerson(person);
			goal.setMeasure(measure);
						
			Goal.updateGoal(goal);
			return true;
		}
	}
	
	public boolean savePersonHealthMeasure(LifeStatus lifeStatus, int personId){
		System.out.println("---> Save new LifeStatus with personId = "+ personId);
		
		LifeStatus ls = LifeStatus.saveLifeStatus(lifeStatus);
		if(ls != null)
			return true;
		else
			return false;
	}
	
	public boolean updatePersonHealthMeasure(LifeStatus lifeStatus, int personId){
		System.out.println("---> Update LifeStatus with personId = "+ personId
				+ " and idStatus = "+lifeStatus.getIdStatus());
		
		LifeStatus ls = LifeStatus.updateLifeStatus(lifeStatus);
		if(ls != null)
			return true;
		else
			return false;
	}
	
	public boolean deletePersonHealthMeasure(LifeStatus lifeStatus, int personId){
		System.out.println("---> Delete LifeStatus with personId = "+ personId
				+ " and idStatus = "+lifeStatus.getIdStatus());
		
		if(LifeStatus.removeLifeStatus(lifeStatus))
			return true;
		else
			return false;
	}
	
	@Override
	public List<HealthMeasureHistory> getPersonHealthMeasureHistory(int personId, int measureId){		
		System.out.println("---> Reading HealthMeasureHistory with person id = "+
				personId+" and measure id = "+ measureId);
    		
    	List<HealthMeasureHistory> healthMeasureHistory = HealthMeasureHistory.getMeasureByPersonId(personId, measureId);      
        return healthMeasureHistory;       
	}
	
	@Override
	public boolean savePersonHealthMeasureHistory(HealthMeasureHistory healthMeasure){		
		System.out.println("---> Save HealthMeasureHistory with person id = "
					+healthMeasure.getPerson().getIdPerson());
		
		HealthMeasureHistory hMeasure = HealthMeasureHistory.saveHealthMeasureHistory(healthMeasure);
		if(hMeasure != null)
			return true;
		else
			return false;
	}
	
	@Override
	public Person getPersonByEmailPass(String email, String pass){
		System.out.println("---> Authenticate user with email = "+ email);
		Person person = Person.getPersonByEmailPass(email, pass);
		return person;
	}
	
	public LifeStatus getPersonHealthMeasureById(int personId, int measureId){
		System.out.println("---> Get PersonHealthMeasure with personId ="+personId
				+" and measureId= "+ measureId);
		
		LifeStatus lifeStatus = LifeStatus.getByMeasure(personId, measureId);
		return lifeStatus;
	}
}  
