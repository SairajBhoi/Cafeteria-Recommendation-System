-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: database-1.cx24yqcse9i2.ap-south-1.rds.amazonaws.com    Database: cafeteria
-- ------------------------------------------------------
-- Server version	8.0.35

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `ChefRecommendation`
--

DROP TABLE IF EXISTS `ChefRecommendation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ChefRecommendation` (
  `recommendationID` int NOT NULL AUTO_INCREMENT,
  `recommendationDate` date NOT NULL,
  `itemID` int NOT NULL,
  `mealTypeID` varchar(3) NOT NULL,
  `votingEndTime` datetime NOT NULL,
  PRIMARY KEY (`recommendationID`),
  KEY `itemID` (`itemID`),
  KEY `mealTypeID` (`mealTypeID`),
  CONSTRAINT `ChefRecommendation_ibfk_1` FOREIGN KEY (`itemID`) REFERENCES `FoodMenuItem` (`itemID`),
  CONSTRAINT `ChefRecommendation_ibfk_2` FOREIGN KEY (`mealTypeID`) REFERENCES `MealType` (`mealTypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ChefRecommendation`
--

LOCK TABLES `ChefRecommendation` WRITE;
/*!40000 ALTER TABLE `ChefRecommendation` DISABLE KEYS */;
/*!40000 ALTER TABLE `ChefRecommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FeedbackSummary`
--

DROP TABLE IF EXISTS `FeedbackSummary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FeedbackSummary` (
  `summaryID` int NOT NULL AUTO_INCREMENT,
  `itemID` int DEFAULT NULL,
  `date` date NOT NULL,
  `averageHygieneRating` decimal(2,1) DEFAULT NULL,
  `averageQualityRating` decimal(2,1) DEFAULT NULL,
  `averageTasteRating` decimal(2,1) DEFAULT NULL,
  `overallSentiment` tinytext,
  PRIMARY KEY (`summaryID`),
  KEY `itemID` (`itemID`),
  CONSTRAINT `FeedbackSummary_ibfk_1` FOREIGN KEY (`itemID`) REFERENCES `FoodMenuItem` (`itemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FeedbackSummary`
--

LOCK TABLES `FeedbackSummary` WRITE;
/*!40000 ALTER TABLE `FeedbackSummary` DISABLE KEYS */;
/*!40000 ALTER TABLE `FeedbackSummary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FoodMenuItem`
--

DROP TABLE IF EXISTS `FoodMenuItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FoodMenuItem` (
  `itemID` int NOT NULL AUTO_INCREMENT,
  `nameOfFood` varchar(30) NOT NULL,
  `foodPrice` decimal(5,2) NOT NULL,
  `foodAvailable` tinyint(1) NOT NULL,
  PRIMARY KEY (`itemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FoodMenuItem`
--

LOCK TABLES `FoodMenuItem` WRITE;
/*!40000 ALTER TABLE `FoodMenuItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `FoodMenuItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FoodMenuItemMealType`
--

DROP TABLE IF EXISTS `FoodMenuItemMealType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FoodMenuItemMealType` (
  `itemID` int NOT NULL,
  `mealTypeID` varchar(3) NOT NULL,
  PRIMARY KEY (`itemID`,`mealTypeID`),
  KEY `mealTypeID` (`mealTypeID`),
  CONSTRAINT `FoodMenuItemMealType_ibfk_1` FOREIGN KEY (`itemID`) REFERENCES `FoodMenuItem` (`itemID`),
  CONSTRAINT `FoodMenuItemMealType_ibfk_2` FOREIGN KEY (`mealTypeID`) REFERENCES `MealType` (`mealTypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FoodMenuItemMealType`
--

LOCK TABLES `FoodMenuItemMealType` WRITE;
/*!40000 ALTER TABLE `FoodMenuItemMealType` DISABLE KEYS */;
/*!40000 ALTER TABLE `FoodMenuItemMealType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MealType`
--

DROP TABLE IF EXISTS `MealType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MealType` (
  `mealTypeID` varchar(3) NOT NULL,
  `mealTypeName` varchar(20) NOT NULL,
  PRIMARY KEY (`mealTypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MealType`
--

LOCK TABLES `MealType` WRITE;
/*!40000 ALTER TABLE `MealType` DISABLE KEYS */;
/*!40000 ALTER TABLE `MealType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Notifications`
--

DROP TABLE IF EXISTS `Notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Notifications` (
  `notificationID` int NOT NULL AUTO_INCREMENT,
  `notificationType` enum('NewItem','ChefRecommendation','DeleteItem','PriceUpdate','AvailabilityStatus') NOT NULL,
  `itemID` int DEFAULT NULL,
  `notificationMessage` text NOT NULL,
  `notificationDate` date NOT NULL,
  `endDate` date NOT NULL,
  PRIMARY KEY (`notificationID`),
  KEY `itemID` (`itemID`),
  CONSTRAINT `Notifications_ibfk_1` FOREIGN KEY (`itemID`) REFERENCES `FoodMenuItem` (`itemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Notifications`
--

LOCK TABLES `Notifications` WRITE;
/*!40000 ALTER TABLE `Notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `Notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserFeedbackOnFoodItem`
--

DROP TABLE IF EXISTS `UserFeedbackOnFoodItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserFeedbackOnFoodItem` (
  `feedbackID` int NOT NULL AUTO_INCREMENT,
  `userID` varchar(13) NOT NULL,
  `itemID` int NOT NULL,
  `feedbackGivenDate` date NOT NULL,
  `hygieneRating` int DEFAULT NULL,
  `qualityRating` int DEFAULT NULL,
  `tasteRating` int DEFAULT NULL,
  `feedbackMessage` tinytext,
  PRIMARY KEY (`feedbackID`),
  UNIQUE KEY `userID` (`userID`,`itemID`,`feedbackGivenDate`),
  KEY `itemID` (`itemID`),
  CONSTRAINT `UserFeedbackOnFoodItem_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `UserInformation` (`userID`),
  CONSTRAINT `UserFeedbackOnFoodItem_ibfk_2` FOREIGN KEY (`itemID`) REFERENCES `FoodMenuItem` (`itemID`),
  CONSTRAINT `UserFeedbackOnFoodItem_chk_1` CHECK ((`hygieneRating` between 1 and 5)),
  CONSTRAINT `UserFeedbackOnFoodItem_chk_2` CHECK ((`qualityRating` between 1 and 5)),
  CONSTRAINT `UserFeedbackOnFoodItem_chk_3` CHECK ((`tasteRating` between 1 and 5))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserFeedbackOnFoodItem`
--

LOCK TABLES `UserFeedbackOnFoodItem` WRITE;
/*!40000 ALTER TABLE `UserFeedbackOnFoodItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserFeedbackOnFoodItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserInformation`
--

DROP TABLE IF EXISTS `UserInformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserInformation` (
  `userID` varchar(13) NOT NULL,
  `username` varchar(30) NOT NULL,
  `roleID` varchar(10) NOT NULL,
  PRIMARY KEY (`userID`),
  KEY `fk_user_role` (`roleID`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`roleID`) REFERENCES `UserRole` (`roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserInformation`
--

LOCK TABLES `UserInformation` WRITE;
/*!40000 ALTER TABLE `UserInformation` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserInformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserNotifications`
--

DROP TABLE IF EXISTS `UserNotifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserNotifications` (
  `userID` varchar(13) NOT NULL,
  `notificationID` int NOT NULL,
  `isOpened` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userID`,`notificationID`),
  KEY `notificationID` (`notificationID`),
  CONSTRAINT `UserNotifications_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `UserInformation` (`userID`),
  CONSTRAINT `UserNotifications_ibfk_2` FOREIGN KEY (`notificationID`) REFERENCES `Notifications` (`notificationID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserNotifications`
--

LOCK TABLES `UserNotifications` WRITE;
/*!40000 ALTER TABLE `UserNotifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserNotifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserRole`
--

DROP TABLE IF EXISTS `UserRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserRole` (
  `roleID` varchar(10) NOT NULL,
  `roleName` varchar(20) NOT NULL,
  PRIMARY KEY (`roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserRole`
--

LOCK TABLES `UserRole` WRITE;
/*!40000 ALTER TABLE `UserRole` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserRole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Vote`
--

DROP TABLE IF EXISTS `Vote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Vote` (
  `userID` varchar(13) NOT NULL,
  `voteGivenDate` date NOT NULL,
  `mealTypeID` varchar(3) NOT NULL,
  `itemID` int DEFAULT NULL,
  PRIMARY KEY (`userID`,`voteGivenDate`,`mealTypeID`),
  KEY `mealTypeID` (`mealTypeID`),
  KEY `itemID` (`itemID`),
  CONSTRAINT `Vote_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `UserInformation` (`userID`),
  CONSTRAINT `Vote_ibfk_2` FOREIGN KEY (`mealTypeID`) REFERENCES `MealType` (`mealTypeID`),
  CONSTRAINT `Vote_ibfk_3` FOREIGN KEY (`itemID`) REFERENCES `FoodMenuItem` (`itemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Vote`
--

LOCK TABLES `Vote` WRITE;
/*!40000 ALTER TABLE `Vote` DISABLE KEYS */;
/*!40000 ALTER TABLE `Vote` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-10  8:48:30
