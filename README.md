# phonebook
<a href="http://phonebook-lardi.herokuapp.com/">Demo</a> - working demo deployed to heroku, first app start can be long.
<br>
<br>
Running on local server:<br><br>
1) If **no params** will be passed to JVM, app will initialize with default JSON data store file "src/main/resources/phonebook.json".
<br>
<br>
2) For running with **MySQL** pass to JVM -Dlardi.conf=your/local/path/filename.properties .<br>
Example configuration:<br>
profile=mysql<br>
url=jdbc:mysql://localhost:3306/phonebook_antonsakhno?verifyServerCertificate=false&useSSL=false&requireSSL=false&characterEncoding=UTF-8 <br>
username=username<br>
password=password
<br>
<br>
3) For running with your custom **JSON** file location set in configuration file:<br>
profile=default<br>
jsonpath=path to file<br>
<br>
<br>
<br>
MySQL script (located in src/main/resources/):<br>

DROP SCHEMA IF EXISTS `phonebook_antonsakhno`;<br>
CREATE SCHEMA IF NOT EXISTS `phonebook_antonsakhno`<br>
  DEFAULT CHARACTER SET utf8;<br>
USE `phonebook`;<br>
<br>
CREATE TABLE IF NOT EXISTS `phonebook_antonsakhno`.`user` (<br>
  `id`       SERIAL PRIMARY KEY,<br>
  `login`    VARCHAR(45) NOT NULL,<br>
  `password` VARCHAR(45) NOT NULL,<br>
  `name`     VARCHAR(45) NULL<br>
)<br>
  ENGINE = InnoDB<br>
  CHARACTER SET = utf8;<br>
<br>
CREATE TABLE IF NOT EXISTS `phonebook_antonsakhno`.`contact` (<br>
  `id`          SERIAL PRIMARY KEY,<br>
  `lastname`    VARCHAR(45) NOT NULL,<br>
  `firstname`   VARCHAR(45) NOT NULL,<br>
  `middlename`  VARCHAR(45) NOT NULL,<br>
  `mobilephone` VARCHAR(45) NOT NULL,<br>
  `homephone`   VARCHAR(45) NULL,<br>
  `address`     VARCHAR(45) NULL,<br>
  `email`       VARCHAR(45) NULL,<br>
  `user_id`     INT REFERENCES users (id)<br>
)<br>
  ENGINE = InnoDB<br>
  CHARACTER SET = utf8;<br>
