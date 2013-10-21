/**
 * 
 */
package org.carcv.web.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.core.model.MediaObject;

/**
 * @author oskopek
 * @deprecated
 */
@Stateless
public class MediaObjectBean {

	@PersistenceContext
	private EntityManager em;
	
	public void create(MediaObject...mediaobjects) {
		for(MediaObject mo : mediaobjects) {
			em.persist(mo);
		}
	}
	
	public MediaObject findById(long id) {
		return (MediaObject) em.createQuery("select mo from MediaObject mo where mo.id = :id")
				.setParameter("id", id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<MediaObject> getAll() {
		return em.createQuery("select mo from MediaObject mo")
				.getResultList();
	}
}
