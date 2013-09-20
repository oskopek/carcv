/**
 * 
 */
package org.carcv.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.model.Address;

/**
 * @author oskopek
 *
 */
@Stateless
public class AddressBean {
	
	@PersistenceContext
	private EntityManager em;
	
	public void create(Address...addresses) {
		for(Address a : addresses) {
			em.persist(a);
		}
	}
	
	public Address findById(long id) {
		return (Address) em.createQuery("select a from address a where a.id = :identifier")
				.setParameter("idenitifier", id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Address> getAll() {
		return em.createQuery("select a from address a")
				.getResultList();
	}

}
