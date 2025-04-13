# codeMate

coding assistant

## 기능 설명

### # 4. 브랜드 및 상품 추가/수정/삭제

모든 기능을 하나의 api로 제공하기 위해 요청 데이터 그대로를 반영하는 방식으로 개발했습니다.
id가 없으면 새로운 데이터를 의미하고, id가 존재하면 기존 데이터 수정을 의미합니다.

**새로운 데이터 생성**
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