package ru;

import java.util.Optional;

public class UsedApplication {
    String appName;
    long usedTime;

    public UsedApplication(String appName, long usedTime) {
        this.appName = appName;
        this.usedTime = usedTime;
    }

    public String getAppName() {
        return appName;
    }

    public long getUsedTime() {
        return usedTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        UsedApplication castedObj = (UsedApplication) obj;
        return this.appName.equals(castedObj.appName);
    }
}
