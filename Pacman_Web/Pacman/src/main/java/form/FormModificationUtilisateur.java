package form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.Utilisateur;

public class FormModificationUtilisateur {

    public static Utilisateur modifierUtilisateur(HttpServletRequest request) {
        try {
            // On récupère les informations du formulaire
            long id = Long.parseLong(request.getParameter("id"));
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            String motDePasse = request.getParameter("mot_de_passe");

            // Validation des données
            if (nom == null || nom.isEmpty() ||
                prenom == null || prenom.isEmpty() ||
                email == null || email.isEmpty() ||
                motDePasse == null || motDePasse.isEmpty()) {
                // Si des champs requis sont manquants, rediriger vers la page "erreur.jsp"
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "Veuillez remplir tous les champs.");
                return null; 
            }

            // On crée un objet Utilisateur avec les nouvelles informations
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(id);
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setEmail(email);
            utilisateur.setMot_de_passe(motDePasse);

            return utilisateur;
        } catch (NumberFormatException e) {
            // En cas d'erreur de conversion de l'identifiant en long, rediriger vers la page "erreur.jsp"
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Erreur lors de la conversion de l'identifiant.");
            return null; // Retourner null pour indiquer une erreur
        }
    }
}
