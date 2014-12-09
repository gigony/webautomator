package edu.unl.webautomator.core.configuration;


import edu.unl.webautomator.core.platform.WebBrowserType;

/**
 * Created by gigony on 12/9/14.
 */
public class WebBrowserConfigurationBuilder  {
    private final WebBrowserConfiguration browserConfiguration;

    public WebBrowserConfigurationBuilder(){
        browserConfiguration = new WebBrowserConfiguration();
        browserConfiguration.browserType = WebBrowserType.CHROME;
    }

    public WebBrowserConfigurationBuilder(WebBrowserType browserType){
        browserConfiguration = new WebBrowserConfiguration();
        browserConfiguration.browserType = browserType;
    }

    public WebBrowserConfigurationBuilder setBrowserType(WebBrowserType browserType){
        browserConfiguration.browserType=browserType;
        return this;
    }
    public WebBrowserConfigurationBuilder setRemoteHubUrl(String remoteHubUrl){
        browserConfiguration.remoteHubUrl = remoteHubUrl;
        return this;
    }

    public WebBrowserConfiguration build() {
        return browserConfiguration;
    }
}
