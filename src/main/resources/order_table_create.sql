DROP TABLE IF EXISTS orders;

DROP SEQUENCE IF EXISTS ordersid;

CREATE SEQUENCE ordersid START WITH 1;

CREATE TABLE orders
(
    id          BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('ordersId'),
    orderNumber VARCHAR(255)
)