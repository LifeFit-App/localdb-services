package com.lifefit.soap.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.lifefit.soap.dao.LifeFitDao;
import com.lifefit.soap.util.DateAdapter;

@Entity
@Table
@NamedQueries({
	@NamedQuery(name="PersonActivity.findAll", query="SELECT a FROM PersonActivity a"),
	@NamedQuery(name="PersonActivity.getPersonActivityByPersonId", query="SELECT a FROM PersonActivity a "
				+ "WHERE a.person.idPerson = :idPerson")
})
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonActivity implements Serializable {
	private final static long serialVersionUID = 1L;

	@Id	
	@Column
	@XmlElement
	private int idPA;
	@XmlElement
	@Temporal(TemporalType.TIMESTAMP)	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date startDatetime;
	@XmlElement
	@Temporal(TemporalType.TIMESTAMP)	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date endEndtime;
	@OneToOne
	@JoinColumn(name="idActivity", referencedColumnName="idActivity")
	private Activity activity;
	@OneToOne
	@JoinColumn(name="idPerson", referencedColumnName="idPerson")
	private Person person;
	
	public PersonActivity(){}

	public int getIdPA() {
		return idPA;
	}

	public void setIdPA(int idPA) {
		this.idPA = idPA;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Date getStartDatetime() {
		return startDatetime;
	}

	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}

	public Date getEndEndtime() {
		return endEndtime;
	}

	public void setEndEndtime(Date endEndtime) {
		this.endEndtime = endEndtime;
	}
	
	//Database operations
	public static PersonActivity getPersonActivityById(int idPA) {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
        PersonActivity p = em.find(PersonActivity.class, idPA);
		LifeFitDao.instance.closeConnections(em);
		return p;
	}
	
	public static PersonActivity getPersonActivityByPersonId(int personId) {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
		PersonActivity p = em.createNamedQuery("PersonActivity.getPersonActivityByPersonId", PersonActivity.class)
				.setParameter("idPerson", personId)
				.getSingleResult();
		LifeFitDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<PersonActivity> getAll() {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
	    List<PersonActivity> list = em.createNamedQuery("PersonActivity.findAll", PersonActivity.class)
	    		.getResultList();
	    LifeFitDao.instance.closeConnections(em);
	    return list;
	}
	
	public static PersonActivity savePersonActivity(PersonActivity p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static PersonActivity updatePersonActivity(PersonActivity p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removePersonActivity(PersonActivity p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	} 
}
