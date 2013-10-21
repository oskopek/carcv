/**
 * 
 */
package org.carcv.web.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.core.model.NumberPlate;

/**
 * @author oskopek
 *
 */
@Stateless
public class NumberPlateBean {

	@PersistenceContext
	private EntityManager em;
	
	public void create(NumberPlate...licenceplates) {
		for(NumberPlate lp : licenceplates) {
			em.persist(lp);
		}
	}
	
	public NumberPlate findById(long id) {
		return (NumberPlate) em.createQuery("select lp from NumberPlate lp where lp.id = :id")
				.setParameter("id", id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<NumberPlate> getAll() {
		return em.createQuery("select lp from NumberPlate lp")
				.getResultList();
	}
}
