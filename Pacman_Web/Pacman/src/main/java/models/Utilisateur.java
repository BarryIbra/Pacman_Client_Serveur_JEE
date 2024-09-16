package models;

import java.io.Serializable;
import java.sql.Timestamp;


public class Utilisateur implements Serializable {
	
	private long id;
	private String nom;
	private String prenom;
	private String email;
	private String mot_de_passe;
	private Timestamp DateInscription;
	private Stats mes_stats;
	
	public Utilisateur() {
		super();
	}

	public Utilisateur(int id, String nom, String prenom, String email, String mot_de_passe, Timestamp DateInscription, Stats stats) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mot_de_passe=mot_de_passe;
		this.DateInscription=DateInscription;
		this.mes_stats=stats;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMot_de_passe() {
		return mot_de_passe;
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}
	
	public Timestamp getDateInscription() {
		return DateInscription;
	}

	public void setDateInscription(Timestamp dateInscription) {
		DateInscription = dateInscription;
	}
	
	public Stats getStats() {
		return mes_stats;
	}
	
	public void setStats(Stats stats) {
		this.mes_stats=stats;
	}

	@Override
	public String toString() {
		return "utilisateur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", mot_de_passe="
				+ mot_de_passe + " Stattistiques du joueur:"+ mes_stats.toString()+ "]";
	}

}
