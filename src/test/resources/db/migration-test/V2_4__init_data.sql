insert into address (street, postal_code, city, country)
values ('Malinowskiego', '99-099', 'Chrzanów', 'Polska');

insert into address (street, postal_code, city, country)
values ('Klonowa', '11-400', 'Kętrzyn', 'Polska');

insert into restaurant (nip, name, description, open_time, close_time, phone, minimum_order_amount, delivery_price,
                        delivery_option, owner_id, address_id)
VALUES ('7654032040', 'Max Burgers', 'best restaurant', '09:00:00+00:00', '17:00:00+00:00', '504050440', '20', '10',
        'true', 1, 3);

insert into menu (name, description, restaurant_id)
values ('Best menu', 'best menu', 1);

insert into menu_category(name, description, menu_id)
VALUES ('Burgerki', 'Najlepsze burgerki', 1);

insert into category_item(name, description, price, image_url, menu_category_id)
VALUES ('bigmac', 'bigmac', '10', 'image_url', 1);

insert into category_item(name, description, price, image_url, menu_category_id)
VALUES ('mcchicken', 'mcchicken', '12', 'image_url', 1);

insert into order_record (order_number, order_date_time, order_status, order_notes, total_amount,
                          contact_phone, delivery_address, delivery_type, customer_id, restaurant_id)
VALUES ('order_number_value', '2023-09-19 23:54:46.561387+02', 'IN_PROGRESS', 'Order notes here', 50.00,
        '123456789', '123 Main Street, City, 80-443', 'Delivery', 1, 1);

insert into order_record (order_number, order_date_time, order_status, order_notes, total_amount,
                          contact_phone, delivery_address, delivery_type, customer_id, restaurant_id)
VALUES ('order_number_value2', '2023-09-19 23:54:46.561387+02', 'IN_PROGRESS', 'Order notes here', 50.00,
        '123456789', '123 Main Street, City, 80-443', 'Delivery', 1, 1);

insert into order_item (unit_price, quantity, order_record_id, category_item_id)
VALUES (10, 5, 1, 1);

insert into order_item (unit_price, quantity, order_record_id, category_item_id)
VALUES (10, 5, 1, 1);

insert into restaurant_address(restaurant_id, address_id)
VALUES (1, 4);
