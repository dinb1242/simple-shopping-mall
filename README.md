# 🛍️ Simple Shopping Mall API Server - Spring Boot

### 🔧 Tech. Stack
* Spring Boot 2.6
* Spring JPA
* QueryDSL
* JWT for Sign-in
* Swagger 2.9.2
* AWS S3

### 📖 기능
* 회원가입
* 로그인
* 유저 관리 (조회 및 삭제) - 관리자
* 상품 관리 (조회, 생성, 수정 및 삭제) - 관리자
* 게시글 작성 및 조회
* 회원 정보 조회 및 수정
* 아이디 찾기 및 비밀번호 분실 시 변경
* 상품 조회 및 장바구니 담기
* 주문(주문번호는 UUID 로 생성)

### 🔨 구현
* JWT Access Token 을 활용한 간단한 로그인
* Spring Security 를 활용한 User Permission (Admin, User) 인증/인가 구현
* RestControllerAdvisor 를 활용한 공통 Response 및 Exception Handler 구현
* Filter 단에서 발생하는 예외를 Response 하는 헬퍼 메소드 구현
* Swagger 를 활용한 API 명세
* 공통 엔티티를 활용한 생성일/수정일/삭제여부 공통 필드 (BaseEntity)
* Client 로부터 전달받은 MultipartFile S3 저장 로직
* Spring JPA 를 활용한 1:N 파일 참조 테이블 구현
* QueryDSL 을 활용한 조회 로직
* 커스텀 UserDetails 및 UserDetailsService implements
* DB 정보, S3 키의 외부 환경 변수 주입
