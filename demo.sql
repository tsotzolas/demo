-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: demodb
-- ------------------------------------------------------
-- Server version	8.0.25

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


SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;

SET character_set_client = @saved_cs_client;

--
-- Table structure for table `tblcompany`
--

DROP TABLE IF EXISTS `tblcompany`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblcompany` (
                              `idcompany` int NOT NULL AUTO_INCREMENT,
                              `companyname` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                              PRIMARY KEY (`idcompany`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcompany`
--

LOCK TABLES `tblcompany` WRITE;
/*!40000 ALTER TABLE `tblcompany` DISABLE KEYS */;
INSERT INTO `tblcompany` VALUES (9,'Test Name');
/*!40000 ALTER TABLE `tblcompany` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblelements`
--

DROP TABLE IF EXISTS `tblelements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblelements` (
                               `idelement` int NOT NULL AUTO_INCREMENT,
                               `element_name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
                               `element_desc` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
                               `element_id_desc` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
                               `ref_page_id` int DEFAULT NULL,
                               `visible` int NOT NULL DEFAULT '1',
                               PRIMARY KEY (`idelement`),
                               KEY `fk_page_id_idx` (`ref_page_id`),
                               CONSTRAINT `fk_page_id` FOREIGN KEY (`ref_page_id`) REFERENCES `tblpages` (`idpage`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblelements`
--

LOCK TABLES `tblelements` WRITE;
/*!40000 ALTER TABLE `tblelements` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblelements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblemployee`
--

DROP TABLE IF EXISTS `tblemployee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblemployee` (
                               `idEmployee` int NOT NULL AUTO_INCREMENT,
                               `idcompany` int DEFAULT NULL,
                               `EmployeeFname` varchar(45) DEFAULT NULL,
                               `EmployeeLname` varchar(45) DEFAULT NULL,
                               `EmployeeEmail` varchar(45) DEFAULT NULL,
                               `personalemail` varchar(45) DEFAULT NULL,
                               `gender_male` int NOT NULL DEFAULT '1',
                               `isactive` tinyint(1) DEFAULT '1',
                               PRIMARY KEY (`idEmployee`),
                               KEY `fk_emploeee_company` (`idcompany`),
                               CONSTRAINT `fk_emploeee` FOREIGN KEY (`idcompany`) REFERENCES `tblcompany` (`idcompany`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblemployee`
--

LOCK TABLES `tblemployee` WRITE;
/*!40000 ALTER TABLE `tblemployee` DISABLE KEYS */;
INSERT INTO `tblemployee` VALUES (2,9,'test','test','test@test.com',NULL,1,1);
/*!40000 ALTER TABLE `tblemployee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblglobalparam`
--

DROP TABLE IF EXISTS `tblglobalparam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblglobalparam` (
                                  `globalparamID` int NOT NULL AUTO_INCREMENT,
                                  `globalparamDesc` varchar(45) DEFAULT NULL,
                                  `globalparamValue` varchar(400) DEFAULT NULL,
                                  `comments` varchar(1000) DEFAULT NULL,
                                  PRIMARY KEY (`globalparamID`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb3 COMMENT='Γενικές Παράμετοι Εφαρμογής';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblglobalparam`
--

LOCK TABLES `tblglobalparam` WRITE;
/*!40000 ALTER TABLE `tblglobalparam` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblglobalparam` ENABLE KEYS */;
UNLOCK TABLES;





--
-- Table structure for table `tblpages`
--

