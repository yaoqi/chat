insert into CHAT.USER(USER_ID,PASSWORD,SALES,SHOP_ID) values('google@gmail.com','welcome1',true, 1);
insert into CHAT.USER(USER_ID,PASSWORD) values('baidu@baidu.com','welcome1');

insert into CHAT.MESSAGE(CHAT_FROM,CHAT_TO,SHOP_ID,MESSAGE) values('baidu@baidu.com','google@gmail.com', 1, 'hello, google');
insert into CHAT.MESSAGE(CHAT_FROM,CHAT_TO,SHOP_ID,MESSAGE) values('google@gmail.com','baidu@baidu.com', 1, 'hello, baidu');
insert into CHAT.MESSAGE(CHAT_FROM,CHAT_TO,SHOP_ID,MESSAGE) values('google@gmail.com','baidu@baidu.com', 1, 'hello, baidu');
insert into CHAT.MESSAGE(CHAT_FROM,CHAT_TO,SHOP_ID,MESSAGE) values('google@gmail.com','baidu@baidu.com', 1, 'hello, baidu');
insert into CHAT.MESSAGE(CHAT_FROM,CHAT_TO,SHOP_ID,MESSAGE) values('google@gmail.com','baidu@baidu.com', 1, 'hello, baidu');
insert into CHAT.MESSAGE(CHAT_FROM,CHAT_TO,SHOP_ID,MESSAGE) values('google@gmail.com','baidu@baidu.com', 1, 'hello, baidu');
insert into CHAT.MESSAGE(CHAT_FROM,CHAT_TO,SHOP_ID,MESSAGE) values('google@gmail.com','baidu@baidu.com', 1, 'hello, baidu');
insert into CHAT.MESSAGE(CHAT_FROM,CHAT_TO,SHOP_ID,MESSAGE) values('google@gmail.com','baidu@baidu.com', 1, 'hello, baidu');

insert into CHAT.QUICK_REPLY(SALEID, MESSAGE) values (1, 'ok');
insert into CHAT.QUICK_REPLY(SALEID, MESSAGE) values (1, 'welcome to google');

insert into CHAT.PRODUCT_SUMMARY(PRODUCT_CODE, SUMMARY) values (1, 'pen');
insert into CHAT.PRODUCT_SUMMARY(PRODUCT_CODE, SUMMARY) values (2, 'book');

insert into CHAT.ORDER_SUMMARY(SUMMARY) values ('XXXX');

insert into CHAT.ORDER_ITEM(ORDER_ID,PRODUCT_ID) values (1,1);
insert into CHAT.ORDER_ITEM(ORDER_ID,PRODUCT_ID) values (1,2);