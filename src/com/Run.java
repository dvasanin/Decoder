package com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Run {

    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter your ID number: ");
        String jmbg = sc.next();

        //If the user enters a 0, exit
        if (jmbg.equals("0")) {
            System.exit(0);
        }

        //Check the SSN length, if invalid try again
        int repeatedEntry = 0;
        while (jmbg.length() != 13) {
            System.out.println("Invalid SSN, try again!");
            jmbg = sc.next();

            repeatedEntry++;

            if (repeatedEntry == 4) {
                System.out.println("System shutdown after the next wrong entry");
            }
            if (repeatedEntry == 5) {
                System.exit(0);
            }
        }

        //Check for invalid characters
        for (int i = 0; i < jmbg.length(); i++) {
            if (!Character.isDigit(jmbg.charAt(i))) {
                System.out.println("Invalid SSN, try again!");
                System.exit(0);
            }
        }

        //Separate the SSN into numbers
        int ctrlNum = Integer.parseInt(String.valueOf(jmbg.charAt(12)));

        int numA = Integer.parseInt(String.valueOf(jmbg.charAt(0)));
        int numB = Integer.parseInt(String.valueOf(jmbg.charAt(1)));
        int numV = Integer.parseInt(String.valueOf(jmbg.charAt(2)));
        int numG = Integer.parseInt(String.valueOf(jmbg.charAt(3)));
        int numD = Integer.parseInt(String.valueOf(jmbg.charAt(4)));
        int numDj = Integer.parseInt(String.valueOf(jmbg.charAt(5)));
        int numE = Integer.parseInt(String.valueOf(jmbg.charAt(6)));
        int numZh = Integer.parseInt(String.valueOf(jmbg.charAt(7)));
        int numZ = Integer.parseInt(String.valueOf(jmbg.charAt(8)));
        int numI = Integer.parseInt(String.valueOf(jmbg.charAt(9)));
        int numJ = Integer.parseInt(String.valueOf(jmbg.charAt(10)));
        int numK = Integer.parseInt(String.valueOf(jmbg.charAt(11)));

        //Formulate the control number
        int ctrlCheck = 11 - ((7 * (numA + numE) + 6 * (numB + numZh) + 5 * (numV + numZ) + 4 * (numG + numI) + 3 * (numD + numJ) + 2 * (numDj + numK)) % 11);

        if (ctrlCheck > 9) {
            ctrlCheck = 0;
        }

        //Control number check
        if (ctrlNum != ctrlCheck) {
            System.out.println("Invalid SSN, try again!");
            System.exit(0);
        }

        //DOB declaration
        String dateOfBirth = jmbg.substring(0, 7);

        int day = Integer.parseInt(jmbg.substring(0, 2));
        int month = Integer.parseInt(jmbg.substring(2, 4));

        //Edge case - DOB
        if (day > 31 || day == 0 || month > 12 || month == 0) {
            System.out.println("Invalid SSN, try again!");
            System.exit(0);
        }

        //Formatting the year
        if (Integer.parseInt(Character.toString(dateOfBirth.charAt(4))) == 9) {
            StringBuilder sb = new StringBuilder(dateOfBirth);
            sb.insert(4, '1');
            dateOfBirth = sb.toString();
        } else if (Integer.parseInt(dateOfBirth.substring(4, 7)) == 0) {
            StringBuilder sb = new StringBuilder(dateOfBirth);
            sb.insert(4, '2');
            dateOfBirth = sb.toString();
        } else {
            StringBuilder sb = new StringBuilder(dateOfBirth);
            sb.insert(4, '2');
            dateOfBirth = sb.toString();
        }

        //Convert to a date format, parse SSN
        SimpleDateFormat format1 = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yyyy.");

        Date dob = format1.parse(dateOfBirth);

        //Region check
        int regionOfBirth = Integer.parseInt(jmbg.substring(7, 9));

        //Region map
        Map<Integer, String> map = new HashMap<>();

        map.put(71, "Beograd");
        map.put(72, "Šumadija");
        map.put(73, "Niš");
        map.put(74, "Južna Morava");
        map.put(75, "Zaječar");
        map.put(76, "Podunavlje");
        map.put(77, "Podrinje i Kolubara");
        map.put(78, "Kraljevo");
        map.put(79, "Užice");

        map.put(80, "Novi Sad");
        map.put(81, "Sombor");
        map.put(82, "Subotica");
        map.put(85, "Zrenjanin");
        map.put(86, "Pančevo");
        map.put(87, "Kikinda");
        map.put(88, "Ruma");
        map.put(89, "Sremska Mitrovica");

        map.put(91, "Priština");
        map.put(92, "Kosovska Mitrovica");
        map.put(93, "Peć");
        map.put(94, "Đakovica");
        map.put(95, "Prizren");
        map.put(96, "Kosovsko Pomoravski okrug");

        //Show sex -> 000-499 men, 500-999 women
        int sexNum = Integer.parseInt(jmbg.substring(9, 13));
        String sex;
        if (sexNum < 500) {
            sex = "Male";
        } else {
            sex = "Female";
        }

        //If the region is valid, print out details
        if (map.containsKey(regionOfBirth)) {
            System.out.println("Username: " + System.getProperty("user.name"));

            System.out.println("Date of birth: " + format2.format(dob));

            System.out.println("Region: " + map.get(regionOfBirth));

            System.out.println("Sex: " + sex);

            System.out.println("Thank you, goodbye!");
        } else {
            System.out.println("Invalid SSN, try again!");
            System.exit(0);
        }
    }
}
