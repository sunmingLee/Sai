-- --------------------------------------------------------
-- 호스트:                          i7a305.p.ssafy.io
-- 서버 버전:                        10.3.35-MariaDB-1:10.3.35+maria~focal - mariadb.org binary distribution
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 테이블 데이터 sai.album:~3 rows (대략적) 내보내기
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` (`album_id`, `family_id`, `album_name`, `album_start_date`, `album_end_date`) VALUES
	(1, '305305', '여름 휴가 - 동해', '2022-08-12', '2022-08-14'),
	(2, '305305', '캠핑', '2021-07-08', '2021-07-09'),
	(3, '305305', '부산 여행', '2020-09-01', '2020-09-03');
/*!40000 ALTER TABLE `album` ENABLE KEYS */;

-- 테이블 데이터 sai.album_media:~5 rows (대략적) 내보내기
/*!40000 ALTER TABLE `album_media` DISABLE KEYS */;
INSERT INTO `album_media` (`album_media_id`, `album_id`, `album_media_thumbnail`, `album_media_thumb_server`, `album_media_path`, `album_media_path_server`, `album_media_original_name`, `album_media_type`) VALUES
	(1, 1, '/saimedia/Album/305305/th_45ad78a4-6f28-49a7-8e8b-7c0b820070bd_동해.jpg', '/app/Album/305305/th_45ad78a4-6f28-49a7-8e8b-7c0b820070bd_동해.jpg', '/saimedia/Album/305305/45ad78a4-6f28-49a7-8e8b-7c0b820070bd_동해.jpg', '/app/Album/305305/45ad78a4-6f28-49a7-8e8b-7c0b820070bd_동해.jpg', '동해.jpg', 'image/jpeg'),
	(2, 2, '/saimedia/Album/305305/th_5447b0ce-b7a2-48d3-aa38-80f48e60030c_캠핑.jpg', '/app/Album/305305/th_5447b0ce-b7a2-48d3-aa38-80f48e60030c_캠핑.jpg', '/saimedia/Album/305305/5447b0ce-b7a2-48d3-aa38-80f48e60030c_캠핑.jpg', '/app/Album/305305/5447b0ce-b7a2-48d3-aa38-80f48e60030c_캠핑.jpg', '캠핑.jpg', 'image/jpeg'),
	(3, 3, '/saimedia/Album/305305/th_31cc2d38-1593-4d4e-81b2-f929a0e06e0a_부산여행1.jpg', '/app/Album/305305/th_31cc2d38-1593-4d4e-81b2-f929a0e06e0a_부산여행1.jpg', '/saimedia/Album/305305/31cc2d38-1593-4d4e-81b2-f929a0e06e0a_부산여행1.jpg', '/app/Album/305305/31cc2d38-1593-4d4e-81b2-f929a0e06e0a_부산여행1.jpg', '부산여행1.jpg', 'image/jpeg'),
	(4, 3, '/saimedia/Album/305305/th_d3f6b0af-e6eb-4ab6-9206-a9a032fa57bf_부산여행2.jpg', '/app/Album/305305/th_d3f6b0af-e6eb-4ab6-9206-a9a032fa57bf_부산여행2.jpg', '/saimedia/Album/305305/d3f6b0af-e6eb-4ab6-9206-a9a032fa57bf_부산여행2.jpg', '/app/Album/305305/d3f6b0af-e6eb-4ab6-9206-a9a032fa57bf_부산여행2.jpg', '부산여행2.jpg', 'image/jpeg'),
	(5, 3, '/saimedia/Album/305305/th_8fcba026-2577-4ce8-8431-e648471fb2ce_부산여행3.jpg', '/app/Album/305305/th_8fcba026-2577-4ce8-8431-e648471fb2ce_부산여행3.jpg', '/saimedia/Album/305305/8fcba026-2577-4ce8-8431-e648471fb2ce_부산여행3.jpg', '/app/Album/305305/8fcba026-2577-4ce8-8431-e648471fb2ce_부산여행3.jpg', '부산여행3.jpg', 'image/jpeg');
/*!40000 ALTER TABLE `album_media` ENABLE KEYS */;

-- 테이블 데이터 sai.board:~7 rows (대략적) 내보내기
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` (`board_id`, `family_id`, `user_id`, `board_reg_datetime`, `board_content`, `board_like_cnt`, `board_reply_cnt`, `board_date`, `board_location`, `board_tagged_yn`, `board_media_yn`, `poll_yn`) VALUES
	(2, '305305', 'dadd', '2022-08-10 06:39:37', '이제 곧 엄마 생일인데 뭐 맛있는 거 먹으러 갈까', 2, 1, NULL, '', 0, 0, 0),
	(3, '305305', 'momm', '2022-08-11 06:42:30', '산에 꽃 예쁘게 폈더라', 3, 1, NULL, '', 0, 1, 0),
	(4, '305305', 'boyy', '2022-08-12 06:44:22', '오늘 따릉이 타고 왔어요', 2, 1, NULL, '', 0, 1, 0),
	(5, '305305', 'boyy', '2022-08-13 06:46:43', '오늘 저녁 메뉴', 1, 0, NULL, '', 0, 1, 0),
	(6, '305305', 'boyy', '2022-08-16 06:48:06', '엄마 생신 축하드려요\n제가 끓인 미역국 맛있으셨어요?\n', 3, 1, NULL, '', 1, 0, 0),
	(7, '305305', 'dadd', '2022-08-17 06:49:25', '', 0, 0, NULL, '', 0, 0, 1),
	(16, '305305', 'boyy', '2022-08-18 07:45:31', '부산에서 탄 바다열차~', 0, 0, NULL, '', 0, 1, 0);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;

