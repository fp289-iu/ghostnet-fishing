package org.sheasepherd.ghostnet.beans;

import org.sheasepherd.ghostnet.dao.GhostNetDAO;
import org.sheasepherd.ghostnet.dao.PersonDAO;
import org.sheasepherd.ghostnet.model.GhostNet;
import org.sheasepherd.ghostnet.model.Person;
import org.sheasepherd.ghostnet.model.GhostNet.Groesse;
import org.sheasepherd.ghostnet.model.Person.Typ;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * CDI-Bean zum melden von Netzen
 * Verwaltet die Formulardaten und steuert das Speichern eines neuen Geisternetzes.
 * @RequestScoped bedeutet: eine neue Instanz pro HTTP-Request.
 */
@Named
@RequestScoped
public class MeldeBean {

	// DAO für Datenbankoperationen auf Geisternetzen
	@Inject
	private GhostNetDAO ghostNetDAO;

	// DAO für Datenbankoperationen auf Personen
	@Inject
	private PersonDAO personDAO;

	// Formularfelder
	private Double latitude;
	private Double longitude;
	private Groesse groesse;
	private boolean anonym;
	private String name;
	private String telefon;

	// Methode zum Netz melden. Validiert EIngaben, legt ggf. eine neue Person an und speichert das Geisternetz
	public String meldenAction() {
		// Validierung: Name und Telefon erforderlich wenn nicht anonym
		if (!anonym && (name == null || name.trim().isEmpty()
		             || telefon == null || telefon.trim().isEmpty())) {
		    FacesContext.getCurrentInstance().addMessage(null,
		        new FacesMessage(FacesMessage.SEVERITY_ERROR,
		            "Bitte Name und Telefonnummer angeben oder anonym melden!", ""));
		    return null;
		}
		
		// Person anlegen
		Person person = null;
		if (!anonym) {
			person = personDAO.findenOderErstellen(name, telefon, Typ.MELDEND);
		}

		// Geisternetz anlegen und speichern
		GhostNet netz = new GhostNet(latitude, longitude, groesse);
		netz.setMeldendePerson(person);
		ghostNetDAO.speichern(netz);
		
		//Meldung und weiterleitung zu Alle NEtze bei erfolg
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg", "Geisternetz wurde erfolgreich gemeldet!"));
		return "/pages/netze?faces-redirect=true";
	}

	// Gibt alle möglichen Größenwerte für das Dropdown-Menü im Formular zurück
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