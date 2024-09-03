import javax.xml.crypto.Data;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.Scanner;

public class Owner {
    private static String patternPhone = "09[0-9]{7,9}";
    public static void own(){

        Scanner scanner = new Scanner(System.in);
        String role = "owner";

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\n\n~~~ Owner Page ~~~\n");
        String phone, password;
        boolean loop = false;
        int option = 0;
        String[] temp = {"sign","in"};

        do {
            System.out.print("Enter phone number to log in: ");
            phone = scanner.next();

            System.out.print("Enter password to log in: ");
            password = scanner.next();

            if(Database.checkUserLogIn(phone,password,role))
                break;
            else
                System.out.println("Phone No and Password do not match.\n Please Try Again...");
        }while (true);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        String ownerName = Database.checkSignInData(phone, password, role);
        if(ownerName != null){
                System.out.println("\t\t\t\t\tWelcome "+ownerName + " to our \"Art of Cafe\"");

            do {
                System.out.print("""
                            \n
                            Choose option to process...
                            1:  View Menu
                            2:  Edit Menu
                            3:  Add Employee
                            4:  Delete Employee
                            5:  View Employee
                            6:  Give Salary
                            7:  View Record
                            0:  Sign Out""");

                try {
                    loop = true;
                    option = scanner.nextInt();
                    switch (option){
                        case 1: Database.viewMenu(); break;
                        case 2: editMenu(); break;
                        case 3: addEmployee(); break;
                        case 4: deleteEmployee(); break;
                        case 5: Database.viewEmployee(); break;
                        case 6: giveSalary(); break;
                        case 7: viewRecord(); break;
                        case 0: {Home.main(temp);
                            loop = false;
                            break;
                        }
                        default:{
                            System.out.println("\nOption must only be number: 1,2,3,4,5, and 0...");
                        }
                    }
                }
                catch (Exception e){
                    System.out.println("Invalid input option...");
                    System.out.println(e.getStackTrace()+e.getLocalizedMessage());
                    scanner= new Scanner(System.in);
                    loop = true;
                }
            }while (loop);

        }
    }
    public static void editMenu(){
        boolean loop = false;
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        do{
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.print("""
                \nChoose the option...
                1:  Insert New Menu
                2:  Change Price
                3:  Delete Menu
                0:  Exit...""");

            try {
                loop = true;
                option = scanner.nextInt();
                switch (option){
                    case 1: Database.insertMenu(); break;
                    case 2: Database.changePrice(); break;
                    case 3: Database.deleteMenu(); break;
                    case 0: {
                        loop = false;
                        break;
                    }
                    default:{
                        System.out.println("\nOption must only be number: 1,2,3 and 0...");
                    }
                }
            }
            catch (Exception e){
                System.out.println("Invalid input option...");
                System.out.println(e.getStackTrace()+e.getLocalizedMessage());
                scanner= new Scanner(System.in);
                loop = true;
            }
        }
        while (loop);
    }


    private static void addEmployee(){
        String phone;
        String gender;
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.print("Enter Employee's name: ");
        String name = new Scanner(System.in).nextLine();

        System.out.print("Enter Employee's password: ");
        String password = new Scanner(System.in).nextLine();

        do {
            System.out.print("Enter Employee's phone no: ");
            phone = new Scanner(System.in).nextLine();
            if (!phone.matches(patternPhone)) {
                System.out.println("Phone number is invalid format...");
            }
        }while(!phone.matches(patternPhone));

        do {
            System.out.print("Enter Employee's gender: ");
            gender = new Scanner(System.in).nextLine();
            if (!(gender.matches("Male") || gender.matches("Female") ||
                    gender.matches("male") || gender.matches("female") || gender.matches("M") || gender.matches("F")))
                System.out.println("Gender is invalid type...");
        }while (!(gender.matches("Male") || gender.matches("Female") ||
                gender.matches("male") || gender.matches("female") || gender.matches("M") || gender.matches("F")));

        System.out.print("Enter Employee's age: ");
        int age = new Scanner(System.in).nextInt();

        Database.insertEmployee(name, password, phone, gender, age);
    }

    private static void deleteEmployee(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.print("Enter Employee's id to delete: ");
        int id = new Scanner(System.in).nextInt();

        Database.deleteEmployee(id);
    }

    private static void giveSalary(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.print("Enter Employee's id: ");
        int employeeId = new Scanner(System.in).nextInt();

        System.out.print("Enter salary amount: ");
        int salary = new Scanner(System.in).nextInt();

        Database.giveSalary(employeeId,salary);
    }

    private static void viewRecord(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.print("Enter Date you want to view (YYYY-MM-dd):");
        String date = new Scanner(System.in).next();

        Database.viewRecord(date);
    }
}
