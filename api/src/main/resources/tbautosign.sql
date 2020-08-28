/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.14-log : Database - tbautosign
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tbautosign` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tbautosign`;

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT,
  `baidu_id` varchar(255) DEFAULT NULL,
  `baidu_name` varchar(255) DEFAULT NULL,
  `BDUSS` text,
  `uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Table structure for table `tbinfo` */

DROP TABLE IF EXISTS `tbinfo`;

CREATE TABLE `tbinfo` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT,
  `tieba_id` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `cur_score` int(11) DEFAULT NULL,
  `level_id` int(11) DEFAULT NULL,
  `level_name` varchar(255) DEFAULT NULL,
  `tbid` bigint(20) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0未签，1已签',
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) NOT NULL DEFAULT 'static/img/defaultavatar.png',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
