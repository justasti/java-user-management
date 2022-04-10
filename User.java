package com.company;

public class User {
  private String username;
  private String password;
  private String role;
  private String name;
  private String surname;
  private int age;

  public User(String username, String password, String name, String surname, int age, String role) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.surname = surname;
    this.age = age;
    this.role = role;
  }


  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getRole() {
    return role;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public int getAge() {
    return age;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
      return "User{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", role='" + role + '\'' +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", age=" + age +
        '}';
  }
}
