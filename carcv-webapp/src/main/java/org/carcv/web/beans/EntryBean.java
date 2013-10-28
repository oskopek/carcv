/*
 * Copyright 2012 CarCV Development Team
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
 *
 */
@Stateless
public class EntryBean {

    @PersistenceContext
    private EntityManager em;

    public void create(FileEntry... entries) {
        for (FileEntry e : entries) {
            em.persist(e);
        }
    }

    public FileEntry findById(long id) {
        return (FileEntry) em.createQuery("select e from FileEntry e where e.id = :id").setParameter("id", id)
            .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<FileEntry> getAll() {
        return em.createQuery("select e from FileEntry e").getResultList();
    }
}