package form;

import javax.servlet.http.HttpServletRequest;
import models.Utilisateur;
import java.sql.Timestamp;

public class FormInscription {

    public static Utilisateur creerUtilisateur(HttpServletRequest request) throws IllegalArgumentException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("Mot_de_passe");

        // Vérifier si les champs requis sont vides
        if (nom == null || nom.isEmpty() ||
            prenom == null || prenom.isEmpty() ||
            email == null || email.isEmpty() ||
            motDePasse == null || motDePasse.isEmpty()) {
            throw new IllegalArgumentException("Tous les champs sont obligatoires.");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);
        utilisateur.setMot_de_passe(motDePasse);

        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);

        utilisateur.setDateInscription(timestamp);

        return utilisateur;
    }
}
