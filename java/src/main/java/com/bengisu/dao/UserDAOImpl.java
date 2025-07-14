package com.bengisu.dao;

import com.bengisu.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO
{

    private final Connection connection;

    public UserDAOImpl(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public void save(User user)
    {
        String sql = "INSERT INTO users(name,age,email) VALUES (?,?,?)";

        try (PreparedStatement kaydet = connection.prepareStatement(sql)) {
            kaydet.setString(1, user.getName());
            kaydet.setInt(2, user.getAge());
            kaydet.setString(3, user.getEmail());

            int kayit = kaydet.executeUpdate();
            System.out.println(kayit + "Kayıt eklendi.");
        } catch (SQLException e)
        {
            throw new RuntimeException("Kayıt eklenemedi.");
        }
    }

    @Override
    public void findAll()
    {
        String sql = "SELECT * FROM users";

        try (PreparedStatement listele = connection.prepareStatement(sql))
        {
            ResultSet resultSet = listele.executeQuery();
            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");

                User user = new User(id, name, age, email);
                System.out.println(user);
            }
        }
        catch(SQLException e)
        {
            throw new RuntimeException("Kullanıcılar listelenemedi.");
        }
    }

    @Override
    public void update(User user)
    {
        String sql = "UPDATE users SET name = ?, age = ?, email = ? WHERE id = ?";

        try (PreparedStatement guncelle = connection.prepareStatement(sql))
        {
            guncelle.setString(1, user.getName());
            guncelle.setInt(2,user.getAge());
            guncelle.setString(3,user.getEmail());
            guncelle.setInt(4,user.getId());

            int guncel = guncelle.executeUpdate();
            if (guncel > 0)
            {
                System.out.println("Kullanıcı güncellendi."+user.getId());
            }
            else
            {
                System.out.println("Güncellenecek kullanıcı bulunamadı."+user.getId());
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Kullanıcı güncellenemedi: "+ e.getMessage());
        }
    }

    @Override
    public void delete(int id)
    {
        String sql = "DELETE FROM users WHERE id = ?";

        try(PreparedStatement sil = connection.prepareStatement(sql))
        {
            sil.setInt(1, id);

            int sil2 = sil.executeUpdate();
            if(sil2 > 0)
            {
                System.out.println("Kullanıcı silindi."+ id);
            }
            else
            {
                System.out.println("Silinecek kullanıcı bulunamadı: "+ id);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Kullanıcı silinemedi: "+e.getMessage());
        }
    }
}
