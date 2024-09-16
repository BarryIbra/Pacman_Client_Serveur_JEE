<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription</title>
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
}

input[type="text"], input[type="password"], input[type="submit"] {
	width: 100%;
	padding: 10px;
	margin-bottom: 10px;
	border: none;
	border-radius: 5px;
	background-color: #ffc20e; 
	color: #000; 
	font-weight: bold;
}

input[type="text"]:focus, input[type="password"]:focus {
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

#retour-btn:hover {
	background-color: #cc2900;
}
</style>
</head>
<body>
	<a id="retour-btn" href="Accueil">Retour vers l'accueil</a>
	<h1>Inscription</h1>
	<form method="post" action="Inscription">

		<p>
			<label for="username">Username : </label> <input type="text"
				name="username" id="username" />

		</p>

		<p>
			<label for="nom">Nom : </label> <input type="text" name="nom"
				id="nom" />
		</p>
		<p>
			<label for="prenom">Prénom : </label> <input type="text"
				name="prenom" id="prenom" />
		</p>
		<p>
			<label for="email">Email : </label> <input type="text" name="email"
				id="email" />
		</p>
		<p>
			<label for="Mot_de_passe">Mot de passe : </label> <input
				type="password" name="Mot_de_passe" id="Mot_de_passe" />
		</p>
		<input type="submit" value="Inscription" />
	</form>
</body>
</html>
