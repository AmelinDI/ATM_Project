DROP TABLE IF EXISTS PHONE_ACCOUNTS;
DROP TABLE IF EXISTS CREDIT_CARDS;

CREATE TABLE PHONE_ACCOUNTS (
  ID BIGINT IDENTITY,
  PHONE_NUMBER BIGINT,
  BALANCE DECIMAL(19, 2)
);

INSERT INTO PHONE_ACCOUNTS (PHONE_NUMBER, BALANCE) VALUES
  (79009009010, 10),
  (79009009020, 11),
  (79009009030, 12);

CREATE TABLE CREDIT_CARDS(
    ID IDENTITY,
    CARD_NUMBER VARCHAR,
    EXP_DATE DATE,
    CARDPIN INTEGER NOT NULL,
    BALANCE DECIMAL(19, 2)
);

INSERT INTO CREDIT_CARDS (CARD_NUMBER, EXP_DATE, CARDPIN, BALANCE) VALUES
    ('1234567812345601', '2022-01-01', 1111, 100),
    ('1234567812345602', '2021-06-01', 2222, 200),
    ('1234567812345603', '2021-10-01', 3333, 300);