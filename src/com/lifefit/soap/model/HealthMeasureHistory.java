package com.lifefit.soap.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.lifefit.soap.dao.LifeFitDao;

@Entity
@Table(name="healthmeasurehistory")
@NamedQueries({
		@NamedQuery(name="HealthMeasureHistory.findAll", query="SELECT h FROM HealthMeasureHistory h"),
		@NamedQuery(name="HealthMeasureHistory.getMeasureByPersonId", query="SELECT h FROM HealthMeasureHistory h "
				+ "WHERE h.person.idPerson = :idPerson AND h.measure.idMeasure = :idMeasure")
})
@XmlAccessorType(XmlAccessType.FIELD)

public class HealthMeasureHistory implements Serializable {
	private final static long serialVersionUID = 1L;
	
	@Id
	@Column
	@XmlElement
	private int idLog;
	@Column
	@XmlElement
	private double value;
	@Column
	@XmlElement
	private String datetime;
	@ManyToOne
	@JoinColumn(name="idPerson", referencedColumnName="idPerson")
	private Person person;
	@ManyToOne
	@JoinColumn(name="idMeasure", referencedColumnName="idMeasure")
	private Measure measure;
	
	public HealthMeasureHistory(){}

	public int getIdLog() {
		return idLog;
	}

	public void setIdLog(int idLog) {
		this.idLog = idLog;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}
	
	//Database operations
	public static HealthMeasureHistory getHealthMeasureHistoryById(int idLog) {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
		HealthMeasureHistory p = em.find(HealthMeasureHistory.class, idLog);
		LifeFitDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<HealthMeasureHistory> getAll() {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
	    List<HealthMeasureHistory> list = em.createNamedQuery("HealthMeasureHistory.findAll", 
	    		HealthMeasureHistory.class).getResultList();
	    LifeFitDao.instance.closeConnections(em);
	    return list;
	}
	
	public static List<HealthMeasureHistory> getMeasureByPersonId(int personId, int idMeasure) {
		List<HealthMeasureHistory> list = null;
		EntityManager em = null;
		
		try{
			em = LifeFitDao.instance.createEntityManager();
			//Refresh the entity manager
	        em.getEntityManagerFactory().getCache().evictAll();
	        
		    list = em.createNamedQuery("HealthMeasureHistory.getMeasureByPersonId", HealthMeasureHistory.class)
		    		.setParameter("idPerson", personId)
		    		.setParameter("idMeasure", idMeasure)
		    		.getResultList();
		}
		catch(Exception e){}
		finally{
			LifeFitDao.instance.closeConnections(em);
		}
				  
	    return list;
	}
	
	public static HealthMeasureHistory saveHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static HealthMeasureHistory updateHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	} 

}
