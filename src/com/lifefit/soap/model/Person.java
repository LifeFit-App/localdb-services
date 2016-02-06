package com.lifefit.soap.model;

import com.lifefit.soap.dao.LifeFitDao;
import com.lifefit.soap.util.DateAdapter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@Table(name="person")
@NamedQueries({
		@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p"),
		@NamedQuery(name="Person.getPersonByEmailPass", query="SELECT p FROM Person p " 
				+ "WHERE LOWER(p.email) = :email AND LOWER(p.password) = :password")		
})
@XmlType(propOrder={"idPerson","firstname","lastname","gender","birthdate","lifeStatus"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idPerson")
	@XmlElement(name="idPerson")
	private int idPerson;
	@Column
	@XmlElement(name="firstname")
	private String firstname;
	@Column
	@XmlElement(name="lastname")
	private String lastname;
	@Column
	@XmlElement(name="gender")
	private String gender;
	@Column
	@XmlElement(name="birthdate")
	@Temporal(TemporalType.DATE)	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date birthdate;
	@Column(name="email")
	@XmlTransient
	private String email;
	@Column(name="password")
	@XmlTransient
	private String password;
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<LifeStatus> lifeStatus;
	
	public Person(){}
	
	public int getIdPerson() {
		return idPerson;
	}
	
	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public List<LifeStatus> getLifeStatus() {
		return lifeStatus;
	}

	public void setLifeStatus(List<LifeStatus> lifeStatus) {
		this.lifeStatus = lifeStatus;
	}
	
	//Database Operations
	public static Person getPersonById(int personId) {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
		Person p = em.find(Person.class, personId);
		LifeFitDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<Person> getAll() {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
	    List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
	    LifeFitDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Person getPersonByEmailPass(String email, String pass) {
		EntityManager em = null;
		Person p = null;
		
		try{
			em = LifeFitDao.instance.createEntityManager();		
			//Refresh the entity manager
	        em.getEntityManagerFactory().getCache().evictAll();
	        
			p = em.createNamedQuery("Person.getPersonByEmailPass", Person.class)
					.setParameter("email", email.toLowerCase())
					.setParameter("password", pass.toLowerCase())
					.getSingleResult();
		}
		catch(Exception e){}
		finally{
			LifeFitDao.instance.closeConnections(em);
		}
		return p;
	}
	
	public static Person savePerson(Person p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static Person updatePerson(Person p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeFitDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removePerson(Person p) {
		EntityManager em = LifeFitDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	}
}
