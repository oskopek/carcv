package org.carcv.web.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.impl.core.model.CarData;

/**
 * Session Bean implementation class CarDataBean
 */
@Stateless
public class CarDataBean {

	@PersistenceContext
	private EntityManager em;
	
	public void create(CarData...cardata) {
		for(CarData cd : cardata) {
			em.persist(cd);
		}
	}
	
	public CarData findById(long id) {
		return (CarData) em.createQuery("select cd from CarData cd where cd.id = :id")
				.setParameter("id", id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<CarData> getAll() {
		return em.createQuery("select cd from CarData cd")
				.getResultList();
	}

}
