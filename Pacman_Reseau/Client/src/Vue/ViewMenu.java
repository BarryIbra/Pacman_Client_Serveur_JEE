package Vue;

import javax.swing.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Controlleur.ControllerPacmanGame;
import Model.IDsConnexion;
import Model.Connexion.ConnexionServeur;
import Model.Game.Game;
import Model.Game.PacmanGame;


public class ViewMenu {
    public JFrame jFrame;
    int maxTurn = 1000;
    boolean isConnexionPanel = true;
    String username = "";
    private JTextField inputUsername;
    private JPasswordField inputPassword;

    public ViewMenu(){
        this.jFrame = new JFrame("Pacman Menu");

        // Création de la fenre au milieu de l'écran

            jFrame.setSize(new Dimension(800, 500));

            Dimension windowSize = jFrame.getSize();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Point centerPoint = ge.getCenterPoint();
            int dx = centerPoint.x - windowSize.width / 2;
            int dy = centerPoint.y - windowSize.height / 2;
            jFrame.setLocation(dx, dy);

            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création de tous les éléments

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JPanel panelConnexion = new JPanel();
            panelConnexion.setLayout(new BoxLayout(panelConnexion, BoxLayout.Y_AXIS));

            //======= Label du user connecté =======

                JLabel labelUserConnected = new JLabel("WELCOME Corentin !");
                labelUserConnected.setAlignmentX(Component.CENTER_ALIGNMENT);
                labelUserConnected.setForeground(Color.WHITE);
                labelUserConnected.setFont(new Font("Comfortaa", Font.BOLD, 20));
                
                //======= Input username =======
                
                JLabel labelUsername = new JLabel("Username :");
                labelUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
                labelUsername.setForeground(Color.WHITE);
                labelUsername.setFont(new Font("Comfortaa", Font.BOLD, 16));
                
                inputUsername = new JTextField();
                inputUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
                inputUsername.setForeground(Color.BLACK);
                inputUsername.setMaximumSize(new Dimension(200, 30)); // Réduire la taille du champ de texte
                
                //======= Input password =======

                JLabel labelPassword = new JLabel("Password :");
                labelPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
                labelPassword.setForeground(Color.WHITE);
                labelPassword.setFont(new Font("Comfortaa", Font.BOLD, 16));

                inputPassword = new JPasswordField();
                inputPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
                inputPassword.setMaximumSize(new Dimension(200, 30)); // Réduire la taille du champ de texte

            //======= Image PACMAN =======

                JLabel labelImage = new JLabel(new ImageIcon("src/icons/pacman_title.png"));
                labelImage.setAlignmentX(Component.CENTER_ALIGNMENT);
                JLabel labelImage2 = new JLabel(new ImageIcon("src/icons/pacman_title.png"));
                labelImage2.setAlignmentX(Component.CENTER_ALIGNMENT);
                
            //======= Boutons =======
                
                JButton boutonNewGame = new JButton("Commencer");
                mettreEnFormeBouton(boutonNewGame);

                JButton boutonChangeUser = new JButton("Changer d'utilisateur");
                mettreEnFormeBouton(boutonChangeUser);
                
                JButton boutonQuit = new JButton("Quitter");
                mettreEnFormeBouton(boutonQuit);
                
                JButton boutonConnexion = new JButton("Se connecter");
                mettreEnFormeBouton(boutonConnexion);

                //======== Actions des boutons ========

                    boutonNewGame.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evenement) {
                            
                            try {
                                ControllerPacmanGame controller = new ControllerPacmanGame(new PacmanGame(maxTurn, Game.mazeFileName));
                                controller.game.username = username;
                                jFrame.dispose();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    boutonQuit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evenement) {

                            sendServerDeconnexion();
                            jFrame.dispose();
                        }
                    });

                    boutonChangeUser.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evenement) {

                            sendServerDeconnexion();

                            if (isConnexionPanel) {
                                jFrame.setContentPane(panel);
                                jFrame.revalidate();
                                jFrame.repaint();
                            } else {
                                jFrame.setContentPane(panelConnexion);
                                jFrame.revalidate();
                                jFrame.repaint();
                            }
                            isConnexionPanel = !isConnexionPanel;
                        }
                    });

                    boutonConnexion.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evenement) {

                            // On envois les logins au server et check si c'est bon
                            if (sendServerConnexion(inputUsername.getText(), inputPassword.getPassword())) 
                            {
                                isConnexionPanel = false;
                                username = inputUsername.getText();
                                labelUserConnected.setText("WELCOME " + username + " !");
                                jFrame.setContentPane(panel);
                                jFrame.revalidate();
                                jFrame.repaint();

                            } else {
                                JOptionPane.showMessageDialog(jFrame, "Nom d'utilisateur ou mot de passe incorrect!");
                            }
                        }
                    });

            //======== Panel de Connexion ========
            
                panelConnexion.add(labelImage2);
                panelConnexion.add(Box.createVerticalGlue()); // Ajoutez un espace flexible
                panelConnexion.add(labelUsername);
                panelConnexion.add(inputUsername);
                panelConnexion.add(Box.createVerticalGlue()); // Ajoutez un espace flexible
                panelConnexion.add(labelPassword);
                panelConnexion.add(inputPassword);
                panelConnexion.add(Box.createVerticalGlue()); // Ajoutez un espace flexible
                panelConnexion.add(boutonConnexion);
                panelConnexion.add(Box.createVerticalGlue()); // Ajoutez un espace flexible

                panelConnexion.setBackground(Color.DARK_GRAY);

            //======== Panel de Menu de jeu ========
            
                panel.add(labelImage);
                panel.add(labelUserConnected);
                panel.add(Box.createVerticalGlue()); // Ajoutez un espace flexible
                panel.add(boutonNewGame);
                panel.add(Box.createVerticalGlue()); // Ajoutez un espace flexible
                panel.add(boutonChangeUser);
                panel.add(Box.createVerticalGlue()); // Ajoutez un espace flexible
                panel.add(boutonQuit);
                panel.add(Box.createVerticalGlue()); // Ajoutez un espace flexible

                panel.setBackground(Color.DARK_GRAY);

            //================

            jFrame.setContentPane(panelConnexion);
        }
        
        public void mettreEnFormeBouton(JButton bouton){
            bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
            bouton.setPreferredSize(new Dimension(200, 50));
            bouton.setBackground(new Color(249, 195, 40));
            bouton.setFont(new Font("Comfortaa", Font.BOLD, 16));
        }
        
    public void show() {
        jFrame.setVisible(true);
    }


    public void sendServerDeconnexion(){
        try {
            ConnexionServeur newConnexion = new ConnexionServeur(Game.ADRESSEIP, Game.PORT);

            IDsConnexion id = new IDsConnexion();
            id.setValeur(-1);

            ObjectMapper objectMapper = new ObjectMapper();

            newConnexion.sendString(objectMapper.writeValueAsString(id));
            newConnexion.closeSocket();

        } catch (IOException e) { e.printStackTrace(); }
    }

    public Boolean sendServerConnexion(String username, char[] password){
        try {
            ConnexionServeur newConnexion = new ConnexionServeur(Game.ADRESSEIP, Game.PORT);

            // On récupère les username et password entré par l'utilisateur
            String tempUsername = inputUsername.getText();
            char[] tempPassword = inputPassword.getPassword();

            IDsConnexion ids = new IDsConnexion();
            ids.setUsername(tempUsername);
            ids.setPassword(tempPassword);
            ids.setValeur(1); // = une connexion

            // On les convertit en JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String idConnexionJson = objectMapper.writeValueAsString(ids);

            // On l'envoi au serveur
            newConnexion.sendString(idConnexionJson);

            // On lit le retour (= valide ou nope)
            String reponse = newConnexion.readString();
            System.out.println(reponse + " received");

            boolean isRightLogin = (reponse.equals("valide"));
            newConnexion.closeSocket(); // on ferme la connexion

            return isRightLogin;

        } catch (IOException e) { e.printStackTrace(); return false; }
    }
}
