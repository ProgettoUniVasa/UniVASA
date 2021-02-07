-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 07, 2021 at 04:40 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `univasa`
--

-- --------------------------------------------------------

--
-- Table structure for table `candidato`
--

DROP TABLE IF EXISTS `candidato`;
CREATE TABLE IF NOT EXISTS `candidato` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `data_nascita` date NOT NULL,
  `nome_universita` varchar(50) NOT NULL,
  `voti_ricevuti` int(10) UNSIGNED NOT NULL DEFAULT '0',
  `id_evento` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_candidato_evento1` (`id_evento`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
CREATE TABLE IF NOT EXISTS `evento` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `regolamento` text NOT NULL,
  `data_inizio` date NOT NULL,
  `data_fine` date NOT NULL,
  `ora_inizio` varchar(8) NOT NULL,
  `ora_fine` varchar(8) NOT NULL,
  `luogo` varchar(50) NOT NULL,
  `report_risultati` text,
  `report_statistiche` text,
  `numero_preferenze_esprimibili` int(10) UNSIGNED NOT NULL,
  `stato` enum('programmato','in corso','terminato') NOT NULL DEFAULT 'programmato',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `evento`
--

INSERT INTO `evento` (`id`, `nome`, `regolamento`, `data_inizio`, `data_fine`, `ora_inizio`, `ora_fine`, `luogo`, `report_risultati`, `report_statistiche`, `numero_preferenze_esprimibili`, `stato`) VALUES
(1, 'RAPPRESENTANTI UNI', 'si vota una pesona per scheda elettorale', '2021-02-03', '2021-02-10', '8:00', '20:00', 'L\'Aquila', 'asdfghfff', 'eeesdfghj', 1, 'terminato'),
(6, 'a', 'a', '2021-02-01', '2021-02-03', '11', '12', 's', NULL, NULL, 2, 'programmato'),
(8, 'Test', 'okokok', '2021-02-24', '2021-02-28', '11', '12', 'AQ', NULL, NULL, 2, 'programmato');

-- --------------------------------------------------------

--
-- Table structure for table `prenotazione`
--

DROP TABLE IF EXISTS `prenotazione`;
CREATE TABLE IF NOT EXISTS `prenotazione` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_utente` int(10) UNSIGNED NOT NULL,
  `id_evento` int(10) UNSIGNED NOT NULL,
  `tipo_prenotazione` enum('online','in presenza') DEFAULT NULL,
  `stato` enum('si','no') NOT NULL DEFAULT 'no',
  `certificato` blob,
  PRIMARY KEY (`id`),
  KEY `fk_utente_has_evento_utente2` (`id_utente`),
  KEY `fk_utente_has_evento_evento3` (`id_evento`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `prenotazione`
--

INSERT INTO `prenotazione` (`id`, `id_utente`, `id_evento`, `tipo_prenotazione`, `stato`, `certificato`) VALUES
(1, 2, 1, 'online', 'no', NULL),
(2, 4, 1, 'online', 'no', ''),
(9, 2, 6, 'in presenza', 'no', NULL),
(10, 2, 8, 'online', 'no', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `turnazione`
--

DROP TABLE IF EXISTS `turnazione`;
CREATE TABLE IF NOT EXISTS `turnazione` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_utente` int(10) UNSIGNED NOT NULL,
  `id_evento` int(10) UNSIGNED NOT NULL,
  `fascia` enum('mattina','pomeriggio','sera') NOT NULL,
  `data_giorno` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_utente_has_evento_utente` (`id_utente`),
  KEY `fk_utente_has_evento_evento1` (`id_evento`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `turnazione`
--

INSERT INTO `turnazione` (`id`, `id_utente`, `id_evento`, `fascia`, `data_giorno`) VALUES
(1, 3, 1, 'mattina', '2020-02-06');

-- --------------------------------------------------------

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
CREATE TABLE IF NOT EXISTS `utente` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `telefono` varchar(11) NOT NULL,
  `data_nascita` date NOT NULL,
  `professione` enum('studente','professore','impiegato') NOT NULL,
  `nome_universita` varchar(50) NOT NULL,
  `dipartimento` varchar(50) NOT NULL,
  `matricola` varchar(10) DEFAULT NULL,
  `tipo_utente` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `utente`
--

INSERT INTO `utente` (`id`, `nome`, `cognome`, `email`, `username`, `password`, `telefono`, `data_nascita`, `professione`, `nome_universita`, `dipartimento`, `matricola`, `tipo_utente`) VALUES
(1, 'a', 'admin', 'aba@gmail.com', 'a', 'a', '123', '1999-06-16', 'professore', 'AQ', 'disim', '', 'amministratore'),
(2, 'e', 'elett', 'bcb@gmail.com', 'e', 'e', '456', '2003-10-23', 'studente', 'AQ', 'disim', '255', 'elettore'),
(3, 'o', 'operat', 'cdc@gmail.com', 'o', 'o', '7892222222', '2000-04-15', 'impiegato', 'AQ', 'ok', NULL, 'operatore'),
(4, 'e2', 'ele2', 'ded@gmail.com', 'e2', 'e2', '012', '2001-08-04', 'studente', 'AQ', 'disim', '260', 'elettore'),
(10, 'Marco', 'Chiava', 'm@gmail.com', 'm', 'm', '1234567890', '2021-02-04', 'studente', 'q', 'q', 'q', 'elettore'),
(12, 'Marco', 'Marco', 'ttt', 't', 't', '1234567890', '2021-02-26', 'professore', 'x', 's', NULL, 'amministratore');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `candidato`
--
ALTER TABLE `candidato`
  ADD CONSTRAINT `fk_candidato_evento1` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `prenotazione`
--
ALTER TABLE `prenotazione`
  ADD CONSTRAINT `fk_utente_has_evento_evento3` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_utente_has_evento_utente2` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `turnazione`
--
ALTER TABLE `turnazione`
  ADD CONSTRAINT `fk_utente_has_evento_evento1` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_utente_has_evento_utente` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
