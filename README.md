# 📝 TDP-Spring

## TDP란?

- "Today I Learned Deserter Pursuit"의 약자로서, [TIL(Today I Learned) 관리 시스템](http://tildp.shop)입니다.

- TIL은 개발자의 성장에 있어 중요한 요소로 언급되고 있으며, <br>
  대부분의 주니어 개발자들은 TIL작성을 두려워하고 많은 시간을 투자하며 작성합니다.<br>
  TDP는 그런 불필요함을 줄이고 TIL에 대한 장벽을 낮추기 위해 고안되었습니다.<br>
  TIL을 매일 작성할 수 있는 알림 서비스와 TIL을 간편하게 작성 할 수 있게 만드는 것이 저희의 서비스 목표입니다.

## 제공하고자 하는 서비스

- 보다 간편하게 TIL을 작성해요 ✏️
- 노력은 배신하지 않아요 🏆
- 프라이빗한 TIL을 작성해요 🔐

## What Did We Do

### 기능 구현

- [ ]  TIL Board - List private / public
- [ ]  TIL Board - Pagination

- [ ]  Notification 구체화
- [ ]  OAuth2(카카오, 구글 등) 사용하여 로그인
- [ ]  Following
- [ ]  Web Socket

### 테스트, 문서화

- [ ]  부하테스트 지표를 남기고, 성능개선 사례 남기기
- [ ]  API문서 자동화(RestDoc)

### 배포

- [ ]  프론트 인프라 s3 + cloudfront로 구성
- [ ]  GitAction과 도커 사용하여 배포

### 기술스택
|종류|이름|
|:---|:---|
|개발 언어|Java|
|데이터베이스|Mysql| -> 임시로 H2 사용중입니다.
|웹 프레임워크|Spring|


# 라이브러리
|라이브러리|Appliance|
|:---|:---|
|spring-boot-starter-date-jap|데이터베이스|
|json|데이터 통신|
|jetbrains:annotations|주석 집합|
|junit|테스트 프레임워크|
|lombok|코드 작성 자동화|
|h2|임시로 사용되는 db|
|springboot_security|Auth|
|Jwt|authentication|
|spring-boot-starter-websocket|Notification|



## How To Use

더 많은 예시와 사용 예제는 [Wiki](https://github.com/0sunzero0/TDP/wiki)를 참고하세요 📚
