insert into CHAT.USER(UUID,USER_ID,PASSWORD) values('google@gmail.com','welcome1');
insert into CHAT.USER(UUID,USER_ID,PASSWORD) values('baidu@baidu.com','welcome1');
insert into CHAT.MESSAGE(ID,FROM,TO,MESSAGE) values('baidu@baidu.com','google@gmail.com', 'hello, google');
insert into CHAT.MESSAGE(ID,FROM,TO,MESSAGE) values('google@gmail.com','baidu@baidu.com', 'hello, baidu');