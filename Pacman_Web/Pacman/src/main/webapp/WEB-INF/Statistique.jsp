<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Statistiques du joueur</title>
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
            width: 50%;
            margin: 0 auto;
            border-collapse: collapse;
            margin-top: 20px; 
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

        #retour-btn {
            color: #fff;
            display: block;
            position: absolute;
            top: 10px;
            left: 10px;
            text-decoration: none;
            background-color: #ff3300; 
            padding: 10px 20px; 
            border-radius: 5px; 
        }

        #retour-btn:hover {
            background-color: #cc2900; 
        }
    </style>
</head>
<body>
    <a id="retour-btn" href="Accueil">Déconnexion</a>
    <h1>Statistiques du joueur</h1>

    <table>
        <tr>
            <th>Username</th>
            <th>Best Score</th>
            <th>Total Score</th>
            <th>Games Played</th>
        </tr>
        <tr>
            <td><c:out value="${stats.username}" /></td>
            <td><c:out value="${stats.bestscore}" /></td>
            <td><c:out value="${stats.totalscore}" /></td>
            <td><c:out value="${stats.gameplayed}" /></td>
        </tr>
    </table>
</body>
</html>
