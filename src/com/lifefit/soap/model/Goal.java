package com.lifefit.soap.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.lifefit.soap.dao.LifeFitDao;

@Entity
@Table(name="goal")
@NamedQueries({
		@NamedQuery(name="Goal.findAll", query="SELECT g FROM Goal g"),
		@NamedQuery(name="Goal.getGoalByPersonId", query="SELECT g FROM Goal g "
				+ "WHERE g.person.idPerson = :idPerson")
})
@XmlAccessorType(XmlAccessType.FIELD)
public class Goal implements Serializable {
	private final static long serialVersionUID = 1L;
	
	@Id
	@Column
	@XmlElement
	private int idGoal;
	@Column
	@XmlElement
	private double goalTarget;
	@OneToOne
	@JoinColumn(name="idPerson", referencedColumnName="idPerson")
	private Person person;
	@OneToOne
	@JoinColumn(name="idMeasure", referencedColumnName="idMeasure")
	private Measure measure;
	
	public Goal(){}

	public int getIdGoal() {
		return idGoal;
	}

	public void setIdGoal(int idGoal) {
		this.idGoal = idGoal;
	}

	public double getGoalTarget() {
		return goalTarget;
	}

	public void setGoalTarget(double goalTarget) {
		this.goalTarget = goalTarget;
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
	public static Goal getGoalById(int goalId) {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
		Goal p = em.find(Goal.class, goalId);
		LifeFitDao.instance.closeConnections(em);
		return p;
	}
	
	public static Goal getGoalByPersonId(int personId) {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
		Goal p = em.createNamedQuery("Goal.getGoalByPersonId", Goal.class)
				.setParameter("idPerson", personId)
				.getSingleResult();
		LifeFitDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<Goal> getAll() {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
	    List<Goal> list = em.createNamedQuery("Goal.findAll", Goal.class).getResultList();
	    LifeFitDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Goal saveGoal(Goal p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static Goal updateGoal(Goal p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeGoal(Goal p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	} 
}
