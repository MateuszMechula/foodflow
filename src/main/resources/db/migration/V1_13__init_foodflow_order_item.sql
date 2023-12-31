CREATE TABLE ORDER_ITEM
(
    ORDER_ITEM_ID    SERIAL        NOT NULL,
    UNIT_PRICE       NUMERIC(7, 2) NOT NULL,
    QUANTITY         INT           NOT NULL,
    ORDER_RECORD_ID  BIGINT        NOT NULL,
    CATEGORY_ITEM_ID BIGINT        NOT NULL,
    PRIMARY KEY (ORDER_ITEM_ID),
    CONSTRAINT FK_ORDER_ITEM_ORDER_RECORD
        FOREIGN KEY (ORDER_RECORD_ID)
            REFERENCES ORDER_RECORD (ORDER_RECORD_ID),
    CONSTRAINT FK_ORDER_ITEM_CATEGORY_ITEM
        FOREIGN KEY (CATEGORY_ITEM_ID)
            REFERENCES CATEGORY_ITEM (CATEGORY_ITEM_ID)
);