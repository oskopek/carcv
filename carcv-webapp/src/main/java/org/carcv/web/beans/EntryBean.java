/*
 * Copyright 2012-2014 CarCV Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.carcv.web.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.carcv.core.model.file.FileEntry;

/**
 * Provides basic JPA EntityManager persistence for the FileEntry entity.
 */
@Stateless
public class EntryBean {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persists the list of FileEntries to the database.
     *
     * @see EntityManager#persist(Object)
     * @param entries array of FileEntries to persist
     */
    public void persist(FileEntry... entries) {
        for (FileEntry e : entries) {
            em.persist(e);
        }
    }

    /**
     * Finds the FileEntry with primary key <code>id</code>.
     *
     * @param id the id to query for
     * @see EntityManager#find(Class, Object)
     * @return the FileEntry with the given id or null if no such FileEntry exists
     */
    public FileEntry findById(long id) {
        // return (FileEntry) em.createQuery("select e from FileEntry e where e.id = :id").setParameter("id", id)
        // .getSingleResult();
        return em.find(FileEntry.class, id);
    }

    /**
     * Queries the database, selecting all FileEntries in the table.
     * <p>
     * Query: <code>select e from FileEntry e</code>
     *
     * @return the list result of the query
     */
    @SuppressWarnings("unchecked")
    public List<FileEntry> getAll() {
        return em.createQuery("select e from FileEntry e").getResultList();
    }

    /**
     * Removes the list of FileEntries from the database.
     *
     * @see EntityManager#remove(Object)
     * @param entries array of FileEntries to remove
     */
    public void remove(FileEntry... entries) {
        for (FileEntry e : entries) {
            FileEntry entryToRemove = findById(e.getId());
            em.remove(entryToRemove);
        }
    }

    /**
     * Removes the list of FileEntries from the database.
     *
     * @see EntityManager#remove(Object)
     * @param entries array of FileEntries to remove
     */
    public void remove(long... ids) {
        FileEntry e = null;
        for (long l : ids) {
            e = em.find(FileEntry.class, l);
            em.remove(e);
        }
    }
}