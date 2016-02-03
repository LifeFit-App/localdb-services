package com.lifefit.soap.model;

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

import com.lifefit.soap.dao.LifeFitDao;

@Entity
@Table(name="activity")
@NamedQueries({
		@NamedQuery(name="Activity.findAll", query="SELECT a FROM Activity a")
})
@XmlAccessorType(XmlAccessType.FIELD)
public class Activity implements Serializable {
	private final static long serialVersionUID = 1L;
	
	@Id
	@Column
	@XmlElement
	private int idActivity;
	@XmlElement
	private String activityName;
	
	public Activity(){}

	public int getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	//Database operations
	public static Activity getActivityById(int activityId) {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
		Activity p = em.find(Activity.class, activityId);
		LifeFitDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<Activity> getAll() {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
	    List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class).getResultList();
	    LifeFitDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Activity saveActivity(Activity p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static Activity updateActivity(Activity p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeActivity(Activity p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	}
}
