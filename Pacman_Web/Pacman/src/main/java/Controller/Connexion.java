package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.DAOFactory;
import DAO.UtilisateurDao;
import form.FormConnexion;

@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtilisateurDao utilisateurDao;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.utilisateurDao = daoFactory.getUtilisateurDao();
    }

    public Connexion() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("utilisateurs", utilisateurDao.lister());
        this.getServletContext().getRequestDispatcher("/WEB-INF/Connexion.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // On récupère les informations de connexion
        String email = request.getParameter("email");
        String mot_de_passe = request.getParameter("Mot_de_passe");

        // On appelle la méthode de vérification dans FormConnexion
        FormConnexion.ConnexionResult result = FormConnexion.verifConnexion(utilisateurDao, email, mot_de_passe);
        
   
        response.sendRedirect(request.getContextPath() + result.redirectUrl);
    }
}
