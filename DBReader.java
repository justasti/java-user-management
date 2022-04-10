package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DBReader {

  private String path;

  public DBReader(String path) {

    this.path = path;
  }

  public ArrayList getAllUsers() throws IOException {
    ArrayList<User> allUsers = new ArrayList<>();

    File file = new File(path);
    Scanner sc = new Scanner(file);

    while (sc.hasNextLine()) {
      String username = sc.nextLine();
      String password = sc.nextLine();
      String name = sc.nextLine();
      String surname = sc.nextLine();
      int age = sc.nextInt();
      sc.nextLine();
      String role = sc.nextLine();
      sc.nextLine();

      allUsers.add(new User(username, password, name, surname, age, role));
    }
    return allUsers;
  }

  public User getUser(String userToDelete, ArrayList<User> usersList) {
    User userToGet = null;
    for (User user : usersList) {
      if (user.getUsername().equalsIgnoreCase(userToDelete)) {
        userToGet = user;
      }
    }
    return userToGet;
  }

  public void addToFile(ArrayList<User> users) throws IOException {
    FileWriter fw = new FileWriter(path);
    PrintWriter printer = new PrintWriter(fw);

    for (User user : users) {
      printer.println(user.getUsername());
      printer.println(user.getPassword());
      printer.println(user.getName());
      printer.println(user.getSurname());
      printer.println(user.getAge());
      printer.println(user.getRole());
      printer.println();
    }
    printer.close();
  }
}
