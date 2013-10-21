package org.carcv.web.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.core.model.file.FileCarImage;

/**
 * Session Bean implementation class FileCarImageBean
 */
@Stateless
public class CarImageBean {

	@PersistenceContext
	private EntityManager em;
	
	public void create(FileCarImage...cardata) {
		for(FileCarImage cd : cardata) {
			em.persist(cd);
		}
	}
	
	public FileCarImage findById(long id) {
		return (FileCarImage) em.createQuery("select cd from FileCarImage cd where cd.id = :id")
				.setParameter("id", id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<FileCarImage> getAll() {
		return em.createQuery("select cd from FileCarImage cd")
				.getResultList();
	}

}
