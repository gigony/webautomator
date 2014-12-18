package edu.unl.webautomator.core.converter;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import edu.unl.webautomator.core.WebAutomator;

/**
 * Created by gigony on 12/6/14.
 */
public class WebTestCaseConverter implements TestCaseConverter {
    private WebAutomator webAutomator;

    @Inject
    public WebTestCaseConverter(@Assisted final WebAutomator automator) {
        this.webAutomator = automator;
    }
}
