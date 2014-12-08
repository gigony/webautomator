package edu.unl.webautomator.core;

import org.junit.Test;
import org.w3c.dom.Document;

/**
 * Created by gigony on 12/4/14.
 */

public class UseCase1 {


/*
    @Test
    public void configureWebAutomator(){

        WebAutomator automator = new WebAutmator(BrowserType.CHROME,"http://www.google.com");
//        WebAutomator automator = new WebAutmator(BrowserType.FIREFOX,"http://www.google.com");
//        WebAutomator automator = new WebAutmator(BrowserType.IEXPLORER,"http://www.google.com");
//        WebAutomator automator = new WebAutmator(BrowserType.PHANTOMJS,"http://www.google.com");
//        WebAutomator automator = new WebAutmator(BrowserType.REMOTE,"http://www.google.com");


        // need to split available eventType and actual commands : WebAutomator is able to execute commands in test cases
        EventTypeConfigurationBuilder eventTypeBuilder = new EventTypeConfigurationBuilder();
        //eventTypeBuilder.setDefault();
        eventTypeBuilder.click("a");
        eventTypeBuilder.click("button");
        eventTypeBuilder.click("input[type=\"submit\"]");
        eventTypeBuilder.click("input[type=\"button\"]");
        eventTypeBuilder.type("input[type=\"text\"]");
        eventTypeBuilder.dontType("input[type=\"password\"]");
        eventTypeBuilder.select("select");
        eventTypeBuilder.dontSelect("select.nothing");
        automator.setEventTypes(eventTypeBuilder.build());



        EventInputSpecificationBuilder inputSpecificationBuilder = new EventInputSpecificationBuilder();
        inputSpecificationBuilder.type("#user").setValue("anonymous");
        inputSpecificationBuilder.type("#password").setValue("1234");
        inputSpecificationBuilder.select("select").setRandomValue();


        EventInputSpecification inputSpecification = inputSpecificationBuilder.build();
        EventInputProvider eventInputProvider = new ManualEventInputProvider(inputSpecification);
        automator.setEventInputProvider(eventInputProvider);





        ProxyConfiguration proxyConfiguration = ProxyConfiguration.nothing();
        automator.setProxyConfiguration(proxyConfiguration);




        WebAutomatorConfiguration config = automator.getConfiguration();
        automator.openPage("http://www.google.com");
    }

    @Test
    public void ordinaryCase(){
        WebAutomator automator = new WebAutmator("chrome");
        automator.openPage("http://www.google.com");

        State initState = automator.extractState();
        initState.saveSnapshotTo("/home/gigon/testCase.png");
        Document dom = initState.getDOM();

        List<Event> events = automator.getAvailableEvents(initState); // equals to 'automator.getAvailableEvents();'
        Event event = events.get(0);
        automator.execute(event);


        automator.reload(); // reset state
        TestCase testcase = automator.loadTestCase("/home/gigon/testcase.html");
        Event firstEvent = testcase.getEvent(0);
        List<String> inputData = automator.getAvailableInputData(firstEvent);
        firstEvent.setInputData(inputData.get(1));

        TestCaseExecutionResult result = automator.execute(testcase);
        if (!result.isSucceed()) {
            int failedIndex = result.getFailedIndex();
            State targetState = result.getStateBefore(failedIndex);
            //StateDiff diff = initState.compareTo(targetState, new SnapshotComparator());
            //System.out.println(diff.toString());

            Event targetEvent = result.getEvent(failedIndex);

            test case = testcase.slice(failedIndex + 1);
        }
    }

 */
}
