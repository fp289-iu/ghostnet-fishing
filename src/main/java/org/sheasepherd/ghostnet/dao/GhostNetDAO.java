package org.sheasepherd.ghostnet.dao;

import org.sheasepherd.ghostnet.model.GhostNet;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class GhostNetDAO {

    @PersistenceContext(unitName = "ghostnetPU")
    private EntityManager em;

    @Transactional
    public void speichern(GhostNet netz) {
        em.persist(netz);
    }
}