package com.lifefit.soap.model;

import com.lifefit.soap.dao.LifeFitDao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name="measure")
@NamedQueries({
	@NamedQuery(name="Measure.findAll", query="SELECT m FROM Measure m"),
	@NamedQuery(name="Measure.getMeasureByName", query="SELECT m FROM Measure m "
			+ "WHERE LOWER(m.measureName) = :measureName"),
	@NamedQuery(name="Measure.getMeasureGoal", query="SELECT m FROM Measure m "
			+ "WHERE m.goalFlag = 'Y'")
})
@XmlAccessorType(XmlAccessType.NONE)
public class Measure implements Serializable {
	private final static long serialVersionUID = 1L;
	
	@Id
	@Column
	@XmlElement
	private int idMeasure;
	@Column
	@XmlElement
	private String measureName;
	@Column
	@XmlElement
	private String measureType;
	@Column
	@XmlElement
	private String goalFlag;
	
	public Measure(){}

	public Measure(int idMeasure){
		this.idMeasure = idMeasure;
	}
	
	public int getIdMeasure() {
		return idMeasure;
	}

	public void setIdMeasure(int idMeasure) {
		this.idMeasure = idMeasure;
	}

	public String getMeasureName() {
		return measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	public String getMeasureType() {
		return measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}
	
	public String getGoalFlag() {
		return goalFlag;
	}

	public void setGoalFlag(String goalFlag) {
		this.goalFlag = goalFlag;
	}

	//Database operations
	public static Measure getMeasureById(int personId) {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
		Measure p = em.find(Measure.class, personId);
		LifeFitDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<Measure> getAll() {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
	    List<Measure> list = em.createNamedQuery("Measure.findAll", Measure.class).getResultList();
	    LifeFitDao.instance.closeConnections(em);
	    return list;
	}
	
	public static List<Measure> getMeasureGoal() {
		EntityManager em = null;
		List<Measure> list = null;
		
		try{
			em = LifeFitDao.instance.createEntityManager();		
			//Refresh the entity manager
	        em.getEntityManagerFactory().getCache().evictAll();
	        
		    list = em.createNamedQuery("Measure.getMeasureGoal", Measure.class).getResultList();
		}
		catch(Exception e){}
		finally{
			LifeFitDao.instance.closeConnections(em);
		}	
	    return list;
	}
	
	public static Measure getMeasureByName(String measureName) {
		EntityManager em = null;
		Measure measureDef = null;
		
		try{
			em = LifeFitDao.instance.createEntityManager();			
			//Refresh the entity manager
	        em.getEntityManagerFactory().getCache().evictAll();
	        
			measureDef = em.createNamedQuery("Measure.getMeasureByName", Measure.class)
		    		.setParameter("measureName", measureName)
		    		.getSingleResult();
		}
		catch(Exception e){}
		finally{
			LifeFitDao.instance.closeConnections(em);
		}
	    return measureDef;
	}
	
	public static Measure saveMeasure(Measure p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static Measure updateMeasure(Measure p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeMeasure(Measure p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	}
}
