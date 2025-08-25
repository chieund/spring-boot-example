-- Create database (run this manually in PostgreSQL)
-- CREATE DATABASE orderdb;

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    order_id INT,
    user_id INT,
    total NUMERIC(10,2),
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_orders_order_id ON orders(order_id);
CREATE INDEX IF NOT EXISTS idx_orders_user_id ON orders(user_id);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders(status);
CREATE INDEX IF NOT EXISTS idx_orders_created_at ON orders(created_at);

-- Insert sample data
INSERT INTO orders (order_id, user_id, total, status) VALUES
(1001, 1, 150.50, 'PENDING'),
(1002, 1, 75.25, 'COMPLETED'),
(1003, 2, 200.00, 'PENDING'),
(1004, 2, 99.99, 'CANCELLED'),
(1005, 3, 300.75, 'COMPLETED')
ON CONFLICT DO NOTHING;