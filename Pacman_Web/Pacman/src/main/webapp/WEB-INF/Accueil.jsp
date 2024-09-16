<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accueil</title>
    <style>
        body {
            background-color: #000;
            color: #fff;
            font-family: Arial, sans-serif;
            text-align: center;
            position: relative; 
            padding-top: 50px; 
        }

        h1 {
            font-size: 40px;
            background-color: #ffc20e;
            padding: 10px 0;
            margin: 0;
            border-radius: 10px;
        }

        table {
            width: 80%;
            margin: 0 auto;
            border-collapse: collapse;
            margin-top: 20px; 
            display: none; 
        }

        th, td {
            border: 2px solid #ffc20e;
            padding: 10px;
        }

        th {
            background-color: #ff3300;
        }

        tr:nth-child(even) {
            background-color: #ff3300;
        }

        tr:nth-child(odd) {
            background-color: #cc2900;
        }

        .btn {
            border-radius: 10px;
            padding: 15px 30px;
            font-size: 24px;
            text-decoration: none;
            cursor: pointer;
            margin-top: 10px;
        }

        .modifier-btn {
            background-color: #00cc00; 
            color: #fff;
            border: none;
        }

        .supprimer-btn {
            background-color: #0000cc; 
            color: #fff;
            border: none;
        }

        .connexion-btn, .inscription-btn {
            background-color: #4CAF50; 
            color: #000; 
            border: none;
        }

        .connexion-btn {
            position: fixed;
            top: 10px; 
            right: 160px; 
        }

        .inscription-btn {
            position: fixed;
            top: 10px; 
            right: 10px; 
        }


        .connexion-btn:hover, .inscription-btn:hover {
            background-color: #45a049; 
        }
    </style>
</head>
<body>
    <h1>Bienvenue sur notre plateforme Pacman de Billy et Corentin</h1>

    <table id="tableJoueurs">
        <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Email</th>
            <th>Action</th> 
        </tr>
        <c:forEach var="utilisateur" items="${utilisateurs}">
            <tr>
                <td><c:out value="${utilisateur.nom}" /></td>
                <td><c:out value="${utilisateur.prenom}" /></td>
                <td><c:out value="${utilisateur.email}" /></td>
                
                <td>
      
                    <a class="btn modifier-btn" href="ModificationUtilisateur">Modifier</a>
                   
                    <a class="btn supprimer-btn" href="SuppressionUtilisateur">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button class="btn" onclick="afficherTable()">Afficher la liste des joueurs</button>
    <button class="btn" onclick="masquerTable()">Masquer la liste des joueurs</button>
    <a class="connexion-btn btn" href="Connexion">Connexion</a>
    <a class="inscription-btn btn" href="Inscription">Inscription</a> 
    
    <script>
        function afficherTable() {
            document.getElementById('tableJoueurs').style.display = 'table';
        }

        function masquerTable() {
            document.getElementById('tableJoueurs').style.display = 'none';
        }
    </script>
</body>
</html>
