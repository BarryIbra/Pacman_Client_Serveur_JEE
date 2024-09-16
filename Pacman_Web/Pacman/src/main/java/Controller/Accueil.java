package Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DAOFactory;
import DAO.UtilisateurDao;
import models.Utilisateur;

@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtilisateurDao utilisateurDao;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.utilisateurDao = daoFactory.getUtilisateurDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Utilisateur> utilisateurs = utilisateurDao.lister();
        request.setAttribute("utilisateurs", utilisateurs);
        this.getServletContext().getRequestDispatcher("/WEB-INF/Accueil.jsp").forward(request, response);
    }
}