-- 테이블 데이터 sai.board_like:~11 rows (대략적) 내보내기
/*!40000 ALTER TABLE `board_like` DISABLE KEYS */;
INSERT INTO `board_like` (`board_like_id`, `board_id`, `user_id`) VALUES
	(1, 2, 'boyy'),
	(2, 2, 'momm'),
	(5, 3, 'boyy'),
	(4, 3, 'dadd'),
	(3, 3, 'momm'),
	(7, 4, 'dadd'),
	(6, 4, 'momm'),
	(11, 5, 'dadd'),
	(8, 6, 'boyy'),
	(10, 6, 'dadd'),
	(9, 6, 'momm');
/*!40000 ALTER TABLE `board_like` ENABLE KEYS */;

-- 테이블 데이터 sai.board_media:~4 rows (대략적) 내보내기
/*!40000 ALTER TABLE `board_media` DISABLE KEYS */;
INSERT INTO `board_media` (`board_media_id`, `board_id`, `board_media_thumbnail`, `board_media_thumb_server`, `board_media_path`, `board_media_path_server`, `board_media_original_name`, `board_media_type`) VALUES
	(1, 3, '/saimedia/Feed/305305/th_2a2eb9f8-4cb8-4b1b-9184-df3f51b59dad_게시글1.jpg', '/app/Feed/305305/th_2a2eb9f8-4cb8-4b1b-9184-df3f51b59dad_게시글1.jpg', '/saimedia/Feed/305305/2a2eb9f8-4cb8-4b1b-9184-df3f51b59dad_게시글1.jpg', '/app/Feed/305305/2a2eb9f8-4cb8-4b1b-9184-df3f51b59dad_게시글1.jpg', '게시글1.jpg', 'image/jpeg'),
	(2, 4, '/saimedia/Feed/305305/th_0a72a568-8520-4acd-9a37-59f851a494bc_따릉이.png', '/app/Feed/305305/th_0a72a568-8520-4acd-9a37-59f851a494bc_따릉이.png', '/saimedia/Feed/305305/0a72a568-8520-4acd-9a37-59f851a494bc_따릉이.png', '/app/Feed/305305/0a72a568-8520-4acd-9a37-59f851a494bc_따릉이.png', '따릉이.png', 'image/png'),
	(3, 5, '/saimedia/Feed/305305/th_46db1b89-f642-474e-a089-22504750d090_저녁.jpg', '/app/Feed/305305/th_46db1b89-f642-474e-a089-22504750d090_저녁.jpg', '/saimedia/Feed/305305/46db1b89-f642-474e-a089-22504750d090_저녁.jpg', '/app/Feed/305305/46db1b89-f642-474e-a089-22504750d090_저녁.jpg', '저녁.jpg', 'image/jpeg'),
	(12, 16, '/saimedia/Feed/305305/th_6b82b7d4-5075-42ff-8ad7-4188adb1fa78_바다열차 (video-converter.com).mp4', '/app/Feed/305305/th_6b82b7d4-5075-42ff-8ad7-4188adb1fa78_바다열차 (video-converter.com).mp4', '/saimedia/Feed/305305/6b82b7d4-5075-42ff-8ad7-4188adb1fa78_바다열차 (video-converter.com).mp4', '/app/Feed/305305/6b82b7d4-5075-42ff-8ad7-4188adb1fa78_바다열차 (video-converter.com).mp4', '바다열차 (video-converter.com).mp4', 'video/mp4');
