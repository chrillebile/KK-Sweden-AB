PRAGMA foreign_keys=OFF;
DROP TABLE IF EXISTS customers;
CREATE TABLE customers(
    id INTEGER,
    name TEXT,
    address TEXT,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders(
    id INTEGER,
    latestDeliveryTime TIMESTAMP,
    customerId INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (customerId) REFERENCES customers(id)
);

DROP TABLE IF EXISTS recipes;
CREATE TABLE recipes(
    id INTEGER,
    name TEXT,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS pallets;
CREATE TABLE pallets(
    id INTEGER,
    amount INTEGER CHECK(amount >= 0),
    productionDate DATE,
    isBlocked BOOLEAN,
    location TEXT,
    deliveryTime TIMESTAMP,
    recipeId INTEGER,
    orderId INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (recipeId) REFERENCES recipes(id),
    FOREIGN KEY (orderId) REFERENCES orders(id)
);

DROP TABLE IF EXISTS rawMaterials;
CREATE TABLE rawMaterials(
    id INTEGER,
    name TEXT,
    amount INTEGER CHECK(amount >= 0),
    unit TEXT,
    lastDeliveryAmount INTEGER,
    lastDeliveryTime DATE,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS recipeIngredients;
CREATE TABLE recipeIngredients(
    recipeId INTEGER,
    rawMaterialId INTEGER,
    amount INTEGER CHECK(amount >= 0),
    FOREIGN KEY (recipeId) REFERENCES recipes(id),
    FOREIGN KEY (rawMaterialId) REFERENCES rawMaterials(id),
    PRIMARY KEY (recipeId, rawMaterialId)
);

DROP TABLE IF EXISTS orderRecipeAmount;
CREATE TABLE orderRecipeAmount(
    orderId INTEGER,
    recipeId INTEGER,
    amount INTEGER CHECK(amount >= 0),
    FOREIGN KEY (orderId) REFERENCES orders(id),
    FOREIGN KEY (recipeId) REFERENCES recipes(id),
    PRIMARY KEY (orderId, recipeId)
);

DROP TRIGGER IF EXISTS updateRawMaterialAmountOnInsert;
CREATE TRIGGER updateRawMaterialAmountOnInsert 
BEFORE INSERT ON pallets
BEGIN
    update rawMaterials
    set amount = amount - 360 * (
        select (recipeIngredients.amount) from recipeIngredients --Get amount for one ingredient.
        where NEW.recipeId = recipeIngredients.recipeId
    )
    where rawMaterials.id = (
        select (rawMaterialId) from recipeIngredients --Get id for ingredients in the recigpe.
        join rawMaterials
        on recipeIngredients.rawMaterialId = rawMaterials.id
    );
END;

PRAGMA foreign_keys=ON;
INSERT INTO customers (id, name, address) VALUES
(1, "Johan Andersson", "Magistratsvägen"),
(2, "Peter Svensson", "Lundavägen 13");

INSERT INTO orders (id, latestDeliveryTime, customerId) VALUES 
(1, 1523224020, 1),
(2, 1483912020, 1);

INSERT into recipes (id, name) VALUES 
(1, "ChokladKakor"),
(2, "VaniljKakor"),
(3, "SyltKakor");

INSERT INTO pallets 
(id, amount, productionDate, isBlocked, location, deliveryTime, recipeId, orderId)
VALUES
(1, 15, "2017-01-08", 0, "Lund", null, 2, 1),
(2, 10, "2017-01-08", 0, "Lund", null, 3, 1);

INSERT INTO rawMaterials (id, name, amount, unit, lastDeliveryAmount, lastDeliveryTime) VALUES
(1, "Mjöl", 2300, 'kg', 0, null),
(2, "Mjölk", 23, 'liter', 0, null);

INSERT INTO recipeIngredients (recipeId, rawMaterialId, amount) VALUES 
(1, 1, 3),
(1, 2, 1),
(2, 1, 7),
(2, 2, 3);

INSERT INTO orderRecipeAmount (orderId, recipeId, amount) VALUES 
(1, 1, 4);
