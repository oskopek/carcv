/**
 * 
 */
package org.carcv.beans;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.model.Speed;

/**
 * @author oskopek
 *
 */
public class SpeedBean {

	@PersistenceContext
	private EntityManager em;
	
	public void create(Speed...speeds) {
		for(Speed s : speeds) {
			em.persist(s);
		}
	}
	
	public Speed findById(long id) {
		return (Speed) em.createQuery("select s from Speed s where s.id = :id")
				.setParameter("id", id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Speed> getAll() {
		return em.createQuery("select s from Speed s")
				.getResultList();
	}
}
