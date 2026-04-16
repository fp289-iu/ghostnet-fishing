package org.sheasepherd.ghostnet.dao;

import java.util.List;

import org.sheasepherd.ghostnet.model.GhostNet;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class GhostNetDAO {
	
	// sucht ein Geisternetz anhand seiner ID
	public GhostNet findById(Long id) {
	    return em.find(GhostNet.class, id);
	}	
	
	// Aktualisiert ein bestehendes Geisternetz in der Datenbank
	@Transactional
	public GhostNet aktualisieren(GhostNet netz) {
	    return em.merge(netz);
	}
	
	public List<GhostNet> zuBergendeNetze() {
	    return em.createQuery(
	        "SELECT g FROM GhostNet g WHERE g.status IN (:s1, :s2) ORDER BY g.erstelltAm ASC",
	        GhostNet.class)
	        .setParameter("s1", GhostNet.Status.GEMELDET)
	        .setParameter("s2", GhostNet.Status.BERGUNG_BEVORSTEHEND)
	        .getResultList();
	}

    @PersistenceContext(unitName = "ghostnetPU")
    private EntityManager em;

    @Transactional
    public void speichern(GhostNet netz) {
        em.persist(netz);
    }
}