/*!40000 ALTER TABLE `board_media` ENABLE KEYS */;

-- 테이블 데이터 sai.board_tagged:~1 rows (대략적) 내보내기
/*!40000 ALTER TABLE `board_tagged` DISABLE KEYS */;
INSERT INTO `board_tagged` (`board_tagged_id`, `board_id`, `user_id`) VALUES
	(1, 6, 'momm');
/*!40000 ALTER TABLE `board_tagged` ENABLE KEYS */;

-- 테이블 데이터 sai.choice:~2 rows (대략적) 내보내기
/*!40000 ALTER TABLE `choice` DISABLE KEYS */;
INSERT INTO `choice` (`choice_id`, `text`, `poll_id`) VALUES
	(3, '소고기', 2),
	(4, '회', 2);
/*!40000 ALTER TABLE `choice` ENABLE KEYS */;

-- 테이블 데이터 sai.family:~1 rows (대략적) 내보내기
/*!40000 ALTER TABLE `family` DISABLE KEYS */;
INSERT INTO `family` (`family_id`, `family_name`, `family_image_path`, `family_image_path_server`, `family_image_name`, `family_image_type`) VALUES
	('305305', '선민이네', '/saimedia/FamilyImage/08dc22a5-621c-455a-8eec-2a6663db705f_가족사진.jpg', '/app/FamilyImage/08dc22a5-621c-455a-8eec-2a6663db705f_가족사진.jpg', '가족사진.jpg', 'image/jpeg');
/*!40000 ALTER TABLE `family` ENABLE KEYS */;

-- 테이블 데이터 sai.family_callsign:~9 rows (대략적) 내보내기
/*!40000 ALTER TABLE `family_callsign` DISABLE KEYS */;
INSERT INTO `family_callsign` (`family_callsign_id`, `from_user_id`, `to_user_id`, `callsign`) VALUES
	(1, 'momm', 'momm', '김희영'),
	(2, 'dadd', 'momm', '아내'),
	(3, 'momm', 'dadd', '남편'),
	(4, 'dadd', 'dadd', '이선민'),
	(5, 'boyy', 'dadd', '아빠'),
	(6, 'dadd', 'boyy', '아들'),
	(7, 'boyy', 'momm', '엄마'),
	(8, 'momm', 'boyy', '아들'),
	(9, 'boyy', 'boyy', '이군선');
/*!40000 ALTER TABLE `family_callsign` ENABLE KEYS */;

-- 테이블 데이터 sai.family_register:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `family_register` DISABLE KEYS */;
/*!40000 ALTER TABLE `family_register` ENABLE KEYS */;

-- 테이블 데이터 sai.main_plan:~3 rows (대략적) 내보내기
/*!40000 ALTER TABLE `main_plan` DISABLE KEYS */;
INSERT INTO `main_plan` (`main_plan_id`, `family_id`, `user_id`, `plan_title`, `plan_type`, `all_day_yn`, `main_plan_start_datetime`, `main_plan_end_datetime`, `plan_place`, `plan_noti_yn`, `plan_routine_day`) VALUES
	(1, '305305', 'boyy', '엄마 생일', 'anniversary', NULL, '2022-08-16 15:17:00', NULL, '', NULL, NULL),
	(2, '305305', 'boyy', '여름 휴가', 'family', NULL, '2022-08-12 15:18:00', '2022-08-14 15:18:00', '동해', NULL, NULL),
	(3, '305305', 'boyy', '군선이 집에 오는 날', 'personal', NULL, '2022-08-01 15:21:00', '2022-08-03 15:21:00', '', NULL, NULL);
