package DAO;

import java.sql.*;
import models.*;

import java.util.ArrayList;
import java.util.List;

import models.Utilisateur;

public class UtilisateurDaoImpl implements UtilisateurDao {
    private DAOFactory daoFactory;

    UtilisateurDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouter(Utilisateur utilisateur) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, DateInscription) VALUES (?, ?, ?, MD5(?), ?);");
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.setString(3, utilisateur.getEmail());
            preparedStatement.setString(4, utilisateur.getMot_de_passe());
            preparedStatement.setTimestamp(5, utilisateur.getDateInscription());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Utilisateur> lister() {
        List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT nom, prenom, email, mot_de_passe, DateInscription FROM utilisateur;");

            while (resultat.next()) {
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                String email = resultat.getString("email");
                
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNom(nom);
                utilisateur.setPrenom(prenom);
                utilisateur.setEmail(email);

                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    @Override
    public boolean verifUtilisateur(String email, String motDePasse) {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;
        boolean utilisateurExiste = false;
        
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = MD5(?)");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, motDePasse);
            resultat = preparedStatement.executeQuery();
            
            utilisateurExiste = resultat.next(); 
            
        } catch (SQLException e) {
            e.printStackTrace(); 
        } finally {

            try {
                if (resultat != null) {
                    resultat.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Gestion appropriée des exceptions à mettre en place
            }
        }
        
        return utilisateurExiste;
  
    }
    public void ajouterStatistique(Stats statistique, long utilisateurId) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO Statistique (username, bestScore, totalScore, gamesPlayed, utilisateur_id) VALUES (?, ?, ?, ?, ?);");
            preparedStatement.setString(1, statistique.getUsername());
            preparedStatement.setInt(2, statistique.getBestscore() );
            preparedStatement.setInt(3, statistique.getTotalscore());
            preparedStatement.setInt(4, statistique.getGameplayed());
            preparedStatement.setLong(5, utilisateurId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public long getIdByEmail(String email) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;
        long userId = -1; // On attribut cette valeur par défaut si jamais l'utilisateur n'est pas trouvé

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("SELECT id FROM utilisateur WHERE email = ?");
            preparedStatement.setString(1, email);
            resultat = preparedStatement.executeQuery();

            if (resultat.next()) {
                userId = resultat.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
          
            try {
                if (resultat != null) {
                    resultat.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userId;
    }
    
    public Stats getStatsById(long utilisateurId) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;
        Stats stats = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("SELECT * FROM Statistique WHERE utilisateur_id = ?");
            preparedStatement.setLong(1, utilisateurId);
            resultat = preparedStatement.executeQuery();

            if (resultat.next()) {
                String username = resultat.getString("username");
                int bestScore = resultat.getInt("bestScore");
                int totalScore = resultat.getInt("totalScore");
                int gamesPlayed = resultat.getInt("gamesPlayed");

                stats = new Stats(username, bestScore, totalScore, gamesPlayed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            try {
                if (resultat != null) {
                    resultat.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return stats;
    }
    
    public void modifier(Utilisateur utilisateur) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE utilisateur SET nom=?, prenom=?, email=?, mot_de_passe=MD5(?) WHERE id=?");
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.setString(3, utilisateur.getEmail());
            preparedStatement.setString(4, utilisateur.getMot_de_passe());
            preparedStatement.setLong(5, utilisateur.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
          
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
        
    public void supprimer(long id) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM utilisateur WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
