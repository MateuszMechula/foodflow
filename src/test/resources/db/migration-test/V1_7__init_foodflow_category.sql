CREATE TABLE CATEGORY
(
    CATEGORY_ID SERIAL      NOT NULL,
    NAME        VARCHAR(64) NOT NULL,
    PRIMARY KEY (CATEGORY_ID),
    UNIQUE (NAME)
);