/*INSERT INTO category (category_id, name)
VALUES
    (1, 'Electronics'),
    (2, 'Clothing');*/

-- Insert statements for member table
INSERT INTO marketbridge.member (membership, email, password, name, phone_no,  is_alert, is_agree, created_at, updated_at, deleted_at)
VALUES ('Silver', 'member1@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'member One', '010-1234-5678',  1, 1, '2024-01-16 12:30:00', '2024-01-17 09:00:00', NULL),
('Gold', 'member2@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'member Two', '010-9876-5432',  1, 1, '2024-01-18 10:45:00', '2024-01-19 08:30:00', NULL);

-- Insert statements for address table
INSERT INTO marketbridge.address (member_id, alias, name, phone_no, city, street, zipcode, is_default, created_at, updated_at, deleted_at)
VALUES
    (1, 'Home', 'John Doe', '1234567890', 'Seoul', '123 Main St', '12345', 1, NOW(), NOW(), NULL),
    (2, 'Office', 'Jane Doe', '9876543210', 'Busan', '456 Oak St', '54321', 1, NOW(), NOW(), NULL);

-- Insert statements for category table
INSERT INTO marketbridge.category (parent_id, level, name, created_at, updated_at, deleted_at)
VALUES
    (NULL, 0, 'Electronics', NOW(), NOW(), NULL),
    (NULL, 0, 'Clothing', NOW(), NOW(), NULL);

-- Insert statements for product_image table
INSERT INTO marketbridge.product_image (product_id, image_id, created_at, updated_at, deleted_at)
VALUES
    (1, 1, NOW(), NOW(), NULL),
    (2, 2, NOW(), NOW(), NULL);

-- Insert statements for image table
INSERT INTO marketbridge.image (type, url, created_at, updated_at, deleted_at)
VALUES
    ('Thumbnail', 'electronics_thumb.jpg', NOW(), NOW(), NULL),
    ('Thumbnail', 'clothing_thumb.jpg', NOW(), NOW(), NULL);

-- Insert statements for option_category table
INSERT INTO marketbridge.option_category (name, created_at, updated_at, deleted_at)
VALUES
    ('Size', NOW(), NOW(), NULL),
    ('Color', NOW(), NOW(), NULL);

-- Insert statements for options table
INSERT INTO marketbridge.options (option_category_id, name, created_at, updated_at, deleted_at)
VALUES
    (1, 'Medium', NOW(), NOW(), NULL),
    (2, 'Blue', NOW(), NOW(), NULL);

-- Insert statements for tag table
INSERT INTO marketbridge.tag (name, created_at, updated_at, deleted_at)
VALUES
    ('Electronics', NOW(), NOW(), NULL),
    ('Fashion', NOW(), NOW(), NULL);

-- Insert statements for prod_tag table
INSERT INTO marketbridge.prod_tag (tag_id, product_id, created_at, updated_at, deleted_at)
VALUES
    (1, 1, NOW(), NOW(), NULL),
    (2, 2, NOW(), NOW(), NULL);

-- Insert statements for status_code table
INSERT INTO marketbridge.status_code (type, code, name, created_at, updated_at, deleted_at)
VALUES
    ('Order', '1001', 'Pending', NOW(), NOW(), NULL),
    ('Order', '1002', 'Shipped', NOW(), NOW(), NULL);

-- Insert statements for orders table
INSERT INTO marketbridge.orders (member_id, address_id, order_name, order_no, total_discount,  total_price, real_price, created_at, updated_at, deleted_at)
VALUES (1, 101, 'First Order', 'ORD001', 275, 280, 5, '2024-01-16 12:30:00', '2024-01-17 09:00:00', NULL),
(2, 102, 'Second Order', 'ORD002',  15,  200, 185, '2024-01-18 10:45:00', '2024-01-19 08:30:00', NULL);

-- Insert statements for order_detail table
INSERT INTO marketbridge.order_detail (order_id, product_id, coupon_id, reward_type, quantity, price, status_code, delivered_date,  reason, cancelled_at, created_at, updated_at, deleted_at)
VALUES
    (1, 1, 1, 'Cashback', 2, 200, '1001', NULL,  NULL, NULL, NOW(), NOW(), NULL),
    (2, 2, 2, 'Discount', 1, 150, '1002', NOW(),  'Out of stock', NULL, NOW(), NOW(), NULL);

-- Insert statements for payment table
/*INSERT INTO payment (order_id, receipt_id, order_name, order_no, transaction_key, payment_type, payment_method, total_amount, balance_amount, payment_key, settlement_status, payment_status, refund_status, customer_name, bank_code, phone_no, cancel_amount, cancel_toss_reason, card_issuer_code, card_no, installment_plan_months, approve_no, account_no, v_account_no, v_due_date, v_expired, deleted_at, canceled_at, approved_at, requested_at, updated_at, cancelled_at, created_at)
VALUES
    (1, 'REC123', 'Order 123', 'ORD123', 'TRANSKEY123', 'Credit Card', 'VISA', 500, 100, 'PAYKEY123', 'Settled', 'Paid', NULL, 'John Doe', 'BANK123', '9876543210', NULL, NULL, 'ISSUER123', 'CARDNO123', 3, 'APPROVE123', 'ACC123', 'VACC123', '2024-02-01', 0, NULL, NULL, NULL, NOW(), NULL, NOW()),
    (2, 'REC456', 'Order 456', 'ORD456', 'TRANSKEY456', 'Debit Card', 'MasterCard', 300, 50, 'PAYKEY456', 'Settled', 'Paid', NULL, 'Jane Doe', 'BANK456', '1234567890', NULL, NULL, 'ISSUER456', 'CARDNO456', 2, 'APPROVE456', 'ACC456', 'VACC456', '2024-03-01', 0, NULL, NULL, NULL, NOW(), NULL, NOW());*/

-- Insert statements for review table
INSERT INTO marketbridge.review (member_id, product_id, rating, content, created_at, updated_at, deleted_at)
VALUES
    (1, 1, 5, 'Great product!', NOW(), NOW(), NULL),
    (2, 2, 4, 'Good quality!', NOW(), NOW(), NULL);

-- Insert statements for review_image table
INSERT INTO marketbridge.review_image (review_id, image_id, created_at, updated_at, deleted_at)
VALUES
    (1, 1, NOW(), NOW(), NULL),
    (2, 2, NOW(), NOW(), NULL);

-- Insert statements for review_image table
INSERT INTO marketbridge.review_likes (review_id, member_id, likes, created_at, updated_at, deleted_at)
VALUES
    (1, 1, 1, NOW(), NOW(), NULL),
    (2, 2, 2, NOW(), NOW(), NULL);


-- Insert statements for review_survey table
INSERT INTO marketbridge.review_survey (review_id, review_survey_category_id, survey_content_id, created_at, updated_at, deleted_at)
VALUES
    (1, 1, 1, NOW(), NOW(), NULL),
    (2, 2, 2, NOW(), NOW(), NULL);

-- Insert statements for review_survey_category table
INSERT INTO marketbridge.review_survey_category (product_id, name, created_at, updated_at, deleted_at)
VALUES
    (1, 'Product Satisfaction', NOW(), NOW(), NULL),
    (2, 'Delivery Time', NOW(), NOW(), NULL);

-- Insert statements for survey_content table
INSERT INTO marketbridge.survey_content (review_survey_category_id, seq_no, content, created_at, updated_at, deleted_at)
VALUES
    (1, 1, 'How satisfied are you with the product?', NOW(), NOW(), NULL),
    (2, 1, 'How was the delivery time?', NOW(), NOW(), NULL);



-- Insert statements for coupon table
INSERT INTO marketbridge.coupon (product_id, name, price, count, minimum_price, start_date, end_date, created_at, updated_at, deleted_at)
VALUES
    (1, 'Discount Coupon', '50', 100, 200, '2024-02-01', '2024-03-01', NOW(), NOW(), NULL),
    (2, 'Free Shipping Coupon', '30', 50, 100, '2024-03-01', '2024-04-01', NOW(), NOW(), NULL);


-- Insert statements for member_coupon table
INSERT INTO marketbridge.member_coupon (member_id, coupon_id, is_used, used_date, end_date, created_at, updated_at, deleted_at)
VALUES
    (1, 1, 0, NULL, '2024-03-01', NOW(), NOW(), NULL),
    (2, 2, 0, NULL, '2024-04-01', NOW(), NOW(), NULL);

-- Insert statements for cart table
INSERT INTO marketbridge.cart (member_id, product_id, is_subs, quantity)
VALUES
    (1, 1, 0, 2),
    (2, 2, 1, 1);


-- Insert statements for prod_option table
INSERT INTO marketbridge.prod_option (product_id, option_id, created_at, updated_at, deleted_at)
VALUES
    (1, 1, NOW(), NOW(), NULL),
    (2, 2, NOW(), NOW(), NULL);

INSERT INTO marketbridge.product (category_id, is_own, name, price, is_subs, stock, thumb_img, discount_rate, product_no, created_at, updated_at, deleted_at)
VALUES (1, 1, 'Product One', 5000, 0, 100, 'product1.jpg', 10, 'PN0001', NOW(), NOW(), NULL),
       (2, 0, 'Product Two', 8000, 1, 50, 'product2.jpg', 15, 'PN0002', NOW(), NOW(), NULL);