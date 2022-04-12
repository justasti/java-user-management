package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException {

    DBReader dbReader = new DBReader("src\\com\\company\\userData.txt");
    Scanner sc = new Scanner(System.in);

    String menuOption = "";
    User currentlyOperatingUser = null;

    System.out.println();

    while (true) {
      ArrayList<User> users = dbReader.getAllUsers();
      if (users.size() > 0) {
        users.get(0).setRole("admin");
      }
      printMenu();
      System.out.print("Įveskite pasirinkimą: ");
      menuOption = sc.nextLine();

      switch (menuOption) {
        case "1":

          System.out.print("Įveskite vartotojo vardą: ");
          String username = sc.nextLine();

          loginUser(users, sc, currentlyOperatingUser, username, dbReader);

          break;

        case "2":
          addNewUser(currentlyOperatingUser, sc, users);
          dbReader.addToFile(users);

          break;

        case "0":
          return;

        default:
          System.out.println("Neatpažinta įvestis.\n");
      }
    }
  }

  private static void loginUser(ArrayList<User> users, Scanner sc,
                                User currentlyOperatingUser, String username,
                                DBReader dbReader) throws IOException {
    for (User user : users) {
      if (user.getUsername().equalsIgnoreCase(username)) {
        currentlyOperatingUser = user;
      }
    }

    if (currentlyOperatingUser == null) {
      System.out.println("Vartotojas tokiu vardu nerastas!\n");
    } else {
      System.out.print("Įveskite slaptažodį: ");
      String password = sc.nextLine();

      if (currentlyOperatingUser.getPassword().equals(password)) {
        System.out.println("Sėkmingai prisijungta!");

        userMenu(sc, currentlyOperatingUser, users, dbReader);
      } else {
        System.out.println("Neteisingas slaptažodis!\n");
      }
    }
  }

  private static void userMenu(Scanner sc, User selectedUser, ArrayList<User> users, DBReader dbReader) throws IOException {
    while (true) {
      String secondaryMenuOption;

      if (selectedUser.getRole().equalsIgnoreCase("simple")) {
        printSimpleUserMenu();

        System.out.print("Įveskite pasirinkimą: ");
        secondaryMenuOption = sc.nextLine();

        if (secondaryMenuOption.equals("1")) {
          printUserData(selectedUser, selectedUser);

        } else if (secondaryMenuOption.equals("0")) {
          break;

        } else {
          System.out.println("Neatpažinta įvestis.\n");
        }

      } else {
        printAdminUserMenu();

        System.out.print("Įveskite pasirinkimą: ");
        secondaryMenuOption = sc.nextLine();

        switch (secondaryMenuOption) {
          case "1":
            printUserData(selectedUser, selectedUser);
            break;
          case "2":
            System.out.println("---------------------");
            for (User user : users) {
              printUserData(user, selectedUser);
              System.out.println("---------------------");
            }

            break;
          case "3":
            addNewUser(selectedUser, sc, users);
            dbReader.addToFile(users);

            break;
          case "4":
            System.out.print("Įveskite vartotojo slapyvardį: ");
            String chosenUser = sc.nextLine();
            User userToDelete = dbReader.getUser(chosenUser, users);

            if (userToDelete == null) {
              System.out.printf("Vartotojas \"%s\" nerastas\n", chosenUser);
            } else {
              users.remove(userToDelete);
              System.out.printf("Vartotojas \"%s\" sėkmingai panaikintas\n", chosenUser);
            }

            dbReader.addToFile(users);
            break;

          case "0":
            return;
          default:
            System.out.println("Neatpažinta įvestis.\n");
            break;
        }
      }
    }
  }

  private static boolean checkForExistingUser(String username, ArrayList<User> users) {
      int count = 0;
      for (User user : users) {
        if (user.getUsername().equalsIgnoreCase(username)) {
          count++;
        }
      }
      if (count > 0) {
        return true;
      }
      return false;
  }

  private static void addNewUser(User selectedUser, Scanner sc, ArrayList<User> users) {
    String username;
    while (true) {
      System.out.print("Įveskite vartotojo slapyvardį: ");
      username = sc.nextLine();
      if (checkForExistingUser(username, users) == true) {
        System.out.println("Toks vartotojas jau egzistuoja.");
      } else {
        break;
      }
    }
    System.out.print("Įveskite vartotojo slaptažodį: ");
    String password = sc.nextLine();
    System.out.print("Įveskite vartotojo vardą: ");
    String name = sc.nextLine();
    System.out.print("Įveskite vartotojo pavardę: ");
    String surname = sc.nextLine();
    System.out.print("Įveskite vartotojo amžių: ");
    int age = sc.nextInt();
    sc.nextLine();

    String role = "simple";
    if (selectedUser != null && selectedUser.getRole().equalsIgnoreCase("admin")) {
      System.out.println("Ar suteikti vartotojui administratoriaus teises? [T/N]");
      String setAdminRights = sc.nextLine();
      if (setAdminRights.equalsIgnoreCase("t")) {
        role = "admin";
      }
    }
    users.add(new User(username, password, name, surname, age, role));

  }

  private static void printUserData(User userToPrint, User operatingUser) {
    System.out.printf("Vartotojo \"%s\" informacija:\n", userToPrint.getUsername());
    System.out.printf("Vardas: %s;\n", userToPrint.getName());
    System.out.printf("Pavardė: %s;\n", userToPrint.getSurname());
    System.out.printf("Amžius: %d\n", userToPrint.getAge());
    if (operatingUser.getRole().equalsIgnoreCase("admin")) {
      if (userToPrint.getRole().equalsIgnoreCase("admin")) {
        System.out.printf("Rolė: Sistemos administratorius\n");
      } else {
        System.out.printf("Rolė: Paprastas vartotojas\n");
      }
    }
  }

  private static void printAdminUserMenu() {
    System.out.println("\n1. Peržiūrėti savo informaciją");
    System.out.println("2. Peržiūrėti visų vartotojų informaciją.");
    System.out.println("3. Pridėti naują vartotoją.");
    System.out.println("4. Ištrinti egzistuojantį vartotoją.");
    System.out.println("0. Atsijungti\n");
  }

  private static void printSimpleUserMenu() {
    System.out.println("\n1. Peržiūrėti savo informaciją");
    System.out.println("0. Atsijungti\n");
  }

  private static void printMenu() {
    System.out.println("1. Prisijungti");
    System.out.println("2. Registruotis");
    System.out.println("0. Baigti darbą\n");
  }
}
