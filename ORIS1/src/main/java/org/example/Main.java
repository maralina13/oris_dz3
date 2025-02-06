package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/oris_db";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Сколько пользователей вы хотите ввести? ");
        int count = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < count; i++) {
            System.out.println("Введите данные для учителя " + (i + 1) + ":");
            System.out.print("Имя: ");
            String firstName = scanner.nextLine();
            System.out.print("Фамилия: ");
            String lastName = scanner.nextLine();
            System.out.print("Возраст: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Пол (М/Ж): ");
            String gender = scanner.nextLine();
            System.out.print("Предмет: ");
            String subject = scanner.nextLine();
            System.out.print("Ставка в час: ");
            double payperhour = Double.parseDouble(scanner.nextLine());

            String sqlInsertTeacher = "INSERT INTO teachers(name, surname, age, gender, subject, payperhour) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertTeacher);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, subject);
            preparedStatement.setDouble(6, payperhour);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Было добавлено " + affectedRows + " строк");
        }

        // Примеры вызовов методов для поиска
        System.out.println("Учителя мужского пола:");
        selectTeachersByGender(connection, "М");

        System.out.println("Учителя по предмету 'Математика':");
        selectTeachersBySubject(connection, "Математика");

        System.out.println("Учителя со ставкой больше 1000:");
        selectTeachersByPayPerHour(connection, 1000);

        // Закрытие ресурсов
        connection.close();
        scanner.close();
    }

    // Метод для поиска учителей по полу
    private static void selectTeachersByGender(Connection connection, String gender) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE gender = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, gender);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            System.out.println(
                    result.getInt("id") + " " +
                            result.getString("name") + " " +
                            result.getString("surname") + " " +
                            result.getInt("age") + " " +
                            result.getString("gender") + " " +
                            result.getString("subject") + " " +
                            result.getDouble("payperhour")
            );
        }
    }

    // Метод для поиска учителей по предмету
    private static void selectTeachersBySubject(Connection connection, String subject) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE subject = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, subject);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            System.out.println(
                    result.getInt("id") + " " +
                            result.getString("name") + " " +
                            result.getString("surname") + " " +
                            result.getInt("age") + " " +
                            result.getString("gender") + " " +
                            result.getString("subject") + " " +
                            result.getDouble("payperhour")
            );
        }
    }

    // Метод для поиска учителей по ставке в час
    private static void selectTeachersByPayPerHour(Connection connection, double payperhour) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE payperhour > ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1, payperhour);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            System.out.println(
                    result.getInt("id") + " " +
                            result.getString("name") + " " +
                            result.getString("surname") + " " +
                            result.getInt("age") + " " +
                            result.getString("gender") + " " +
                            result.getString("subject") + " " +
                            result.getDouble("payperhour")
            );
        }
    }
}



