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

* Buy : 구매관련 상품 이름, 수량, 가격, 상세내용
* Sell : 판매관련 구매자, 상품 이름, 수량, 가격, 수익
* Product : 상품 관련 상품 이름, 재고, 가격, 원가, 유통기한
* Memo : 메모 관련 글 내용, 링크
* Wallet : 판매, 구매에 따른 입출금 내역 및 잔액
* BaseTimeEntity : Entity클래스가 상속하면 createdDate, modifiedDate 컬럼을 생성해줌
* Pages : 페이징 기능 담당

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
##테스트

**test**

* 각 Entitiy 별 생성, 수정에 대한 단위 테스트
* BaseTimeEntity 
