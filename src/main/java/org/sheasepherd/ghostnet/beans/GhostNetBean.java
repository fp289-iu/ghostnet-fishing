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
	
	//Speichert die ID des ausgewählten Netzes für den Popup Dialog
	private Long ausgewaehltesNetzId;
	
	public void netzAuswaehlen(Long netzId) {
		this.ausgewaehltesNetzId = netzId;
	}

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
	    
	    zuBergendeNetze = null; // Cache leeren
	    return null;
	}


	@Inject
	private GhostNetDAO ghostNetDAO;
	
	private List<GhostNet> zuBergendeNetze;
	
	public List<GhostNet> getZuBergendeNetze() {
		if (zuBergendeNetze == null) {
			zuBergendeNetze = ghostNetDAO.zuBergendeNetze();
		}
		return zuBergendeNetze;
	}
	

	// Getter und Setter

	public String getBergendePersonName() {
		return bergendePersonName;
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

	public void setZuBergendeNetze(List<GhostNet> zuBergendeNetze) {
		this.zuBergendeNetze = zuBergendeNetze;
	}
	
}
