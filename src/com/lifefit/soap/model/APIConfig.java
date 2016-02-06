package com.lifefit.soap.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.lifefit.soap.dao.LifeFitDao;

@Entity
@Table(name="apiconfig")
@NamedQueries({
	@NamedQuery(name="APIConfig.findAll", query="SELECT a FROM APIConfig a"),
	@NamedQuery(name="APIConfig.getAPIConfigByName", query="SELECT a FROM APIConfig a "+
				"WHERE LOWER(a.name) = :name")
})
@XmlAccessorType(XmlAccessType.FIELD)
public class APIConfig implements Serializable {
	private final static long serialVersionUID = 1L;
	
	@Id
	@Column
	@XmlElement
	private int id;
	@XmlElement
	private String name;
	@XmlElement
	private String endpoint;
	@XmlElement
	private String param1;
	@XmlElement
	private String param2;
	@XmlElement
	private String param3;
	
	public APIConfig(){}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	
	public static List<APIConfig> getAll() {
		EntityManager em = LifeFitDao.instance.createEntityManager();		
		//Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
	    List<APIConfig> list = em.createNamedQuery("APIConfig.findAll", APIConfig.class).getResultList();
	    LifeFitDao.instance.closeConnections(em);
	    return list;
	}
	
	public static APIConfig getAPIConfigByName(String name) {
		EntityManager em = null;
		APIConfig apiConfig = null;
		
		try{
			em = LifeFitDao.instance.createEntityManager();			
			//Refresh the entity manager
	        em.getEntityManagerFactory().getCache().evictAll();
	        
	        apiConfig = em.createNamedQuery("APIConfig.getAPIConfigByName", APIConfig.class)
		    		.setParameter("name", name)
		    		.getSingleResult();
		}
		catch(Exception e){}
		finally{
			LifeFitDao.instance.closeConnections(em);
		}
	    return apiConfig;
	}
}
