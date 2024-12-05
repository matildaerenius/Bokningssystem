package data;

import models.User;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataManager {
    private static UserDataManager instance;
    private final ConcurrentHashMap<String, User> users;
    private static final String FILE_PATH = "src/data/userdata.csv";

    private UserDataManager() {
        users = new ConcurrentHashMap<>();
        loadUsersFromFile();
    }

    public static synchronized UserDataManager getInstance() {
        if (instance == null) {
            instance = new UserDataManager();
        }
        return instance;
    }

    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine(); // Skippar första raden
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    User user = new User(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    users.put(user.getId(), user);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("ID,Name,Email,Phonenumber,Password\n");
            for (User user : users.values()) {
                writer.write(String.format("%s,%s,%s,%s,%s\n",
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhonenumber(),
                        user.getPassword()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving users to file", e);
        }
    }

    public boolean registerUser(User newUser) {
        if (users.containsKey(newUser.getId())) {
            return false; // User finns redan med det personnumret
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.format("%s,%s,%s,%s,%s\n",
                    newUser.getId(),
                    newUser.getName(),
                    newUser.getEmail(),
                    newUser.getPhonenumber(),
                    newUser.getPassword()));
        } catch (IOException e) {
            return false;
        }

        users.put(newUser.getId(), newUser);
        return true;
    }

    public User authenticateUser(String id, String password) {
        User user = users.get(id);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }

}
