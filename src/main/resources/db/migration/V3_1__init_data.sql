insert into food_flow_user (user_name, password, active)
values ('owner', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into food_flow_user (user_name, password, active)
values ('customer', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into address (street, postal_code, city, country)
values ('Niepołomicka', '80-180', 'Gdańsk', 'Polska');

insert into owner (name, surname, email, phone, address_id, user_id)
values ('Mateusz', 'Mechula', 'mateuszmechua@gmail.com', '515273042', 1, 1);

insert into address (street, postal_code, city, country)
values ('Skarpiego', '11-700', 'Mrągowo', 'Polska');

insert into customer (name, surname, email, phone, address_id, user_id)
values ('Jan', 'Kowalski', 'jankowalski@gmail.com', '666054032', 2, 2);

insert into food_flow_role (role_id, role)
values (1, 'OWNER'),
       (2, 'CUSTOMER');

insert into food_flow_user_role (user_id, role_id)
values (1, 1);

insert into food_flow_user_role (user_id, role_id)
values (2, 2);

INSERT INTO address (street, postal_code, city, country)
VALUES ('Pierwsza 1', '12-345', 'Kraków', 'Polska'),
       ('Druga 2', '23-456', 'Warszawa', 'Polska'),
       ('Trzecia 3', '34-567', 'Gdańsk', 'Polska'),
       ('Czwarta 4', '45-678', 'Wrocław', 'Polska'),
       ('Piąta 5', '56-789', 'Poznań', 'Polska'),
       ('Szósta 6', '67-890', 'Łódź', 'Polska'),
       ('Siódma 7', '78-901', 'Szczecin', 'Polska'),
       ('Ósma 8', '89-012', 'Kraków', 'Polska'),
       ('Dziewiąta 9', '90-123', 'Warszawa', 'Polska'),
       ('Dziesiąta 10', '01-234', 'Gdańsk', 'Polska'),
       ('Jedenaście 11', '11-111', 'Kraków', 'Polska'),
       ('Dwanaście 12', '12-222', 'Warszawa', 'Polska'),
       ('Trzynaście 13', '13-333', 'Gdańsk', 'Polska'),
       ('Czternaście 14', '14-444', 'Wrocław', 'Polska'),
       ('Piętnaście 15', '15-555', 'Poznań', 'Polska'),
       ('Szesnaście 16', '16-666', 'Łódź', 'Polska'),
       ('Siedemnaście 17', '17-777', 'Szczecin', 'Polska'),
       ('Osiemnaście 18', '18-888', 'Kraków', 'Polska'),
       ('Dziewiętnaście 19', '19-999', 'Warszawa', 'Polska'),
       ('Dwadzieścia 20', '20-000', 'Gdańsk', 'Polska'),
       ('klonowa 6a', '11-400', 'Kętrzyn', 'Polska');

INSERT INTO food_flow_user (user_name, password, active)
VALUES ('user1', 'password1', true),
       ('user2', 'password2', true),
       ('user3', 'password3', true),
       ('user4', 'password4', true),
       ('user5', 'password5', true),
       ('user6', 'password6', true),
       ('user7', 'password7', true),
       ('user8', 'password8', true),
       ('user9', 'password9', true),
       ('user10', 'password10', true);

insert into owner (name, surname, email, phone, address_id, user_id)
values ('John', 'Doe', 'john.doe@example.com', '123456789', 3, 3),
       ('Jane', 'Smith', 'jane.smith@example.com', '987654321', 4, 4),
       ('Alice', 'Johnson', 'alice.johnson@example.com', '555555555', 5, 5),
       ('Bob', 'Williams', 'bob.williams@example.com', '111111111', 6, 6),
       ('Eve', 'Brown', 'eve.brown@example.com', '999999999', 7, 7),
       ('Charlie', 'Davis', 'charlie.davis@example.com', '777777777', 8, 8),
       ('Grace', 'Miller', 'grace.miller@example.com', '222222222', 9, 9),
       ('David', 'Martinez', 'david.martinez@example.com', '888888888', 10, 10),
       ('Olivia', 'Jones', 'olivia.jones@example.com', '444444444', 11, 11),
       ('William', 'Garcia', 'william.garcia@example.com', '666666666', 12, 12);

insert into food_flow_user_role (user_id, role_id)
values (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 1),
       (10, 1),
       (11, 1),
       (12, 1);

INSERT INTO restaurant (nip, name, description, open_time, close_time, phone, minimum_order_amount, delivery_price,
                        delivery_option, owner_id, address_id)
VALUES ('1234567890', 'Restauracja 1', 'Opis restauracji 1', '12:00:00', '22:00:00', '111222333', 10.00, 2.50, true, 2,
        13),
       ('9876543210', 'Restauracja 2', 'Opis restauracji 2', '11:00:00', '21:00:00', '444555666', 12.00, 3.00, true, 3,
        14),
       ('1112223333', 'Restauracja 3', 'Opis restauracji 3', '10:00:00', '20:00:00', '777888999', 15.00, 2.00, true, 4,
        15),
       ('4445556663', 'Restauracja 4', 'Opis restauracji 4', '11:30:00', '22:30:00', '222333444', 20.00, 2.50, true, 5,
        16),
       ('7778889990', 'Restauracja 5', 'Opis restauracji 5', '12:00:00', '23:00:00', '555666777', 10.00, 3.00, true, 6,
        17),
       ('2223334440', 'Restauracja 6', 'Opis restauracji 6', '11:00:00', '22:00:00', '666777888', 12.00, 2.50, true, 7,
        18),
       ('5556667770', 'Restauracja 7', 'Opis restauracji 7', '10:30:00', '21:30:00', '888999000', 25.00, 3.50, true, 8,
        19),
       ('6667778880', 'Restauracja 8', 'Opis restauracji 8', '12:30:00', '22:30:00', '333444555', 10.00, 3.50, true, 9,
        20),
       ('1112223330', 'Restauracja 9', 'Opis restauracji 9', '10:00:00', '20:00:00', '777888999', 15.00, 2.00, true, 10,
        21),
       ('4445556660', 'Restauracja 10', 'Opis restauracji 10', '11:30:00', '22:30:00', '222333444', 20.00, 2.50, true,
        11, 22);

INSERT INTO restaurant_address(restaurant_id, address_id)
VALUES (1, 23),
       (2, 23),
       (3, 23),
       (4, 23),
       (5, 23),
       (6, 23),
       (7, 23),
       (8, 23),
       (9, 23),
       (10, 23);

INSERT INTO menu (name, description, restaurant_id)
VALUES ('menu1', 'description menu1', 1),
       ('menu2', 'description menu2', 2),
       ('menu3', 'description menu3', 3),
       ('menu4', 'description menu4', 4),
       ('menu5', 'description menu5', 5),
       ('menu6', 'description menu6', 6),
       ('menu7', 'description menu7', 7),
       ('menu8', 'description menu8', 8),
       ('menu9', 'description menu9', 9),
       ('menu10', 'description menu10', 10);

INSERT INTO menu_category (name, description, menu_id)
VALUES ('menuCategory1', 'description1', 1),
       ('menuCategory2', 'description2', 2),
       ('menuCategory3', 'description3', 3),
       ('menuCategory4', 'description4', 4),
       ('menuCategory5', 'description5', 5),
       ('menuCategory6', 'description6', 6),
       ('menuCategory7', 'description7', 7),
       ('menuCategory8', 'description8', 8),
       ('menuCategory9', 'description9', 9),
       ('menuCategory10', 'description10', 10);

INSERT INTO category_item (name, description, price, image_url, menu_category_id)
VALUES ('categoryItem1', 'description1', 10, 'mcchicken.png', 1),
       ('categoryItem2', 'description2', 11, 'mcchicken.png', 1),
       ('categoryItem3', 'description3', 12, 'mcchicken.png', 2),
       ('categoryItem4', 'description4', 13, 'mcchicken.png', 2),
       ('categoryItem5', 'description5', 54, 'mcchicken.png', 3),
       ('categoryItem6', 'description6', 23, 'mcchicken.png', 3),
       ('categoryItem7', 'description7', 22, 'mcchicken.png', 4),
       ('categoryItem8', 'description8', 77, 'mcchicken.png', 4),
       ('categoryItem9', 'description9', 33, 'mcchicken.png', 5),
       ('categoryItem10', 'description10', 33, 'mcchicken.png', 5),
       ('categoryItem11', 'description11', 66, 'mcchicken.png', 6),
       ('categoryItem12', 'description12', 40, 'mcchicken.png', 6),
       ('categoryItem13', 'description13', 4, 'mcchicken.png', 7),
       ('categoryItem14', 'description14', 5, 'mcchicken.png', 7),
       ('categoryItem15', 'description15', 7, 'mcchicken.png', 8),
       ('categoryItem16', 'description16', 29, 'mcchicken.png', 8),
       ('categoryItem17', 'description17', 3, 'mcchicken.png', 9),
       ('categoryItem18', 'description18', 40, 'mcchicken.png', 9),
       ('categoryItem19', 'description19', 18, 'mcchicken.png', 10),
       ('categoryItem20', 'description20', 27, 'mcchicken.png', 10);
