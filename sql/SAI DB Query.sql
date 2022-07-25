CREATE TABLE `USER` (
	`user_id`	varchar(20)	NOT NULL,
	`family_id`	varchar(10)	NULL,
	`user_name`	varchar(10)	NULL,
	`email`	varchar(30)	NULL,
	`password`	varchar(20)	NULL,
	`nickname`	varchar(10)	NULL,
	`birthday`	date	NULL,
	`lunar`	TINYINT(1)	NULL,
	`user_image`	varchar(255)	NULL,
	`user_message`	varchar(255)	NULL
);

DROP TABLE IF EXISTS `FAMILY`;

CREATE TABLE `FAMILY` (
	`family_id`	varchar(10)	NOT NULL,
	`family_name`	varchar(20)	NULL,
	`family_image_path`	varchar(255)	NULL,
	`family_image_name`	VARCHAR(255)	NULL,
	`family_image_type`	VARCHAR(255)	NULL
);

DROP TABLE IF EXISTS `MEMO`;

CREATE TABLE `MEMO` (
	`memo_id`	bigint	NOT NULL,
	`user_id`	varchar(20)	NOT NULL,
	`family_id`	varchar(10)	NOT NULL,
	`memo_reg_datetime`	datetime	NULL,
	`x`	double	NULL,
	`y`	double	NULL,
	`color`	varchar(30)	NULL,
	`memo_content`	varchar(255)	NULL
);

DROP TABLE IF EXISTS `ALBUM`;

CREATE TABLE `ALBUM` (
	`album_id`	bigint	NOT NULL,
	`album_name`	VARCHAR(30)	NULL,
	`album_date`	DATE	NULL,
    `album_start_date` DATE NULL,
    `album_end_date` DATE NULL,
	`family_id`	varchar(10)	NOT NULL
);

DROP TABLE IF EXISTS `POLL`;

CREATE TABLE `POLL` (
	`poll_id`	bigint	NOT NULL,
	`board_id`	bigint	NOT NULL,
	`poll_title`	varchar(40)	NOT NULL,
	`poll_option1`	varchar(100)	NOT NULL,
	`poll_option2`	varchar(100)	NOT NULL,
	`poll_option3`	varchar(100)	NULL,
	`poll_option4`	varchar(100)	NULL,
	`poll_option5`	varchar(100)	NULL,
    `poll_end_datetime`	DATETIME	NULL
);

DROP TABLE IF EXISTS `MEDIA`;

CREATE TABLE `MEDIA` (
	`album_media_id`	bigint	NOT NULL,
	`album_id`	bigint	NOT NULL,
	`album_media_path`	VARCHAR(255)	NULL,
	`album_media_name`	varchar(255)	NULL,
	`album_media_type`	VARCHAR(10)	NULL
);

DROP TABLE IF EXISTS `POLL_RESULT`;

CREATE TABLE `POLL_RESULT` (
	`result_id`	bigint	NOT NULL,
	`poll_id`	bigint	NOT NULL,
	`poll_result`	varchar(20)	NOT NULL
);

DROP TABLE IF EXISTS `MASTER_PLAN`;

CREATE TABLE `MASTER_PLAN` (
	`master_plan_id`	bigint	NOT NULL,
	`family_id`	varchar(10)	NOT NULL,
	`user_id`	varchar(20)	NOT NULL,
	`plan_type`	VARCHAR(10)	NULL,
	`master_plan_start_datetime`	DATETIME	NULL,
	`master_plan_end_datetime`	DATETIME	NULL,
	`plan_noti_datetime`	DATETIME	NULL,
	`plan_content`	VARCHAR(255)	NULL,
	`plan_routine_day`	bigint	NULL,
	`routine_end_date`	DATE	NULL
);

DROP TABLE IF EXISTS `Notification`;

CREATE TABLE `Notification` (
	`noti_id`	bigint	NOT NULL,
	`user_id`	varchar(20)	NOT NULL,
	`noti_content`	varchar(255)	NULL,
	`noti_type`	varchar(20)	NULL,
	`noti_datetime`	DATETIME	NULL,
    `noti_read_yn` tinyint(1) NULL,
	`action_user_id`	varchar(20)	NULL,
	`noti_content_id`	bigint	NULL
);

DROP TABLE IF EXISTS `REPLY`;

CREATE TABLE `REPLY` (
	`reply_id`	bigint	NOT NULL,
	`board_id`	bigint	NOT NULL,
	`reply_content`	varchar(100)	NOT NULL,
	`reply_reg_datetime`	datetime	NOT NULL
);

DROP TABLE IF EXISTS `FAMILY_CALLSIGN`;

CREATE TABLE `FAMILY_CALLSIGN` (
	`from_user_id`	varchar(20)	NOT NULL,
	`to_user_id`	varchar(20)	NOT NULL,
	`callsign`	varchar(30)	NULL
);

DROP TABLE IF EXISTS `REACTION`;

CREATE TABLE `REACTION` (
	`reaction_id`	bigint	NOT NULL,
	`board_id`	bigint	NOT NULL,
	`reaction_type`	varchar(10)	NOT NULL,
    `user_id` varchar(20) NOT NULL
);

