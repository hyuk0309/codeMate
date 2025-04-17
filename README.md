# codyMate

Cody Assistant Service

## 설명

코디를 추천해주는 서비스입니다. 제공하는 기능은 아래와 같습니다.

### 운영자
- 브랜드를 추가, 수정 할 수 있습니다.
  - 브랜드에 있는 상품을 추가/수정/삭제 할 수 있습니다. 단, 카테고리별로 적어도 1개의 상품이 존재해야 합니다.

### 일반 사용자
- 카테고리 별로 최저 가격 상품의 브랜드와 가격정보를 알 수 있고, 해당 상품들로 코디했을때 필요한 금액을 알 수 있습니다.
- 단일 브랜드로 코디할 때 가장 저렴한 브랜드를 알려줍니다.
- 특정 카테고리의 최저 가격 상품과 최고 가격 상품을 제공합니다. 

## 시작 방법
1. repo를 clone 합니다.
```shell
git clone https://github.com/hyuk0309/codyMate.git
```

2.  프로젝트로 들어가 코드를 빌드합니다.
```shell
./gradlew clean build
```

3. .jar 파일을 실행합니다.
```shell
java -jar ./build/libs/codyMate-0.0.1-SNAPSHOT.jar
```

### API SAMPLE

- 브랜드 정보 추가/수정
  **새로운 브랜드 생성**
```shell
curl --location 'http://localhost:8080/v1.0/brands' \
--header 'Content-Type: application/json' \
--data '{
  "name": "Nike",
  "products": [
    {
      "category": "TOP",
      "price": 12000
    },
    {
      "category": "PANTS",
      "price": 15000
    },
    {
      "category": "SNEAKERS",
      "price": 9000
    },
    {
      "category": "BAG",
      "price": 20000
    },
    {
      "category": "OUTER",
      "price": 18000
    },
    {
      "category": "HAT",
      "price": 5000
    },
    {
      "category": "SOCKS",
      "price": 2000
    },
    {
      "category": "ACCESSORY",
      "price": 2500
    }
  ]
}'
```

**기존 브랜드 수정**
```shell
curl --location 'http://localhost:8080/v1.0/brands' \
--header 'Content-Type: application/json' \
--data '{
    "id": 10,
    "name": "Nike",
    "products": [
        {
            "id": 73,
            "category": "TOP",
            "price": 12000
        },
        {
            "id": 74,
            "category": "PANTS",
            "price": 15000
        },
        {
            "id": 75,
            "category": "SNEAKERS",
            "price": 9000
        },
        {
            "id": 76,
            "category": "BAG",
            "price": 20000
        },
        {
            "id": 77,
            "category": "OUTER",
            "price": 18000
        },
        {
            "id": 78,
            "category": "HAT",
            "price": 5000
        },
        {
            "id": 79,
            "category": "SOCKS",
            "price": 2000
        },
        {
            "category": "ACCESSORY",
            "price": 1
        }
    ]
}'
```
(id 값이 있으면 수정이라고 판단합니다.)

- 카테고리별 최저 가격 상품 조합 조회 API
```shell
curl --location 'http://localhost:8080/v1.0/cody/categories/lowest-price-summary'
```

- 단일 브랜드 최저 가격 조합 조회 API
```shell
curl --location 'http://localhost:8080/v1.0/cody/brands/lowest-price-summary'
```

- 카테고리별 최저/최고 가격 상품 조회 API
```shell
curl --location 'http://localhost:8080/v1.0/cody/categories/top/top-bottom-price'
```

## 구현 설명

### 브랜드 및 상품 정보 추가/수정/삭제
DDD 관점으로 개발했습니다. 브랜드 및 상품 저장시 필요한 조건들(ex 모든 카테고리에 적어도 1개 상품 존재)을 비지니스 로직이라 생각하고
도메인 모델에 비지니스 로직을 작성했습니다. 그리고 해당 스펙들을 TC로 문서화했습니다.

### 조회 관련
카테고리별 최저가 상품 조합과 최저 단일 브랜드 조홥을 구하기 위해서는 많은 데이터를 봐야합니다.
이를 매 조회마다 집계하는건 효율적이지 않다고 생각했습니다. 따라서, 실제 데이터 변경 시점에만 이벤트로 받아 집계하도록 개발했습니다.

카테고리별 최저가/최고가를 구하는 기능은 DB Index를 타면 빠르게 조회할 수 있기 때문에 조회마다 DB를 조회하도록 개발했습니다.
이는 부하가 심하다면 Cache를 적용하거나, 위 방법처럼 개선 가능합니다.
