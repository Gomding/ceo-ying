# ceo-ying

## 한눈에 보는 쇼핑몰 통계

### 프로젝트의 목적

보고싶은 쇼핑몰 통계 정보를 한눈에 볼 수 있는 서비스

* * * 

프로젝트 개발환경은 다음과 같습니다.

#### 개발환경
* SpringBoot (2.1.7), JPA, Spring-Oauth2-Client
* Java8
* Gradle

#### 테스트 환경 
* JUnit4

#### 템플릿 엔진
* Mustache

#### 구현 부분
* 메인페이지
    - 현재 남은 잔액을 볼 수 있어야함
    - 최근 한달 수익 상황을 확인
        + 최근 판매내역 5개 표시(판매상품 이름, 가격)
    - 최근 한달 구매한 상품과 금액을 확인할 수 있어야함
        + 최근 구매내역 5개 표시(구매상품 이름, 가격)
    - 주요 상품의 재고, 원가를 표시
    - 게시글 작성을 통해 관리자와 소통

* 상품등록, 판매내역, 구매내역 게시판은 등록, 수정, 삭제가 가능해야함
    - 상품 등록
        + 상품이름, 수량, 가격, 원가, 유통기한 등록
        + 원가 = (상품매입가격 + (배송비 / 상품개수)) * 환율

**패키지 내용**
![image](https://user-images.githubusercontent.com/57378410/140740000-73964362-179d-4afc-99c5-93d7b248777e.png)


#### 용어 정의
* Buy : 구매 상품
    - name : 구매한 상품 이름
    - price : 가격
    - amount : 개수
    - content : 상세 내용
    - buydate : 구매한 날짜
* Sell : 판매 관련
    - name : 구매자 ID, 이름
    - product : 상품 이름
    - price : 판매 금액
    - amount : 수량
    - methodOfPayment : 결제수단 ( 카드결제, 계좌이체, 등등) -- ENUM 으로 만들어도됨
    - profit : 수익 (판매금액 - 결제수단에 따른 수수료)
    - selldate : 판매한 날짜
* Product : 상품 관련
    - name : 제품 명
    - amount : 재고
    - price : 가격
    - costprice : 원가
    - sellByDate : 유통기한
* Memo : 메모 관련
    - link : 링크
    - content : 메모 내용
* Wallet : 입출금 내역, 잔액 관련
    - money : 현재 잔액
    - record : 입출금된 돈의 내역 (-1000, +1000)
    - statement : 입출금 내용
    - statementDate : 입출금 날짜
* User : 사용자 관련
    - name : 이름
    - email : 이메일
    - picture : 프로필사진
    - role : 사용자 권한
* Role : 사용자 권한 관련
    - GUEST : 손님 권한 ( 읽기만 가능 )
    - ADMIN : 관리자 권한
