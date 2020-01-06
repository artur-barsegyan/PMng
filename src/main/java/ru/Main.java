package ru;

import org.hibernate.Session;

import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Session session = AppResources.getDBSession();
        if (args.length < 2) {
            System.out.println("NIY");
            return;
        }

        switch (args[1]) {
            case "cron": {
                ProcessCapture pc = ProcessCapture.getInstance();
                final Collection<UsedApplication> capture = pc.capture();

                for (UsedApplication app : capture) {
                    System.out.println(app.getAppName());
                }

                break;
            }

            case "add-categories": {

            }
        }
    }
}
