/**
 * 
 */
package org.carcv.web.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.core.model.file.FileEntry;

/**
 * @author oskopek
 *
 */
@Stateless
public class EntryBean {

	@PersistenceContext
	private EntityManager em;
	
	public void create(FileEntry...entries) {
		for(FileEntry e : entries) {
			em.persist(e);
		}
	}
	
	public FileEntry findById(long id) {
		return (FileEntry) em.createQuery("select e from FileEntry e where e.id = :id")
				.setParameter("id", id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<FileEntry> getAll() {
		return em.createQuery("select e from FileEntry e")
				.getResultList();
	}
}
