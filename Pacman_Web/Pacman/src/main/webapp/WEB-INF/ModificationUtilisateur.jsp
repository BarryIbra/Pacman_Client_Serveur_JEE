<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modification Utilisateur</title>
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

        form {
            width: 300px;
            margin: 0 auto;
            margin-top: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #ffc20e; 
        }

        input[type="text"], input[type="password"], input[type="email"], input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: none;
            border-radius: 5px;
            background-color: #ffc20e; 
            color: #000; 
            font-weight: bold;
        }

        input[type="text"]:focus, input[type="password"]:focus, input[type="email"]:focus {
            outline: none; 
            box-shadow: 0 0 5px #ffc20e; 
        }

        input[type="submit"] {
            background-color: #ff3300; 
            color: #fff; 
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #cc2900; 
        }

        #retour-btn {
            background-color: #ff3300;
            color: #fff;
            border: none;
            border-radius: 5px;
            padding: 10px 20px;
            font-size: 20px;
            text-decoration: none;
            cursor: pointer;
            position: fixed;
            top: 10px; 
            left: 10px; 
        }

        #retour-btn:hover {
            background-color: #cc2900;
        }
    </style>
</head>
<body>
    <h1>Modification Utilisateur</h1>
    <form action="ModificationUtilisateur" method="post">
        <label for="id">ID de l'utilisateur à modifier: </label> <input type="text" name="id" /><br/>
        <label for="nom">Nouveau nom: </label> <input type="text" name="nom" /><br/>
        <label for="prenom">Nouveau prénom: </label> <input type="text" name="prenom" /><br/>
        <label for="email">Nouvel email: </label> <input type="email" name="email" /><br/>
        <label for="mot_de_passe">Nouveau mot de passe: </label> <input type="password" name="mot_de_passe" /><br/>
        <input type="submit" value="Modifier" />
    </form>
</body>
</html>
