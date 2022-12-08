-- MySQL dump 10.13  Distrib 5.7.31, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: cargo_db
-- ------------------------------------------------------
-- Server version	5.7.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `DELIVERY_TABLE`
--

DROP TABLE IF EXISTS `DELIVERY_TABLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DELIVERY_TABLE` (
  `id` int(11) NOT NULL,
  `info` text COLLATE utf8mb4_bin,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DELIVERY_TABLE`
--

LOCK TABLES `DELIVERY_TABLE` WRITE;
/*!40000 ALTER TABLE `DELIVERY_TABLE` DISABLE KEYS */;
INSERT INTO `DELIVERY_TABLE` VALUES (2,'[{\"content\":\"哈哈哈\",\"time\":\"2022/11/17 21:53:11\",\"active\":false},{\"content\":\"快递已揽收\",\"time\":\"2022/11/17 21:56:13\",\"active\":false},{\"content\":\"测试最新\",\"time\":\"2022/11/17 21:57:13\",\"active\":false},{\"content\":\"快递已签收\",\"time\":\"2022/11/17 23:02:30\",\"active\":true}]'),(3,'[{\"content\":\"123\",\"time\":\"2022/11/17 21:59:53\",\"active\":true}]');
/*!40000 ALTER TABLE `DELIVERY_TABLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PACKAGE_TABLE`
--

DROP TABLE IF EXISTS `PACKAGE_TABLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PACKAGE_TABLE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` text COLLATE utf8mb4_bin,
  `startplace` text COLLATE utf8mb4_bin,
  `endplace` text COLLATE utf8mb4_bin,
  `status` text COLLATE utf8mb4_bin,
  `sender` text COLLATE utf8mb4_bin,
  `receiver` text COLLATE utf8mb4_bin,
  `time` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PACKAGE_TABLE`
--

LOCK TABLES `PACKAGE_TABLE` WRITE;
/*!40000 ALTER TABLE `PACKAGE_TABLE` DISABLE KEYS */;
INSERT INTO `PACKAGE_TABLE` VALUES (2,'SQUARE ENIX','加雷马','艾欧泽亚','艾欧泽亚','吉田直树','异国诗人'),(3,'SQUARE ENIX','加雷马','艾欧泽亚','艾欧泽亚','异国诗人','吉田直树');
/*!40000 ALTER TABLE `PACKAGE_TABLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_TABLE`
--

DROP TABLE IF EXISTS `USER_TABLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_TABLE` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` text COLLATE utf8mb4_bin,
  `passwd` text COLLATE utf8mb4_bin,
  `contact` text COLLATE utf8mb4_bin,
  `type` int(11) DEFAULT '1',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `UID` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_TABLE`
--

LOCK TABLES `USER_TABLE` WRITE;
/*!40000 ALTER TABLE `USER_TABLE` DISABLE KEYS */;
INSERT INTO `USER_TABLE` VALUES (1,'kmo','12345','1234@111.com',1),(2,'kno','12345','12345@qq.com',1),(3,'querty','123','123',1),(4,'admin','admin','admin',0),(5,'demo','demo','123',1),(6,'吉田直树','12345','sqex@sqex.com',1),(7,'测试','12345','111@111.com',1);
/*!40000 ALTER TABLE `USER_TABLE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-17 23:37:27
