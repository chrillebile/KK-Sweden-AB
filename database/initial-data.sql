INSERT INTO customers (id, name, address) VALUES
(1, "Finkakor AB", "Helsingborg"),
(2, "Småbröd AB", "Malmö"),
(3, "Kaffebröd AB", "Landskorna"),
(4, "Bjudkakor AB", "Ystad"),
(5, "Kalaskakor AB", "Trelleborg"),
(6, "Partykakor AB", "Kristianstad"),
(7, "Gästkakor AB", "Hässleholm"),
(8, "Skånekakor AB", "Perstorp");

INSERT INTO orders (id, latestDeliveryTime, customerId) VALUES 
(1, NULL, 2),
(2, NULL, 1),
(3, NULL, 1),
(4, NULL, 4),
(5, NULL, 3),
(6, NULL, 8),
(7, NULL, 6),
(8, NULL, 3);

INSERT into recipes (id, name) VALUES 
(1, "Nut ring"),
(2, "Nut cookie"),
(3, "Amneris"),
(4, "Tango"),
(5, "Almond delight"),
(6, "Berliner");

INSERT INTO rawMaterials (id, name, amount, unit, lastDeliveryAmount, lastDeliveryTime) VALUES
(1, "Flour", 10000000, 'g', 10000000, "2018-01-01"),
(2, "Butter", 10000000, 'g', 10000000, "2018-01-01"),
(3, "Icing sugar", 10000000, 'g', 10000000, "2000-01-01"),
(4, "Roasted, chopped nuts", 10000000, 'g', 10000000, "2001-12-01"),
(5, "Fine-ground nuts", 10000000, 'g', 10000000, "2005-11-01"),
(6, "Ground, roasted nuts", 10000000, 'g', 10000000, "2017-01-01"),
(7, "Bread crumbs", 10000000, 'g', 10000000, "2017-01-01"),
(8, "Sugar", 10000000, 'g', 10000000, "2018-01-02"),
(9, "Egg whites", 10000000, 'ml', 10000000, "2017-01-01"),
(10, "Chocolate", 10000000, 'g', 10000000, "2017-01-01"),
(11, "Marzipan", 10000000, 'g', 10000000, "1997-08-17"),
(12, "Eggs", 10000000, 'g', 10000000, "2017-01-01"),
(13, "Potato starch", 10000000, 'g', 10000000, "2017-01-01"),
(14, "Wheat flour", 10000000, 'g', 10000000, "2018-01-02"),
(15, "Sodium bicarbonate", 10000000, 'g', 10000000, "2017-01-01"),
(16, "Vanilla", 10000000, 'g', 10000000, "2016-02-02"),
(17, "Chopped almonds", 10000000, 'g', 10000000, "2017-01-01"),
(18, "Cinnamon", 10000000, 'g', 10000000, "2017-01-01"),
(19, "Vanilla sugar", 10000000, 'g', 10000000, "2018-01-01");

INSERT INTO recipeIngredients (recipeId, rawMaterialId, amount) VALUES 
(1, 1, 450),
(1, 2, 450),
(1, 3, 190),
(1, 4, 225),
(2, 5, 750),
(2, 6, 625),
(2, 7, 125),
(2, 8, 375),
(2, 9, 350),
(2, 10, 50),
(3, 11, 750),
(3, 2, 250),
(3, 12, 250),
(3, 13, 25),
(3, 14, 25),
(4, 2, 200),
(4, 8, 250),
(4, 1, 300),
(4, 15, 4),
(4, 16, 2),
(5, 2, 400),
(5, 8, 270),
(5, 17, 279),
(5, 1, 400),
(5, 18, 10),
(6, 1, 350),
(6, 2, 250),
(6, 3, 100),
(6, 12, 50),
(6, 19, 5),
(6, 10, 50);

INSERT INTO orderRecipeAmount (orderId, recipeId, amount) VALUES 
(1, 1, 1),
(2, 2, 1),
(3, 3, 1),
(4, 4, 1),
(5, 5, 1),
(5, 6, 1),
(6, 1, 1),
(7, 2, 1),
(7, 3, 1),
(7, 4, 1),
(8, 5, 1);

INSERT INTO pallets 
(id, productionDate, isBlocked, location, deliveryTime, recipeId, orderId)
VALUES
(1, "2000-12-23", 0, "Lund", NULL, 2, 2),
(2, "1997-11-12", 0, "Malmö", NULL, 1, 1),
(3, "2013-07-11", 0, "Göteborg", NULL, 4, 4),
(4, "2016-06-01", 0, "Stockholm", NULL, 6, 5),
(5, "2015-05-03", 0, "Lund", NULL, 5, 5),
(6, "2008-04-04", 0, "Lund", NULL, 3, 3),
(7, "2011-03-09", 0, "Lund", NULL, 1, 1),
(8, "2018-02-05", 0, "Lund", NULL, 1, 1),
(9, "2018-01-02", 0, "Lund", NULL, 3, NULL);