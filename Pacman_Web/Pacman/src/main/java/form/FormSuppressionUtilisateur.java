package form;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.UtilisateurDao;

public class FormSuppressionUtilisateur {
	
    public static void suprimerUtilisateur(HttpServletRequest request, HttpServletResponse response, UtilisateurDao utilisateurDao) throws ServletException, IOException {

        long idUtilisateur = Long.parseLong(request.getParameter("idUtilisateur"));

        utilisateurDao.supprimer(idUtilisateur);

        response.sendRedirect("/Pacman/Accueil");
    }
}
