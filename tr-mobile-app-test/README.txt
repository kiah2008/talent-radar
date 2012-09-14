
Hi! Welcome to the tr-mobile-app-test project!
We hope you can test as many test cases as possible to help us develop this great application.

Please take special attention to your JMock test cases, as you may have to add the following line 
at the end of each of them for the expectations to be checked:

	context.assertIsSatisfied();
	
Where context is an instance of some kind of Mockery. Otherwise, you'll see that your tests are
false positives (appear to be green as your mocks' expectations are not asserted anywhere).
