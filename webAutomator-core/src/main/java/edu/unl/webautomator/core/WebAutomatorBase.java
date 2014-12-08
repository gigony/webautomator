package edu.unl.webautomator.core;

import com.google.inject.Inject;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.converter.TestCaseConverter;
import edu.unl.webautomator.core.executor.EventExecutor;
import edu.unl.webautomator.core.extractor.EventExtractor;
import edu.unl.webautomator.core.extractor.StateExtractor;
import edu.unl.webautomator.core.provider.EventInputProvider;

/**
 * Created by gigony on 12/6/14.
 */
public class WebAutomatorBase implements WebAutomator{

    private TestCaseConverter testCaseConverter;
    private StateExtractor stateExtractor;
    private EventExtractor eventExtractor;
    private EventInputProvider eventInputProvider;
    private EventExecutor eventExecutor;


    @Inject
    WebAutomatorBase(TestCaseConverter testCaseConverter, StateExtractor stateExtractor, EventExtractor eventExtractor, EventInputProvider eventInputProvider, EventExecutor eventExecutor) {
        this.testCaseConverter = testCaseConverter;
        this.stateExtractor = stateExtractor;
        this.eventExtractor = eventExtractor;
        this.eventInputProvider = eventInputProvider;
        this.eventExecutor = eventExecutor;
    }
}
