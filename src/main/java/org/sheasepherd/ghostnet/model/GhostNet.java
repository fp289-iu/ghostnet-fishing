package org.sheasepherd.ghostnet.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * JPA-Entity für ein Geisternetz.
 * Bildet die Datenbanktabelle "ghost_net" ab und enthält alle
 * relevanten Informationen zu einem gemeldeten Geisternetz.
 */
@Entity
@Table(name = "ghost_net")
public class GhostNet implements Serializable {

	 /**
     * Mögliche Statuswerte eines Geisternetzes im Lebenszyklus.
     * GEMELDET: Netz wurde erfasst, aber noch nicht zur Bergung eingeplant.
     * BERGUNG_BEVORSTEHEND: Eine bergende Person hat sich eingetragen.
     * GEBORGEN: Das Netz wurde erfolgreich geborgen.
     * VERSCHOLLEN: Das Netz konnte nicht mehr gefunden werden.
     */
	public enum Status {
		GEMELDET, BERGUNG_BEVORSTEHEND, GEBORGEN, VERSCHOLLEN
	}

	// Geschätzte Größe des Geisternetzes
	public enum Groesse {
		KLEIN, MITTEL, GROSS
	}

	//Primärschlüssen 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//Breitengrad des Fundorts (Pflichtfeld)
	@Column(nullable = false)
	private Double latitude;

	// Längengrad des Fundorts (Pflichtfeld)
	@Column(nullable = false)
	private Double longitude;

	// Geschätzte Größe des Netzes
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Groesse groesse;

	//Aktueller Status des Netzes
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.GEMELDET;

	//Zeitstempel der Erfassung (wird automatisch gesetzt beim anlegen)
	@Column(name = "erstellt_am")
	private LocalDateTime erstelltAm = LocalDateTime.now();

	// Meldende Person (ist nullable, da Person anonym sein kann)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meldende_person_id")
	private Person meldendePerson;

	// Bergende Person (maximal eine pro Netz)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bergende_person_id")
	private Person bergendePerson;

	// Person die das Netz als verschollen gemeldet hat
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verschollene_person_id")
	private Person verschollenePerson;

	// Konstruktoren

	//Parameterloser Konstruktor (für JPA)
	public GhostNet() {
	}

	// Konstruktor zum Anlegen eines neuen Geisternetzes
	public GhostNet(Double latitude, Double longitude, Groesse groesse) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.groesse = groesse;
		this.status = Status.GEMELDET;
		this.erstelltAm = LocalDateTime.now();
	}

	// Getter und Setter

	public Long getId() {
		return id;
	}

	public Person getVerschollenePerson() {
		return verschollenePerson;
	}

	public void setVerschollenePerson(Person verschollenePerson) {
		this.verschollenePerson = verschollenePerson;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getErstelltAm() {
		return erstelltAm;
	}

	public void setErstelltAm(LocalDateTime erstelltAm) {
		this.erstelltAm = erstelltAm;
	}

	public Person getMeldendePerson() {
		return meldendePerson;
	}

	public void setMeldendePerson(Person meldendePerson) {
		this.meldendePerson = meldendePerson;
	}

	public Person getBergendePerson() {
		return bergendePerson;
	}

	public void setBergendePerson(Person bergendePerson) {
		this.bergendePerson = bergendePerson;
	}

}