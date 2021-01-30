-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: univasa
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `candidatura`
--

DROP TABLE IF EXISTS `candidatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidatura` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `id_utente` int unsigned NOT NULL,
  `id_evento` int unsigned NOT NULL,
  `voti_ricevuti` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_utente_has_evento_evento2_idx` (`id_evento`),
  KEY `fk_utente_has_evento_utente1_idx` (`id_utente`),
  CONSTRAINT `fk_utente_has_evento_evento2` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`),
  CONSTRAINT `fk_utente_has_evento_utente1` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidatura`
--

LOCK TABLES `candidatura` WRITE;
/*!40000 ALTER TABLE `candidatura` DISABLE KEYS */;
/*!40000 ALTER TABLE `candidatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evento` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `regolamento` text NOT NULL,
  `data_inizio` date NOT NULL,
  `data_fine` date NOT NULL,
  `ora_inizio` varchar(8) NOT NULL,
  `ora_fine` varchar(8) NOT NULL,
  `luogo` varchar(50) NOT NULL,
  `report_risultati` text,
  `report_statistiche` text,
  `numero_preferenze_esprimibili` int unsigned NOT NULL,
  `stato` enum('programmato','in corso','terminato') NOT NULL DEFAULT 'programmato',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prenotazione`
--

DROP TABLE IF EXISTS `prenotazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prenotazione` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `id_utente` int unsigned NOT NULL,
  `id_evento` int unsigned NOT NULL,
  `tipo_prenotazione` enum('online','in presenza') DEFAULT NULL,
  `stato` enum('si','no') NOT NULL DEFAULT 'no',
  `certificato` blob,
  PRIMARY KEY (`id`),
  KEY `fk_utente_has_evento_evento3_idx` (`id_evento`),
  KEY `fk_utente_has_evento_utente2_idx` (`id_utente`),
  CONSTRAINT `fk_utente_has_evento_evento3` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`),
  CONSTRAINT `fk_utente_has_evento_utente2` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prenotazione`
--

LOCK TABLES `prenotazione` WRITE;
/*!40000 ALTER TABLE `prenotazione` DISABLE KEYS */;
/*!40000 ALTER TABLE `prenotazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `turnazione`
--

DROP TABLE IF EXISTS `turnazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turnazione` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `id_utente` int unsigned NOT NULL,
  `id_evento` int unsigned NOT NULL,
  `fascia` enum('mattina','pomeriggio','sera') NOT NULL,
  `data_giorno` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_utente_has_evento_evento1_idx` (`id_evento`),
  KEY `fk_utente_has_evento_utente_idx` (`id_utente`),
  CONSTRAINT `fk_utente_has_evento_evento1` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`),
  CONSTRAINT `fk_utente_has_evento_utente` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `turnazione`
--

LOCK TABLES `turnazione` WRITE;
/*!40000 ALTER TABLE `turnazione` DISABLE KEYS */;
/*!40000 ALTER TABLE `turnazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,'a','A','Aa','a','a','22','2010-10-10','professore','bo','bo',NULL,'amministratore'),(2,'b','B','Bb','b','b','33','2000-11-11','impiegato','bo','bo',NULL,'operatore'),(3,'c','C','Cc','c','c','44','2000-12-12','studente','bo','bo','12345','elettore');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-30 18:31:04
