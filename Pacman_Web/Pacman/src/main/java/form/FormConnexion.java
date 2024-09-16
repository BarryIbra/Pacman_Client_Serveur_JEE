package form;

import DAO.UtilisateurDao;

public class FormConnexion {

    public static class ConnexionResult {
        public final boolean success;
        public final String redirectUrl;

        public ConnexionResult(boolean success, String redirectUrl) {
            this.success = success;
            this.redirectUrl = redirectUrl;
        }
    }

    public static ConnexionResult verifConnexion(UtilisateurDao utilisateurDao, String email, String mot_de_passe) {
        try {
            // On vérifie ici les informations de connexion
            boolean isConnected = utilisateurDao.verifUtilisateur(email, mot_de_passe);
            
            // On détermine l'URL de redirection en fonction du résultat de la vérification
            String redirectUrl = isConnected ? "/Statistiques?utilisateurId=" + utilisateurDao.getIdByEmail(email) : "/Connexion";
            
            return new ConnexionResult(isConnected, redirectUrl);
        } catch (Exception e) {
        	
            return new ConnexionResult(false, "/WEB-INF/erreur.jsp");
            
        }
    }
}
