package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.UtilisateurDao;
import DAO.DAOFactory;
import form.FormModificationUtilisateur;
import models.Utilisateur;

@WebServlet("/ModificationUtilisateur")
public class ModificationUtilisateur extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtilisateurDao utilisateurDao;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.utilisateurDao = daoFactory.getUtilisateurDao();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("utilisateurs", utilisateurDao.lister());
        this.getServletContext().getRequestDispatcher("/WEB-INF/ModificationUtilisateur.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur utilisateur = FormModificationUtilisateur.modifierUtilisateur(request);
        
        // On appele la méthode pour modifier l'utilisateur dans la base de données
        utilisateurDao.modifier(utilisateur);

        response.sendRedirect("/Pacman/Accueil");
    }
}
