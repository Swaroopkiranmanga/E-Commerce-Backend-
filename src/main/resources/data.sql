-- Insert data into Category table
INSERT INTO Category (id, name) VALUES
(1, 'Electronics'),
(2, 'Clothing'),
(3, 'Books');

-- Insert data into Subcategory table
INSERT INTO Subcategory (id, name, category_id) VALUES
(1, 'Smartphones', 1),
(2, 'Laptops', 1),
(3, 'Men\'s Wear', 2),
(4, 'Women\'s Wear', 2),
(5, 'Fiction', 3),
(6, 'Non-Fiction', 3);

-- Insert data into Product table
INSERT INTO Product (id, name, price, description, subcategory_id, brand, image, rating, quantity) VALUES
(1, 'iPhone 14', 999.99, 'The latest Apple smartphone with cutting-edge technology.', 1, 'Apple', 'iphone14.jpg', 4.5, 100),
(2, 'Samsung Galaxy S22', 799.99, 'High-end Android smartphone with advanced features.', 1, 'Samsung', 'galaxy_s22.jpg', 4.3, 120),
(3, 'MacBook Pro', 1999.99, 'Apple\'s premium laptop for professionals.', 2, 'Apple', 'macbook_pro.jpg', 4.7, 50),
(4, 'Dell XPS 13', 1299.99, 'Compact and powerful Windows laptop.', 2, 'Dell', 'dell_xps13.jpg', 4.4, 75),
(5, 'Men\'s T-Shirt', 19.99, 'Comfortable cotton T-shirt for men.', 3, 'H&M', 'mens_tshirt.jpg', 4.2, 200),
(6, 'Women\'s Dress', 49.99, 'Stylish summer dress for women.', 4, 'Zara', 'womens_dress.jpg', 4.5, 150),
(7, 'The Great Gatsby', 10.99, 'Classic fiction novel by F. Scott Fitzgerald.', 5, 'Penguin', 'great_gatsby.jpg', 4.8, 300),
(8, 'Atomic Habits', 15.99, 'Best-selling book on building good habits.', 6, 'James Clear', 'atomic_habits.jpg', 4.9, 250);
