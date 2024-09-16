package DAO;


import java.util.List; 
import models.*;

import models.Utilisateur;

public interface UtilisateurDao {
	
    void ajouter( Utilisateur utilisateur );
    
    void modifier(Utilisateur utilisateur);
    
    void supprimer(long id); 
    
    List<Utilisateur> lister();
    
    boolean verifUtilisateur(String email, String Mot_de_passe);
    
    void ajouterStatistique(Stats statistique, long utilisateurId);
    
    long getIdByEmail(String email);
    
    Stats getStatsById(long utilisateurId);
        
}