/*!40000 ALTER TABLE `main_plan` ENABLE KEYS */;

-- 테이블 데이터 sai.memo:~4 rows (대략적) 내보내기
/*!40000 ALTER TABLE `memo` DISABLE KEYS */;
INSERT INTO `memo` (`memo_id`, `family_id`, `user_id`, `memo_reg_datetime`, `color`, `memo_content`, `read_list`) VALUES
	(1, '305305', 'boyy', '2022-08-18 06:15:46', 'LightGoldenRodYellow', '8월 19일 미애 발표회', 'boyy'),
	(2, '305305', 'boyy', '2022-08-18 06:16:08', 'LightBlue', '이번 주 화요일 11시\n정수기 점검 온다', 'boyy'),
	(3, '305305', 'boyy', '2022-08-18 06:16:26', 'LightPink', '올 때 콜라 사와', 'boyy'),
	(4, '305305', 'boyy', '2022-08-18 06:17:00', 'PaleGreen', '엄마 미용실 다녀올게 반찬 꺼내 먹어', 'boyy');
/*!40000 ALTER TABLE `memo` ENABLE KEYS */;

-- 테이블 데이터 sai.notification:~14 rows (대략적) 내보내기
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` (`noti_id`, `noti_to_user_id`, `noti_from_user_id`, `noti_content`, `noti_type`, `noti_datetime`, `noti_read_yn`, `noti_content_id`) VALUES
	(3, 'dadd', 'boyy', '님이 당신의 가족인가요?', 'FAMILYREGISTER', '2022-08-18 06:12:26', 1, 3),
	(5, 'dadd', 'boyy', '좋아요를 눌렀습니다.', 'LIKE', '2022-08-18 06:40:10', 0, 2),
	(6, 'dadd', 'boyy', '네, 좋아요', 'COMMENT', '2022-08-18 06:40:16', 0, 2),
	(7, 'dadd', 'momm', '좋아요를 눌렀습니다.', 'LIKE', '2022-08-18 06:40:24', 0, 2),
	(8, 'momm', 'dadd', '좋아요를 눌렀습니다.', 'LIKE', '2022-08-18 06:42:56', 0, 3),
	(9, 'momm', 'boyy', '좋아요를 눌렀습니다.', 'LIKE', '2022-08-18 06:43:02', 0, 3),
	(10, 'momm', 'boyy', '아빠랑 같이 가셨어요?', 'COMMENT', '2022-08-18 06:43:12', 0, 3),
	(11, 'boyy', 'momm', '좋아요를 눌렀습니다.', 'LIKE', '2022-08-18 06:44:34', 1, 4),
	(12, 'boyy', 'momm', '아들 집에는 언제 오니?', 'COMMENT', '2022-08-18 06:44:44', 1, 4),
	(14, 'momm', 'boyy', '님이 당신을 태그했습니다 !', 'TAGGED', '2022-08-18 06:48:06', 0, 6),
	(15, 'boyy', 'momm', '좋아요를 눌렀습니다.', 'LIKE', '2022-08-18 06:48:17', 1, 6),
	(16, 'boyy', 'momm', '그럼, 엄청 맛있었지', 'COMMENT', '2022-08-18 06:48:23', 1, 6),
	(17, 'boyy', 'dadd', '좋아요를 눌렀습니다.', 'LIKE', '2022-08-18 06:48:32', 1, 6),
	(18, 'boyy', 'dadd', '좋아요를 눌렀습니다.', 'LIKE', '2022-08-18 06:50:15', 1, 5);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;

-- 테이블 데이터 sai.poll:~1 rows (대략적) 내보내기
/*!40000 ALTER TABLE `poll` DISABLE KEYS */;
INSERT INTO `poll` (`poll_id`, `created_at`, `updated_at`, `created_by`, `updated_by`, `poll_end_datetime`, `poll_title`, `board_id`) VALUES
	(2, '2022-08-18 06:49:25.902000', '2022-08-18 06:49:25.902000', 'dadd', 'dadd', '2022-08-21 15:49:25.969000', '오늘 저녁 뭐 먹을까', 7);
/*!40000 ALTER TABLE `poll` ENABLE KEYS */;

-- 테이블 데이터 sai.reply:~4 rows (대략적) 내보내기
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` (`reply_id`, `board_id`, `reply_user_id`, `reply_content`, `reply_reg_datetime`) VALUES
	(1, 2, 'boyy', '네, 좋아요', '2022-08-18 06:40:16'),
	(2, 3, 'boyy', '아빠랑 같이 가셨어요?', '2022-08-18 06:43:12'),
	(3, 4, 'momm', '아들 집에는 언제 오니?', '2022-08-18 06:44:44'),
	(4, 6, 'momm', '그럼, 엄청 맛있었지', '2022-08-18 06:48:23');
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;

