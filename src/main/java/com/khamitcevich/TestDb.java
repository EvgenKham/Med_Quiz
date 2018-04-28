package com.khamitcevich;

import com.khamitcevich.model.entitiesDB.User;
import com.khamitcevich.model.exception.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDb {
    public static void main(String[] args) throws DBSystemException, SQLException {

//        UserDao dao = new UserDaoJdbc();
//
//        System.out.println("All Current Users: ");
//        for (User us: dao.selectAll()) {
//            System.out.println("  " + us.toString());
//        }
//
//        System.out.println("Delete: ");
//        for (User us: dao.selectAll()) {
//            dao.deleteById(us.getId());
//            System.out.println("  User with id = " + us.getId() + " delete");
//        }

//        System.out.println("Insert new ");
//        User user1 = new User(4);
//        user1.newUser("Evgeny", "ktu", "evgeny@gmail.com", 3221);
//        User user2 = new User(5);
//        user2.newUser("Petr", "qwerty", "petr@mail.ru", 4332);

//        List<User> user = new ArrayList<User>();
//        user.add(user1);
//        user.add(user2);

//        try {
//            int countAddedUser [] = dao.insert(user);
//        } catch (NotUniqueUserLoginException e) {
//            e.printStackTrace();
//        } catch (NotUniqueUserPasswordException e) {
//            e.printStackTrace();
//        } catch (NotUniqueUserEmailException e) {
//            e.printStackTrace();
//        } catch (NotUniqueUserTimesheetNumberException e) {
//            e.printStackTrace();
//        }

//        try {
//            int newId = dao.insert(user1);
////            dao.insert(user2);
//            System.out.println("newId = " + newId);
//            System.out.println("   Users inserted");
//        } catch (NotUniqueUserNameException e) {
//            e.printStackTrace();
//        } catch (NotUniqueUserEmailException e) {
//            e.printStackTrace();
//        }
    }
}
