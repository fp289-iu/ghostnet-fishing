package org.sheashepherd.ghostnet.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person implements Serializable {

    public enum TYP {
        MELDEND,
        BERGEND
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @Column(nullable = true)
    private String name;
    
    @Column(nullable = true)
    private String telefonnummer;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TYP typ;
    
    @Column(nullable = false)
    private Boolean anonym;

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

	public TYP getTyp() {
		return typ;
	}

	public void setTyp(TYP typ) {
		this.typ = typ;
	}

	public Boolean getAnonym() {
		return anonym;
	}

	public void setAnonym(Boolean anonym) {
		this.anonym = anonym;
	}
	
}