-- 테이블 데이터 sai.user:~5 rows (대략적) 내보내기
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `family_id`, `user_name`, `email`, `password`, `user_type`, `birthday`, `lunar`, `user_image_path`, `user_image_path_server`, `user_image_name`, `user_image_type`, `user_message`, `role`) VALUES
	('boyy', '305305', '이군선', 'boyy@ssafy.com', '$2a$10$.8TvU4IOi2JuHVmP8nLIH.D6uHBY8cb6.03zx8c8BZrcB.sU/DXzi', NULL, '1994-08-19', 0, '/saimedia/UserImage/9abcbcbb-c480-40a9-be80-d32a1ca77856_아들프사.jpg', '/app/UserImage/9abcbcbb-c480-40a9-be80-d32a1ca77856_아들프사.jpg', '아들프사.jpg', 'image/jpeg', '', 'USER'),
	('dadd', '305305', '이선민', 'dadd@ssafy.com', '$2a$10$DIvCnMqpMq3NwTE97CwfzuqyCdaR51N13KE6kzzc8QBzxXATMxbUe', NULL, '1967-07-17', 0, '/saimedia/UserImage/6e833cd9-002d-4f22-ad0b-1668261249f5_아빠프사.jpg', '/app/UserImage/6e833cd9-002d-4f22-ad0b-1668261249f5_아빠프사.jpg', '아빠프사.jpg', 'image/jpeg', '', 'USER'),
	('girl', NULL, '이미애', 'girl@ssafy.com', '$2a$10$0sD2A9H5U6Vq3HK2e3/sI.6WnO373nzbNlUalyRm1P.PcKVIMyM6e', NULL, '1998-03-23', 0, '/saimedia/UserImage/dd835e7e-975b-49bc-9f47-3c738aa11ffb_딸프사.jpg', '/app/UserImage/dd835e7e-975b-49bc-9f47-3c738aa11ffb_딸프사.jpg', '딸프사.jpg', 'image/jpeg', '', 'USER'),
	('kjsw', NULL, '김싸피', 'kjsw@ssafy.com', '$2a$10$A9Plnu8UMUiuLQfXoAKViu0Igx.OnYUPR8oqJ.Xl23iTyIBFKKv7m', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'USER'),
	('momm', '305305', '김희영', 'momm@ssafy.com', '$2a$10$1XrDxIZce2JK9dadmgZ9FOZZgJkwenZ556FCNulCbsBAJIvzQNAjG', NULL, '1970-08-16', 0, '/saimedia/UserImage/e2d3e7d8-a04a-4bbd-a317-c56d17dc1db6_엄마프사.jpg', '/app/UserImage/e2d3e7d8-a04a-4bbd-a317-c56d17dc1db6_엄마프사.jpg', '엄마프사.jpg', 'image/jpeg', '바르고 고운 말을 쓰자', 'USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- 테이블 데이터 sai.vote:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `vote` DISABLE KEYS */;
/*!40000 ALTER TABLE `vote` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
