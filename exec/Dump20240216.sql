-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: i10a108.p.ssafy.io    Database: sgdc_db
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `badge`
--

DROP TABLE IF EXISTS `badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `badge` (
  `badge_id` int NOT NULL AUTO_INCREMENT,
  `badge_name` varchar(20) DEFAULT NULL,
  `badge_detail` varchar(200) DEFAULT NULL,
  `badge_img` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`badge_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `badge`
--

LOCK TABLES `badge` WRITE;
/*!40000 ALTER TABLE `badge` DISABLE KEYS */;
INSERT INTO `badge` VALUES (1,'기린이','기상 경쟁 승리 횟수 5회 이상 - 열심히 일어나셨네요','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/girini.png'),(2,'싸피를 나온 암탉','기상 경쟁 승리 횟수 10회 이상 - 이젠 싸피 학생들을 깨워주는 닭...','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/amtark.png'),(3,'굶어 죽은 새','기상 경쟁 패배 횟수 5회 이상 - 일찍 일어난 새가 벌레를 잡아요','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/diechicken.png'),(4,'맘스터치','기상 경쟁 패배 횟수 10회 이상 - 으이구 일어나!! 해가 중천이야!!','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/momstouch.png'),(5,'가장 따뜻한 로봇','알고리즘 경쟁 승리 횟수 5회 이상 - 혹시 당신은 알고리즘 푸는 로봇?','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/gaddaro.png'),(6,'깡통 로봇','알고리즘 경쟁 패배 횟수 5회 이상 - 이 쓸모없는 깡통 녀석','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/kkangtong.png'),(7,'트리케라톱스','식단 경쟁 승리 3회 이상 - 프로토케라톱스는 초식 공룡입니다.','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/triceratops.png'),(8,'코끼리','식단 경쟁 승리 5회 이상 - 코끼리는 초식 동물입니다.','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/elephant.png'),(9,'꽃사슴','식단 경쟁 승리 7회 이상 - 꽃사슴은 초식 동물입니다.','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/Cervus+nippon.png'),(10,'요정','식단 경쟁 승리 10회 이상 - 이슬만 먹고 사시는군요','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/fairy.png'),(11,'밥도둑','식단 경쟁 패배 5회 이상 - 내 밥가져와','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/ricetheif.png'),(12,'언더아머 단속반','운동 경쟁 승리 5회 이상 - 500이하 언더아머 금지','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/underarmor.png'),(13,'김종꾹','운동 경쟁 승리 10회 이상 - 본업은 가수에요','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/kim.png'),(14,'숨쉬기 운동 금지','운동 경쟁 패배 5회 이상 - 숨은 잘 쉬어요','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/breath.png'),(15,'운동 전문 실패자','운동 경쟁 패배 10회 이상 - 하면 잘 하는 사람이..','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/fail.png'),(16,'엉덩이 1t','스터디 시간 경쟁 승리 5회 이상 - 엉덩이가 진짜 무거우시네요.','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/1t.png'),(17,'피노키오','거짓으로 인증했을 시 - 우리한테 왜 그랬어요..','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/pinokio.png'),(18,'싸피 보안관','거짓 인증 신고하기 5회 - 거짓말쟁이 피노키오 발견! 탕!','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/BADGE/ssafypolice.png');
/*!40000 ALTER TABLE `badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` enum('morning','algorithm','running','studyTime','diet','selfControl') DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'morning'),(2,'algorithm'),(3,'running'),(4,'studyTime'),(5,'diet'),(6,'selfControl');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compet_detail`
--

DROP TABLE IF EXISTS `compet_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compet_detail` (
  `compet_result_id` int NOT NULL AUTO_INCREMENT,
  `compet_result` enum('SEND_WIN','RECEIVE_WIN','BOTH_WIN','BOTH_FAIL') DEFAULT NULL,
  PRIMARY KEY (`compet_result_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compet_detail`
--

LOCK TABLES `compet_detail` WRITE;
/*!40000 ALTER TABLE `compet_detail` DISABLE KEYS */;
INSERT INTO `compet_detail` VALUES (1,'BOTH_WIN'),(2,'BOTH_WIN'),(3,'BOTH_WIN'),(4,'BOTH_WIN'),(5,'BOTH_WIN'),(6,'BOTH_WIN'),(7,'BOTH_WIN'),(8,'BOTH_WIN'),(9,'BOTH_WIN'),(10,'BOTH_WIN'),(11,'BOTH_WIN'),(12,'BOTH_WIN'),(13,'SEND_WIN');
/*!40000 ALTER TABLE `compet_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competition`
--

DROP TABLE IF EXISTS `competition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `competition` (
  `compet_detail_id` int DEFAULT NULL,
  `compet_id` int NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `done_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`compet_id`),
  UNIQUE KEY `UK_7wcu4s82btxqjubatx3vc2lmy` (`compet_detail_id`),
  CONSTRAINT `FKdj4a8jlxe98dkr1ut90yln8ds` FOREIGN KEY (`compet_detail_id`) REFERENCES `compet_detail` (`compet_result_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition`
--

LOCK TABLES `competition` WRITE;
/*!40000 ALTER TABLE `competition` DISABLE KEYS */;
INSERT INTO `competition` VALUES (2,1,'2024-02-15 17:56:45.369554','2024-02-15 23:53:47.698091'),(1,2,'2024-02-15 17:56:47.602697','2024-02-15 23:51:41.577212'),(4,3,'2024-02-15 18:06:45.356235','2024-02-16 00:02:22.141917'),(3,4,'2024-02-15 18:07:15.167991','2024-02-15 23:55:08.708069'),(13,5,'2024-02-15 18:09:40.439601','2024-02-15 23:52:33.514043'),(5,6,'2024-02-15 18:09:42.666970','2024-02-15 23:53:17.473909'),(6,7,'2024-02-15 18:11:32.345173','2024-02-16 00:07:16.463742'),(NULL,8,'2024-02-15 18:11:34.621196','2024-02-16 00:08:42.531887'),(NULL,9,'2024-02-15 18:12:12.525786','2024-02-16 00:05:57.590943'),(10,10,'2024-02-15 18:12:35.865820','2024-02-15 23:51:48.684090'),(11,11,'2024-02-15 18:12:50.337886','2024-02-15 23:09:17.618394'),(7,12,'2024-02-15 18:17:39.010762','2024-02-16 00:17:35.774486'),(8,13,'2024-02-15 18:17:46.570173','2024-02-16 00:17:39.186648'),(9,14,'2024-02-15 18:18:27.247366','2024-02-16 00:17:26.465946'),(NULL,15,'2024-02-15 18:18:54.933562','2024-02-16 00:18:36.850499'),(12,16,'2024-02-16 08:28:35.624070','2024-02-16 08:33:34.073437');
/*!40000 ALTER TABLE `competition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feed`
--

DROP TABLE IF EXISTS `feed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feed` (
  `compet_id` int DEFAULT NULL,
  `feed_id` int NOT NULL AUTO_INCREMENT,
  `feed_like_num` int DEFAULT NULL,
  `privacy` bit(1) DEFAULT NULL,
  `views` int DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `feed_content` varchar(200) DEFAULT NULL,
  `feed_img` varchar(200) DEFAULT NULL,
  `feed_title` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`feed_id`),
  UNIQUE KEY `UK_erb6tam38es874acr9e1sx6kw` (`compet_id`),
  CONSTRAINT `FKryx3inr0wgw1knnaotd22qwuc` FOREIGN KEY (`compet_id`) REFERENCES `competition` (`compet_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feed`
--

LOCK TABLES `feed` WRITE;
/*!40000 ALTER TABLE `feed` DISABLE KEYS */;
INSERT INTO `feed` VALUES (2,1,4,_binary '\0',44,'2024-02-15 18:01:34.143231',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/3_2_%EA%B8%B0%EC%83%81.PNG','[기상] 태범아발표잘해라님과 지니님이 공동승리 하였습니다!'),(1,2,5,_binary '\0',52,'2024-02-15 18:01:42.593883',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/4_1_1694679858306_0.png','[식단] 통통한선인장님과 지니님이 공동승리 하였습니다!'),(4,3,4,_binary '\0',50,'2024-02-15 18:08:02.850743',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_4_%EC%8B%9D%EB%8B%A8%EC%9D%B8%EC%A6%9D.png','[식단] 지니님과 통통한선인장님이 공동승리 하였습니다!'),(3,4,5,_binary '\0',53,'2024-02-15 18:08:07.481722',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/3_3_%EA%B3%B5%EB%B6%80%EC%8B%9C%EA%B0%84.jpg','[스터디] 태범아발표잘해라님과 지니님이 공동승리 하였습니다!'),(6,5,4,_binary '\0',51,'2024-02-15 18:11:48.159229',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/4_6_KakaoTalk_20240215_181117423.jpg','[운동] 통통한선인장님과 태범아발표잘해라님이 공동승리 하였습니다!'),(7,6,4,_binary '\0',45,'2024-02-15 18:16:32.398601',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_7_%EC%9A%B4%EB%8F%99%EC%9D%B8%EC%A6%9D1.png','[운동] 지니님과 춘식이사생팬님이 공동승리 하였습니다!'),(12,7,4,_binary '\0',42,'2024-02-15 18:18:25.238492',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/4_12_2021091301491_0.jpg','[기상] 통통한선인장님과 춘식이사생팬님이 공동승리 하였습니다!'),(13,8,4,_binary '\0',41,'2024-02-15 18:19:13.519927',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/4_13_SSI_20170823165908_V.jpg','[절제] 통통한선인장님과 지니님이 공동승리 하였습니다!'),(14,9,3,_binary '\0',39,'2024-02-15 18:20:47.515422',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/4_14_121.PNG','[알고리즘] 통통한선인장님과 춘식이사생팬님이 공동승리 하였습니다!'),(10,10,3,_binary '\0',35,'2024-02-15 18:22:41.432353',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/3_10_%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98.PNG','[알고리즘] 태범아발표잘해라님과 춘식님이 공동승리 하였습니다!'),(11,11,1,_binary '\0',21,'2024-02-15 23:09:17.644740',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_11_%EA%B8%B0%EC%83%81%EC%B1%8C%EB%A6%B0%EC%A7%80.png','[기상] 지니님과 춘식님이 공동승리 하였습니다!'),(16,12,1,_binary '\0',10,'2024-02-16 08:33:34.092774',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_16_%EA%B8%B0%EC%83%812.png','[기상] 지니님과 춘식이사생팬님이 공동승리 하였습니다!'),(5,13,0,_binary '\0',4,'2024-02-16 09:04:20.939962',NULL,'','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_5_%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98%EC%9D%B8%EC%A6%9D.png','[알고리즘] 지니님이 태범아발표잘해라님과의 경쟁에서 이겼습니다!');
/*!40000 ALTER TABLE `feed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feed_like`
--

DROP TABLE IF EXISTS `feed_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feed_like` (
  `feed_id` int NOT NULL,
  `feed_like_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  PRIMARY KEY (`feed_like_id`),
  KEY `FKgurobtyio3jh1vn4n8tmqt842` (`feed_id`),
  KEY `FKht7sg59oaw8w3bcrbgbeip8u7` (`user_id`),
  CONSTRAINT `FKgurobtyio3jh1vn4n8tmqt842` FOREIGN KEY (`feed_id`) REFERENCES `feed` (`feed_id`),
  CONSTRAINT `FKht7sg59oaw8w3bcrbgbeip8u7` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feed_like`
--

LOCK TABLES `feed_like` WRITE;
/*!40000 ALTER TABLE `feed_like` DISABLE KEYS */;
INSERT INTO `feed_like` VALUES (1,1,3),(2,3,3),(4,4,3),(4,5,2),(3,6,2),(2,7,2),(5,8,2),(5,9,4),(4,10,4),(3,11,4),(2,12,4),(7,13,2),(6,14,2),(8,15,2),(1,16,4),(9,17,4),(10,18,4),(8,19,4),(7,20,4),(6,21,4),(10,22,1),(9,23,1),(8,24,1),(7,25,1),(6,26,1),(5,27,1),(4,28,1),(3,29,1),(2,30,1),(1,31,1),(1,34,5),(2,35,5),(4,37,5),(5,38,5),(6,39,5),(7,40,5),(8,41,5),(9,42,5),(10,43,5),(11,44,5),(3,45,5),(12,46,5);
/*!40000 ALTER TABLE `feed_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feed_report`
--

DROP TABLE IF EXISTS `feed_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feed_report` (
  `feed_report_id` int NOT NULL AUTO_INCREMENT,
  `report_feed_id` int DEFAULT NULL,
  `report_user_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `report_detail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`feed_report_id`),
  KEY `FK4mnnk8id471p653nytlba8srr` (`report_feed_id`),
  KEY `FK4sxhdgj0iv65gby5x79nx7764` (`report_user_id`),
  KEY `FKnr122yxpxyccasp1rib38x5qq` (`user_id`),
  CONSTRAINT `FK4mnnk8id471p653nytlba8srr` FOREIGN KEY (`report_feed_id`) REFERENCES `feed` (`feed_id`),
  CONSTRAINT `FK4sxhdgj0iv65gby5x79nx7764` FOREIGN KEY (`report_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKnr122yxpxyccasp1rib38x5qq` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feed_report`
--

LOCK TABLES `feed_report` WRITE;
/*!40000 ALTER TABLE `feed_report` DISABLE KEYS */;
INSERT INTO `feed_report` VALUES (1,1,3,2,'지각했어요!!!!'),(2,4,3,1,'3분 공부했어요'),(3,1,3,1,'지각했어요');
/*!40000 ALTER TABLE `feed_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow` (
  `following_id` int DEFAULT NULL,
  `friend_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`friend_id`),
  UNIQUE KEY `follow_uk` (`user_id`,`following_id`),
  KEY `FK9oqsjovu9bl95dwt8ibiy2oey` (`following_id`),
  CONSTRAINT `FK3xydb9wjv9vxkr0ohgba0n0e6` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK9oqsjovu9bl95dwt8ibiy2oey` FOREIGN KEY (`following_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES (2,1,1),(3,7,1),(4,12,1),(5,20,1),(1,2,2),(3,4,2),(4,9,2),(5,17,2),(7,33,2),(9,32,2),(1,5,3),(2,3,3),(4,10,3),(5,18,3),(1,11,4),(2,8,4),(3,6,4),(5,19,4),(7,24,4),(1,13,5),(2,14,5),(3,16,5),(4,15,5),(1,22,6),(3,21,6),(4,27,7),(5,29,7),(9,25,7),(1,30,9),(2,28,9),(5,31,9),(7,26,9);
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image_auth`
--

DROP TABLE IF EXISTS `image_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image_auth` (
  `auth_id` int NOT NULL AUTO_INCREMENT,
  `compet_id` int DEFAULT NULL,
  `matcing_id` int DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `auth_img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`auth_id`),
  UNIQUE KEY `UK_84aafvst2dt4rufiy1bvsnnn0` (`matcing_id`),
  KEY `FK22kan7yx8olwh2v69yr17tifx` (`compet_id`),
  CONSTRAINT `FK22kan7yx8olwh2v69yr17tifx` FOREIGN KEY (`compet_id`) REFERENCES `competition` (`compet_id`),
  CONSTRAINT `FKl7f2lwsgg2f63ltml45w7vu1e` FOREIGN KEY (`matcing_id`) REFERENCES `matching` (`matching_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image_auth`
--

LOCK TABLES `image_auth` WRITE;
/*!40000 ALTER TABLE `image_auth` DISABLE KEYS */;
INSERT INTO `image_auth` VALUES (1,2,2,'2024-02-15 18:00:34.861620','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/3_2_%EA%B8%B0%EC%83%81.PNG'),(2,1,12,'2024-02-15 18:00:59.064664','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/4_1_1694679858306_0.png'),(3,2,1,'2024-02-15 18:01:34.109459','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_2_%EA%B8%B0%EC%83%81%EC%B1%8C%EB%A6%B0%EC%A7%80.png'),(4,1,11,'2024-02-15 18:01:42.567774','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_1_%EC%8B%9D%EB%8B%A8%EC%9D%B8%EC%A6%9D.png'),(5,4,16,'2024-02-15 18:07:39.144552','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_4_%EC%8B%9D%EB%8B%A8%EC%9D%B8%EC%A6%9D.png'),(6,3,17,'2024-02-15 18:07:47.899603','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_3_%EC%8A%A4%ED%84%B0%EB%94%94%EC%9D%B8%EC%A6%9D2.png'),(7,4,15,'2024-02-15 18:08:02.823345','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/4_4_detail_76863_0_b1616cc8-3a25-41a7-a145-613b50eb75b4.jpg'),(8,3,18,'2024-02-15 18:08:07.457939','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/3_3_%EA%B3%B5%EB%B6%80%EC%8B%9C%EA%B0%84.jpg'),(9,5,8,'2024-02-15 18:10:07.683360','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_5_%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98%EC%9D%B8%EC%A6%9D.png'),(10,6,9,'2024-02-15 18:10:31.930239','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/3_6_%EB%A3%AC%EB%8F%99.png'),(11,6,10,'2024-02-15 18:11:48.134957','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/4_6_KakaoTalk_20240215_181117423.jpg'),(12,7,24,'2024-02-15 18:12:42.279208','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_7_%EC%9A%B4%EB%8F%99%EC%9D%B8%EC%A6%9D1.png'),(13,11,6,'2024-02-15 18:13:33.353852','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_11_%EA%B8%B0%EC%83%81%EC%B1%8C%EB%A6%B0%EC%A7%80.png'),(14,9,21,'2024-02-15 18:15:00.169094','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/4_9_KakaoTalk_20240215_181428059.jpg'),(15,8,25,'2024-02-15 18:16:13.046827','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/5_8_rn_image_picker_lib_temp_be45ecde-056c-47cf-95dc-5d64a9e7040a.jpg'),(16,7,23,'2024-02-15 18:16:32.376521','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/5_7_rn_image_picker_lib_temp_be45ecde-056c-47cf-95dc-5d64a9e7040a.jpg'),(17,12,29,'2024-02-15 18:18:16.236860','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/5_12_%EC%BA%A1%EC%B2%98.PNG'),(18,10,4,'2024-02-15 18:18:23.233293','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/3_10_%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98.PNG'),(19,12,30,'2024-02-15 18:18:25.215173','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/4_12_2021091301491_0.jpg'),(20,13,31,'2024-02-15 18:19:06.763063','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_13_%EC%A0%88%EC%A0%9C%EA%B1%B0%EC%A7%93.png'),(21,13,32,'2024-02-15 18:19:13.505502','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/4_13_SSI_20170823165908_V.jpg'),(22,14,27,'2024-02-15 18:20:08.379224','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/5_14_ssadasd.PNG'),(23,14,28,'2024-02-15 18:20:47.492425','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/4_14_121.PNG'),(24,15,33,'2024-02-15 18:21:02.702564','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/5_15_%EC%BA%A1%EC%B2%98dfsdf.PNG'),(25,10,3,'2024-02-15 18:22:41.408962','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/1_10_%EC%BA%A1%EC%B2%98.PNG'),(26,11,5,'2024-02-15 23:09:17.604699','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/1_11_%ED%99%94%EB%A9%B4%EC%BA%A1%EC%B2%982024-02-15230859.jpg'),(27,16,38,'2024-02-16 08:30:37.871924','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/2_16_%EA%B8%B0%EC%83%812.png'),(28,16,37,'2024-02-16 08:33:34.060141','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMPETITION_AUTH_IMAGE/5_16_sdasd.PNG');
/*!40000 ALTER TABLE `image_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matching`
--

DROP TABLE IF EXISTS `matching`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matching` (
  `category_id` int DEFAULT NULL,
  `compet_id` int DEFAULT NULL,
  `matching_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `compet_expiration_time` datetime(6) DEFAULT NULL,
  `compet_kind` enum('FRIEND','RANDOM') DEFAULT NULL,
  `is_sender` enum('Y','N') DEFAULT NULL,
  `match_status` enum('WAIT','ACCEPT','REJECT','EXPERATION') DEFAULT NULL,
  PRIMARY KEY (`matching_id`),
  KEY `FKltrrknjwrcbcweb6q7wdcu3x1` (`category_id`),
  KEY `FKhmk2r01og1myk02sj2vlyka2y` (`compet_id`),
  KEY `FKtpubyyhxtsc4ltr1bcn0yfbw7` (`user_id`),
  CONSTRAINT `FKhmk2r01og1myk02sj2vlyka2y` FOREIGN KEY (`compet_id`) REFERENCES `competition` (`compet_id`),
  CONSTRAINT `FKltrrknjwrcbcweb6q7wdcu3x1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `FKtpubyyhxtsc4ltr1bcn0yfbw7` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matching`
--

LOCK TABLES `matching` WRITE;
/*!40000 ALTER TABLE `matching` DISABLE KEYS */;
INSERT INTO `matching` VALUES (1,2,1,2,'2024-02-15 19:51:41.577212','FRIEND','N','ACCEPT'),(1,2,2,3,'2024-02-15 19:51:41.577187','FRIEND','Y','ACCEPT'),(2,10,3,1,'2024-02-15 19:51:48.684090','FRIEND','N','ACCEPT'),(2,10,4,3,'2024-02-15 19:51:48.684077','FRIEND','Y','ACCEPT'),(1,11,5,1,'2024-02-15 19:52:30.392121','FRIEND','N','ACCEPT'),(1,11,6,2,'2024-02-15 19:52:30.392105','FRIEND','Y','ACCEPT'),(2,5,7,3,'2024-02-15 19:52:33.514043','FRIEND','N','ACCEPT'),(2,5,8,2,'2024-02-15 19:52:33.513964','FRIEND','Y','ACCEPT'),(3,6,9,3,'2024-02-15 19:53:17.473909','FRIEND','N','ACCEPT'),(3,6,10,4,'2024-02-15 19:53:17.473899','FRIEND','Y','ACCEPT'),(5,1,11,2,'2024-02-15 19:53:47.698091','FRIEND','N','ACCEPT'),(5,1,12,4,'2024-02-15 19:53:47.698058','FRIEND','Y','ACCEPT'),(2,NULL,13,1,'2024-02-15 19:54:30.298946','FRIEND','N','WAIT'),(2,NULL,14,4,'2024-02-15 19:54:30.298937','FRIEND','Y','WAIT'),(5,4,15,4,'2024-02-15 19:55:08.708069','FRIEND','N','ACCEPT'),(5,4,16,2,'2024-02-15 19:55:08.708059','FRIEND','Y','ACCEPT'),(4,3,17,2,'2024-02-15 20:02:22.141917','FRIEND','N','ACCEPT'),(4,3,18,3,'2024-02-15 20:02:22.141907','FRIEND','Y','ACCEPT'),(2,NULL,19,3,'2024-02-15 20:02:33.477267','FRIEND','N','WAIT'),(2,NULL,20,2,'2024-02-15 20:02:33.477256','FRIEND','Y','WAIT'),(3,9,21,4,'2024-02-15 20:05:57.590943','FRIEND','N','ACCEPT'),(3,9,22,3,'2024-02-15 20:05:57.590933','FRIEND','Y','ACCEPT'),(3,7,23,5,'2024-02-15 20:07:16.463742','FRIEND','N','ACCEPT'),(3,7,24,2,'2024-02-15 20:07:16.463734','FRIEND','Y','ACCEPT'),(6,8,25,5,'2024-02-15 20:08:42.531887','FRIEND','N','ACCEPT'),(6,8,26,3,'2024-02-15 20:08:42.531871','FRIEND','Y','ACCEPT'),(2,14,27,5,'2024-02-15 20:17:26.465946','FRIEND','N','ACCEPT'),(2,14,28,4,'2024-02-15 20:17:26.465937','FRIEND','Y','ACCEPT'),(1,12,29,5,'2024-02-15 20:17:35.774486','FRIEND','N','ACCEPT'),(1,12,30,4,'2024-02-15 20:17:35.774479','FRIEND','Y','ACCEPT'),(6,13,31,2,'2024-02-15 20:17:39.186648','FRIEND','N','ACCEPT'),(6,13,32,4,'2024-02-15 20:17:39.186638','FRIEND','Y','ACCEPT'),(5,15,33,5,'2024-02-15 20:18:36.850499','FRIEND','N','ACCEPT'),(5,15,34,3,'2024-02-15 20:18:36.850489','FRIEND','Y','ACCEPT'),(6,NULL,35,2,'2024-02-16 01:08:04.709030','FRIEND','N','WAIT'),(6,NULL,36,1,'2024-02-16 01:08:04.709005','FRIEND','Y','WAIT'),(1,16,37,5,'2024-02-16 10:27:07.981393','FRIEND','N','ACCEPT'),(1,16,38,2,'2024-02-16 10:27:07.981370','FRIEND','Y','ACCEPT'),(1,NULL,39,2,'2024-02-16 10:52:22.462381','FRIEND','N','WAIT'),(1,NULL,40,1,'2024-02-16 10:52:22.462372','FRIEND','Y','WAIT');
/*!40000 ALTER TABLE `matching` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solo`
--

DROP TABLE IF EXISTS `solo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solo` (
  `category_id` int DEFAULT NULL,
  `solo_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `solo_auth_img` varchar(200) DEFAULT NULL,
  `solo_result` enum('COMPLETE','INCOMPLETE') DEFAULT NULL,
  `solo_status` enum('PROGRESS','DONE') DEFAULT NULL,
  PRIMARY KEY (`solo_id`),
  KEY `FKpkex3bs6e2eq8dx0ocuj0y8ee` (`category_id`),
  KEY `FKktmqtifklf2f7x3flv8ynxobr` (`user_id`),
  CONSTRAINT `FKktmqtifklf2f7x3flv8ynxobr` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKpkex3bs6e2eq8dx0ocuj0y8ee` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solo`
--

LOCK TABLES `solo` WRITE;
/*!40000 ALTER TABLE `solo` DISABLE KEYS */;
INSERT INTO `solo` VALUES (1,1,3,'2024-02-15 18:03:38.691528','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/SOLO_AUTH_IMAGE/3_1_%EA%B8%B0%EC%83%81.PNG','COMPLETE','DONE'),(2,2,3,'2024-02-15 18:03:52.240619','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/SOLO_AUTH_IMAGE/3_2_%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98.jpg','COMPLETE','DONE'),(3,3,3,'2024-02-15 18:03:54.923689','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/SOLO_AUTH_IMAGE/3_3_%EC%9A%B4%EB%8F%99.jpg','COMPLETE','DONE'),(1,4,2,'2024-02-15 18:10:39.742069','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/SOLO_AUTH_IMAGE/2_1_%EA%B8%B0%EC%83%81%EC%B1%8C%EB%A6%B0%EC%A7%80.png','COMPLETE','DONE'),(2,5,2,'2024-02-15 18:10:46.718719','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/SOLO_AUTH_IMAGE/2_2_%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98%EC%9D%B8%EC%A6%9D1.png','COMPLETE','DONE'),(3,6,2,'2024-02-15 18:10:54.118750','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/SOLO_AUTH_IMAGE/2_3_%EC%9A%B4%EB%8F%99%EC%9D%B8%EC%A6%9D.png','COMPLETE','DONE'),(4,7,2,'2024-02-15 18:11:00.219694','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/SOLO_AUTH_IMAGE/2_4_%EC%8A%A4%ED%84%B0%EB%94%94%EC%9D%B8%EC%A6%9D.png','COMPLETE','DONE'),(2,8,4,'2024-02-15 18:21:33.147242','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/SOLO_AUTH_IMAGE/4_2_121.PNG','COMPLETE','DONE'),(6,9,4,'2024-02-15 18:21:40.552972','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/SOLO_AUTH_IMAGE/4_6_detail_76863_0_b1616cc8-3a25-41a7-a145-613b50eb75b4.jpg','COMPLETE','DONE'),(5,10,4,'2024-02-15 18:21:50.691294','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/SOLO_AUTH_IMAGE/4_5_1694679858306_0.png','COMPLETE','DONE'),(3,11,4,'2024-02-15 18:22:01.364392','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/SOLO_AUTH_IMAGE/4_3_KakaoTalk_20240215_181117423.jpg','COMPLETE','DONE'),(2,12,1,'2024-02-15 18:22:59.959313','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/SOLO_AUTH_IMAGE/1_2_%EC%BA%A1%EC%B2%98.PNG','COMPLETE','DONE'),(1,13,4,'2024-02-15 23:06:36.431780',NULL,'INCOMPLETE','DONE'),(1,14,6,'2024-02-16 00:33:05.149892',NULL,'INCOMPLETE','PROGRESS');
/*!40000 ALTER TABLE `solo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_badge`
--

DROP TABLE IF EXISTS `user_badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_badge` (
  `badge_id` int DEFAULT NULL,
  `user_badge_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`user_badge_id`),
  KEY `FKjqx9n26pk9mqf1qo8f7xvvoq9` (`badge_id`),
  KEY `FKa96xbjeyiemypugmi9g8x8fiu` (`user_id`),
  CONSTRAINT `FKa96xbjeyiemypugmi9g8x8fiu` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKjqx9n26pk9mqf1qo8f7xvvoq9` FOREIGN KEY (`badge_id`) REFERENCES `badge` (`badge_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_badge`
--

LOCK TABLES `user_badge` WRITE;
/*!40000 ALTER TABLE `user_badge` DISABLE KEYS */;
INSERT INTO `user_badge` VALUES (1,1,2),(1,2,3),(7,3,2),(7,4,4),(8,5,4),(8,6,2),(16,7,3),(16,8,2),(12,9,4),(12,10,3),(12,11,5),(12,12,2),(1,13,4),(1,14,5),(5,15,4),(5,16,5),(5,17,1),(5,18,3),(1,19,1),(2,20,2),(17,21,3),(2,22,5),(5,23,2),(5,24,3),(6,25,3);
/*!40000 ALTER TABLE `user_badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_category`
--

DROP TABLE IF EXISTS `user_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_category` (
  `category_fail_cnt` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `category_win_cnt` int DEFAULT NULL,
  `user_category_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `category_status` enum('NONE_STATUS','MATCH_STATUS','PLAY_STATUS') DEFAULT NULL,
  PRIMARY KEY (`user_category_id`),
  KEY `FKjchjxphkf5owj1i5bp95g5mfs` (`category_id`),
  KEY `FKh1fip9lpe4alrpurxqfmpftvl` (`user_id`),
  CONSTRAINT `FKh1fip9lpe4alrpurxqfmpftvl` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKjchjxphkf5owj1i5bp95g5mfs` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_category`
--

LOCK TABLES `user_category` WRITE;
/*!40000 ALTER TABLE `user_category` DISABLE KEYS */;
INSERT INTO `user_category` VALUES (0,1,1,1,1,'MATCH_STATUS'),(0,2,1,2,1,'NONE_STATUS'),(0,3,0,3,1,'NONE_STATUS'),(0,4,0,4,1,'NONE_STATUS'),(0,5,0,5,1,'NONE_STATUS'),(0,6,0,6,1,'MATCH_STATUS'),(0,1,3,7,2,'MATCH_STATUS'),(0,2,1,8,2,'NONE_STATUS'),(0,3,1,9,2,'NONE_STATUS'),(0,4,1,10,2,'NONE_STATUS'),(0,5,2,11,2,'NONE_STATUS'),(0,6,1,12,2,'MATCH_STATUS'),(0,1,1,13,3,'NONE_STATUS'),(1,2,1,14,3,'NONE_STATUS'),(0,3,1,15,3,'PLAY_STATUS'),(0,4,1,16,3,'NONE_STATUS'),(0,5,0,17,3,'PLAY_STATUS'),(0,6,0,18,3,'PLAY_STATUS'),(0,1,1,19,4,'NONE_STATUS'),(0,2,1,20,4,'NONE_STATUS'),(0,3,1,21,4,'PLAY_STATUS'),(0,4,0,22,4,'NONE_STATUS'),(0,5,2,23,4,'NONE_STATUS'),(0,6,1,24,4,'NONE_STATUS'),(0,1,2,25,5,'NONE_STATUS'),(0,2,1,26,5,'NONE_STATUS'),(0,3,1,27,5,'NONE_STATUS'),(0,4,0,28,5,'NONE_STATUS'),(0,5,0,29,5,'PLAY_STATUS'),(0,6,0,30,5,'PLAY_STATUS'),(0,1,0,31,6,'NONE_STATUS'),(0,2,0,32,6,'NONE_STATUS'),(0,3,0,33,6,'NONE_STATUS'),(0,4,0,34,6,'NONE_STATUS'),(0,5,0,35,6,'NONE_STATUS'),(0,6,0,36,6,'NONE_STATUS'),(0,1,0,37,7,'NONE_STATUS'),(0,2,0,38,7,'NONE_STATUS'),(0,3,0,39,7,'NONE_STATUS'),(0,4,0,40,7,'NONE_STATUS'),(0,5,0,41,7,'NONE_STATUS'),(0,6,0,42,7,'NONE_STATUS'),(0,1,0,43,8,'NONE_STATUS'),(0,2,0,44,8,'NONE_STATUS'),(0,3,0,45,8,'NONE_STATUS'),(0,4,0,46,8,'NONE_STATUS'),(0,5,0,47,8,'NONE_STATUS'),(0,6,0,48,8,'NONE_STATUS'),(0,1,0,49,9,'NONE_STATUS'),(0,2,0,50,9,'NONE_STATUS'),(0,3,0,51,9,'NONE_STATUS'),(0,4,0,52,9,'NONE_STATUS'),(0,5,0,53,9,'NONE_STATUS'),(0,6,0,54,9,'NONE_STATUS');
/*!40000 ALTER TABLE `user_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `badge_id` int DEFAULT NULL,
  `challenge_cnt` int DEFAULT NULL,
  `complain_cnt` int DEFAULT NULL,
  `kakao_push` bit(1) DEFAULT NULL,
  `signout` bit(1) DEFAULT NULL,
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_ssafy_id` int DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `login_id` varchar(20) DEFAULT NULL,
  `user_email` varchar(20) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_nickname` varchar(20) DEFAULT NULL,
  `user_phone` varchar(20) DEFAULT NULL,
  `user_img` varchar(200) DEFAULT NULL,
  `user_password` varchar(250) DEFAULT NULL,
  `auth` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_i3xs7wmfu2i3jt079uuetycit` (`login_id`),
  KEY `FKbytj9yik4e35vetw9knqatfdx` (`badge_id`),
  CONSTRAINT `FKbytj9yik4e35vetw9knqatfdx` FOREIGN KEY (`badge_id`) REFERENCES `badge` (`badge_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (NULL,5,0,_binary '',_binary '\0',1,1049963,'2024-02-15 17:36:20.489641','2024-02-15 17:36:20.489665','hyunchun24','hyunchun24@naver.com','박현춘','춘식','01097739052','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/PROFILE_IMAGE/hyunchun24_jitensya_kuma.png','$2a$10$K6wkrsgPY2fKAvkvuXj6De87Z2G1oikLGxKngcg1519n32MfqHhF2','ROLE_USER',NULL),(7,5,0,_binary '',_binary '\0',2,1044244,'2024-02-15 17:38:04.780721','2024-02-15 17:38:04.780733','hhhjieun','hon1770@naver.com','홍지은','지니','01029725070','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/PROFILE_IMAGE/hhhjieun_%EA%B0%95%EC%95%84%EC%A7%80.png','$2a$10$rgxtGX43ebXC/KQT9SyKHOoJA1tiQypD3zj9YjFf4Cabf0f7yPiWe','ROLE_USER',NULL),(16,6,0,_binary '',_binary '\0',3,1041970,'2024-02-15 17:50:44.578205','2024-02-15 17:50:44.578220','woori5030','woori50300@gmail.com','저누리','태범아발표잘해라','01072165030','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/PROFILE_IMAGE/woori5030_%EB%AA%A8%ED%86%A0%EB%9E%91%EB%82%98%28%EC%BA%90%EB%A6%AC%EC%BB%A4%EC%B3%90%29.webp','$2a$10$zR7ZJpvM1K4UNrBywLK7Hu3ojqD2vT0qXkEd.FBmXZGN1.ywHCx2u','ROLE_USER',NULL),(7,6,0,_binary '',_binary '\0',4,1040305,'2024-02-15 17:52:44.694431','2024-02-15 17:52:44.694446','ktb5057','ktb5057@gmail.com','김태범','통통한선인장','01033907917','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/PROFILE_IMAGE/ktb5057_ai_search.png','$2a$10$xznKab33XN/rEb1xoEDaousPA5TXQHIdnBOaj0Hrm31CfEWsmBLsW','ROLE_USER',NULL),(NULL,6,0,_binary '',_binary '\0',5,1042213,'2024-02-15 17:59:59.463105','2024-02-15 17:59:59.463110','hwasuk528','hwasuk528@naver.com','오화석','춘식이사생팬','01072706318','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/PROFILE_IMAGE/hwasuk528_%EC%B6%98.png','$2a$10$22fRdGdGwniPVbVrVDt7GuE7/S2NF0WloMLEf.qd2i7rGnrixIEbW','ROLE_USER',NULL),(NULL,6,0,_binary '',_binary '\0',6,102345,'2024-02-16 00:29:45.460556','2024-02-16 00:29:45.460561','awetumnn','awetumnn@gmail.com','유수안','망그러지다가만곰','01064255946','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/PROFILE_IMAGE/awetumnn_trip_img1.jpg','$2a$10$y5GWm3CGhp1Ty5LmXqlGFuNQjijRMK3Y0ZHJvls9M/p04wuITAuFW','ROLE_USER',NULL),(NULL,6,0,_binary '',_binary '\0',7,101231,'2024-02-16 09:06:57.271257','2024-02-16 09:06:57.271264','ssafy1','zsa332@gmail.com','공튀기기','공튀기기','01012341234','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/PROFILE_IMAGE/ssafy1_%EB%AA%A8%ED%86%A0%EB%9E%91%EB%82%98%28%EC%BA%90%EB%A6%AC%EC%BB%A4%EC%B3%90%292.webp','$2a$10$cNMoeYOz7Jej2aeJfE1AGuTJs0cQkrTpyb0h9j1dAMFwwqcf1F92y','ROLE_USER',NULL),(NULL,6,0,_binary '',_binary '\0',8,1044512,'2024-02-16 09:18:34.801156','2024-02-16 09:18:34.801161','hhjieun','bjh4395@naver.com','김싸피','우리우리언니','01045218569','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/PROFILE_IMAGE/hhjieun_%EA%B0%95%EC%95%84%EC%A7%80.png','$2a$10$sOm5L6PiQsQrZSnWLSrC3OolXySNP/OlW.WPaRU3D9DxbdwDW9SVO','ROLE_USER',NULL),(NULL,6,0,_binary '',_binary '\0',9,1044857,'2024-02-16 09:20:56.798910','2024-02-16 09:20:56.798914','ssafy2','chh9450@naver.com','이싸피','우리우리','01045217856','https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/PROFILE_IMAGE/ssafy2_%EA%B3%A0%EC%96%91%EC%9D%B4.png','$2a$10$.G4EiJrMr2Y0trRTeVuztuwSYGqRk7x/uCf5ARXIfjej0Pp/SJ5m6','ROLE_USER',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-16  9:28:56
