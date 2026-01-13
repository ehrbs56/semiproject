# 편의점 재고관리 시스템 프로젝트 분석 보고서

## 📋 프로젝트 개요

**프로젝트명**: 편의점 재고관리 시스템 (Convenience Store Inventory Management System)  
**기술 스택**: Spring Boot 4.0.1, MyBatis, Thymeleaf, Oracle Database, Java 17  
**아키텍처**: MVC 패턴 (Controller-Service-Mapper 계층 구조)

---

## 🏗️ 프로젝트 구조

### 1. 계층 구조 (Layered Architecture)

```
com.example.demo
├── controller/     # 웹 요청 처리 (8개 컨트롤러)
├── service/        # 비즈니스 로직 (12개 서비스)
├── mapper/        # MyBatis 인터페이스 (11개 매퍼)
└── dto/           # 데이터 전송 객체 (21개 DTO)
```

### 2. 주요 디렉토리

- **Controller**: HTTP 요청 처리 및 뷰 반환
- **Service**: 비즈니스 로직 및 트랜잭션 관리
- **Mapper**: MyBatis 인터페이스 (SQL은 XML에 정의)
- **DTO**: 계층 간 데이터 전송 객체
- **Templates**: Thymeleaf 템플릿 파일

---

## 🎯 주요 기능 모듈

### 1. **인증 및 권한 관리 (Authentication & Authorization)**

#### 컨트롤러
- `LoginController`: 로그인 처리
- `LoginCheckController`: 아이디 중복 체크

#### 기능
- **역할 기반 접근 제어**: 
  - `role = 0`: 본사 관리자 → `/hq/home` 리다이렉트
  - `role = 1`: 가맹점 관리자 → `/branch/orders` 리다이렉트
- **세션 관리**: `HttpSession`에 `LoginDto` 저장
- **로그인 정보**: user_id, login_id, role, store_id, owner 정보 포함

#### 데이터베이스 테이블
- `login`: 사용자 로그인 정보 (user_id, login_id, password, role)
- `owner`: 가맹점 주인 정보 (owner_id, store_id, user_id, owner_name, owner_phone)

---

### 2. **가맹점 관리 (Store Management)** - 본사 기능

#### 컨트롤러
- `StoreController`: 가맹점 목록 조회, 검색, 필터링
- `StoreDetailController`: 가맹점 상세 정보 조회
- `StoreUpdateController`: 가맹점 정보 수정
- `InsertController`: 가맹점 등록

#### 주요 기능
- ✅ 가맹점 목록 조회 (`/store`)
- ✅ 가맹점 검색 (`/store/search?keyword=`)
- ✅ 가맹점 필터링 (`/store/filter?status=`)
- ✅ 가맹점 상세 조회 (`/store/detail/{id}`)
- ✅ 가맹점 정보 수정 (`/store/update/{id}`)
- ✅ 가맹점 등록 (`/store/insert`)

#### 데이터베이스 테이블
- `store`: 가맹점 정보 (store_id, store_name, store_status, commission_rate)
- `owner`: 가맹점 주인 정보

---

### 3. **발주 관리 (Order Management)** - 가맹점 기능

#### 컨트롤러
- `OrdersController`: 발주 관련 모든 기능 처리

#### 주요 기능

##### 3.1 발주 요청 (`/branch/orders`)
- 본사 재고 조회 및 발주 수량 입력
- 실시간 발주 목록 관리 (추가/삭제)
- 발주 총액 계산
- **트랜잭션 처리**: 발주 생성 시 재고 차감, 지급 정보 생성

##### 3.2 발주 내역 조회 (`/branch/orders/ordersList`)
- 가맹점별 발주 목록 조회
- **필터링 기능**: 날짜, 카테고리, 상품별 검색
- 대표 상품 표시 (여러 상품일 경우 "상품명 외 N건")

##### 3.3 발주 상세 (`/branch/orders/ordersDetail`)
- 발주번호별 상세 상품 목록 조회
- 상품별 수량, 가격, 총액 표시

#### 비즈니스 로직 (OrdersService.createOrder)
1. **발주 생성**: `orders` 테이블에 발주 정보 저장
2. **수수료 계산**: `total_supply_amount * commission_rate / 100`
3. **지급 생성**: `supply` 테이블에 지급 정보 저장
4. **발주 상세 생성**: `order_item` 테이블에 상품별 발주 정보 저장
5. **재고 차감**: 
   - `product_batch` → `hq_inventory` 순서로 재고 차감
   - FIFO 방식으로 배치별 재고 관리
   - 재고 부족 시 예외 발생
6. **지급 상세 생성**: `supply_detail` 테이블에 배치별 지급 정보 저장

