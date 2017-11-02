
grant select,insert,update,delete on management.* to mysql@"%" Identified by  "mysql123";

CREATE DATABASE nutch DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

CREATE TABLE `webpage` (
`id` varchar(767) NOT NULL,`headers` blob,`text` mediumtext DEFAULT NULL,`status` int(11) DEFAULT NULL,
`markers` blob,`parseStatus` blob,`modifiedTime` bigint(20) DEFAULT NULL,`score` float DEFAULT NULL,
`typ` varchar(32) CHARACTER SET latin1 DEFAULT NULL,`baseUrl` varchar(767) DEFAULT NULL,
`content` longblob,`title` varchar(2048) DEFAULT NULL,`reprUrl` varchar(767) DEFAULT NULL,
`fetchInterval` int(11) DEFAULT NULL,`prevFetchTime` bigint(20) DEFAULT NULL,`inlinks` mediumblob,
`prevSignature` blob,`outlinks` mediumblob,`fetchTime` bigint(20) DEFAULT NULL,
`retriesSinceFetch` int(11) DEFAULT NULL,`protocolStatus` blob,`signature` blob,`metadata` blob,
PRIMARY KEY (`id`(255))
) ENGINE=InnoDB ROW_FORMAT=COMPRESSED DEFAULT CHARSET=utf8;

