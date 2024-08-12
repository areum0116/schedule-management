# 나만의 일정 관리 앱 서버
### 🎯Goal
1. 구현하고자 하는 서비스의 전체적인 흐름을 파악하고 필요한 기능 설계하기
2. API 명세서, ERD, SQL 작성하기
3. Spring Boot 기반 CRUD (Create, Read, Update, Delete) 기능이 포함된 REST API 만들기

## API 명세서

|기능|Method|URL|request|response|상태코드|
|---|------|----|------|--------|-------|
|일정 전체 조회|**GET**|/schedules|요청 param|다건 응답 정보|200: 정상조회|
|일정 조회|**GET**|/schedules/{id}|요청 param|단건 응답 정보|200: 정상조회|
|일정 등록|**POST**|/schedules|요청 body|등록 정보|200: 정상등록|
|수정일 기준 일정 조회|**GET**|/schedules?sort=updateDate|경로 변수|다건 혹은 단건 응답 정보|200: 정상조회|
|매니저 기준 일정 조회|**GET**|/schedules?manager={manager}|경로 변수|다건 혹은 단건 응답 정보|200: 정상조회|
|일정 수정|**PUT**|/schedules/{id}|요청 body|수정 날짜|200: 정상수정|
|일정 삭제|**DELETE**|/schedules/{id}|요청 body|정상 삭제 메시지|200: 정상삭제|
