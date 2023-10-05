insert into food_flow_user (user_name, password, active)
values ('test_owner', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into food_flow_user (user_name, password, active)
values ('test_customer', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into address (street, postal_code, city, country)
values ('Testing', '99-180', 'Test', 'Polska');

insert into address (street, postal_code, city, country)
values ('Testing', '99-099', 'Test', 'Polska');

insert into owner (name, surname, email, phone, address_id, user_id)
values ('Owner', 'Owner', 'owner@gmail.com', '504033032', 1, 1);

insert into customer (name, surname, email, phone, address_id, user_id)
values ('Customer', 'Customer', 'customer@gmail.com', '666054032', 2, 2);

insert into food_flow_user_role (user_id, role_id)
values (1, 1);

insert into food_flow_user_role (user_id, role_id)
values (2, 2);