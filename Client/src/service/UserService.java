package service;

import Account.Goal;
import Account.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.GoalToWeightDTO;
import dto.TaskDTO;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final String ADMIN_ROLE = "admin";
    private static final String USER_ROLE = "user";

    public static UserService userService;

    private static Socket cl;
    private static PrintStream clout;
    private static BufferedReader clin;
    private static String userLogin;

    public static UserService getInstanceUser() {
        if (userService == null) {
            try {
                cl = new Socket(InetAddress.getLocalHost(), 8080);
                clout = new PrintStream(cl.getOutputStream());
                clin = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return userService;
    }

    public static String authorization(String login, String password) {
        String command = "authorization";
        User admin = new User(login, password, ADMIN_ROLE);
        try {
            clout.println(command);
            clout.println(new Gson().toJson(admin));
            return clin.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "false";
    }

    public static ArrayList<Account.User> getAllAccs() throws IOException {
        String getUserStatement = "GetAllUsers";
        clout.println(getUserStatement);
        String receive = clin.readLine();
        ArrayList<Account.User> arrayList = new Gson().fromJson(receive, new TypeToken<ArrayList<Account.User>>() {
        }.getType());
        return arrayList;
    }

    public static ArrayList<Goal> getAllGoals() throws IOException {
        String getAllGoals = "GetAllGoals";
        clout.println(getAllGoals);
        String receive = clin.readLine();
        ArrayList<Goal> arrayList = new Gson().fromJson(receive, new TypeToken<ArrayList<Account.Goal>>() {
        }.getType());
        return arrayList;
    }

    public static void addUser(Account.User acc) {
        String adst = "AddUser";
        clout.println(adst);
        String str = new Gson().toJson(acc);
        clout.println(str);
    }

    public static void addGoal(Goal acc) {
        String adst = "AddGoal";
        clout.println(adst);
        String str = new Gson().toJson(acc);
        clout.println(str);
    }

    public static void deleteUser(String str) {
        String delSelectedAcc = "DeleteUser";
        clout.println(delSelectedAcc);
        clout.println(str);
    }

    public static void deleteGoal(String str) {
        String delSelectedAcc = "DeleteGoal";
        clout.println(delSelectedAcc);
        clout.println(str);
        System.out.println(str);
    }

    public static void editPassword(String str, String log) {
        String editPassword = new String("RedactUser");
        clout.println(editPassword);
        clout.println(log);
        clout.println(str);
    }

    public static List<TaskDTO> getAllMarksByUser() throws IOException {
        String getAllMarksByUser = "GetAllMarksByUser";
        clout.println(getAllMarksByUser);
        clout.println(userLogin);
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<TaskDTO>>() {
        }.getType());
    }

    public static void setUserLogin(String userLogin) {
        UserService.userLogin = userLogin;
    }

    public static void saveAllMarks(ObservableList<TaskDTO> taskDTOS) throws IOException {
        String SaveAllMarks = "SaveAllMarks";
        clout.println(SaveAllMarks);
        String tasks = new Gson().toJson(taskDTOS);
        clout.println(tasks);
    }

    public static List<GoalToWeightDTO> getGoalsWeight() throws IOException {
        String GetGoalsWeight = "GetGoalsWeight";
        clout.println(GetGoalsWeight);
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<List<GoalToWeightDTO>>() {
        }.getType());
    }

    public static String getMainGoal() throws IOException {
        String GetMainGoal = "GetMainGoal";
        clout.println(GetMainGoal);
        return clin.readLine();
    }

    public static Integer getMarksCount() throws IOException {
        String GetMarksCount = "GetMarksCount";
        clout.println(GetMarksCount);
        return Integer.valueOf(clin.readLine());
    }

    public static List<TaskDTO> getAllMarksToUsers() throws IOException {
        String GetAllMarksToUsers = "GetAllMarksToUsers";
        clout.println(GetAllMarksToUsers);
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<List<TaskDTO>>() {
        }.getType());
    }
}

