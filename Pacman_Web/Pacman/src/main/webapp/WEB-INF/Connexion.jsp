<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <style>
        body {
            background-color: #000;
            color: #fff;
            font-family: Arial, sans-serif;
            text-align: center; 
            position: relative; 
            padding-top: 50px; 
        }

        #pacman {
            font-size: 40px;
            line-height: 1; 
            margin-bottom: 20px;
            background-color: orange;
            padding: 5px 10px; 
            display: inline-block;
            border-radius: 10px; 
        }

        form {
            width: 300px;
            margin: 0 auto;
        }

        label {
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"],
        input[type="password"],
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: none;
            border-radius: 5px;
        }

        input[type="submit"] {
            background-color: #ffc20e;
            color: #000;
            font-weight: bold;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #ffdd67;
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

        .inscription-link {
            color: #ffc20e;
            display: block;
            text-align: center;
            margin-top: 20px;
            text-decoration: none;
        }

        .inscription-link:hover {
            color: #ffdd67;
        }
    </style>
</head>
<body>
    <a id="retour-btn" href="Accueil">Retour vers l'accueil</a>
    <h2><span id="pacman">PACMAN GAME</span></h2>
    <form method="post" action="Connexion">
        <p>
            <label for="email">Email : </label>
            <input type="text" name="email" id="email" />
        </p>

        <p>
            <label for="Mot_de_passe">Mot de passe : </label>
            <input type="password" name="Mot_de_passe" id="Mot_de_passe" />
        </p>

        <input type="submit" value="Connexion" />
    </form>
    <div class="inscription-link">
        <a href="Inscription">Pas encore inscrit ? Inscrivez-vous !</a>
    </div>
</body>
</html>
