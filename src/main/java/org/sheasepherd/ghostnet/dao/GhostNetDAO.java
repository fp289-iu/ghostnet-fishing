package org.sheasepherd.ghostnet.dao;

import java.util.List;

import org.sheasepherd.ghostnet.model.GhostNet;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class GhostNetDAO {

    @PersistenceContext(unitName = "ghostnetPU")
    private EntityManager em;
	
	// sucht ein Geisternetz anhand seiner ID
	public GhostNet findById(Long id) {
	    return em.find(GhostNet.class, id);
	}	
	
	// Lädt ein Geisternetz mit der bergenden Person (um lazyloading und error zu vermeiden wird hier eager loading verwendet)
	public GhostNet findByIdMitPerson(Long id) {
	    return em.createQuery(
	        "SELECT g FROM GhostNet g LEFT JOIN FETCH g.bergendePerson WHERE g.id = :id",
	        GhostNet.class)
	        .setParameter("id", id)
	        .getSingleResult();
	}
	
	// Ändert den Status eines Geisternetzes direkt in der Datenbank
	@Transactional
	public void statusAendern(Long netzId, GhostNet.Status neuerStatus) {
	    GhostNet netz = em.find(GhostNet.class, netzId);
	    netz.setStatus(neuerStatus);
	}
	
	// Aktualisiert ein bestehendes Geisternetz in der Datenbank
	@Transactional
	public GhostNet aktualisieren(GhostNet netz) {
	    return em.merge(netz);
	}
	
	// Gibt eine Liste der noch zu bergenden Netze (Status Gemeldet oder Bergung_Bevorstehend)
	public List<GhostNet> alleNetze() {
	    return em.createQuery(
	        "SELECT g FROM GhostNet g " +
	        "LEFT JOIN FETCH g.meldendePerson " +
	        "LEFT JOIN FETCH g.bergendePerson " +
	        "LEFT JOIN FETCH g.verschollenePerson " +
	        "ORDER BY g.erstelltAm ASC",
	        GhostNet.class)
	        .getResultList();
	}

    @Transactional
    public void speichern(GhostNet netz) {
        em.persist(netz);
    }
}