#### 데이터베이스 테이블
- `orders`: 발주 헤더 (order_id, store_id, order_date, total_supply_amount, commission_amount)
- `order_item`: 발주 상세 (order_item_id, order_id, product_id, order_qty)
- `supply`: 지급 헤더 (supply_id, store_id, order_id, supply_date, hq_supply_price)
- `supply_detail`: 지급 상세 (supply_detail_id, supply_id, batch_id, quantity)
- `product_batch`: 상품 배치 정보 (batch_id, product_id)
- `hq_inventory`: 본사 재고 (inventory_id, batch_id, hq_quantity)

---

### 4. **상품 관리 (Product Management)**

#### 서비스
- `ProductService`: 상품 목록 조회, 상품 선택
- `CategoryService`: 카테고리 목록 조회

#### 데이터베이스 테이블
- `product`: 상품 정보 (product_id, category_id, product_name, hq_supply_price)
- `category`: 카테고리 정보 (category_id, category_name)

---

## 🗄️ 데이터베이스 스키마 분석

### 주요 테이블 관계도

```
login (사용자)
  └─ owner (가맹점 주인)
      └─ store (가맹점)
          └─ orders (발주)
              ├─ order_item (발주 상세)
              └─ supply (지급)
                  └─ supply_detail (지급 상세)
                      └─ product_batch (상품 배치)
                          └─ hq_inventory (본사 재고)

product (상품)
  ├─ category (카테고리)
  └─ product_batch (상품 배치)
      └─ hq_inventory (본사 재고)
```

### 시퀀스 (Sequences)
- `seq_orders`: 발주 ID 생성
- `seq_store`: 가맹점 ID 생성
- 기타 시퀀스들...

---

## 🔍 코드 품질 및 아키텍처 분석

### ✅ 잘 구현된 부분

1. **계층 분리**: Controller-Service-Mapper 계층 구조 명확
2. **트랜잭션 관리**: `@Transactional` 어노테이션 사용
3. **의존성 주입**: `@RequiredArgsConstructor` + `final` 필드 사용 (Lombok)
4. **세션 관리**: 로그인 정보를 세션에 저장하여 권한 관리
5. **재고 관리 로직**: FIFO 방식으로 배치별 재고 차감 구현

### ⚠️ 개선이 필요한 부분

1. **에러 처리**
   - 예외 처리가 일부만 구현됨 (OrdersService에서만 RuntimeException 사용)
   - 전역 예외 처리기 없음

2. **보안**
   - 비밀번호 평문 저장 (암호화 필요)
   - SQL Injection 방지 (MyBatis 사용으로 어느 정도 방지되지만 추가 검증 필요)

3. **코드 중복**
   - 일부 컨트롤러에서 비슷한 로직 반복

4. **로깅**
   - `System.out.println` 사용 (SLF4J/Logback 사용 권장)

5. **API 응답 형식**
   - 일부는 JSON, 일부는 뷰 반환으로 일관성 부족

6. **유효성 검증**
   - 입력값 검증 로직 부족 (Bean Validation 미사용)

---

## 📁 주요 파일 구조

### Controller (8개)
1. `homeController.java` - 홈/로그인 페이지
2. `LoginController.java` - 로그인 처리
3. `LoginCheckController.java` - 아이디 중복 체크
4. `OrdersController.java` - 발주 관리 (가맹점)
5. `StoreController.java` - 가맹점 목록/검색/필터
6. `StoreDetailController.java` - 가맹점 상세
7. `StoreUpdateController.java` - 가맹점 수정
8. `InsertController.java` - 가맹점 등록

### Service (12개)
- `OrdersService.java` - 발주 비즈니스 로직 (복잡한 트랜잭션 처리)
- `StoreService.java` - 가맹점 CRUD
- `ProductService.java` - 상품 조회
- `CategoryService.java` - 카테고리 조회
- `LoginService.java` - 로그인 처리
- 기타 서비스들...

### Mapper XML (11개)
- `OrdersMapper.xml` - 발주 관련 SQL
- `StoreMapper.xml` - 가맹점 관련 SQL
- `ProductMapper.xml` - 상품 관련 SQL
- `LoginMapper.xml` - 로그인 관련 SQL
- 기타 매퍼들...

---

## 🌐 URL 라우팅 구조

### 본사 관리자 (role = 0)
- `/` - 로그인 페이지
- `/login` - 로그인 처리 (POST)
- `/store` - 가맹점 목록
- `/store/search` - 가맹점 검색
- `/store/filter` - 가맹점 필터링
- `/store/detail/{id}` - 가맹점 상세
- `/store/update/{id}` - 가맹점 수정
- `/store/insert` - 가맹점 등록

### 가맹점 관리자 (role = 1)
- `/branch/orders` - 발주 요청 페이지
- `/branch/orders/add` - 상품 정보 조회 (AJAX)
- `/branch/orders` - 발주 생성 (POST)
- `/branch/orders/ordersList` - 발주 내역 목록
- `/branch/orders/select` - 발주 내역 검색 (POST)
- `/branch/orders/ordersDetail` - 발주 상세

---

## 🎨 프론트엔드 구조

### 레이아웃
- `fragments/branchLayout.html` - 가맹점 레이아웃
- `fragments/hqLayout.html` - 본사 레이아웃

