
CREATE TABLE `user` (
   `no` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(45) NOT NULL,
   `id` varchar(45) NOT NULL,
   `password` varchar(45) NOT NULL,
   `join_date` date NOT NULL,
   PRIMARY KEY (`no`),
   UNIQUE KEY `id` (`id`)
 );
 
 
 CREATE TABLE `blog` (
   `no` int(11) NOT NULL,
   `title` varchar(200) NOT NULL,
   `logo` varchar(200) NOT NULL DEFAULT '/assets/upload-images/default-logo.jpg',
   PRIMARY KEY (`no`),
   CONSTRAINT `blog_ibfk_1` FOREIGN KEY (`no`) REFERENCES `user` (`no`) ON DELETE CASCADE
 );
 
 CREATE TABLE `category` (
   `no` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(200) NOT NULL,
   `desc` text,
   `is_default` tinyint(1) NOT NULL DEFAULT '0',
   `blog_no` int(11) NOT NULL,
   PRIMARY KEY (`no`),
   KEY `blog_no` (`blog_no`),
   CONSTRAINT `category_ibfk_1` FOREIGN KEY (`blog_no`) REFERENCES `blog` (`no`) ON DELETE CASCADE
 );
 
 
CREATE TABLE `post` (
   `no` int(11) NOT NULL AUTO_INCREMENT,
   `title` varchar(200) NOT NULL,
   `contents` text,
   `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `category_no` int(11) NOT NULL,
   PRIMARY KEY (`no`),
   KEY `category_no` (`category_no`),
   CONSTRAINT `post_ibfk_1` FOREIGN KEY (`category_no`) REFERENCES `category` (`no`) ON DELETE CASCADE
 );
 