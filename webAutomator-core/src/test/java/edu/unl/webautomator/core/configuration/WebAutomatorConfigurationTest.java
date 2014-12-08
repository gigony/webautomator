package edu.unl.webautomator.core.configuration;


import org.junit.Test;

public class WebAutomatorConfigurationTest {

    @Test
    public void configurationLoadTest() {
        WebAutomatorConfiguration config = new WebAutomatorConfiguration();
        config.exportToJson("/Users/gigony/Development/Repository/github/webautomator/webAutomator-core/config.json");


//        File dest = new File("/Users/gigony/Development/Repository/github/webautomator/webAutomator-core/config.json");
//        IO.write(IO.path(dest.toString()), Boon.toPrettyJson(config));
//
//        ObjectMapper mapper =  JsonFactory.create();
//        WebAutomatorConfiguration config2= mapper.readValue(dest,WebAutomatorConfiguration.class);
//        File dest2 = new File("/Users/gigony/Development/Repository/github/webautomator/webAutomator-core/config2.json");
//        IO.write(IO.path(dest2.toString()), Boon.toPrettyJson(config2));
//        //mapper.writeValue(dest, config);
//        //IO.write(IO.path(dest.toString()), mapper.serializer().serialize(config).prettyPrintObject(config,false,0).toString());
    }


}