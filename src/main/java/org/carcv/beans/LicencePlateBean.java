/**
 * 
 */
package org.carcv.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.model.LicencePlate;

/**
 * @author oskopek
 *
 */
@Stateless
public class LicencePlateBean {

	@PersistenceContext
	private EntityManager em;
	
	public void create(LicencePlate...licenceplates) {
		for(LicencePlate lp : licenceplates) {
			em.persist(lp);
		}
	}
	
	public LicencePlate findById(long id) {
		return (LicencePlate) em.createQuery("select lp from LicencePlate lp where lp.id = :identifier")
				.setParameter("idenitifier", id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<LicencePlate> getAll() {
		return em.createQuery("select lp from LicencePlate lp")
				.getResultList();
	}
}
