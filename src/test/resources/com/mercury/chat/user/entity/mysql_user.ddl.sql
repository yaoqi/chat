create schema if not exists CHAT;
set schema CHAT;

drop table if exists CHAT.USER;

drop table if exists CHAT.MESSAGE;

drop table if exists CHAT.QUICK_REPLY;

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
/* Table: QUICK_REPLY                                                */
/*==============================================================*/
CREATE TABLE CHAT.QUICK_REPLY
(
   UUID         		BIGINT IDENTITY NOT NULL,
   SALEID            	BIGINT,
   MESSAGE      		CLOB,
   PRIMARY KEY (UUID)
);

