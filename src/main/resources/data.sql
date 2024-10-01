-- 테스트 계정
-- TODO: 테스트용이지만 비밀번호가 노출된 데이터 세팅. 개선하는 것이 좋을 지 고민해 보자.
INSERT INTO "user" (username, password, role, created_at, updated_at, deleted_at) VALUES ('rhkwk3333', 'qwerty', 'USER', now(), now(), now());
INSERT INTO "user" (username, password, role, created_at, updated_at, deleted_at) VALUES ('iady7777', 'qwerty', 'USER', now(), now(), now());
INSERT INTO "user" (username, password, role, created_at, updated_at, deleted_at) VALUES ('iamysd629', 'qwerty', 'USER', now(), now(), now());
INSERT INTO "user" (username, password, role, created_at, updated_at, deleted_at) VALUES ('math0685', 'qwerty', 'USER', now(), now(), now());
INSERT INTO "user" (username, password, role, created_at, updated_at, deleted_at) VALUES ('hcho302', 'qwerty', 'USER', now(), now(), now());

-- 샘플 게시글 수 15개
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('Post1', 'Sample content 1', 'rhkwk3333', '2024-05-14 10:34:56', '2024-05-14 10:34:56', '2024-05-14 10:34:56');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('Post2', 'Sample content 2', 'iady7777', '2024-06-10 12:22:33', '2024-06-10 12:22:33', '2024-06-10 12:22:33');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('Post3', 'Sample content 3', 'iamysd629', '2024-07-18 09:15:44', '2024-07-18 09:15:44', '2024-07-18 09:15:44');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('Post4', 'Sample content 4', 'math0685', '2024-08-22 08:45:12', '2024-08-22 08:45:12', '2024-08-22 08:45:12');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('Post5', 'Sample content 5', 'hcho302', '2024-09-02 11:35:22', '2024-09-02 11:35:22', '2024-09-02 11:35:22');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('random', 'how are you?? hello world', 'iady7777', '2024-05-14 10:34:56', '2024-05-14 10:34:56', '2024-05-14 10:34:56');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('hello', 'nice to meet you!', 'math0685', '2024-06-22 15:20:11', '2024-06-22 15:20:11', '2024-06-22 15:20:11');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('world', 'this is a test', 'rhkwk3333', '2024-07-08 08:12:44', '2024-07-08 08:12:44', '2024-07-08 08:12:44');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('test', 'checking post content', 'hcho302', '2024-08-01 12:45:34', '2024-08-01 12:45:34', '2024-08-01 12:45:34');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('sample', 'content with random text', 'iady7777', '2024-09-03 14:21:09', '2024-09-03 14:21:09', '2024-09-03 14:21:09');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('short', 'shorter content', 'iamysd629', '2024-05-26 09:13:24', '2024-05-26 09:13:24', '2024-05-26 09:13:24');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('long', 'this is a longer content', 'math0685', '2024-06-10 07:29:31', '2024-06-10 07:29:31', '2024-06-10 07:29:31');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('quick', 'fast post content', 'rhkwk3333', '2024-07-19 11:54:17', '2024-07-19 11:54:17', '2024-07-19 11:54:17');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('fun', 'fun times ahead', 'hcho302', '2024-08-12 16:33:40', '2024-08-12 16:33:40', '2024-08-12 16:33:40');
INSERT INTO post (title, content, username, created_at, updated_at, deleted_at) VALUES ('news', 'latest updates and news', 'iamysd629', '2024-09-17 18:46:52', '2024-09-17 18:46:52', '2024-09-17 18:46:52');
