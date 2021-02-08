-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: univasa
-- ------------------------------------------------------
-- Server version	5.7.31

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
-- Table structure for table `candidato`
--

DROP TABLE IF EXISTS `candidato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidato` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `telefono` varchar(11) NOT NULL,
  `data_nascita` date NOT NULL,
  `nome_universita` varchar(50) NOT NULL,
  `voti_ricevuti` int(10) unsigned NOT NULL DEFAULT '0',
  `id_evento` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_candidato_evento1` (`id_evento`),
  CONSTRAINT `fk_candidato_evento1` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidato`
--

LOCK TABLES `candidato` WRITE;
/*!40000 ALTER TABLE `candidato` DISABLE KEYS */;
INSERT INTO `candidato` VALUES (13,'Enrico','Adamelli','enry@adams.it','3980987695','2000-01-16','Univaq',12,1),(14,'Lorenzo','Solazzo','lollo@sola.it','3859786959','2000-05-11','Univaq',11,1),(15,'Martina','Nolletti','martina@nolletti.org','3459319859','1999-05-24','UniBocconi',34,9),(16,'Samuel','Cipriani','samuel@cipciop.it','3893833983','2000-02-29','Univaq',102,9),(17,'Andrea','Fonte','andrea@fonte.com','1234567892','1999-05-13','Univaq',0,10),(18,'Samuel','Cipriani','samuel@cipciop.it','3893833983','2000-02-29','Univaq',0,10),(19,'Lorenzo','Solazzo','lollo@sola.it','3859786959','2000-05-11','Univaq',1,9);
/*!40000 ALTER TABLE `candidato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evento` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `regolamento` text NOT NULL,
  `data_inizio` date NOT NULL,
  `data_fine` date NOT NULL,
  `ora_inizio` varchar(8) NOT NULL,
  `ora_fine` varchar(8) NOT NULL,
  `luogo` varchar(50) NOT NULL,
  `report_risultati` text,
  `report_statistiche` text,
  `numero_preferenze_esprimibili` int(10) unsigned NOT NULL,
  `stato` enum('programmato','in corso','terminato') NOT NULL DEFAULT 'programmato',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` VALUES (1,'RAPPRESENTANTI UNI','si vota una persona per scheda elettorale','2021-02-02','2021-02-11','8:00','20:00','L\'Aquila','Enrico Adamelli   12\nLorenzo Solazzo   11\n','ssss',1,'in corso'),(9,'Rappresentanti Prof','Votate max 2 persone','2021-02-03','2021-02-07','11:00','18:00','Coppito 2','Samuel Cipriani   102\nMartina Nolletti   34\nLorenzo Solazzo   1\n','Martina ha preso 0 voti.',2,'in corso'),(10,'Elezione segreteria','Massimo 3 preferenze','2021-02-17','2021-02-22','10:00','23:00','Blocco 0',NULL,NULL,3,'programmato');
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prenotazione`
--

DROP TABLE IF EXISTS `prenotazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prenotazione` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_utente` int(10) unsigned NOT NULL,
  `id_evento` int(10) unsigned NOT NULL,
  `tipo_prenotazione` enum('online','in presenza') DEFAULT NULL,
  `stato` enum('si','no') NOT NULL DEFAULT 'no',
  `certificato` blob,
  PRIMARY KEY (`id`),
  KEY `fk_utente_has_evento_utente2` (`id_utente`),
  KEY `fk_utente_has_evento_evento3` (`id_evento`),
  CONSTRAINT `fk_utente_has_evento_evento3` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_utente_has_evento_utente2` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prenotazione`
--

LOCK TABLES `prenotazione` WRITE;
/*!40000 ALTER TABLE `prenotazione` DISABLE KEYS */;
INSERT INTO `prenotazione` VALUES (1,2,1,'online','no',NULL),(11,17,9,'online','si',NULL);
/*!40000 ALTER TABLE `prenotazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `turnazione`
--

DROP TABLE IF EXISTS `turnazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turnazione` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_utente` int(10) unsigned NOT NULL,
  `id_evento` int(10) unsigned NOT NULL,
  `fascia` enum('mattina','pomeriggio','sera') NOT NULL,
  `data_giorno` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_utente_has_evento_utente` (`id_utente`),
  KEY `fk_utente_has_evento_evento1` (`id_evento`),
  CONSTRAINT `fk_utente_has_evento_evento1` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_utente_has_evento_utente` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `turnazione`
--

LOCK TABLES `turnazione` WRITE;
/*!40000 ALTER TABLE `turnazione` DISABLE KEYS */;
INSERT INTO `turnazione` VALUES (4,3,1,'mattina','2021-03-11'),(5,14,9,'pomeriggio','2021-02-10'),(6,14,10,'sera','2021-02-18');
/*!40000 ALTER TABLE `turnazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,'Ciccio','Parrucco','aba@gmail.com','a','a','1230928292','1999-06-16','professore','AQ','DISIM','','amministratore'),(2,'Elena','Ricci','bcb@gmail.com','e','e','4562222221','2003-10-23','studente','AQ','DISIM','255222','elettore'),(3,'Chiara','Ferry','cdc@gmail.com','o','o','7892222222','2000-04-15','impiegato','AQ','DISCAB',NULL,'operatore'),(13,'Martina','Nolletti','martina@nolletti.org','martina','martina','3459319859','1999-05-24','impiegato','UniBocconi','DISIM',NULL,'amministratore'),(14,'Marco','Chiavaroli','marco@chiav.top','marco','marco','3890459739','1999-08-23','studente','Univaq','DISCAB',NULL,'operatore'),(15,'Samuel','Cipriani','samuel@cipciop.it','samuel','samuel','3893833983','2000-02-29','professore','Univaq','DISIM','289282','elettore'),(16,'Lorenzo','Solazzo','lollo@sola.it','lorenzo','lorenzo','3859786959','2000-05-11','professore','Univaq','DISIM','989839','elettore'),(17,'Enrico','Adamelli','enry@adams.it','enrico','enrico','3980987695','2000-01-16','impiegato','Univaq','DISIM','123456','elettore'),(18,'Andrea','Fonte','andrea@fonte.com','andrea','andrea','1234567892','1999-05-13','professore','Univaq','DISIM','192948','elettore');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'univasa'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `aggiornaStatoEvento` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `aggiornaStatoEvento` ON SCHEDULE EVERY 30 SECOND STARTS '2021-02-08 18:41:52' ON COMPLETION NOT PRESERVE ENABLE DO call aggiornaStato */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'univasa'
--
/*!50003 DROP PROCEDURE IF EXISTS `aggiornaStato` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `aggiornaStato`()
begin update evento e set e.stato = 'in corso' where e.data_inizio<=now() and e.data_fine>=now();
update evento e set e.stato = 'terminato' where e.data_fine<now(); 
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-08 18:43:21
