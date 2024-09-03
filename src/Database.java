import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.StringJoiner;

public class Database {
    static String url="jdbc:mysql://localhost:3306/artofcafe";
    static String dbuser= "root";
    static String dbpassword = "";
    public static String checkSignInData(String phone, String password, String role){
        ResultSet singleData = retrieveData(phone, role);
        while(true){
            try{
                if(!singleData.next())
                    return null;
                else{
                    String phoneDb = singleData.getString("phone");
                    String passwordDb = singleData.getString("password");
                    String nameDb = singleData.getString("name");

                    if(phone.equals(phoneDb) && password.equals(passwordDb))
                        return nameDb;
                }
            }
            catch (SQLException e){
                System.out.println(e.getLocalizedMessage());
                return null;
            }
        }
    }

    public static int checkSignInData1(String phone, String password, String role){
        ResultSet singleData = retrieveData(phone, role);
        while(true){
            try{
                if(!singleData.next())
                    return 0;
                else{
                    String phoneDb = singleData.getString("phone");
                    String passwordDb = singleData.getString("password");
                    int idDb = singleData.getInt("id");

                    if(phone.equals(phoneDb) && password.equals(passwordDb))
                        return idDb;
                }
            }
            catch (SQLException e){
                System.out.println(e.getLocalizedMessage());
                return 0;
            }
        }
    }

