 	Feature: 百度搜索
	    需求描述: 百度搜索http://www.baidu.com

	Scenario: 搜索testNG
	    Given 搜索词:testNG
	    And 浏览器:firefox
	    When 打开百度，输入搜索词搜索
	    And 打开

	Scenario: 搜索Cucumber
	    Given 搜索词:Cucumber
	    When 打开百度，输入搜索词搜索