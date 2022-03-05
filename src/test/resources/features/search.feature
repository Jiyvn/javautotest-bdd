	Feature: 百度搜索
		需求描述: 百度搜索http://www.baidu.com //description for every scenario in allure report
	Background:

	@severity=minor
	@firefox @chrome @edge
	Scenario: 搜索testNG
		Given 搜索词:testNG
		And 浏览器:chrome
		When 打开百度，输入搜索词搜索
		And 打开www
		And 点击xxx

	@severity=critical
	@firefox @chrome @edge @safari
	Scenario: 搜索Cucumber
		Given 搜索词:Cucumber
		When 打开百度，输入搜索词搜索


