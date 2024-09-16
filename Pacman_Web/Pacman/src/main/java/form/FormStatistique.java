package form;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UtilisateurDao;

public class FormStatistique {
    public static void handleForm(HttpServletRequest request, HttpServletResponse response, UtilisateurDao utilisateurDao) throws ServletException, IOException {
     
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("mot_de_passe");
        
        // Vérification des champs vides
        if (email == null || email.isEmpty() || motDePasse == null || motDePasse.isEmpty()) {
            System.out.println("Erreur : Tous les champs doivent être remplis.");
            return;
        }

        if (utilisateurDao.verifUtilisateur(email, motDePasse)) {
            // On récupère l'ID de l'utilisateur
            long utilisateurId = utilisateurDao.getIdByEmail(email);
            
            // On redirige vers la servlet des statistiques avec l'identifiant de l'utilisateur
            response.sendRedirect(request.getContextPath() + "/Statistiques?utilisateurId=" + utilisateurId);
        } else {
            // On redirige vers la page de connexion en cas d'échec de l'authentification
            response.sendRedirect(request.getContextPath() + "/Connexion");
        }
    }
}
