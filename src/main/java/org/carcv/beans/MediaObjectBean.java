/**
 * 
 */
package org.carcv.beans;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.model.MediaObject;

/**
 * @author oskopek
 *
 */
public class MediaObjectBean {

	@PersistenceContext
	private EntityManager em;
	
	public void create(MediaObject...mediaobjects) {
		for(MediaObject mo : mediaobjects) {
			em.persist(mo);
		}
	}
	
	public MediaObject findById(long id) {
		return (MediaObject) em.createQuery("select mo from mediaobject mo where mo.id = :identifier")
				.setParameter("idenitifier", id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<MediaObject> getAll() {
		return em.createQuery("select mo from mediaobject mo")
				.getResultList();
	}
}
