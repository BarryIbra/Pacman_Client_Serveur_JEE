package DAO;

public class ConcreteDAOFactory extends DAOFactory {

    public ConcreteDAOFactory(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public UtilisateurDao getUtilisateurDao() {
        return new UtilisateurDaoImpl(this);
    }
}
