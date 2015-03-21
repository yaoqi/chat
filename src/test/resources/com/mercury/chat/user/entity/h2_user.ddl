create schema if not exists CHAT;
set schema CHAT;

drop table if exists CHAT.USER;

drop table if exists CHAT.MESSAGE;

/*==============================================================*/
/* Table: USER                                                */
/*==============================================================*/
CREATE TABLE CHAT.USER
(
   UUID                 BIGINT IDENTITY NOT NULL,
   USER_ID              VARCHAR(64),
   PASSWORD             VARCHAR(64),
   SALES				BOOLEAN,
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

