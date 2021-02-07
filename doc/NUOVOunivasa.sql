-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 07, 2021 at 05:24 PM
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
  `telefono` varchar(11) NOT NULL,
  `data_nascita` date NOT NULL,
  `nome_universita` varchar(50) NOT NULL,
  `voti_ricevuti` int(10) UNSIGNED NOT NULL DEFAULT '0',
  `id_evento` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_candidato_evento1` (`id_evento`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `candidato`
--

INSERT INTO `candidato` (`id`, `nome`, `cognome`, `email`, `telefono`, `data_nascita`, `nome_universita`, `voti_ricevuti`, `id_evento`) VALUES
(13, 'Enrico', 'Adamelli', 'enry@adams.it', '3980987695', '2000-01-16', 'Univaq', 0, 1),
(14, 'Lorenzo', 'Solazzo', 'lollo@sola.it', '3859786959', '2000-05-11', 'Univaq', 0, 1),
(15, 'Martina', 'Nolletti', 'martina@nolletti.org', '3459319859', '1999-05-24', 'UniBocconi', 0, 9),
(16, 'Samuel', 'Cipriani', 'samuel@cipciop.it', '3893833983', '2000-02-29', 'Univaq', 10, 9),
(17, 'Andrea', 'Fonte', 'andrea@fonte.com', '1234567892', '1999-05-13', 'Univaq', 0, 10),
(18, 'Samuel', 'Cipriani', 'samuel@cipciop.it', '3893833983', '2000-02-29', 'Univaq', 0, 10);

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `evento`
--

INSERT INTO `evento` (`id`, `nome`, `regolamento`, `data_inizio`, `data_fine`, `ora_inizio`, `ora_fine`, `luogo`, `report_risultati`, `report_statistiche`, `numero_preferenze_esprimibili`, `stato`) VALUES
(1, 'RAPPRESENTANTI UNI', 'si vota una persona per scheda elettorale', '2021-03-10', '2021-03-11', '8:00', '20:00', 'L\'Aquila', NULL, NULL, 1, 'programmato'),
(9, 'Rappresentanti Prof', 'Votate max 2 persone', '2021-02-03', '2021-02-06', '11:00', '18:00', 'Coppito 2', 'Ha vinto Samuel.', 'Martina ha preso 0 voti.', 2, 'terminato'),
(10, 'Elezione segreteria', 'Massimo 3 preferenze', '2021-02-17', '2021-02-22', '10:00', '23:00', 'Blocco 0', NULL, NULL, 3, 'programmato');

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
  `voti_espressi` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_utente_has_evento_utente2` (`id_utente`),
  KEY `fk_utente_has_evento_evento3` (`id_evento`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `prenotazione`
--

INSERT INTO `prenotazione` (`id`, `id_utente`, `id_evento`, `tipo_prenotazione`, `stato`, `certificato`, `voti_espressi`) VALUES
(1, 2, 1, 'online', 'no', NULL, 0),
(11, 17, 10, 'in presenza', 'no', NULL, 0),
(12, 17, 1, 'online', 'no', NULL, 0);

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `turnazione`
--

INSERT INTO `turnazione` (`id`, `id_utente`, `id_evento`, `fascia`, `data_giorno`) VALUES
(4, 3, 1, 'mattina', '2021-03-11'),
(5, 14, 9, 'pomeriggio', '2021-02-10'),
(6, 14, 10, 'sera', '2021-02-18');

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `utente`
--

INSERT INTO `utente` (`id`, `nome`, `cognome`, `email`, `username`, `password`, `telefono`, `data_nascita`, `professione`, `nome_universita`, `dipartimento`, `matricola`, `tipo_utente`) VALUES
(1, 'Ciccio', 'Parrucco', 'aba@gmail.com', 'a', 'a', '1230928292', '1999-06-16', 'professore', 'AQ', 'DISIM', '', 'amministratore'),
(2, 'Elena', 'Ricci', 'bcb@gmail.com', 'e', 'e', '4562222221', '2003-10-23', 'studente', 'AQ', 'DISIM', '255222', 'elettore'),
(3, 'Chiara', 'Ferry', 'cdc@gmail.com', 'o', 'o', '7892222222', '2000-04-15', 'impiegato', 'AQ', 'DISCAB', NULL, 'operatore'),
(13, 'Martina', 'Nolletti', 'martina@nolletti.org', 'martina', 'martina', '3459319859', '1999-05-24', 'impiegato', 'UniBocconi', 'DISIM', NULL, 'amministratore'),
(14, 'Marco', 'Chiavaroli', 'marco@chiav.top', 'marco', 'marco', '3890459739', '1999-08-23', 'studente', 'Univaq', 'DISCAB', NULL, 'operatore'),
(15, 'Samuel', 'Cipriani', 'samuel@cipciop.it', 'samuel', 'samuel', '3893833983', '2000-02-29', 'professore', 'Univaq', 'DISIM', '289282', 'elettore'),
(16, 'Lorenzo', 'Solazzo', 'lollo@sola.it', 'lorenzo', 'lorenzo', '3859786959', '2000-05-11', 'professore', 'Univaq', 'DISIM', '989839', 'elettore'),
(17, 'Enrico', 'Adamelli', 'enry@adams.it', 'enrico', 'enrico', '3980987695', '2000-01-16', 'impiegato', 'Univaq', 'DISIM', '123456', 'elettore'),
(18, 'Andrea', 'Fonte', 'andrea@fonte.com', 'andrea', 'andrea', '1234567892', '1999-05-13', 'professore', 'Univaq', 'DISIM', '192948', 'elettore');

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
