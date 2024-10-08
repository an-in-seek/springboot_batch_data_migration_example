# Product Migration Batch Job

이 프로젝트는 Spring Batch를 사용하여 Product1 테이블의 데이터를 Product2 테이블로 마이그레이션하는 애플리케이션입니다. 배치 작업은 Product1 데이터를 읽고, 처리한 후, 이를 Product2 테이블에 기록하는 과정을 수행합니다.

## 목차

- [개요](#개요)
- [사전 요구 사항](#사전-요구-사항)
- [사용된 기술](#사용된-기술)
- [작동 방식](#작동-방식)
- [설치 방법](#설치-방법)
- [API 엔드포인트](#api-엔드포인트)
- [커스터마이징](#커스터마이징)

## 개요

이 프로젝트는 다음과 같은 배치 작업을 구현합니다:

1. **읽기**: `JpaPagingItemReader`를 사용하여 `Product1` 테이블에서 데이터를 읽습니다.
2. **처리**: 각 `Product1` 엔티티를 처리하여 `Product2` 엔티티로 변환합니다.
3. **쓰기**: 처리된 데이터를 `JpaItemWriter`를 사용하여 `Product2` 테이블에 기록합니다.

또한, 배치 작업을 실행하는 API 엔드포인트를 포함하고 있습니다.

## 사전 요구 사항

이 프로젝트를 실행하기 위해 다음이 필요합니다:

- JDK 11 이상
- Spring Boot 3.2.x 이상
- Gradle
- 관계형 데이터베이스 (예: H2, MySQL, PostgreSQL)

## 사용된 기술

- **Spring Boot**: 애플리케이션 빌드 및 배치 작업 설정에 사용.
- **Spring Batch**: 배치 처리를 구현 (읽기, 처리, 쓰기).
- **Spring Data JPA**: 데이터베이스와 상호작용.
- **H2 Database** (옵션): 테스트를 위한 인메모리 데이터베이스.
- **Lombok**: 보일러플레이트 코드를 줄이기 위해 사용.
- **Slf4j**: 로깅을 위해 사용.

## 작동 방식

### 배치 처리 개요

배치 작업은 다음을 수행합니다:

1. `JpaPagingItemReader`를 통해 `Product1` 테이블에서 데이터를 읽습니다.
2. 각 `Product1` 엔티티를 처리하여 `Product2` 엔티티로 변환하는 `ItemProcessor`가 사용됩니다.
3. 변환된 `Product2` 엔티티는 `JpaItemWriter`를 사용하여 데이터베이스에 기록됩니다.

### 청크 기반 처리

배치 작업은 **청크** 단위로 데이터를 처리합니다. 여기서는 10개씩 처리합니다 (`CHUNK_SIZE = 10`). 즉, 한 번에 10개의 레코드를 읽고, 처리하고, 기록한 후 트랜잭션을 커밋합니다.

### 작업 흐름

1. **작업 정의**: `productMigrationJob` 빈에서 작업이 정의되며, 처리 단계와 연결됩니다.
2. **단계 정의**: `productMigrationStep` 빈에서 청크 크기를 정의하고, 리더, 프로세서, 라이터를 설정합니다.
3. **컨트롤러**: `ProductMigrationController`는 REST 엔드포인트를 통해 작업을 트리거할 수 있도록 API를 제공합니다.

### 코드 구조

- **BatchConfig**: `Product1` 엔티티의 샘플 데이터를 초기화합니다.
- **ProductMigrationJobConfig**: 배치 작업과 필요한 리더, 프로세서, 라이터 빈을 설정합니다.
- **ProductMigrationController**: 배치 작업을 실행할 수 있는 엔드포인트를 제공합니다.

## 설치 방법

### 1. 리포지토리 클론

```bash
git clone https://github.com/your-repo/product-migration-batch.git
cd product-migration-batch
```

### 2. Gradle 빌드

프로젝트 루트 디렉터리에서 다음 명령어로 종속성을 설치합니다:

```bash
./gradlew build
```

### 3. 데이터베이스 설정

`src/main/resources/application.properties` 파일에서 데이터베이스 설정을 구성합니다. 또는 테스트를 위해 H2 인메모리 데이터베이스를 사용할 수 있습니다.

#### MySQL 예시:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

### 4. 애플리케이션 실행

Gradle을 사용하여 애플리케이션을 실행할 수 있습니다:

```bash
./gradlew bootRun
```

### 5. 배치 작업 트리거

애플리케이션이 실행 중일 때 제공된 REST API를 사용하여 배치 작업을 트리거할 수 있습니다.

```bash
curl -X POST http://localhost:8080/api/batches/product-migration
```

배치 작업이 성공적으로 완료되면, `Product1` 테이블의 데이터가 `Product2` 테이블로 마이그레이션됩니다.

### 6. 로그 확인

로그를 통해 작업 실행 상태 및 오류가 발생했는지 확인할 수 있습니다.

## 테스트

JUnit과 Spring Batch Test를 사용하여 배치 작업을 테스트합니다.

### 테스트 실행

```bash
./gradlew test
```

### 테스트 클래스 설명

- **`SpringbootBatchExampleApplicationTests`**: 배치 작업인 `productMigrationJob`이 성공적으로 실행되는지 확인하는 통합 테스트입니다.
    - `Product1` 데이터를 생성하여 작업 실행 전 상태를 준비합니다.
    - 작업 실행 후, `Product2` 테이블로 데이터가 정상적으로 이동했는지 확인합니다.

## API 엔드포인트

다음 REST 엔드포인트가 제공됩니다:

- **POST** `/api/batches/product-migration`: 이 엔드포인트는 제품 마이그레이션 배치 작업을 트리거합니다.

## 커스터마이징

- **청크 크기**: `ProductMigrationJobConfig` 클래스에서 `CHUNK_SIZE` 상수를 수정하여 청크 크기를 조정할 수 있습니다.
- **데이터베이스 설정**: `application.properties` 파일에서 데이터베이스 설정을 환경에 맞게 수정할 수 있습니다.
- **에러 처리**: `ProductMigrationController`의 에러 처리 로직을 필요에 따라 수정할 수 있습니다.
- **데이터 변환**: `ItemProcessor<Product1, Product2>`에서 마이그레이션 요구사항에 맞게 데이터 변환 로직을 수정할 수 있습니다.

## 예시 데이터

`BatchConfig` 클래스는 애플리케이션 시작 시 초기화되는 `Product1` 데이터 예시를 포함하고 있습니다.

| id  | product_code | product_name    |
|-----|--------------|-----------------|
| 1   | P001         | Product Name 1  |
| 2   | P002         | Product Name 2  |
| 3   | P003         | Product Name 3  |
| ... | ...          | ...             |
| 20  | P020         | Product Name 20 |

마이그레이션 동안 이 데이터는 읽혀져서 처리된 후 `Product2` 테이블에 기록됩니다.

---