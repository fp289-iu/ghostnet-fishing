package org.sheasepherd.ghostnet.beans;

import org.sheasepherd.ghostnet.dao.GhostNetDAO;
import org.sheasepherd.ghostnet.dao.PersonDAO;
import org.sheasepherd.ghostnet.model.GhostNet;
import org.sheasepherd.ghostnet.model.Person;
import org.sheasepherd.ghostnet.model.GhostNet.Groesse;
import org.sheasepherd.ghostnet.model.Person.Typ;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class MeldeBean {

    @Inject
    private GhostNetDAO ghostNetDAO;

    @Inject
    private PersonDAO personDAO;

    // Formularfelder
    private Double latitude;
    private Double longitude;
    private Groesse groesse;
    private boolean anonym;
    private String name;
    private String telefon;

    public String meldenAction() {
        // Person anlegen
        Person person = null;
        if (!anonym) {
            person = personDAO.findenOderErstellen(name, telefon, Typ.MELDEND);
        }

        // Geisternetz anlegen und speichern
        GhostNet netz = new GhostNet(latitude, longitude, groesse);
        netz.setMeldendePerson(person);
        ghostNetDAO.speichern(netz);

        return "pages/netze?faces-redirect=true";
    }
    
    public Groesse[] getGroesseValues() {
        return Groesse.values();
    }

    // Getter und Setter

	public GhostNetDAO getGhostNetDAO() {
		return ghostNetDAO;
	}

	public void setGhostNetDAO(GhostNetDAO ghostNetDAO) {
		this.ghostNetDAO = ghostNetDAO;
	}

	public PersonDAO getPersonDAO() {
		return personDAO;
	}

	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Groesse getGroesse() {
		return groesse;
	}

	public void setGroesse(Groesse groesse) {
		this.groesse = groesse;
	}

	public boolean isAnonym() {
		return anonym;
	}

	public void setAnonym(boolean anonym) {
		this.anonym = anonym;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
    
}