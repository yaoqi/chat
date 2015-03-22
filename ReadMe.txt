TODO:

1.spring bean化所有会调用后台的类
	a)server 服务化,bean化
	b)SessionManager bean化
	c)MessageStoreCallable bean化

2.实现连接的监听

3.user的查找
  a)用cache去实现加载
  b)如何存储shipId
  c)如何查找同一个shop下面的所有sales.

4.test case

5.属性配置化properties.

6.后台各种校验
  a)client对入口参数的校验
  b)server对入口参数的校验
  c)校验用户各种操作是否授权
  d)校验用户各种操作是否有效
  	1)重复登录
  	2)未登录就进行各种操作
  	3)聊天对象根本就不存在
  	
7.error code/status code具体化  	