package sample.database;

import org.sqlite.JDBC;
import org.sqlite.SQLiteException;
import sample.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBase {
    private final String CON_STR = "jdbc:sqlite:base.db";

    private static DataBase instance = null;

    public static synchronized DataBase getInstance() throws SQLException {
        if (instance == null)
            instance = new DataBase();
        return instance;
    }

    private final Connection connection;

    private DataBase () throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
        try(Statement statement = this.connection.createStatement()){
            statement.execute(
                    "CREATE TABLE users (" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "   name VARCHAR(40),\n" +
                    "   country VARCHAR(20),\n" +
                    "   street VARCHAR(70),\n" +
                    "   phone VARCHAR(15),\n" +
                    "   city VARCHAR(50),\n" +
                    "   region VARCHAR(50),\n" +
                    "   district VARCHAR(50),\n" +
                    "   ZIPCode VARCHAR(10),\n" +
                    "   printFormat VARCHAR(10),\n" +
                    "   leafCount INTEGER,\n" +
                    "   payer TEXT,\n" +
                    "   subscriptionStartDate LONG,\n" +
                    "   subscriptionFinishDate LONG);");
        }catch (SQLiteException ignored){}
    }
    public List<User> getAllUser(){
        try (Statement statement = this.connection.createStatement()){
            List<User> users = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT id , name , country , street , phone , city , region , district , ZIPCode , printFormat , leafCount , payer , subscriptionStartDate , subscriptionFinishDate FROM users;");
            resultSetToListUsers(users, resultSet);
            return users;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void resultSetToListUsers(List<User> users, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            users.add(new User(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("country"),
                    resultSet.getString("street"),
                    resultSet.getString("phone"),
                    resultSet.getString("city"),
                    resultSet.getString("region"),
                    resultSet.getString("district"),
                    resultSet.getString("ZIPCode"),
                    resultSet.getString("printFormat"),
                    resultSet.getInt("leafCount"),
                    resultSet.getString("payer"),
                    LocalDate.ofEpochDay(resultSet.getLong("subscriptionStartDate")),
                    LocalDate.ofEpochDay(resultSet.getLong("subscriptionFinishDate"))
            ));
        }
    }

    public void addUser (User user){
        try(PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (name , country , street , phone , city , " +
                        "region , district , ZIPCode , printFormat , leafCount , payer , " +
                        "subscriptionStartDate , subscriptionFinishDate)" +
                        " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);")) {
            statement.setObject(1,user.getName());
            statement.setObject(2,user.getCountry());
            statement.setObject(3,user.getStreet());
            statement.setObject(4,user.getPhone());
            statement.setObject(5,user.getCity());
            statement.setObject(6,user.getRegion());
            statement.setObject(7,user.getDistrict());
            statement.setObject(8,user.getZIPCode());
            statement.setObject(9,user.getPrintFormat());
            statement.setObject(10,user.getLeafCount());
            statement.setObject(11,user.getPayer());
            statement.setObject(12,user.getSubscriptionStartDate().toEpochDay());
            statement.setObject(13,user.getSubscriptionFinishDate().toEpochDay());

            statement.execute();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUser(User user){
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?;")){
            statement.setObject(1,user.getId());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateUser(User user){
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET name = ? , country = ? , street = ? , phone = ? ," +
                        " city = ?, region = ? , district = ? , ZIPCode = ? , printFormat = ? ," +
                        " leafCount = ? , payer = ? , subscriptionStartDate = ?, subscriptionFinishDate = ? WHERE id = ?;"
        )){
            statement.setObject(1,user.getName());
            statement.setObject(2,user.getCountry());
            statement.setObject(3,user.getStreet());
            statement.setObject(4,user.getPhone());
            statement.setObject(5,user.getCity());
            statement.setObject(6,user.getRegion());
            statement.setObject(7,user.getDistrict());
            statement.setObject(8,user.getZIPCode());
            statement.setObject(9,user.getPrintFormat());
            statement.setObject(10,user.getLeafCount());
            statement.setObject(11,user.getPayer());
            statement.setObject(12,user.getSubscriptionStartDate().toEpochDay());
            statement.setObject(13,user.getSubscriptionFinishDate().toEpochDay());
            statement.setObject(14,user.getId());

            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getMaxId(){
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT seq FROM sqlite_sequence WHERE name = \"users\";");
            return resultSet.getInt("seq");
        } catch (SQLException throwables) {
            return 0;
        }
    }

    public List<User> getAllCondition(String condition){
        try (Statement statement = connection.createStatement()){
            List<User> users = new ArrayList<>();
            String sql = "SELECT id , name , country ," +
                    " street , phone , city , region , district ," +
                    " ZIPCode , printFormat , leafCount , payer ," +
                    " subscriptionStartDate , subscriptionFinishDate" +
                    " FROM users" +
                    " WHERE " + condition + " ;";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSetToListUsers(users, resultSet);
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<User> getSearchToId(String id){
        try (Statement statement = connection.createStatement()){
            List<User> users = new ArrayList<>();
            ResultSet result = statement.executeQuery("SELECT id , name , country ," +
                    " street , phone , city , region , district , ZIPCode , printFormat , leafCount , payer" +
                    " , subscriptionStartDate , subscriptionFinishDate FROM users WHERE id LIKE '%"+id+"%';");
            resultSetToListUsers(users, result);
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<User> getSearchToName(String name){
        try (Statement statement = connection.createStatement()){
            List<User> users = new ArrayList<>();
            ResultSet result = statement.executeQuery("SELECT id , name , country ," +
                    " street , phone , city , region , district , ZIPCode , printFormat , leafCount , payer" +
                    " , subscriptionStartDate , subscriptionFinishDate FROM users WHERE name LIKE '%"+name+"%';");
            resultSetToListUsers(users, result);
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<User> getSearchToCountry(String country){
        try (Statement statement = connection.createStatement()){
            List<User> users = new ArrayList<>();
            ResultSet result = statement.executeQuery("SELECT id , name , country ," +
                    " street , phone , city , region , district , ZIPCode , printFormat , leafCount , payer" +
                    " , subscriptionStartDate , subscriptionFinishDate FROM users WHERE country LIKE '%"+country+"%';");
            resultSetToListUsers(users, result);
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<User> getSearchToZIPCode(String ZIPCode) {
        try (Statement statement = connection.createStatement()){
            List<User> users = new ArrayList<>();
            ResultSet result = statement.executeQuery("SELECT id , name , country ," +
                    " street , phone , city , region , district , ZIPCode , printFormat , leafCount , payer" +
                    " , subscriptionStartDate , subscriptionFinishDate FROM users WHERE ZIPCode LIKE '%"+ZIPCode+"%';");
            resultSetToListUsers(users, result);
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<User> getSearchToStreet(String street){
        try (Statement statement = connection.createStatement()){
            List<User> users = new ArrayList<>();
            ResultSet result = statement.executeQuery("SELECT id , name , country ," +
                    " street , phone , city , region , district , ZIPCode , printFormat , leafCount , payer" +
                    " , subscriptionStartDate , subscriptionFinishDate FROM users WHERE street LIKE '%"+street+"%';");
            resultSetToListUsers(users, result);
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<User> getSearchToCity(String city){
        try (Statement statement = connection.createStatement()){
            List<User> users = new ArrayList<>();
            ResultSet result = statement.executeQuery("SELECT id , name , country ," +
                    " street , phone , city , region , district , ZIPCode , printFormat , leafCount , payer" +
                    " , subscriptionStartDate , subscriptionFinishDate FROM users WHERE city LIKE '%"+city+"%';");
            resultSetToListUsers(users, result);
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }


}
