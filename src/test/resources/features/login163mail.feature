@163
Feature: 登陆163邮箱
	    需求描述: 登陆163邮箱https://mail.163.com/

Scenario: 使用错误用户名和密码登陆163邮箱
	Given 用户：163youxiangtest@163.com，密码：abcowglxe
	And 打开:https://mail.163.com
	When 输入用户名和密码
	And 点击登陆按钮
	Then 登陆失败，出现提示"帐号或密码错误"