### 주요 페이지
- `home.html` - 로그인 페이지
- `branch/orders.html` - 발주 요청 페이지 (JavaScript 포함)
- `branch/ordersList.html` - 발주 내역 목록 (JavaScript 포함)
- `branch/ordersDetail.html` - 발주 상세
- `store/store.html` - 가맹점 목록
- `store/detail.html` - 가맹점 상세
- `store/update.html` - 가맹점 수정
- `store/insert.html` - 가맹점 등록

### JavaScript 사용
- `orders.html`: 발주 추가/삭제, 발주 제출 기능
- `ordersList.html`: 발주 내역 검색 기능 (AJAX)

---

## 🔧 기술 스택 상세

### Backend
- **Spring Boot 4.0.1**: 웹 애플리케이션 프레임워크
- **MyBatis 4.0.1**: SQL 매퍼 프레임워크
- **Oracle Database**: 관계형 데이터베이스 (Oracle 11g/12c)
- **Lombok**: 보일러플레이트 코드 제거
- **Java 17**: 프로그래밍 언어

### Frontend
- **Thymeleaf**: 서버 사이드 템플릿 엔진
- **JavaScript (Vanilla)**: 클라이언트 사이드 스크립트
- **CSS**: 스타일링 (`layout.css`)

---

## 📊 비즈니스 로직 흐름

### 발주 프로세스
```
1. 가맹점 관리자 로그인
   ↓
2. 발주 요청 페이지 접근 (/branch/orders)
   ↓
3. 상품 선택 및 수량 입력
   ↓
4. 발주 목록에 추가 (AJAX: /branch/orders/add)
   ↓
5. 발주 제출 (POST: /branch/orders)
   ↓
6. OrdersService.createOrder() 실행
   ├─ 발주 생성 (orders 테이블)
   ├─ 수수료 계산
   ├─ 지급 생성 (supply 테이블)
   ├─ 발주 상세 생성 (order_item 테이블)
   ├─ 재고 차감 (hq_inventory 테이블)
   │  └─ FIFO 방식으로 배치별 재고 차감
   └─ 지급 상세 생성 (supply_detail 테이블)
   ↓
7. 발주 완료 알림 및 페이지 리다이렉트
```

---

## 🐛 발견된 문제점 및 개선 사항

### 1. ordersList.html 스크립트 문제 (해결됨)
- **문제**: 스크립트가 실행되지 않음
- **원인**: `th:inline="javascript"` 속성과 Thymeleaf 레이아웃 시스템 충돌
- **해결**: 일반 `<script>` 태그 사용으로 변경

### 2. 보안 취약점
- 비밀번호 평문 저장
- CSRF 보호 미구현
- XSS 방지 미구현

### 3. 에러 처리 부족
- 전역 예외 처리기 없음
- 일관된 에러 응답 형식 없음

### 4. 코드 품질
- 하드코딩된 값들 (예: 수수료 계산 로직)
- 매직 넘버 사용
- 주석 부족

---

## 📝 권장 개선 사항

1. **보안 강화**
   - 비밀번호 암호화 (BCrypt)
   - CSRF 토큰 추가
   - XSS 방지 (Thymeleaf 기본 제공)

2. **에러 처리**
   - 전역 예외 처리기 구현 (`@ControllerAdvice`)
   - 커스텀 예외 클래스 생성
   - 일관된 에러 응답 형식 정의

3. **코드 품질**
   - 상수 클래스 생성 (매직 넘버 제거)
   - 유효성 검증 추가 (`@Valid`, `@NotNull` 등)
   - 로깅 프레임워크 사용 (SLF4J)

4. **테스트**
   - 단위 테스트 작성
   - 통합 테스트 작성

5. **문서화**
   - API 문서화 (Swagger/OpenAPI)
   - 코드 주석 추가

---

## 🎯 프로젝트 특징 요약

### 강점
- ✅ 명확한 계층 구조
- ✅ 트랜잭션 관리
- ✅ 복잡한 재고 관리 로직 구현
- ✅ 역할 기반 접근 제어

### 약점
- ⚠️ 보안 취약점
- ⚠️ 에러 처리 부족
- ⚠️ 테스트 코드 없음
- ⚠️ 문서화 부족

---

## 📌 결론

이 프로젝트는 **편의점 본사-가맹점 간 재고 및 발주 관리 시스템**으로, 기본적인 CRUD 기능과 복잡한 비즈니스 로직(재고 차감, 지급 관리)을 구현하고 있습니다. 

전반적으로 **MVC 패턴을 잘 따르고 있으며**, 특히 발주 생성 시 재고 차감 로직이 잘 구현되어 있습니다. 다만 **보안, 에러 처리, 테스트** 측면에서 개선이 필요합니다.

---

**분석 일시**: 2024년  
**분석자**: AI Assistant  
**프로젝트 버전**: 0.0.1-SNAPSHOT
