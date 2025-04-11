CREATE TABLE `customer` (
  `CustomerID` int NOT NULL AUTO_INCREMENT,
  `CustomerName` varchar(50) DEFAULT NULL,
  `CustomerPoints` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`CustomerID`)
) ;

select *from employee;
CREATE TABLE `employee` (
  `EmployeeID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) DEFAULT NULL,
  `ContactInfo` varchar(15) NOT NULL,
  `Position` varchar(30) NOT NULL,
  `Password` varchar(15) NOT NULL,
  `salary` double NOT NULL,
  `address` varchar(15) NOT NULL,
  PRIMARY KEY (`EmployeeID`)
) ;

CREATE TABLE `ingredient` (
  `IngredientId` int NOT NULL AUTO_INCREMENT,
  `IngredientName` varchar(50) DEFAULT NULL,
  `IngredientSupplier` varchar(50) DEFAULT NULL,
  `quantity_in_stock` int DEFAULT NULL,
  PRIMARY KEY (`IngredientId`)
) ;

CREATE TABLE `menu_item` (
  `MenuID` int NOT NULL AUTO_INCREMENT,
  `MenuName` varchar(50) DEFAULT NULL,
  `MenuDescription` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`MenuID`)
) ;

CREATE TABLE `menu_item_ingredient` (
  `MenuID` int NOT NULL,
  `IngredientId` int NOT NULL,
  `QuantityRequired` int DEFAULT NULL,
  PRIMARY KEY (`MenuID`,`IngredientId`),
  KEY `ingredient_fk` (`IngredientId`),
  CONSTRAINT `ingredient_fk` FOREIGN KEY (`IngredientId`) REFERENCES `ingredient` (`IngredientId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `menu_item_fk` FOREIGN KEY (`MenuID`) REFERENCES `menu_item` (`MenuID`) ON DELETE CASCADE
) ;

CREATE TABLE `order_items` (
  `OrderID` int NOT NULL,
  `MenuID` int NOT NULL,
  `Quantity` int NOT NULL DEFAULT '1',
  `OrderPrice` decimal(10,2) NOT NULL,
  PRIMARY KEY (`OrderID`,`MenuID`,`Quantity`,`OrderPrice`),
  KEY `fk_menu_item` (`MenuID`),
  CONSTRAINT `fk_menu_item` FOREIGN KEY (`MenuID`) REFERENCES `menu_item` (`MenuID`) ON DELETE CASCADE,
  CONSTRAINT `fk_order` FOREIGN KEY (`OrderID`) REFERENCES `orders` (`OrderID`) ON DELETE CASCADE
) ;

select orders.CustomerID, count(*) AS nummber_of_Orders
from orders 
group by orders.CustomerID 
ORDER BY nummber_of_Orders desc;

create view Sales AS
select c.CustomerName ,o.OrderDate
from customer c   join orders o 
on c.CustomerID=o.CustomerID;

INSERT INTO orders (OrderDate, OrderTime, CustomerID, EmployeeID, PaymentID, TotalPrice)
VALUES ('2025-1-16', '1:45:12', 3, 2, 1, 250);


CREATE TABLE `orders` (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `OrderDate` date DEFAULT NULL,
  `OrderTime` time DEFAULT NULL,
  `CustomerID` int DEFAULT NULL,
  `EmployeeID` int DEFAULT NULL,
  `PaymentID` int DEFAULT NULL,
  `TotalPrice` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`OrderID`),
  KEY `CustomerID` (`CustomerID`),
  KEY `EmployeeID` (`EmployeeID`),
  KEY `PaymentID` (`PaymentID`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`CustomerID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`EmployeeID`) REFERENCES `employee` (`EmployeeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`PaymentID`) REFERENCES `payment` (`PaymentID`) ON DELETE CASCADE ON UPDATE CASCADE
) ;

CREATE TABLE `payment` (
  `PaymentID` int NOT NULL AUTO_INCREMENT,
  `PaymentMethod` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`PaymentID`)
) ;



SELECT i.`IngredientId`, i.`IngredientName` ,i.`IngredientSupplier`,i.`quantity_in_stock`
	               FROM `ingredient` AS i 
	               JOIN `menu_item_ingredient` AS m ON i.`IngredientId` = m.`IngredientId` 
	               WHERE m.`MenuID` = 12;
