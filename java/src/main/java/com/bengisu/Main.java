package com.bengisu;

import com.bengisu.config.DataBaseConfig;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)
    {
        {
            //Tablo Oluşturma
            String sql = "CREATE TABLE IF NOT EXISTS users("+
                    "id SERIAL PRIMARY KEY,"+
                    "name VARCHAR(100),"+
                    "email VARCHAR(100))";

            try
            {
                //Veritabanı Bağlantısı
                Connection connection = DriverManager.getConnection(DataBaseConfig.DATABASE_URL, DataBaseConfig.DATABASE_USERNAME,
                        DataBaseConfig.DATABASE_PASSWORD);

                Statement statement = connection.createStatement();
                statement.execute(sql);
                System.out.println("Tablo Oluşturuldu.");

                //Veri Ekleme
                String insertSql = "INSERT INTO users (name, email) VALUES (?,?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertSql);
                insertStatement.setString(1,"Ali");
                insertStatement.setString(2,"ali@mail.com");
                insertStatement.executeUpdate();
                System.out.println("Kayıt Eklendi.");

                //Güncelleme
                String updateSql = "UPDATE users SET name = ? WHERE id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, "Ali Kaya");
                updateStatement.setInt(2, 1);
                int updatedRows = updateStatement.executeUpdate();
                System.out.println("Güncellenen satır sayısı: "+ updatedRows);

                //Silme
                String deleteSql = "DELETE FROM users WHERE id = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                deleteStatement.setInt(1, 2);
                int deletedRows = deleteStatement.executeUpdate();
                System.out.println("Silinen satır sayısı: "+ deletedRows);

                //Veri Listeleme
                String selectSql = "SELECT * FROM users";
                PreparedStatement preparedStatement1 = connection.prepareStatement(selectSql);
                ResultSet resultSet = preparedStatement1.executeQuery();
                while (resultSet.next())
                {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");

                    System.out.println("ID: "+ id + ", NAME: "+name + ",EMAIL: "+ email);
                }

                resultSet.close();
                preparedStatement1.close();
                insertStatement.close();
                updateStatement.close();
                deleteStatement.close();
                statement.close();
                connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}