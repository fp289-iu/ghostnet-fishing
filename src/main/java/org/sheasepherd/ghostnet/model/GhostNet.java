package org.sheasepherd.ghostnet.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ghost_net")
public class GhostNet implements Serializable {

    public enum Status {
        GEMELDET,
        BERGUNG_BEVORSTEHEND,
        GEBORGEN,
        VERSCHOLLEN
    }

    public enum Groesse {
        KLEIN,
        MITTEL,
        GROSS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Groesse groesse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.GEMELDET;

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
    

    // Konstruktoren

    public GhostNet() {}

    public GhostNet(Double latitude, Double longitude, Groesse groesse) {
        this.latitude  = latitude;
        this.longitude = longitude;
        this.groesse   = groesse;
        this.status    = Status.GEMELDET;
        this.erstelltAm = LocalDateTime.now();
    }
    
    // Getter und Setter

	public Long getId() {
		return id;
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