package com.lifefit.soap.model;

import com.lifefit.soap.dao.LifeFitDao;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name="lifestatus")
@NamedQueries({
	@NamedQuery(name = "LifeStatus.findAll", query = "SELECT l FROM LifeStatus l"),
	@NamedQuery(name="LifeStatus.getByMeasure", query = "SELECT l FROM LifeStatus l "
			+ "WHERE l.person.idPerson = :idPerson AND l.measure.idMeasure = :idMeasure")
})
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder={"idStatus","measure","value"})
public class LifeStatus implements Serializable {
	private final static long serialVersionUID = 1L;
	
	@Id
	@Column
	@XmlElement(name="sid")
	private int idStatus;	
	@Column
	@XmlElement
	private double value;
	@OneToOne
	@JoinColumn(name="idMeasure", referencedColumnName="idMeasure", insertable=true, updatable=true)
	@XmlElement
	private Measure measure;
	@ManyToOne
	@JoinColumn(name="idPerson", referencedColumnName="idPerson")
	private Person person;
	
	public LifeStatus(){}
	
	public int getIdStatus() {
		return idStatus;
	}
	
	public void setIdStatus(int idStatus) {
		this.idStatus = idStatus;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}

	@XmlTransient
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
	public static LifeStatus getLifeStatusById(int lifestatusId) {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
		LifeStatus p = em.find(LifeStatus.class, lifestatusId);
		LifeFitDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<LifeStatus> getAll() {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
	    List<LifeStatus> list = em.createNamedQuery("LifeStatus.findAll", LifeStatus.class).getResultList();
	    LifeFitDao.instance.closeConnections(em);
	    return list;
	}
	
	public static LifeStatus getByMeasure(int idPerson, int idMeasure){
		EntityManager em = null;
		LifeStatus lifeStatus = null;
		
		try{
			em = LifeFitDao.instance.createEntityManager();			
			//Refresh the entity manager
	        em.getEntityManagerFactory().getCache().evictAll();
	        
		    lifeStatus = em.createNamedQuery("LifeStatus.getByMeasure", LifeStatus.class)
		    		.setParameter("idPerson", idPerson)
		    		.setParameter("idMeasure", idMeasure)
		    		.getSingleResult();
		}
		catch(Exception e){e.printStackTrace();}
		finally{
			LifeFitDao.instance.closeConnections(em);
		}
	    return lifeStatus;
	}
	
	public static LifeStatus saveLifeStatus(LifeStatus p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static LifeStatus updateLifeStatus(LifeStatus p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static boolean removeLifeStatus(LifeStatus p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return true;
	}
}
