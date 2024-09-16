-- phpMyAdmin SQL Dump
-- version 5.2.1deb1ubuntu1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : dim. 07 avr. 2024 à 17:30
-- Version du serveur : 8.0.36-0ubuntu0.23.10.1
-- Version de PHP : 8.2.10-2ubuntu1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `Pacman`
--

-- --------------------------------------------------------

--
-- Structure de la table `Statistique`
--

CREATE TABLE `Statistique` (
  `id` int NOT NULL,
  `username` varchar(100) NOT NULL,
  `bestScore` int DEFAULT '0',
  `totalScore` int DEFAULT '0',
  `gamesPlayed` int DEFAULT '0',
  `utilisateur_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Statistique`
--

INSERT INTO `Statistique` (`id`, `username`, `bestScore`, `totalScore`, `gamesPlayed`, `utilisateur_id`) VALUES
(1, 'toto', 0, 0, 0, 3),
(2, 'ssssss', 0, 0, 0, 7),
(4, 'zzzzzz', 0, 0, 0, 11),
(7, 'mgl', 0, 0, 0, 14),
(8, 'aaa', 0, 0, 0, 5),
(9, 'java', 0, 0, 0, 16),
(10, 'petitjoueur', 0, 0, 0, 17),
(11, 'yoyo', 0, 0, 0, 18),
(13, 'vovovo', 0, 0, 0, 20),
(14, 'jason', 0, 0, 0, 21),
(15, 'poulet', 0, 0, 0, 22),
(16, 'aaa', 0, 0, 0, 23),
(17, 'aa', 0, 0, 0, 24),
(18, 'hahah', 0, 0, 0, 25),
(19, 'azs', 0, 0, 0, 26);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `mot_de_passe` varchar(50) NOT NULL,
  `DateInscription` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom`, `prenom`, `email`, `mot_de_passe`, `DateInscription`) VALUES
(3, 'fofo', 'fofo', 'fofo@gmail.com', 'de2d6d3888b45f2f3b51afefc8d15e45', '2024-03-27'),
(5, 'fffffff', 'vvvvvv', 'v@gmail.com', '9e3669d19b675bd57058fd4664205d2a', '2024-03-28'),
(6, 'Bill', 'Finn', 'test25@gmail.com', '81dc9bdb52d04dc20036dbd8313ed055', '2024-03-28'),
(7, 'fffffff', 'vvvvvvv', 'dddddd@gmail.com', '2475c20d9e9a1aaee80dcbc4e6316157', '2024-03-28'),
(8, 'dddd', 'fff', 'fff@gmail.com', '3dcc7571afdddb53bf44753fe3c56c61', '2024-03-28'),
(9, 'ddddddddddd', 'bbbbbbbb', 'dddddd@gmail.com', '81dc9bdb52d04dc20036dbd8313ed055', '2024-03-28'),
(10, 'eeeeee', 'dddd', 'a@gmail.com', '81dc9bdb52d04dc20036dbd8313ed055', '2024-03-28'),
(11, 'aaaaa', 'rrrrrr', 'billy@gmail.com', '81dc9bdb52d04dc20036dbd8313ed055', '2024-03-28'),
(12, 'kkk', 'pppp', 'n@gmail.com', '81dc9bdb52d04dc20036dbd8313ed055', '2024-03-28'),
(14, 'ttt', 'dddd', 'mgl@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', '2024-03-28'),
(15, 'ddd', 'dd', 'test@gmail.com', '098f6bcd4621d373cade4e832627b4f6', '2024-03-28'),
(16, 'ddd', 'xxx', 'java@gmail.com', '93f725a07423fe1c889f448b33d21f46', '2024-03-28'),
(17, 'aa', 'bb', 'bebe@gmail.com', '1364cba01e0ee80ef4381175bd6cf0d3', '2024-03-29'),
(18, 'roro', 'fofo', 'fafa@gmail.com', '05d251ea28c5be9426611a121db0c92a', '2024-03-29'),
(20, 'vovovo', 'vovovo', 'vovo@gmail.com', 'b735cc89fdf524ca5355146319f85b0e', '2024-04-07'),
(21, 'jason', 'jason', 'jason@gmail.com', '2b877b4b825b48a9a0950dd5bd1f264d', '2024-04-07'),
(22, 'poulet', 'poulet', 'poulet@gmail.com', '5337aff4d7c42f4124010fc66bcec881', '2024-04-07'),
(23, '', '', '', '098f6bcd4621d373cade4e832627b4f6', '2024-04-07'),
(24, 'nn', 'n', 'nee@gmail.com', 'c322182e6279fd00cf24101223265689', '2024-04-07'),
(25, 'hahahhahah', 'haha', 'haha@gmail.com', '4e4d6c332b6fe62a63afe56171fd3725', '2024-04-07'),
(26, 'azb', 'azb', 'azb@gmail.com', '976f58e7e8c9407e347eceeec4bea372', '2024-04-07');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Statistique`
--
ALTER TABLE `Statistique`
  ADD PRIMARY KEY (`id`),
  ADD KEY `utilisateur_id` (`utilisateur_id`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `Statistique`
--
ALTER TABLE `Statistique`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `Statistique`
--
ALTER TABLE `Statistique`
  ADD CONSTRAINT `Statistique_ibfk_1` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
