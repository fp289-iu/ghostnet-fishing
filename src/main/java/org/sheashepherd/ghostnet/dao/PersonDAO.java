package org.sheashepherd.ghostnet.dao;

import java.util.List;

import org.sheashepherd.ghostnet.model.Person;
import org.sheashepherd.ghostnet.model.Person.Typ;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PersonDAO {
	
	 @PersistenceContext(unitName = "ghostnetPU")
	    private EntityManager em;

	    @Transactional
	    public void speichern(Person person) {
	        em.persist(person);
	    }

	    @Transactional
	    public Person findenOderErstellen(String name, String telefon, Typ typ) {
	        List<Person> result = em.createQuery(
	                "SELECT p FROM Person p WHERE p.name = :name AND p.telefonnummer = :tel AND p.typ = :typ",
	                Person.class)
	                .setParameter("name", name)
	                .setParameter("tel", telefon)
	                .setParameter("typ", typ)
	                .getResultList();
	        if (!result.isEmpty()) return result.get(0);
	        Person neu = new Person(name, telefon, typ, false);
	        em.persist(neu);
	        return neu;
	    }
}
