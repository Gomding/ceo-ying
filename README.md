# ceo-ying

## 한눈에 보는 쇼핑몰 통계

### 프로젝트의 목적
보고싶은 정보를 한눈에 볼 수 있는 통계페이지

* * * 

프로젝트 개발환경은 다음과 같습니다.      

**개발환경**
* IDE : IntelliJ IDEA Ultimate
* OS : Mac OS X
* SpringBoot 2.1.7
* Java8
* Gradle
* JPA
* 테스트 환경 JUnit4

**템플릿 엔진**
* Mustache

**패키지 내용**

* Buy : 구매 상품
  - id(PK)
  - name : 구매한 상품 이름
  - price : 가격
  - amount : 개수
  - content : 상세 내용
  - buydate : 구매한 날짜
* Sell : 판매 관련
  - id(PK)
  - name : 구매자 ID, 이름
  - product : 상품 이름
  - price : 판매 금액
  - amount : 수량
  - methodOfPayment : 결제수단 ( 카드결제, 계좌이체, 등등) -- ENUM 으로 만들어도됨
  - profit : 수익 (판매금액 - 결제수단에 따른 수수료)
  - selldate : 판매한 날짜
* Product : 제품 관련
  - id(PK)
  - name : 제품 명
  - amount : 재고
  - price : 가격
  - costprice : 원가
  - sellByDate : 유통기한
* Memo : 메모 관련
  - link : 링크
  - content : 메모 내용
* Wallet : 입출금 내역, 잔액 관련
  - id(PK)
  - money : 현재 잔액
  - record : 입출금된 돈의 내역 (-1000, +1000)
  - statement : 입출금 내용
  - statementDate : 입출금 날짜
* User : 사용자 관련
  - id(PK)
  - name : 이름
  - email : 이메일
  - picture : 프로필사진
  - role : 사용자 권한
* Role : 사용자 권한 관련
  - GUEST : 손님 권한 ( 읽기만 가능 )
  - ADMIN : 관리자 권한
* BaseTimeEntity : Entity클래스가 상속하면 createdDate, modifiedDate 컬럼을 생성해줌
   
* Pages : 페이징 기능을 위한 클래스
  - nextPage : 다음 페이지번호
  - prevPage : 이전 페이지번호
  - pageNums : 현재 위치한 페이지의 시작부터 끝까지의 번호리스트 (ex. 1 ~ 10 / 11 ~ 20 / 21 ~ 30 )

**Web**

* Request 와 Response를 위한 DTO 와 Controller 로 구성되어 있습니다.

**service**
* 트랜잭션과 도메인간의 순서를 보장하는 Service 가 있습니다.
* [Service Layer에 대한 오해](https://parkadd.tistory.com/13?category=913964)

**Domain**
* 각각의 Entity들이 있습니다.

**config**
* 각종 설정관련 클래스가 있습니다.
* 현재는 구글,네이버 로그인, JPA Auditing, 로그인시 Session을 추가하는 어노테이션 등이 있습니다.

* * * 
## 테스트

**test**

* 각 Entitiy 별 생성, 수정에 대한 단위 테스트
* BaseTimeEntity 
