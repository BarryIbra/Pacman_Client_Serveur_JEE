package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.UtilisateurDao;
import DAO.DAOFactory;
import form.*;

@WebServlet("/SuppressionUtilisateur")
public class SuppressionUtilisateur extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtilisateurDao utilisateurDao;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.utilisateurDao = daoFactory.getUtilisateurDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setAttribute("utilisateurs", utilisateurDao.lister());
        this.getServletContext().getRequestDispatcher("/WEB-INF/SupprimerUtilisateur.jsp").forward(request, response);
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FormSuppressionUtilisateur.suprimerUtilisateur(request, response, utilisateurDao);
    }
}
