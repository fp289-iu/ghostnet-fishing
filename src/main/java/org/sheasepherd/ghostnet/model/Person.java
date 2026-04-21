package org.sheasepherd.ghostnet.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA-Entity für eine Person.
 * Bildet die Datenbanktabelle "person" ab. Eine Person kann einem
 * Geisternetz als meldende, bergende oder verschollen meldende Person
 * zugeordnet sein.
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {

	/**
	 * Typ der Person in Bezug auf ihre Rolle im System.
	 * MELDEND: Person hat ein Geisternetz gemeldet oder als verschollen gemeldet.
	 * BERGEND: Person hat sich für die Bergung eines Geisternetzes eingetragen.
	 */
    public enum Typ {
        MELDEND,
        BERGEND
    }
    
    // Primärschlüssel
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Name der Person, nullable da anonyme Meldungen möglich sind
    @Column(nullable = true)
    private String name;
    
    // Telefonnummer der Person, nullable da anonyme Meldungen möglich sind
    @Column(nullable = true)
    private String telefonnummer;

    // Rolle der Person im System (MELDEND oder BERGEND)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Typ typ;

    // Gibt an ob die Person anonym gemeldet hat
    @Column(nullable = false)
    private Boolean anonym;
    

    // Konstruktoren

    // Parameterloser Konstruktor für JPA
    public Person() {}

    // Konstruktor für das Anlegen einer neuen Person
    public Person(String name, String telefonnummer, Typ typ, Boolean anonym) {
        this.name          = name;
        this.telefonnummer = telefonnummer;
        this.typ           = typ;
        this.anonym        = anonym;
    }
    
    // Getter und Setter 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	public Typ getTyp() {
		return typ;
	}

	public void setTyp(Typ typ) {
		this.typ = typ;
	}

	public Boolean getAnonym() {
		return anonym;
	}

	public void setAnonym(Boolean anonym) {
		this.anonym = anonym;
	}
	
}
