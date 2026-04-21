package org.sheasepherd.ghostnet.dao;

import java.util.List;

import org.sheasepherd.ghostnet.model.Person;
import org.sheasepherd.ghostnet.model.Person.Typ;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/* DAO-Klasse für den DAtenbankzugriff auf Personen
 * Kapselt alle Persistenzoperationen für die person-Entity
 * Wird von CDI-Beans per @Inject verwendet
 */
@ApplicationScoped
public class PersonDAO {
	
	// EntityManager wird von TomEE injiziert und verwaltet die Datenbankverbindung
	 @PersistenceContext(unitName = "ghostnetPU")
	    private EntityManager em;

	 	// Methode zum speichern einer neuen person in der DB
	    @Transactional
	    public void speichern(Person person) {
	        em.persist(person);
	    }

	    // Methode zum Person erstellen, falls sie noch nicht existiert
	    // Sucht Person anhand von Name, Telefonnummer und Typ. Falls keine gefunden wird, wird eine neue angelegt
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
