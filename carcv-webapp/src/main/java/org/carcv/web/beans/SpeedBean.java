/**
 * 
 */
package org.carcv.web.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.core.model.Speed;

/**
 * @author oskopek
 *
 */
@Stateless
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