    public static ResultSet retrieveData(String phone, String role){
        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from "+role+" where phone='"+phone+"';");
            return resultSet;
        }
        catch (SQLException e){
            return null;
        }
    }

    public static boolean checkUserLogIn(String phone, String password, String role){
        if(!checkPhNumber(phone, role))
            return false;

        ResultSet userInfo = retrieveData(phone, role);
        while(true){
            try {
                if(!userInfo.next())
                    break;
                String dbPassword = userInfo.getString("password");
                return password.equals(dbPassword);
            }
            catch(SQLException e){
                System.out.println(e.getLocalizedMessage());
                return false;
            }
        }
        return false;
    }

    public static boolean checkPhNumber(String inputPhone, String role){
        return checkDbData("phone", inputPhone, role);
    }

    private static boolean checkDbData(String column, String inputData, String role){
        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from "+role+";");

            String dbData;
            while(resultSet.next()){
                if(column.equals("id")|| column.equals("age")){
                    int dbDataInt = resultSet.getInt(column);
                    dbData = Integer.toString(dbDataInt);
                }
                else{
                    dbData = resultSet.getString(column);
                }

                if(dbData.equals(inputData)){
                    return true;
                }
            }
            return false;
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void insertMenu(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Scanner scanner = new Scanner(System.in);
        String productName;
        int productPrice;

        System.out.print("Enter product name: ");
        productName = scanner.nextLine();

        System.out.print("Enter product price: ");
        productPrice = scanner.nextInt();

        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate(
                    "insert into menu (productName, productPrice) " +
                            "values ('"
                            +productName+"',"
                            +productPrice+");");

            if(status == 1) {
                System.out.println(productName+" is successfully inserted...");
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void changePrice(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Scanner scanner = new Scanner(System.in);
        String productName;
        int productPrice;

        System.out.print("Enter product name: ");
        productName = scanner.nextLine();

        System.out.print("Enter product price to change: ");
        productPrice = scanner.nextInt();

        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate("update menu set productPrice="+productPrice+" where productName='"+productName+"';");
            if(status == 1){
                System.out.println("The price of "+productName+ " is successfully updated...");
            }
        }
        catch(SQLException e){
            System.out.println(productName+" is not found!");
            throw new RuntimeException(e);
        }
    }

    public static void deleteMenu(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Scanner scanner = new Scanner(System.in);
        String productName;

        System.out.print("Enter product name: ");
        productName = scanner.nextLine();

        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate("delete from menu where productName='"+productName+"';");
            if(status == 1){
                System.out.println(productName+" is successfully deleted...");
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void viewMenu(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from menu;");

            if(!resultSet.next()){
                System.out.println("Currently Unavailable...");
            }
            else{
                System.out.println("""
                        ..........Art of Cafe..........
                        Code.          Menu                       Price    \s""".toUpperCase());

                do{
                    int no = resultSet.getInt("id");
                    String menu = resultSet.getString("productName");
                    String price = resultSet.getString("productPrice");

                    if(menu.length()<9)
                        menu+="\t";

                    String formatStr= """
                        %d   \t       %s            \t  %s      \s"""
                            .formatted(no,menu,price);
                    System.out.println(formatStr);
                }while(resultSet.next());
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertEmployee(String name, String password, String phone, String gender, int age){
        try{
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate(
                    "insert into employee (role, name, password, phone, gender, age) " +
                            "values ('employee','"
                            +name+"','"
                            +password+"','"
                            +phone+"','"
                            +gender+"',"
                            +age+");");

            if(status == 1){
                System.out.println("New Employee is successfully inserted...");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteEmployee(int id){
        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate("delete from employee where id="+id+";");
            if(status == 1){
                System.out.println("Employee id ("+id+") is successfully deleted...");
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void viewEmployee(){
        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from employee;");

            if(!resultSet.next()){
                System.out.println("Currently Unavailable...");
            }
            else{
                System.out.println("""
                        ..........Employee List..........
                        Id.   \t  Name       \t        Phone        \t   Gender   \t  Age     \s""".toUpperCase());

                do{
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String phone = resultSet.getString("phone");
                    String gender = resultSet.getString("gender");
                    int age = resultSet.getInt("age");

                    if(name.length()<=5)
                        name+="\t\t";
                    else if(name.length()<=7)
                        name+="\t";
                    if(gender.length()<=4)
                        gender+="\t";

                    String formatStr= """
                        %d    \t  %s      \t    %s     \t %s   \t   %d      \s"""
                            .formatted(id,name,phone,gender,age);
                    System.out.println(formatStr);
                }while(resultSet.next());
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void giveSalary(int employeeId, int salary){
        try{
            LocalDateTime timeNow = LocalDateTime.now();
            DateTimeFormatter formatStyle = DateTimeFormatter.ofPattern("YYYY-MM-dd");
            String time = formatStyle.format(timeNow);

            Connection connection = DriverManager.getConnection(url,dbuser,dbpassword);
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate("insert into "+
                    "salaryRecord (employeeId, salary, time)"+
                    "values ("+employeeId+","+salary+",'"+time+"');");

            if(status ==1){
                System.out.println("Give Salary successfully...");
            }
            else
                System.out.println("Failed to give salary...");
        }catch (SQLException e){
            System.out.println("There is no employee with id no: "+employeeId+"...");
            throw new RuntimeException(e);
        }
    }

    public static void viewSalary(int employeeId,String employeeName){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        try{
            Connection connection = DriverManager.getConnection(url,dbuser,dbpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from salaryRecord where employeeId="+employeeId+";");

            if(!resultSet.next()){
                System.out.println("No Record...");
            }
            else {
                System.out.println("Employee Id: "+employeeId);
                System.out.println("Employee Name: "+employeeName);
                System.out.println("""
                        \n
                        Date                Salary""");
                do{
                    Date time = resultSet.getDate("time");
                    int salary = resultSet.getInt("salary");

                    String formatStr = """
                            %s          %d      \s"""
                            .formatted(time, salary);
                    System.out.println(formatStr);
                }while (resultSet.next());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public  static void order() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        int total = 0;
        int sum = 0;
        int menuNo;
        int amount = 0;
        StringJoiner output=new StringJoiner("\n");

        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatStyle = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        String time = formatStyle.format(timeNow);


        try{
            do {
                System.out.print("Enter coffee code: (Enter '0' to finish ordering): ");
                menuNo = new Scanner(System.in).nextInt();

                if(menuNo==0){
                    break;
                }

                System.out.print("Enter amount: (Enter '0' to finish ordering):");
                amount = new Scanner(System.in).nextInt();

                ResultSet menuInfo = retrieveMenu(menuNo);


                if(!menuInfo.next()){
                    System.out.println("No menu with this id...");
                }
                else{
                    String menu = menuInfo.getString("productName");
                    int price = menuInfo.getInt("productPrice");
                    total = price * amount;
                    sum += total;

                    if(menu.length()<9)
                        menu+="\t\t";

                    String formatStr = """
                    %d    \t\t  %s       \t   %d    \t      %d          %d          \s"""
                            .formatted(menuNo, menu, price, amount, total);
                    output.add(formatStr);
                }

            } while (menuNo != 0);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        addRecord(time,sum);
        String result = output.toString();
        System.out.println("Date: " + time);
        System.out.println("""
                Code.         Product             Price       Amount         Total   \s""");
        System.out.println(result);
        System.out.println("""
                Total                                                       \s""" + sum);
    }

    public static void addRecord(String time, int sum){
        try{
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate(
                    "insert into record (date,amount) " +
                            "values ('"+time+"',"
                            +sum+");");

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet retrieveMenu(int id){
        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from menu where id="+id+";");
            return resultSet;
        }
        catch (SQLException e){
            return null;
        }
    }

    public  static void viewRecord(String date){
        try{
            int sum = 0;
            Connection connection = DriverManager.getConnection(url,dbuser,dbpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from record where date='"+date+"';");

            if(!resultSet.next()){
                System.out.println("No Record...");
            }
            else {
                System.out.println("""
                        Date                Total """);
                do{
                    Date time = resultSet.getDate("date");
                    int amount = resultSet.getInt("amount");
                    sum += amount;

                    String formatStr = """
                            %s          %d      \s"""
                            .formatted(time, amount);
                    System.out.println(formatStr);
                }while (resultSet.next());
                System.out.println("""
                \n
                Total   \t\t   \s     """ + sum);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
