import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Scanner;

public class Employee {
    public static void work(){

        Scanner scanner = new Scanner(System.in);
        String role = "employee";

        System.out.println("\n\n~~~ Employee ~~~\n");
        String phone, password;
        boolean loop = false;
        int option = 0;
        String[] temp = {"sign","in"};

        do {
            System.out.print("Enter phone number to log in: ");
            phone = scanner.next();

            System.out.print("Enter password: ");
            password = scanner.next();

            if(Database.checkUserLogIn(phone,password,role))
                break;
            else
                System.out.println("Phone No and Password do not match.\n Please Try Again...");
        }while (true);

        String employeeName = Database.checkSignInData(phone, password, role);
        int employeeId = Database.checkSignInData1(phone, password, role);
        if(employeeName != null){
            System.out.println("Welcome "+employeeName+" to our \"Art of Cafe\"");

            do {

                System.out.print("""
                            \n
                            Choose option to process...
                            1:  View Menu
                            2:  Edit Menu
                            3:  View Salary
                            0:  Sign Out""");

                try {
                    loop = true;
                    option = scanner.nextInt();
                    switch (option){
                        case 1: Database.viewMenu(); break;
                        case 2: Owner.editMenu(); break;
                        case 3: Database.viewSalary(employeeId,employeeName);  break;
                        case 0: {Home.main(temp);
                            loop = false;
                            break;
                        }
                        default:{
                            System.out.println("\nOption must only 1 and 0...");
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
}
