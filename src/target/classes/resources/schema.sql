CREATE DATABASE QuanLyCuaHangThuCung;

USE [QuanLyCuaHangThuCung]
GO
/****** Object:  Table [dbo].[Bill]    Script Date: 03/05/2025 2:15:50 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bill](
    [billID] [int] IDENTITY(1,1) NOT NULL,
    [billMethod] [varchar](50) NULL,
    [amount] [decimal](10, 2) NULL,
    [billTime] [datetime] NULL,
    [orderID] [int] NULL,
    CONSTRAINT [PK_Bill] PRIMARY KEY CLUSTERED
(
[billID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Category]    Script Date: 03/05/2025 2:15:50 SA ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Category](
    [categoryID] [int] NOT NULL,
    [categoryName] [nvarchar](100) NULL,
    PRIMARY KEY CLUSTERED
(
[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Customer]    Script Date: 03/05/2025 2:15:50 SA ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Customer](
    [id] [int] NOT NULL,
    [loyaltyPoints] [int] NULL,
    [membershipLevel] [varchar](50) NULL,
    CONSTRAINT [PK_Customer] PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Order]    Script Date: 03/05/2025 2:15:50 SA ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Order](
    [orderID] [int] IDENTITY(1,1) NOT NULL,
    [userID] [int] NULL,
    [customerID] [int] NULL,
    [totalPrice] [decimal](10, 2) NULL,
    [orderDate] [datetime] NULL,
    PRIMARY KEY CLUSTERED
(
[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[OrderDetail]    Script Date: 03/05/2025 2:15:50 SA ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[OrderDetail](
    [orderDetailID] [int] IDENTITY(1,1) NOT NULL,
    [orderID] [int] NULL,
    [productID] [int] NULL,
    [quantity] [int] NULL,
    [price] [decimal](10, 2) NULL,
    [petID] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[orderDetailID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Person]    Script Date: 03/05/2025 2:15:50 SA ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Person](
    [id] [int] IDENTITY(1,1) NOT NULL,
    [name] [varchar](255) NULL,
    [phone] [varchar](50) NULL,
    [address] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Pet]    Script Date: 03/05/2025 2:15:50 SA ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Pet](
    [petID] [int] IDENTITY(1,1) NOT NULL,
    [name] [varchar](255) NULL,
    [species] [varchar](100) NULL,
    [breed] [varchar](100) NULL,
    [age] [float] NULL,
    [price] [decimal](10, 2) NULL,
    [gender] [nvarchar](10) NULL,
    [trangThai] [nvarchar](20) NULL,
    PRIMARY KEY CLUSTERED
(
[petID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Product]    Script Date: 03/05/2025 2:15:50 SA ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Product](
    [productID] [int] IDENTITY(1,1) NOT NULL,
    [name] [nvarchar](200) NOT NULL,
    [price] [decimal](10, 2) NULL,
    [categoryID] [int] NULL,
    [quantity] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[productID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Role]    Script Date: 03/05/2025 2:15:50 SA ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Role](
    [roleName] [varchar](50) NOT NULL,
    PRIMARY KEY CLUSTERED
(
[roleName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[User]    Script Date: 03/05/2025 2:15:50 SA ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[User](
    [id] [int] NOT NULL,
    [username] [varchar](100) NULL,
    [password] [varchar](255) NULL,
    [roleName] [varchar](50) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    UNIQUE NONCLUSTERED
(
[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[Bill] ADD  DEFAULT (getdate()) FOR [billTime]
    GO
ALTER TABLE [dbo].[Order] ADD  DEFAULT (getdate()) FOR [orderDate]
    GO
ALTER TABLE [dbo].[Pet] ADD  DEFAULT ('available') FOR [trangThai]
    GO
ALTER TABLE [dbo].[Product] ADD  DEFAULT ((0)) FOR [quantity]
    GO
ALTER TABLE [dbo].[Bill]  WITH CHECK ADD  CONSTRAINT [FK_Bill_Order] FOREIGN KEY([orderID])
    REFERENCES [dbo].[Order] ([orderID])
    GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FK_Bill_Order]
    GO
ALTER TABLE [dbo].[Customer]  WITH CHECK ADD  CONSTRAINT [FK_Customer_Person] FOREIGN KEY([id])
    REFERENCES [dbo].[Person] ([id])
    GO
ALTER TABLE [dbo].[Customer] CHECK CONSTRAINT [FK_Customer_Person]
    GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD FOREIGN KEY([customerID])
    REFERENCES [dbo].[Customer] ([id])
    GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD FOREIGN KEY([userID])
    REFERENCES [dbo].[User] ([id])
    GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD FOREIGN KEY([orderID])
    REFERENCES [dbo].[Order] ([orderID])
    GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD FOREIGN KEY([productID])
    REFERENCES [dbo].[Product] ([productID])
    GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetail_Pet] FOREIGN KEY([petID])
    REFERENCES [dbo].[Pet] ([petID])
    GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_OrderDetail_Pet]
    GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK_Product_Category] FOREIGN KEY([categoryID])
    REFERENCES [dbo].[Category] ([categoryID])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK_Product_Category]
    GO
ALTER TABLE [dbo].[User]  WITH CHECK ADD FOREIGN KEY([id])
    REFERENCES [dbo].[Person] ([id])
    GO
ALTER TABLE [dbo].[User]  WITH CHECK ADD FOREIGN KEY([roleName])
    REFERENCES [dbo].[Role] ([roleName])
    GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [CK_OnlyOneItem] CHECK  (([productID] IS NOT NULL AND [petID] IS NULL OR [productID] IS NULL AND [petID] IS NOT NULL))
    GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [CK_OnlyOneItem]
    GO

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

