/**
 * 
 */
package org.carcv.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.model.Entry;

/**
 * @author oskopek
 *
 */
@Stateless
public class EntryBean {

	@PersistenceContext
	private EntityManager em;
	
	public void create(Entry...entries) {
		for(Entry e : entries) {
			em.persist(e);
		}
	}
	
	public Entry findById(long id) {
		return (Entry) em.createQuery("select e from Entry e where e.id = :id")
				.setParameter("id", id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Entry> getAll() {
		return em.createQuery("select e from Entry e")
				.getResultList();
	}
}
