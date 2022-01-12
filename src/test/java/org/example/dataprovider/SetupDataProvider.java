package org.example.dataprovider;

import org.testng.annotations.DataProvider;

public class SetupDataProvider {
    @DataProvider(name = "platforms")
    public Object[] platforms(){

        return new Object[] {
                "web",
                "mobile"
        };
    }
}
