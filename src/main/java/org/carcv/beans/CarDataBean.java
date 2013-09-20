package org.carcv.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.model.CarData;

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
		return (CarData) em.createQuery("select cd from cardata cd where cd.id = :identifier")
				.setParameter("idenitifier", id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<CarData> getAll() {
		return em.createQuery("select cd from cardata cd")
				.getResultList();
	}

}
