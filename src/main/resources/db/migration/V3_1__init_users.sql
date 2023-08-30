ALTER TABLE owner
    ADD COLUMN user_id INT,
    ADD FOREIGN KEY (user_id) REFERENCES food_flow_user (user_id);

ALTER TABLE customer
    ADD COLUMN user_id INT,
    ADD FOREIGN KEY (user_id) REFERENCES food_flow_user (user_id);

insert into food_flow_user (user_id, user_name, password, active)
values (1, 'owner', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into food_flow_user (user_id, user_name, password, active)
values (2, 'cust', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

UPDATE owner
SET user_id = 1
WHERE email = 'mateuszmechua@gmail.com';

UPDATE customer
SET user_id = 2
WHERE email = 'jankowalski@gmail.com';

insert into food_flow_role (role_id, role)
values (1, 'OWNER'),
       (2, 'CUSTOMER');

insert into food_flow_user_role (user_id, role_id)
values (1, 1);

insert into food_flow_user_role (user_id, role_id)
values (2, 2);

ALTER TABLE owner
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE customer
    ALTER COLUMN user_id SET NOT NULL;
