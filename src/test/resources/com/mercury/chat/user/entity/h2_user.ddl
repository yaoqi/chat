create schema if not exists CHAT;
set schema CHAT;

drop table if exists CHAT.USER;

drop table if exists CHAT.MESSAGE;

drop table if exists CHAT.QUICK_REPLY;

drop table if exists CHAT.PRODUCT_SUMMARY;

drop table if exists CHAT.ORDER_SUMMARY;

drop table if exists CHAT.ORDER_ITEM;

/*==============================================================*/
/* Table: USER                                                */
/*==============================================================*/
CREATE TABLE CHAT.USER
(
   UUID                 BIGINT IDENTITY NOT NULL,
   USER_ID              VARCHAR(64),
   PASSWORD             VARCHAR(64),
   SALES				BOOLEAN DEFAULT false,
   SHOP_ID				BIGINT,
   PRIMARY KEY (UUID)
);

/*==============================================================*/
/* Table: MESSAGE                                                */
/*==============================================================*/
CREATE TABLE CHAT.MESSAGE
(
   ID         			BIGINT IDENTITY NOT NULL,
   CHAT_FROM            VARCHAR(64),
   CHAT_TO              VARCHAR(64),
   SHOP_ID				BIGINT,
   CREATE_TS            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   MESSAGE      		CLOB,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: QUICK_REPLY                                           */
/*==============================================================*/
CREATE TABLE CHAT.QUICK_REPLY
(
   UUID         		BIGINT IDENTITY NOT NULL,
   SALEID            	BIGINT,
   MESSAGE      		CLOB,
   PRIMARY KEY (UUID)
);

/*==============================================================*/
/* Table: PRODUCT_SUMMARY	                                    */
/*==============================================================*/
CREATE TABLE CHAT.PRODUCT_SUMMARY
(
   PRODUCT_ID         		BIGINT IDENTITY NOT NULL,
   PRODUCT_CODE            	VARCHAR(12),
   SUMMARY      			VARCHAR(64),
   PRIMARY KEY (product_id)
);

/*==============================================================*/
/* Table: ORDER_SUMMARY	                                        */
/*==============================================================*/
CREATE TABLE CHAT.ORDER_SUMMARY
(
   ORDER_ID         		BIGINT IDENTITY NOT NULL,
   SUMMARY      			VARCHAR(64),
   PRIMARY KEY (ORDER_ID)
);

/*==============================================================*/
/* Table: ORDER_ITEM	                                        */
/*==============================================================*/
CREATE TABLE CHAT.ORDER_ITEM
(
   ITEM_ID					BIGINT IDENTITY NOT NULL,
   ORDER_ID         		BIGINT NOT NULL,
   PRODUCT_ID      			BIGINT NOT NULL,
   PRIMARY KEY (ITEM_ID)
);

alter table CHAT.ORDER_ITEM add constraint FK_ORDER_ID foreign key (ORDER_ID)
      references CHAT.ORDER_SUMMARY (ORDER_ID) on delete restrict on update restrict;

alter table CHAT.ORDER_ITEM add constraint FK_PRODUCT_ID foreign key (PRODUCT_ID)
      references CHAT.PRODUCT_SUMMARY (PRODUCT_ID) on delete restrict on update restrict;
