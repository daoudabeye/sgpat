package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the fournisseur database table.
 * 
 */
@Entity
@Table(name="fournisseur")
@NamedQuery(name="Fournisseur.findAll", query="SELECT f FROM Fournisseur f")
public class Fournisseur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String adresse;

	@Column(name="code_fournisseur")
	private String codeFournisseur;

	private String nom;

	private String telephone;

	//bi-directional many-to-one association to Piece
	@OneToMany(mappedBy="fournisseur")
	private List<Piece> pieces;

	public Fournisseur() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCodeFournisseur() {
		return this.codeFournisseur;
	}

	public void setCodeFournisseur(String codeFournisseur) {
		this.codeFournisseur = codeFournisseur;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Piece> getPieces() {
		return this.pieces;
	}

	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}

	public Piece addPiece(Piece piece) {
		getPieces().add(piece);
		piece.setFournisseur(this);

		return piece;
	}

	public Piece removePiece(Piece piece) {
		getPieces().remove(piece);
		piece.setFournisseur(null);

		return piece;
	}

}