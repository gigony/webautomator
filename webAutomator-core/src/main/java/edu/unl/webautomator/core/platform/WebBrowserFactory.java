package edu.unl.webautomator.core.platform;

import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
import edu.unl.webautomator.core.platform.browser.*;

/**
 * Created by gigony on 12/9/14.
 */
public class WebBrowserFactory {
    public static WebBrowser create(WebBrowserConfiguration browserConfiguration){
        WebBrowserType browserType = browserConfiguration.getBrowserType();
        switch(browserType){
            case CHROME:
                return new ChromeWebBrowser(browserConfiguration);
            case FIREFOX:
                return new FireFoxWebBrowser(browserConfiguration);
            case IEXPLORER:
                return new IExplorerWebBrowser(browserConfiguration);
            case PHANTOMJS:
                return new PhantomJsWebBrowser(browserConfiguration);
            case REMOTE:
                return new RemoteWebBrowser(browserConfiguration);
            default:
                throw new RuntimeException("No available webbrowser ("+browserType.name()+")");
        }

    }
}
