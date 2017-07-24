-- begin REPORTGENERATION_PRODUCT
create table REPORTGENERATION_PRODUCT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    PRICE decimal(19, 2) not null,
    --
    primary key (ID)
)^
-- end REPORTGENERATION_PRODUCT
-- begin REPORTGENERATION_ORDER_ITEM
create table REPORTGENERATION_ORDER_ITEM (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRODUCT_ID varchar(36) not null,
    QUANTITY integer not null,
    ORDER_ID varchar(36) not null,
    SUB_TOTAL decimal(19, 2),
    --
    primary key (ID)
)^
-- end REPORTGENERATION_ORDER_ITEM
-- begin REPORTGENERATION_CUSTOMER
create table REPORTGENERATION_CUSTOMER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    BILLING_ADDRESS longvarchar not null,
    LOGO_ID varchar(36),
    --
    primary key (ID)
)^
-- end REPORTGENERATION_CUSTOMER
-- begin REPORTGENERATION_ORDER
create table REPORTGENERATION_ORDER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_ varchar(30) not null,
    DATE_ date not null,
    CUSTOMER_ID varchar(36) not null,
    TOTAL_PRICE decimal(19, 2),
    --
    primary key (ID)
)^
-- end REPORTGENERATION_ORDER
