package Controller;

import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.DAOFactory;
import DAO.UtilisateurDao;
import form.FormInscription;
import models.Utilisateur;
import models.Stats;

/**
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtilisateurDao utilisateurDao;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.utilisateurDao = daoFactory.getUtilisateurDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("utilisateurs", utilisateurDao.lister());
        this.getServletContext().getRequestDispatcher("/WEB-INF/Inscription.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        Utilisateur utilisateur = FormInscription.creerUtilisateur(request);

        // On ajoute l'utilisateur
        utilisateurDao.ajouter(utilisateur);

        // On récupère  l'ID de l'utilisateur nouvellement inséré
        long utilisateurId = utilisateurDao.getIdByEmail(utilisateur.getEmail());

        // Et ensuite on insère les statistiques initiales pour cet utilisateur
        Stats statistiqueInitiale = new Stats(username, 0, 0, 0);
        utilisateurDao.ajouterStatistique(statistiqueInitiale, utilisateurId);

        request.setAttribute("utilisateurs", utilisateurDao.lister());

        this.getServletContext().getRequestDispatcher("/WEB-INF/Connexion.jsp").forward(request, response);
    }
}
