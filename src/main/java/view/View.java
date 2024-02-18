package view;

import Controller.Controller;
import exceptions.MyException;

import java.io.IOException;
import java.util.Scanner;

public class View {
    Scanner scan;
    Controller controller;
    public View(Controller con){
        controller = con;
        scan = new Scanner(System.in);
    }


    public void ShowMenu() throws MyException, IOException {
        String option;
        boolean debugFlag = false;
        while(true)
        {
            System.out.println("Debug flag is currently set to: " + debugFlag);
            System.out.println("Press 1 for first example");
            System.out.println("Press 2 for second example");
            System.out.println("Press 3 for third example");
            System.out.println("Press 4 for fourth example");

            System.out.println("Press 5 to switch debug flag");
            System.out.println("Press 0 to close program");
            option = scan.nextLine();
            if(option.equals("1"))
                runProgram(0, debugFlag);
            else if(option.equals("2"))
                runProgram(1, debugFlag);
            else if(option.equals("3"))
                runProgram(2, debugFlag);
            else if(option.equals("5"))
                debugFlag = !debugFlag;
            else if(option.equals("0"))
                System.exit(0);
            else if(option.equals("4"))
                runProgram(3, debugFlag);
            else
                System.out.println("Not a valid option!");
        }
    }

    public void runProgram(int index, boolean debugFlag) throws MyException, IOException {
//        if(index == 0)
//            controller.loadFirstExample();
//        if(index == 1)
//            controller.loadSecondExample();
//        if(index == 2)
//            controller.loadThirdExample();
//        if(index == 3)
//            controller.loadFourthExample();
//        controller.allStep();
    }

}
