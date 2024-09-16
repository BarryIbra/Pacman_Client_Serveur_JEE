package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DAOFactory;
import DAO.UtilisateurDao;
import models.Stats;
import form.*;

@WebServlet("/Statistiques")
public class Statistique extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtilisateurDao utilisateurDao;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.utilisateurDao = daoFactory.getUtilisateurDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //On récupére  l'identifiant de l'utilisateur à partir des paramètres de requête
        long utilisateurId = Long.parseLong(request.getParameter("utilisateurId"));

        // On récupère les statistiques de l'utilisateur
        Stats stats = utilisateurDao.getStatsById(utilisateurId);


        request.setAttribute("stats", stats);

        this.getServletContext().getRequestDispatcher("/WEB-INF/Statistique.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FormStatistique.handleForm(request, response, utilisateurDao);
    }
}
