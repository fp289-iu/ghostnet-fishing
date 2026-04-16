package org.sheasepherd.ghostnet.beans;

import java.io.Serializable;
import java.util.List;

import org.sheasepherd.ghostnet.dao.GhostNetDAO;
import org.sheasepherd.ghostnet.dao.PersonDAO;
import org.sheasepherd.ghostnet.model.GhostNet;
import org.sheasepherd.ghostnet.model.Person;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class GhostNetBean implements Serializable{
	
	// PersonDAO für Datenbankzugriff auf Personen
	@Inject private PersonDAO personDAO;

	// Felder für Meldung von Netz von einer Person
	private String bergendePersonName;
	private String bergendePersonTelefon;

	//Speichert das ausgewählte Netz für den Bestätigungsdialog beim als geborgen melden (User Story 4)
	private GhostNet ausgewaehltesNetz;
	
	// Name der bergenden Person für den Bestätigungsdialog
	private String bergendePersonNameAnzeige;
	
	//Speichert die ID des ausgewählten Netzes für den Popup Dialog
	private Long ausgewaehltesNetzId;
	
	// Felder für Verschollen-Meldung (User Story 7)
	private String verschollenPersonName;
	private String verschollenPersonTelefon;
	
	// Speichert die ID des ausgewählten Netzes für den Verschollen-Dialog
	private Long verschollenNetzId;
	
	// Öffnet den Verschollen-Dialog für das gewählte Netz
	public void netzFuerVerschollenAuswaehlen(Long netzId) {
	    verschollenNetzId = netzId;
	}
	
	// methode zum öffnen des Dialogs am gewählten netz
	public void netzAuswaehlen(Long netzId) {
		this.ausgewaehltesNetzId = netzId;
	}
	
	// Speichert das ausgewählte Netz und öffnet den Bestätigungsdialog
	public void netzFuerBestaetigungAuswaehlen(Long netzId) {
	    ausgewaehltesNetzId = netzId;
	    ausgewaehltesNetz = ghostNetDAO.findByIdMitPerson(netzId);
	    bergendePersonNameAnzeige = ausgewaehltesNetz.getBergendePerson().getName();
	}

	// Methode um Person für Netzbergung einzutragen (User Story 2)
	public String bergungMelden(Long netzId) {
	    GhostNet netz = ghostNetDAO.findById(netzId);
	    
	    if (netz.getBergendePerson() != null) {
	        return null; // Netz ist bereits einer person zugeordnet
	    }
	    
	    Person person = personDAO.findenOderErstellen(
	        bergendePersonName, bergendePersonTelefon, Person.Typ.BERGEND);
	    netz.setBergendePerson(person);
	    netz.setStatus(GhostNet.Status.BERGUNG_BEVORSTEHEND);
	    ghostNetDAO.aktualisieren(netz);
	    
	    alleNetze = null; // Cache leeren
	    return null;
	}
	
	// Setzt den Status des Geisternetzes auf GEBORGEN (User Story 4)
	public String alsGeborgenMelden(Long netzId) {
	    ghostNetDAO.statusAendern(netzId, GhostNet.Status.GEBORGEN);
	    alleNetze = null; //cache leeren
	    return null;
	}

	
	// Setzt den Status des Geisternetzes auf VERSCHOLLEN (User Story 7)
	public String alsVerschollenMelden() { 
	    // Person anlegen oder finden
	    Person person = personDAO.findenOderErstellen(
	        verschollenPersonName, verschollenPersonTelefon, Person.Typ.MELDEND);
	    // Person dem Netz zuweisen und Status setzen
	    GhostNet netz = ghostNetDAO.findById(verschollenNetzId);
	    netz.setVerschollenePerson(person);
	    netz.setStatus(GhostNet.Status.VERSCHOLLEN);
	    ghostNetDAO.aktualisieren(netz);
	    alleNetze = null;
	    return null;
	}
	
	// Gefilterte Netzliste für die Tabelle
	private List<GhostNet> gefilterteNetze;


	@Inject
	private GhostNetDAO ghostNetDAO;
	
	private List<GhostNet> alleNetze;
	
	public List<GhostNet> getAlleNetze() {
		if (alleNetze == null) {
			alleNetze = ghostNetDAO.alleNetze();
		}
		return alleNetze;
	}
	

	// Getter und Setter
	
	public List<GhostNet> getGefilterteNetze() {
	    return gefilterteNetze;
	}
	public void setGefilterteNetze(List<GhostNet> gefilterteNetze) {
	    this.gefilterteNetze = gefilterteNetze;
	}
	
	public String getBergendePersonName() {
		return bergendePersonName;
	}

	public String getVerschollenPersonName() {
		return verschollenPersonName;
	}

	public void setVerschollenPersonName(String verschollenPersonName) {
		this.verschollenPersonName = verschollenPersonName;
	}

	public String getVerschollenPersonTelefon() {
		return verschollenPersonTelefon;
	}

	public void setVerschollenPersonTelefon(String verschollenPersonTelefon) {
		this.verschollenPersonTelefon = verschollenPersonTelefon;
	}

	public Long getVerschollenNetzId() {
		return verschollenNetzId;
	}

	public void setVerschollenNetzId(Long verschollenNetzId) {
		this.verschollenNetzId = verschollenNetzId;
	}

	public String getBergendePersonNameAnzeige() {
		return bergendePersonNameAnzeige;
	}

	public void setBergendePersonNameAnzeige(String bergendePersonNameAnzeige) {
		this.bergendePersonNameAnzeige = bergendePersonNameAnzeige;
	}

	public GhostNet getAusgewaehltesNetz() {
		return ausgewaehltesNetz;
	}

	public void setAusgewaehltesNetz(GhostNet ausgewaehltesNetz) {
		this.ausgewaehltesNetz = ausgewaehltesNetz;
	}

	public void setAlleNetze(List<GhostNet> alleNetze) {
		this.alleNetze = alleNetze;
	}

	public Long getAusgewaehltesNetzId() {
		return ausgewaehltesNetzId;
	}


	public void setAusgewaehltesNetzId(Long ausgewaehltesNetzId) {
		this.ausgewaehltesNetzId = ausgewaehltesNetzId;
	}


	public void setBergendePersonName(String bergendePersonName) {
		this.bergendePersonName = bergendePersonName;
	}

	public String getBergendePersonTelefon() {
		return bergendePersonTelefon;
	}

	public void setBergendePersonTelefon(String bergendePersonTelefon) {
		this.bergendePersonTelefon = bergendePersonTelefon;
	}

	public void setalleNetze(List<GhostNet> alleNetze) {
		this.alleNetze = alleNetze;
	}
	
}
