package com.lifefit.soap.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public enum LifeFitDao {
	
	instance;
	EntityManagerFactory emf;
	
	private LifeFitDao(){
		if(emf != null)
			emf.close();
		emf = Persistence.createEntityManagerFactory("lifefit-jpa");
	}

	public EntityManager createEntityManager(){
		try{
			return emf.createEntityManager();
		}
		catch(Exception e){e.printStackTrace();}
		return null;
	}
	
	public EntityManagerFactory getEntityManagerFactory(){
		return emf;
	}
	
	public void closeConnections(EntityManager em){
		em.close();
	}
	
	public EntityTransaction getTransaction(EntityManager em){
		return em.getTransaction();
	}
}
