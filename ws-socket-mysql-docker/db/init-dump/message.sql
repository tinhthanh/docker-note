CREATE DATABASE IF NOT EXISTS master;
use master;
CREATE TABLE `websocket_message` (
  `destination` varchar(250) NOT NULL,
  `message` mediumtext NOT NULL,
  `last_update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`destination`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
