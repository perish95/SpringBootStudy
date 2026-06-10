-- data.sql (데이터 정의)

-- 사용자 초기 Mock 데이터
INSERT INTO users (email, nickname) VALUES
('user1@example.com','uesr1'),
('user2@example.com','uesr2'),
('user3@example.com','uesr3');

-- 게시글 초기 Mock 데이터
INSERT INTO posts (user_id, title, content) VALUES
(1, '스프링 스터디 모집', '함께 공부하실 분 모집'),
(1, 'Mybatis 초고수만', '상시 환영'),
(2, 'REST API 고도화 전략', 'HTTP 상태 코드와 에러 응답의 표준을 정합니다.');
