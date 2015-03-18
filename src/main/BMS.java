package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;


public class BMS {


    public static BMSState BMS_STATE;
    public static ConcurrentHashMap centralStorage;                //Collection for storing data of BMS in (key, pair) format


    public BMS() {
        BMS_STATE = BMSState.IDLE;
        centralStorage = new ConcurrentHashMap<String, Object>();


        //Initializing Central Storage data elements
        centralStorage.put("chargingCyclesLeft", new Integer(5000));
        centralStorage.put("carSpeed", new Integer(0));
        centralStorage.put("distanceTravelled", new Float(0));
        centralStorage.put("consumptionRate", new Float(0));
        centralStorage.put("batteryLevel", new Float(10));
    }

    public static String getBMSStatus() {
        return BMS_STATE.toString();
    }

    public static void setBMSStatus(BMSState b) {
        BMS_STATE = b;
    }

    //Function to store data in Collection
    public synchronized static void storeDataInCollection(String key, Object data) {
        if (key.equals("charginCyclesLeft")) {
            centralStorage.put("charginCyclesLeft", data);
        } else if (key.equals("carSpeed")) {
            centralStorage.put("carSpeed", data);
        } else if (key.equals("distanceTravelled")) {
            centralStorage.put("distanceTravelled", data);
        } else if (key.equals("consumptionRate")) {
            centralStorage.put("consumptionRate", data);
        } else if (key.equals("batteryLevel")) {
            centralStorage.put("batteryLevel", data);
        }
    }

    //Function to get data from Collection
    public static Object getDataInCollection(String key) {
        return centralStorage.get(key);
    }

    public static void main(String... args) throws NumberFormatException, InterruptedException {
        //Declaring BMS Object
        BMS bmsObject = new BMS();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Integer choice = -1;
        System.out.println("Welcome to Battery Management System.\n\n");

        do {
            System.out.println("\nSelect your option\n1 - For charging Battery.\n2 - For drive the car.\n0 - To exit");
            System.out.print("\nEnter your choice: ");
            try {
                choice = Integer.parseInt(br.readLine());
            } catch (NumberFormatException nf) {
                System.err.println("\nPlease enter input in numeric format!!\n");
                continue;
            } catch (IOException e) {
                System.err.println("Some error occured please try again");
            }

            //Swtich case for options selected by the User
            switch (choice) {
                case 0:
                    break;

                case 1:
                    setBMSStatus(BMSState.CHARGING);
                    System.out.println("BMS State is " + getBMSStatus());
                    //Function to start charging operation
                    break;

                case 2:
                    setBMSStatus(BMSState.ONMOVE);
                    System.out.println("BMS State is " + getBMSStatus());
                    //Functions to start driving a car i.e. BMS State is ONMOVE
                    while ((Float) getDataInCollection("batteryLevel") > 0) {


                        bmsObject.getNewInputs();
                    }
                    break;

                default:
                    System.err.println("\nKnock Knock!!! Wrong Input!!");
                    break;
            }


        } while (choice != 0);

        System.out.println("\nThanks for checking out our BMS. Yo Yo!!!");
    }

    //Function to get new Inputs from user
    public void getNewInputs() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("------------------------------------------------------");
        System.out.println("Do you want to provide new Inputs? (y/n)");
        String choice = null;
        try {
            choice = br.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (choice.equalsIgnoreCase("n")) {
            System.out.println("------------------------------------------------------");
            return;
        }


        System.out.println("------------------------------------------------------\n\n");

    }

}
