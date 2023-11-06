ALTER TABLE owner
    ADD COLUMN user_id INT,
    ADD FOREIGN KEY (user_id) REFERENCES food_flow_user (user_id);

ALTER TABLE customer
    ADD COLUMN user_id INT,
    ADD FOREIGN KEY (user_id) REFERENCES food_flow_user (user_id);

ALTER TABLE owner
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE customer
    ALTER COLUMN user_id SET NOT NULL;
