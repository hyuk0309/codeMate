-- Brand 더미 데이터
INSERT INTO brand (id, name) VALUES (1, 'A');
INSERT INTO brand (id, name) VALUES (2, 'B');
INSERT INTO brand (id, name) VALUES (3, 'C');
INSERT INTO brand (id, name) VALUES (4, 'D');
INSERT INTO brand (id, name) VALUES (5, 'E');
INSERT INTO brand (id, name) VALUES (6, 'F');
INSERT INTO brand (id, name) VALUES (7, 'G');
INSERT INTO brand (id, name) VALUES (8, 'H');
INSERT INTO brand (id, name) VALUES (9, 'I');

ALTER TABLE brand ALTER COLUMN id RESTART WITH 10;

-- Product 더미 데이터
-- Brand A
INSERT INTO product (category, price, brand_id) VALUES ('TOP', 11200, 1);
INSERT INTO product (category, price, brand_id) VALUES ('OUTER', 5500, 1);
INSERT INTO product (category, price, brand_id) VALUES ('PANTS', 4200, 1);
INSERT INTO product (category, price, brand_id) VALUES ('SNEAKERS', 9000, 1);
INSERT INTO product (category, price, brand_id) VALUES ('BAG', 2000, 1);
INSERT INTO product (category, price, brand_id) VALUES ('HAT', 1700, 1);
INSERT INTO product (category, price, brand_id) VALUES ('SOCKS', 1800, 1);
INSERT INTO product (category, price, brand_id) VALUES ('ACCESSORY', 2300, 1);

-- Brand B
INSERT INTO product (category, price, brand_id) VALUES ('TOP', 10500, 2);
INSERT INTO product (category, price, brand_id) VALUES ('OUTER', 5900, 2);
INSERT INTO product (category, price, brand_id) VALUES ('PANTS', 3800, 2);
INSERT INTO product (category, price, brand_id) VALUES ('SNEAKERS', 9100, 2);
INSERT INTO product (category, price, brand_id) VALUES ('BAG', 2100, 2);
INSERT INTO product (category, price, brand_id) VALUES ('HAT', 2000, 2);
INSERT INTO product (category, price, brand_id) VALUES ('SOCKS', 2000, 2);
INSERT INTO product (category, price, brand_id) VALUES ('ACCESSORY', 2200, 2);

-- Brand C
INSERT INTO product (category, price, brand_id) VALUES ('TOP', 10000, 3);
INSERT INTO product (category, price, brand_id) VALUES ('OUTER', 6200, 3);
INSERT INTO product (category, price, brand_id) VALUES ('PANTS', 3300, 3);
INSERT INTO product (category, price, brand_id) VALUES ('SNEAKERS', 9200, 3);
INSERT INTO product (category, price, brand_id) VALUES ('BAG', 2200, 3);
INSERT INTO product (category, price, brand_id) VALUES ('HAT', 1900, 3);
INSERT INTO product (category, price, brand_id) VALUES ('SOCKS', 2200, 3);
INSERT INTO product (category, price, brand_id) VALUES ('ACCESSORY', 2100, 3);

-- Brand D
INSERT INTO product (category, price, brand_id) VALUES ('TOP', 10100, 4);
INSERT INTO product (category, price, brand_id) VALUES ('OUTER', 5100, 4);
INSERT INTO product (category, price, brand_id) VALUES ('PANTS', 3000, 4);
INSERT INTO product (category, price, brand_id) VALUES ('SNEAKERS', 9500, 4);
INSERT INTO product (category, price, brand_id) VALUES ('BAG', 2500, 4);
INSERT INTO product (category, price, brand_id) VALUES ('HAT', 1500, 4);
INSERT INTO product (category, price, brand_id) VALUES ('SOCKS', 2400, 4);
INSERT INTO product (category, price, brand_id) VALUES ('ACCESSORY', 2000, 4);

-- Brand E
INSERT INTO product (category, price, brand_id) VALUES ('TOP', 10700, 5);
INSERT INTO product (category, price, brand_id) VALUES ('OUTER', 5000, 5);
INSERT INTO product (category, price, brand_id) VALUES ('PANTS', 3800, 5);
INSERT INTO product (category, price, brand_id) VALUES ('SNEAKERS', 9900, 5);
INSERT INTO product (category, price, brand_id) VALUES ('BAG', 2300, 5);
INSERT INTO product (category, price, brand_id) VALUES ('HAT', 1800, 5);
INSERT INTO product (category, price, brand_id) VALUES ('SOCKS', 2100, 5);
INSERT INTO product (category, price, brand_id) VALUES ('ACCESSORY', 2100, 5);

-- Brand F
INSERT INTO product (category, price, brand_id) VALUES ('TOP', 11200, 6);
INSERT INTO product (category, price, brand_id) VALUES ('OUTER', 7200, 6);
INSERT INTO product (category, price, brand_id) VALUES ('PANTS', 4000, 6);
INSERT INTO product (category, price, brand_id) VALUES ('SNEAKERS', 9300, 6);
INSERT INTO product (category, price, brand_id) VALUES ('BAG', 2100, 6);
INSERT INTO product (category, price, brand_id) VALUES ('HAT', 1600, 6);
INSERT INTO product (category, price, brand_id) VALUES ('SOCKS', 2300, 6);
INSERT INTO product (category, price, brand_id) VALUES ('ACCESSORY', 1900, 6);

-- Brand G
INSERT INTO product (category, price, brand_id) VALUES ('TOP', 10500, 7);
INSERT INTO product (category, price, brand_id) VALUES ('OUTER', 5800, 7);
INSERT INTO product (category, price, brand_id) VALUES ('PANTS', 3900, 7);
INSERT INTO product (category, price, brand_id) VALUES ('SNEAKERS', 9000, 7);
INSERT INTO product (category, price, brand_id) VALUES ('BAG', 2200, 7);
INSERT INTO product (category, price, brand_id) VALUES ('HAT', 1700, 7);
INSERT INTO product (category, price, brand_id) VALUES ('SOCKS', 2100, 7);
INSERT INTO product (category, price, brand_id) VALUES ('ACCESSORY', 2000, 7);

-- Brand H
INSERT INTO product (category, price, brand_id) VALUES ('TOP', 10800, 8);
INSERT INTO product (category, price, brand_id) VALUES ('OUTER', 6300, 8);
INSERT INTO product (category, price, brand_id) VALUES ('PANTS', 3100, 8);
INSERT INTO product (category, price, brand_id) VALUES ('SNEAKERS', 9700, 8);
INSERT INTO product (category, price, brand_id) VALUES ('BAG', 2100, 8);
INSERT INTO product (category, price, brand_id) VALUES ('HAT', 1600, 8);
INSERT INTO product (category, price, brand_id) VALUES ('SOCKS', 2000, 8);
INSERT INTO product (category, price, brand_id) VALUES ('ACCESSORY', 2000, 8);

-- Brand I
INSERT INTO product (category, price, brand_id) VALUES ('TOP', 11400, 9);
INSERT INTO product (category, price, brand_id) VALUES ('OUTER', 6700, 9);
INSERT INTO product (category, price, brand_id) VALUES ('PANTS', 3200, 9);
INSERT INTO product (category, price, brand_id) VALUES ('SNEAKERS', 9500, 9);
INSERT INTO product (category, price, brand_id) VALUES ('BAG', 2400, 9);
INSERT INTO product (category, price, brand_id) VALUES ('HAT', 1700, 9);
INSERT INTO product (category, price, brand_id) VALUES ('SOCKS', 1700, 9);
INSERT INTO product (category, price, brand_id) VALUES ('ACCESSORY', 2400, 9);