DROP TABLE IF EXISTS `BOARD_MEDIA`;

CREATE TABLE `BOARD_MEDIA` (
	`board_meida_id`	bigint	NOT NULL,
	`board_id`	bigint	NOT NULL,
	`board_media_path`	varchar(255)	NOT NULL,
	`board_media_name`	varchar(255)	NOT NULL,
	`board_media_type`	varchar(10)	NOT NULL
);

DROP TABLE IF EXISTS `FAMILY_REGISTER`;

CREATE TABLE `FAMILY_REGISTER` (
	`family_register_id`	bigint	NOT NULL,
	`family_id`	varchar(10)	NOT NULL,
	`approve_user_id`	varchar(20)	NOT NULL,
	`approve_yn`	tinyint(1)	NULL,
	`user_id`	varchar(20)	NOT NULL
);

DROP TABLE IF EXISTS `BOARD_TAGGED`;

-- show tables;

CREATE TABLE `BOARD_TAGGED` (
	`board_tagged_id`	bigint	NOT NULL,
	`board_id`	bigint	NOT NULL,
	`tagged_id`	varchar(20)	NOT NULL
);

DROP TABLE IF EXISTS `SLAVE_PLAN`;

CREATE TABLE `SLAVE_PLAN` (
	`slave_plan_id`	bigint	NOT NULL,
	`master_plan_id`	bigint	NOT NULL,
	`slave_plan_start_datetime`	DATETIME	NULL,
	`slave_plan_end_datetime`	DATETIME	NULL
);

DROP TABLE IF EXISTS `PLAN_TAGGED`;

CREATE TABLE `PLAN_TAGGED` (
	`plan_tagged_id`	bigint	NOT NULL,
	`plan_id`	bigint	NOT NULL,
	`user_id`	varchar(20)	NOT NULL
);

DROP TABLE IF EXISTS `BOARD`;

CREATE TABLE `BOARD` (
	`board_id`	bigint	NOT NULL,
	`board_content`	varchar(1000)	NOT NULL,
	`board_reg_datetime`	datetime	NOT NULL,
	`board_media`	varchar(500)	NULL,
	`board_date`	date	NULL,
	`board_location`	varchar(40)	NULL,
	`board_people`	varchar(40)	NULL,
	`poll_yn`	tinyint	NOT NULL,
	`family_id`	varchar(10)	NOT NULL,
	`user_id`	varchar(20)	NOT NULL
);

ALTER TABLE `USER` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`user_id`
);

ALTER TABLE `FAMILY` ADD CONSTRAINT `PK_FAMILY` PRIMARY KEY (
	`family_id`
);

ALTER TABLE `MEMO` ADD CONSTRAINT `PK_MEMO` PRIMARY KEY (
	`memo_id`
);

ALTER TABLE `ALBUM` ADD CONSTRAINT `PK_ALBUM` PRIMARY KEY (
	`album_id`
);

ALTER TABLE `POLL` ADD CONSTRAINT `PK_POLL` PRIMARY KEY (
	`poll_id`
);

ALTER TABLE `MEDIA` ADD CONSTRAINT `PK_MEDIA` PRIMARY KEY (
	`album_media_id`
);

ALTER TABLE `POLL_RESULT` ADD CONSTRAINT `PK_POLL_RESULT` PRIMARY KEY (
	`result_id`
);

ALTER TABLE `MASTER_PLAN` ADD CONSTRAINT `PK_MASTER_PLAN` PRIMARY KEY (
	`master_plan_id`
);

ALTER TABLE `Notification` ADD CONSTRAINT `PK_NOTIFICATION` PRIMARY KEY (
	`noti_id`
);

ALTER TABLE `REPLY` ADD CONSTRAINT `PK_REPLY` PRIMARY KEY (
	`reply_id`
);

ALTER TABLE `FAMILY_CALLSIGN` ADD CONSTRAINT `PK_FAMILY_CALLSIGN` PRIMARY KEY (
	`from_user_id`,
	`to_user_id`
);

ALTER TABLE `REACTION` ADD CONSTRAINT `PK_REACTION` PRIMARY KEY (
	`reaction_id`
);

ALTER TABLE `BOARD_MEDIA` ADD CONSTRAINT `PK_BOARD_MEDIA` PRIMARY KEY (
	`board_meida_id`
);

ALTER TABLE `FAMILY_REGISTER` ADD CONSTRAINT `PK_FAMILY_REGISTER` PRIMARY KEY (
	`family_register_id`
);

ALTER TABLE `BOARD_TAGGED` ADD CONSTRAINT `PK_BOARD_TAGGED` PRIMARY KEY (
	`board_tagged_id`
);

ALTER TABLE `SLAVE_PLAN` ADD CONSTRAINT `PK_SLAVE_PLAN` PRIMARY KEY (
	`slave_plan_id`
);

ALTER TABLE `PLAN_TAGGED` ADD CONSTRAINT `PK_PLAN_TAGGED` PRIMARY KEY (
	`plan_tagged_id`
);

