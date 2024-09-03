import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;

public class Home {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loop = false;
        int option = 0;

        System.out.println("\n\n\t\t\t\t\t\t\" Art Of Cafe \"");
        System.out.println("\t\t\t\tCoffee is our Love Language...");
        System.out.println("\t\t\t\t\t\t\t(^-^) (^-^)");

        do {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.print("""
                    Choose the option...
                    1:  Owner
                    2:  Employee
                    3:  Customer
                    0:  Exit...""");

            try {
                loop = false;
                option=scanner.nextByte();
                switch (option){
                    case 1: Owner.own(); break;
                    case 2: Employee.work(); break;
                    case 3: Customer.buy(); break;
                    case 0: System.exit(0); break;
                    default: {
                        System.out.println("\n Option must only be 1,2,3 and 0...");
                        loop= true;
                    }
                }
            }
            catch (Exception e){
                System.out.println("Invalid input...");
                scanner = new Scanner(System.in);
                loop = true;
            }
        }
        while (loop);
    }

}
