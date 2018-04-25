SET FOREIGN_KEY_CHECKS=1;
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
SET FOREIGN_KEY_CHECKS=1;