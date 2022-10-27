package app;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        String command;
        String entity;
        Scanner scanner = new Scanner(System.in);
        CRUDDialog crudDialog = new CRUDDialog(scanner);

        while (true) {
            System.out.println("enter command:");
            command = scanner.nextLine().toUpperCase();

            if(command.equals("EXIT")) {
                break;
            }

            System.out.println("enter entity:");
            entity = scanner.nextLine();

            switch (command) {
                case "CREATE":
                crudDialog.create(entity);
                break;

                case "READ":
                crudDialog.read(entity);
                break;

                case "UPDATE":
                crudDialog.update(entity);
                break;

                case "DELETE":
                crudDialog.delete(entity);
                break;
            }
            
            System.out.println("\n");
        }

       scanner.close();
    }
}
