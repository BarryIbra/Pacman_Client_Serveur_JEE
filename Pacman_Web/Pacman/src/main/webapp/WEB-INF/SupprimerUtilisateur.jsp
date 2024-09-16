<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Suppression d'un utilisateur</title>
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

        input[type="text"], input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: none;
            border-radius: 5px;
            background-color: #ffc20e; 
            color: #000; 
            font-weight: bold;
        }

        input[type="text"]:focus {
            outline: none; 
            box-shadow: 0 0 5px #ffc20e; 
        }

        input[type="submit"] {
            background-color: #ff3300; 
            color: #fff; 
            cursor: pointer;
            border: none;
            border-radius: 5px;
            padding: 10px;
        }

        input[type="submit"]:hover {
            background-color: #cc2900; 
        }
    </style>
</head>
<body>
    <h1>Suppression d'un utilisateur</h1>
    <form action="SuppressionUtilisateur" method="post">
        <label for="idUtilisateur">ID de l'utilisateur à supprimer :</label>
        <input type="text" id="idUtilisateur" name="idUtilisateur">
        <input type="submit" value="Supprimer">
    </form>
</body>
</html>
