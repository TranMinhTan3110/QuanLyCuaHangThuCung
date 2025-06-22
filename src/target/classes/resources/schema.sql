create DATABASE QuanLyCuaHangThuCung;

CREATE TABLE Pet (
                     petID INT PRIMARY KEY IDENTITY(1,1),
                     name NVARCHAR(100) NOT NULL,
                     species NVARCHAR(50) NOT NULL,  -- Loài (Chó, Mèo, ...)
                     breed NVARCHAR(100) NULL,       -- Giống loài (Golden Retriever, Maine Coon, ...)
                     age INT CHECK (age >= 0),       -- Tuổi thú cưng >= 0
                     gender NVARCHAR(10) CHECK (gender IN ('Male', 'Female')), -- Giới tính
                     price DECIMAL(10,2) NULL,       -- Giá thú cưng
                     trangThai NVARCHAR(20) DEFAULT 'Chưa bán'  -- Mặc định là 'available'
);

CREATE TABLE Product (
                         productID INT PRIMARY KEY IDENTITY(1,1),
                         name NVARCHAR(200) NOT NULL,
                         price DECIMAL(10,2) NULL,
                         categoryID INT NULL,
                         quantity INT NOT NULL DEFAULT 0,
                         trangThai NVARCHAR(20) DEFAULT 'Còn hàng',
                         FOREIGN KEY (categoryID) REFERENCES Category(categoryID)
);

CREATE TABLE Role (
                      roleName VARCHAR(50) PRIMARY KEY
);

CREATE TABLE [User] (
                        id INT PRIMARY KEY,
                        username VARCHAR(100) UNIQUE,
                        password VARCHAR(255),
                        roleName VARCHAR(50),
                        FOREIGN KEY (id) REFERENCES Person(id),
                        FOREIGN KEY (roleName) REFERENCES Role(roleName)
);

CREATE TABLE OrderDetail (
                             orderDetailID INT PRIMARY KEY IDENTITY(1,1),
                             orderID INT,
                             productID INT,
                             petID INT,
                             quantity INT,
                             price DECIMAL(10,2),

                             FOREIGN KEY (orderID) REFERENCES [Order](orderID),
                             FOREIGN KEY (productID) REFERENCES Product(productID),
                             FOREIGN KEY (petID) REFERENCES Pet(petID),

                             CHECK (
                                 (productID IS NOT NULL AND petID IS NULL)
                                     OR
                                 (productID IS NULL AND petID IS NOT NULL)
                                 )
);

CREATE TABLE Person (
                        id INT PRIMARY KEY IDENTITY(1,1),
                        name VARCHAR(255),
                        phone VARCHAR(50),
                        address VARCHAR(255)
);

CREATE TABLE [Order] (
                         orderID INT PRIMARY KEY IDENTITY(1,1),
                         userID INT,
                         customerID INT,
                         totalPrice DECIMAL(10,2),
                         orderDate DATETIME DEFAULT GETDATE(),
                         FOREIGN KEY (userID) REFERENCES [User](id),
                         FOREIGN KEY (customerID) REFERENCES Customer(id)
);

CREATE TABLE Customer (
                          id INT PRIMARY KEY,
                          loyaltyPoints INT,
                          membershipLevel VARCHAR(50),
                          FOREIGN KEY (id) REFERENCES Person(id)
);

CREATE TABLE Category (
                          categoryID INT PRIMARY KEY,
                          categoryName NVARCHAR(100)
);

CREATE TABLE Bill (
                      billID INT PRIMARY KEY IDENTITY(1,1),
                      billMethod VARCHAR(50),
                      amount DECIMAL(10,2),
                      billTime DATETIME DEFAULT GETDATE(),
                      orderID INT,
                      FOREIGN KEY (orderID) REFERENCES [Order](orderID)
);



-- inser mẫu

INSERT INTO Person (name, phone, address)
VALUES
    (N'Nguyễn Văn A', '0909123456', N'123 Đường A, Quận 1'),
    (N'Lê Thị B', '0909123457', N'456 Đường B, Quận 2');

INSERT INTO Role (roleName)
VALUES
    ('admin'),
    ('employee');

INSERT INTO Customer (id, loyaltyPoints, membershipLevel)
VALUES
    (2, 150, 'Gold'); -- personID = 2

INSERT INTO Category (categoryID, categoryName)
VALUES
    (1, N'Thức ăn'),
    (2, N'Thức uống'),
    (3, N'Đồ dùng');

INSERT INTO Product (name, price, categoryID, quantity)
VALUES
    (N'Xương gặm cho chó', 50000, 1, 100),
    (N'Chuột đồ chơi', 30000, 2, 50);

INSERT INTO Pet (name, species, breed, age, price, gender)
VALUES
    (N'LuLu', N'Dog', N'Poodle', 1.5, 3000000, N'Cái'),
    (N'MiMi', N'Cat', N'Maine Coon', 2, 2500000, N'Đực');

