import java.util.Scanner;

public class Customer {
    public static void buy(){
        Scanner scanner = new Scanner(System.in);
        String role = "customer";

        System.out.println("\n\n~~~ Customer ~~~\n");
        boolean loop = false;
        int option = 0;
        String[] temp = {"sign","in"};

            do {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.print("""
                            Choose option to process...
                            1:  View Menu
                            2:  Order
                            0:  Sign Out""");

                try {
                    loop = true;
                    option = scanner.nextInt();
                    switch (option){
                        case 1: Database.viewMenu();  break;
                        case 2: Database.order(); break;
                        case 0: {Home.main(temp);
                            loop = false;
                            break;
                        }
                        default:{
                            System.out.println("\nOption must only 1,2 and 0...");
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