ALTER TABLE `BOARD` ADD CONSTRAINT `PK_BOARD` PRIMARY KEY (
	`board_id`
);

ALTER TABLE `USER` ADD CONSTRAINT `FK_FAMILY_TO_USER_1` FOREIGN KEY (
	`family_id`
)
REFERENCES `FAMILY` (
	`family_id`
);

ALTER TABLE `MEMO` ADD CONSTRAINT `FK_USER_TO_MEMO_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `USER` (
	`user_id`
);

ALTER TABLE `MEMO` ADD CONSTRAINT `FK_FAMILY_TO_MEMO_1` FOREIGN KEY (
	`family_id`
)
REFERENCES `FAMILY` (
	`family_id`
);

ALTER TABLE `ALBUM` ADD CONSTRAINT `FK_FAMILY_TO_ALBUM_1` FOREIGN KEY (
	`family_id`
)
REFERENCES `FAMILY` (
	`family_id`
);

ALTER TABLE `POLL` ADD CONSTRAINT `FK_BOARD_TO_POLL_1` FOREIGN KEY (
	`board_id`
)
REFERENCES `BOARD` (
	`board_id`
);

ALTER TABLE `MEDIA` ADD CONSTRAINT `FK_ALBUM_TO_MEDIA_1` FOREIGN KEY (
	`album_id`
)
REFERENCES `ALBUM` (
	`album_id`
);

ALTER TABLE `POLL_RESULT` ADD CONSTRAINT `FK_POLL_TO_POLL_RESULT_1` FOREIGN KEY (
	`poll_id`
)
REFERENCES `POLL` (
	`poll_id`
);

ALTER TABLE `MASTER_PLAN` ADD CONSTRAINT `FK_FAMILY_TO_MASTER_PLAN_1` FOREIGN KEY (
	`family_id`
)
REFERENCES `FAMILY` (
	`family_id`
);

ALTER TABLE `MASTER_PLAN` ADD CONSTRAINT `FK_USER_TO_MASTER_PLAN_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `USER` (
	`user_id`
);

ALTER TABLE `Notification` ADD CONSTRAINT `FK_USER_TO_Notification_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `USER` (
	`user_id`
);

ALTER TABLE `REPLY` ADD CONSTRAINT `FK_BOARD_TO_REPLY_1` FOREIGN KEY (
	`board_id`
)
REFERENCES `BOARD` (
	`board_id`
);

ALTER TABLE `FAMILY_CALLSIGN` ADD CONSTRAINT `FK_USER_TO_FAMILY_CALLSIGN_1` FOREIGN KEY (
	`to_user_id`
)
REFERENCES `USER` (
	`user_id`
);

ALTER TABLE `REACTION` ADD CONSTRAINT `FK_BOARD_TO_REACTION_1` FOREIGN KEY (
	`board_id`
)
REFERENCES `BOARD` (
	`board_id`
);

ALTER TABLE `BOARD_MEDIA` ADD CONSTRAINT `FK_BOARD_TO_BOARD_MEDIA_1` FOREIGN KEY (
	`board_id`
)
REFERENCES `BOARD` (
	`board_id`
);

ALTER TABLE `FAMILY_REGISTER` ADD CONSTRAINT `FK_FAMILY_TO_FAMILY_REGISTER_1` FOREIGN KEY (
	`family_id`
)
REFERENCES `FAMILY` (
	`family_id`
);

ALTER TABLE `FAMILY_REGISTER` ADD CONSTRAINT `FK_USER_TO_FAMILY_REGISTER_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `USER` (
	`user_id`
);

ALTER TABLE `BOARD_TAGGED` ADD CONSTRAINT `FK_BOARD_TO_BOARD_TAGGED_1` FOREIGN KEY (
	`board_id`
)
REFERENCES `BOARD` (
	`board_id`
);

ALTER TABLE `SLAVE_PLAN` ADD CONSTRAINT `FK_MASTER_PLAN_TO_SLAVE_PLAN_1` FOREIGN KEY (
	`master_plan_id`
)
REFERENCES `MASTER_PLAN` (
	`master_plan_id`
);

ALTER TABLE `PLAN_TAGGED` ADD CONSTRAINT `FK_MASTER_PLAN_TO_PLAN_TAGGED_1` FOREIGN KEY (
	`plan_id`
)
REFERENCES `MASTER_PLAN` (
	`master_plan_id`
);

ALTER TABLE `PLAN_TAGGED` ADD CONSTRAINT `FK_USER_TO_PLAN_TAGGED_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `USER` (
	`user_id`
);

ALTER TABLE `BOARD` ADD CONSTRAINT `FK_FAMILY_TO_BOARD_1` FOREIGN KEY (
	`family_id`
)
REFERENCES `FAMILY` (
	`family_id`
);

ALTER TABLE `BOARD` ADD CONSTRAINT `FK_USER_TO_BOARD_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `USER` (
	`user_id`
);

-- show tables;

-- show full columns from board_tagged;

-- show full columns from board;

-- create database sai;

-- use sai;