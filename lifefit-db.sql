/*
SQLyog Community v12.17 (64 bit)
MySQL - 5.6.21 : Database - lifefit
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `activity` */

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `idActivity` int(11) NOT NULL AUTO_INCREMENT,
  `activityName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idActivity`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `activity` */

insert  into `activity`(`idActivity`,`activityName`) values 
(1,'Walking'),
(2,'Running'),
(3,'Cycling');

/*Table structure for table `apiconfig` */

DROP TABLE IF EXISTS `apiconfig`;

CREATE TABLE `apiconfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `endpoint` varchar(200) DEFAULT NULL,
  `param1` varchar(30) DEFAULT NULL,
  `param2` varchar(30) DEFAULT NULL,
  `param3` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `apiconfig` */

insert  into `apiconfig`(`id`,`name`,`endpoint`,`param1`,`param2`,`param3`) values 
(1,'STANDS4','http://www.stands4.com/services/v2/quotes.php','uid=4663','tokenid=RUs6LmD2APwQw81I','searchtype=SEARCH&query=health');

/*Table structure for table `goal` */

DROP TABLE IF EXISTS `goal`;

CREATE TABLE `goal` (
  `idGoal` int(11) NOT NULL AUTO_INCREMENT,
  `idPerson` int(11) DEFAULT NULL,
  `idMeasure` int(11) DEFAULT NULL,
  `goalTarget` double DEFAULT NULL,
  PRIMARY KEY (`idGoal`),
  KEY `fk_idPerson_goal` (`idPerson`),
  KEY `fk_idMeasure_goal` (`idMeasure`),
  CONSTRAINT `fk_idMeasure_goal` FOREIGN KEY (`idMeasure`) REFERENCES `measure` (`idMeasure`),
  CONSTRAINT `fk_idPerson_goal` FOREIGN KEY (`idPerson`) REFERENCES `person` (`idPerson`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `goal` */

/*Table structure for table `healthmeasurehistory` */

DROP TABLE IF EXISTS `healthmeasurehistory`;

CREATE TABLE `healthmeasurehistory` (
  `idLog` int(11) NOT NULL AUTO_INCREMENT,
  `idPerson` int(11) DEFAULT NULL,
  `idMeasure` int(11) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`idLog`),
  KEY `fk_idPerson_historieslog` (`idPerson`),
  KEY `fk_idMeasure_historieslog` (`idMeasure`),
  CONSTRAINT `fk_idMeasure_historieslog` FOREIGN KEY (`idMeasure`) REFERENCES `measure` (`idMeasure`),
  CONSTRAINT `fk_idPerson_historieslog` FOREIGN KEY (`idPerson`) REFERENCES `person` (`idPerson`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

/*Data for the table `healthmeasurehistory` */

insert  into `healthmeasurehistory`(`idLog`,`idPerson`,`idMeasure`,`value`,`datetime`) values 
(1,1,1,78,'2016-01-08 16:35:22'),
(2,1,1,80,'2016-01-15 16:35:35'),
(3,1,2,190,'2016-01-09 16:36:09'),
(8,1,4,4000,'2016-01-08 17:15:14'),
(17,1,2,200,'2016-02-04 22:56:41'),
(18,1,6,1500,'2016-02-04 23:09:36'),
(19,1,2,180,'2016-02-04 23:13:56'),
(24,1,4,2300,'2016-02-05 17:14:31'),
(25,1,4,2000,'2016-02-06 16:21:04'),
(26,1,4,4000,'2016-02-06 16:21:24'),
(27,1,4,1200,'2016-02-06 16:41:00'),
(28,1,4,3000,'2016-02-06 16:47:16'),
(29,1,1,180,'2016-02-06 16:48:40'),
(30,1,4,4700,'2016-02-06 16:49:53'),
(31,1,4,3800,'2016-02-06 17:00:43'),
(32,1,4,3800,'2016-02-06 17:01:02');

/*Table structure for table `lifestatus` */

DROP TABLE IF EXISTS `lifestatus`;

CREATE TABLE `lifestatus` (
  `idStatus` int(11) NOT NULL AUTO_INCREMENT,
  `idPerson` int(11) DEFAULT NULL,
  `idMeasure` int(11) DEFAULT NULL,
  `value` double DEFAULT NULL,
  PRIMARY KEY (`idStatus`),
  KEY `fk_idPerson` (`idPerson`),
  KEY `fk_idMeasure` (`idMeasure`),
  CONSTRAINT `fk_idMeasure` FOREIGN KEY (`idMeasure`) REFERENCES `measure` (`idMeasure`),
  CONSTRAINT `fk_idPerson` FOREIGN KEY (`idPerson`) REFERENCES `person` (`idPerson`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;

/*Data for the table `lifestatus` */

insert  into `lifestatus`(`idStatus`,`idPerson`,`idMeasure`,`value`) values 
(1,1,2,185),
(2,1,3,110),
(3,1,1,185);

/*Table structure for table `measure` */

DROP TABLE IF EXISTS `measure`;

CREATE TABLE `measure` (
  `idMeasure` int(11) NOT NULL AUTO_INCREMENT,
  `measureName` varchar(20) DEFAULT NULL,
  `measureType` varchar(20) DEFAULT NULL,
  `goalFlag` char(1) DEFAULT NULL,
  PRIMARY KEY (`idMeasure`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `measure` */

insert  into `measure`(`idMeasure`,`measureName`,`measureType`,`goalFlag`) values 
(1,'weight','double',NULL),
(2,'height','double',NULL),
(3,'blood pressure','double',NULL),
(4,'steps','integer','Y'),
(5,'distance','double','Y'),
(6,'energy','double','Y'),
(7,'heart rate','double',NULL);

/*Table structure for table `person` */

DROP TABLE IF EXISTS `person`;

CREATE TABLE `person` (
  `idPerson` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `gender` char(1) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`idPerson`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `person` */

insert  into `person`(`idPerson`,`firstname`,`lastname`,`gender`,`birthdate`,`email`,`password`) values 
(1,'Matteo','Ferrari','M','1948-08-20','matteo@gmail.com','21232f297a57a5a743894a0e4a801fc3');

/*Table structure for table `personactivity` */

DROP TABLE IF EXISTS `personactivity`;

CREATE TABLE `personactivity` (
  `idPA` int(11) NOT NULL AUTO_INCREMENT,
  `idActivity` int(11) NOT NULL,
  `idPerson` int(11) NOT NULL,
  `startDatetime` datetime DEFAULT NULL,
  `endDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`idPA`),
  KEY `fk_idActivity_personActivity` (`idActivity`),
  KEY `fk_idPerson_personActivity` (`idPerson`),
  CONSTRAINT `fk_idActivity_personActivity` FOREIGN KEY (`idActivity`) REFERENCES `activity` (`idActivity`),
  CONSTRAINT `fk_idPerson_personActivity` FOREIGN KEY (`idPerson`) REFERENCES `person` (`idPerson`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `personactivity` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