DROP TABLE IF EXISTS `tblpages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblpages` (
                            `idpage` int NOT NULL AUTO_INCREMENT,
                            `page_name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
                            `page_desc` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
                            `page_path` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
                            `menu_label` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
                            `menu_icon` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
                            PRIMARY KEY (`idpage`),
                            UNIQUE KEY `page_name_UNIQUE` (`page_name`),
                            UNIQUE KEY `page_desc_UNIQUE` (`page_desc`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblpages`
--

LOCK TABLES `tblpages` WRITE;
/*!40000 ALTER TABLE `tblpages` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblpages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblpasshistory`
--

DROP TABLE IF EXISTS `tblpasshistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblpasshistory` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `idUser` int DEFAULT NULL,
                                  `old_password` varchar(150) NOT NULL,
                                  `salt` varchar(200) NOT NULL,
                                  `change_date` datetime NOT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `tblpasshistory_tblusers_idUser_fk` (`idUser`),
                                  CONSTRAINT `tblpasshistory_tblusers_idUser_fk` FOREIGN KEY (`idUser`) REFERENCES `tblusers` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblpasshistory`
--

LOCK TABLES `tblpasshistory` WRITE;
/*!40000 ALTER TABLE `tblpasshistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblpasshistory` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table `tblrights`
--

DROP TABLE IF EXISTS `tblrights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblrights` (
                             `idRight` int NOT NULL AUTO_INCREMENT,
                             `RightDesc` varchar(100) NOT NULL,
                             PRIMARY KEY (`idRight`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblrights`
--

LOCK TABLES `tblrights` WRITE;
/*!40000 ALTER TABLE `tblrights` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblrights` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblrolerights`
--

DROP TABLE IF EXISTS `tblrolerights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblrolerights` (
                                 `idRole` int NOT NULL,
                                 `idElement` int NOT NULL,
                                 `idPage` int NOT NULL,
                                 PRIMARY KEY (`idRole`,`idElement`,`idPage`),
                                 KEY `fk_tblRoleRights_tblElements_idx` (`idElement`),
                                 KEY `fk_tblRoleRights_tblPages_idx` (`idPage`),
                                 CONSTRAINT `fk_tblRoleRights_tblElements` FOREIGN KEY (`idElement`) REFERENCES `tblelements` (`idelement`),
                                 CONSTRAINT `fk_tblRoleRights_tblPages` FOREIGN KEY (`idPage`) REFERENCES `tblpages` (`idpage`),
                                 CONSTRAINT `fk_tblRoleRights_tblRoles` FOREIGN KEY (`idRole`) REFERENCES `tblroles` (`idRole`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblrolerights`
--

LOCK TABLES `tblrolerights` WRITE;
/*!40000 ALTER TABLE `tblrolerights` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblrolerights` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblroles`
--

DROP TABLE IF EXISTS `tblroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblroles` (
                            `idRole` int NOT NULL AUTO_INCREMENT,
                            `RoleDesc` varchar(45) NOT NULL,
                            PRIMARY KEY (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblroles`
--

LOCK TABLES `tblroles` WRITE;
/*!40000 ALTER TABLE `tblroles` DISABLE KEYS */;
INSERT INTO `tblroles` VALUES (1,'Administrator');
/*!40000 ALTER TABLE `tblroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblusers`
--

DROP TABLE IF EXISTS `tblusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblusers` (
                            `idUser` int NOT NULL AUTO_INCREMENT,
                            `username` varchar(30) NOT NULL,
                            `email` varchar(255) DEFAULT NULL,
                            `password` varchar(150) NOT NULL,
                            `saltedpassword` varchar(150) DEFAULT NULL,
                            `salt` varchar(100) DEFAULT NULL,
                            `newsalt` varchar(100) DEFAULT NULL,
                            `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                            `idEmployee` int NOT NULL DEFAULT '0' COMMENT '0 ο χρήστης είναι ανενεργός\n1 ο χρήστης είναι ενεργός',
                            `idRole` int NOT NULL DEFAULT '0',
                            `userActive` int NOT NULL DEFAULT '1' COMMENT '0 ο χρήστης είναι ανενεργός\n1 ο χρήστης είναι ενεργός',
                            `theme` varchar(45) DEFAULT NULL,
                            `layoutMode` varchar(45) DEFAULT NULL,
                            `lightMenu` tinyint(1) DEFAULT NULL,
                            `avatar` text,
                            `specialEffect` tinyint(1) DEFAULT '0',
                            `altpass` varchar(150) DEFAULT NULL,
                            `expDate` datetime DEFAULT NULL,
                            `lastLogin` datetime DEFAULT NULL,
                            `wrongCred` int DEFAULT '0',
                            `lastLoginIp` varchar(50) DEFAULT NULL COMMENT 'Η τελευταία IP με την οποία έκανα login το χρήστης. ',
                            `locked_until` datetime DEFAULT NULL,
                            PRIMARY KEY (`idUser`),
                            UNIQUE KEY `username_UNIQUE` (`username`),
                            UNIQUE KEY `idEmployee_UNIQUE` (`idEmployee`),
                            KEY `fk_tblUsers_tblEmployee1_idx` (`idEmployee`),
                            KEY `fk_tblUsers_tblRoles1_idx` (`idRole`),
                            CONSTRAINT `fk_tblUsers_tblEmployee1` FOREIGN KEY (`idEmployee`) REFERENCES `tblemployee` (`idEmployee`),
                            CONSTRAINT `fk_tblUsers_tblRoles1` FOREIGN KEY (`idRole`) REFERENCES `tblroles` (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblusers`
--

LOCK TABLES `tblusers` WRITE;
/*!40000 ALTER TABLE `tblusers` DISABLE KEYS */;
INSERT INTO `tblusers` VALUES (3,'test',NULL,'f2b7aa51f4885463bb198e3b569e3dd5b67d06aa','a5a1ac5401e190a4b2bbd9a68c61fb59449e3d3d7c832ec202a66fe55621f6cb7fc4743eba5900831255cf0887a7d5c114c8c8853adb0a9c8145131d93727f72','e7f3f234dfd8fd9633568ad18525ec15',NULL,'2024-03-23 14:22:56',2,1,1,'green-pink','overlay',0,NULL,0,NULL,'2025-10-15 15:39:54','2023-08-16 10:28:30',0,'192.168.10.101',NULL);
/*!40000 ALTER TABLE `tblusers` ENABLE KEYS */;
UNLOCK TABLES;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-21 19:09:02
