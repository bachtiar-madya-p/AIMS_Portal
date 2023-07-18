-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: aims_db
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
                         `uid` varchar(50) NOT NULL,
                         `role` varchar(50) NOT NULL,
                         `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `createDt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                         `modifyDt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('2e3910ad-bd48-472d-8071-30e24f41bf2c','USER_DELETE','Delete user','2023-07-18 11:23:17','2023-07-18 11:23:17'),('4dfdb19a-47d0-4de0-a332-0d7cd2410bdc','USER_VIEW','Retrieve user details','2023-07-18 11:23:17','2023-07-18 11:23:17'),('63b64ac2-fafc-4a9b-978e-e04bfeb471b9','SYS_ADMIN','System Administrator','2023-07-18 08:38:02','2023-07-18 13:52:44'),('7a5e01b6-e26d-487d-b097-8ff8fee70239','USER_UPDATE','Update user information','2023-07-18 11:23:17','2023-07-18 11:23:17'),('7db8f690-3b7d-4173-8586-f26ce6e6a878','USER_CREATE','Create new user','2023-07-18 11:23:17','2023-07-18 11:23:17'),('c29b633f-4b88-49de-b308-fbf237ea79f3','USER_BULK','Bulk upload user','2023-07-18 11:23:17','2023-07-18 11:23:17');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `userid` varchar(50) NOT NULL,
                              `roleId` varchar(50) DEFAULT NULL,
                              `createDt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              `modifyDt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`),
                              KEY `userid` (`userid`),
                              KEY `roleId` (`roleId`),
                              CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
                              CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `roles` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'742bd1fd-96c4-4bb3-97be-717f08e0982f','63b64ac2-fafc-4a9b-978e-e04bfeb471b9','2023-07-18 13:20:18','2023-07-18 13:20:24'),(3,'742bd1fd-96c4-4bb3-97be-717f08e0982f','7db8f690-3b7d-4173-8586-f26ce6e6a878','2023-07-18 13:20:18','2023-07-18 13:20:24'),(4,'742bd1fd-96c4-4bb3-97be-717f08e0982f','4dfdb19a-47d0-4de0-a332-0d7cd2410bdc','2023-07-18 13:20:18','2023-07-18 13:20:24'),(5,'742bd1fd-96c4-4bb3-97be-717f08e0982f','7a5e01b6-e26d-487d-b097-8ff8fee70239','2023-07-18 13:20:18','2023-07-18 13:20:24'),(6,'742bd1fd-96c4-4bb3-97be-717f08e0982f','2e3910ad-bd48-472d-8071-30e24f41bf2c','2023-07-18 13:20:18','2023-07-18 13:20:24'),(7,'742bd1fd-96c4-4bb3-97be-717f08e0982f','c29b633f-4b88-49de-b308-fbf237ea79f3','2023-07-18 13:20:18','2023-07-18 13:20:24');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `userid` varchar(50) NOT NULL,
                         `firstName` varchar(128) NOT NULL,
                         `lastName` varchar(128) NOT NULL,
                         `fullName` varchar(256) NOT NULL,
                         `employeeId` varchar(20) NOT NULL,
                         `gender` varchar(6) NOT NULL,
                         `email` varchar(50) NOT NULL,
                         `mobile` varchar(20) NOT NULL,
                         `active` tinyint DEFAULT '1',
                         `createDt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                         `modifyDt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('742bd1fd-96c4-4bb3-97be-717f08e0982f','System','Administratir','System Administratir','xxx-xxx-xxx-x','Male','sadmin@mail.com','08123456789',1,'2023-07-18 08:39:45','2023-07-18 11:33:02');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_authentication`
--

DROP TABLE IF EXISTS `users_authentication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_authentication` (
                                        `userid` varchar(50) NOT NULL,
                                        `salt` varchar(128) NOT NULL,
                                        `password` varchar(128) NOT NULL,
                                        `locked` tinyint DEFAULT '0',
                                        `createDt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                        `lastLogin` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        PRIMARY KEY (`userid`),
                                        CONSTRAINT `users_authentication_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_authentication`
--

LOCK TABLES `users_authentication` WRITE;
/*!40000 ALTER TABLE `users_authentication` DISABLE KEYS */;
INSERT INTO `users_authentication` VALUES ('742bd1fd-96c4-4bb3-97be-717f08e0982f','39hs8k','364dbd283b910033a38027fa1ab6af5d01a671995760fbaa314896f24a440e1b',0,'2023-07-18 09:46:19','2023-07-18 13:49:25');
/*!40000 ALTER TABLE `users_authentication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'aims_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-18 20:53:05
