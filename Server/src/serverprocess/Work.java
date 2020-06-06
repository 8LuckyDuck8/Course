package serverprocess;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.GoalToWeightDTO;
import dto.TaskDTO;
import model.Account;
import model.Goal;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static Database.Database.*;


public class Work extends Thread {
    private static Socket cl = null;
    String AUTHORIZATION = "authorization";
    String ADD_USER = "AddUser";
    String ADD_GOAL = "AddGoal";
    String GET_ALL_GOALS = "GetAllGoals";
    String GET_ALL_USERS = "GetAllUsers";
    String DELETE_USER = "DeleteUser";
    String DELETE_GOAL = "DeleteGoal";
    String REDACT_USER = "RedactUser";
    String GET_ALL_MARKS_BY_USER = "GetAllMarksByUser";
    String SAVE_ALL_MARKS = "SaveAllMarks";
    String GET_GOALS_WEIGHT = "GetGoalsWeight";
    String GET_MAIN_GOAL = "GetMainGoal";
    String GET_MARKS_COUNT = "GetMarksCount";
    String GET_ALL_MARKS_TO_USERS = "GetAllMarksToUsers";
    private static String END = "End";
    ArrayList<String> resultSplit;
    BufferedReader sin;
    PrintStream sout;

    public Work(Socket cl) {
        this.cl = cl;
    }

    public void run() {
        try {
            sin = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            sout = new PrintStream(cl.getOutputStream());

            while (true) {
                String input;
                input = sin.readLine();
                if (END.equals(input)) {
                    break;
                } else if (AUTHORIZATION.equals(input)) {
                    userAuthorization();
                } else if (ADD_USER.equals(input)) {
                    addNewUser();
                } else if (ADD_GOAL.equals(input)) {
                    addNewGoal();
                } else if (GET_ALL_USERS.equals(input)) {
                    getAllUsers();
                } else if (DELETE_USER.equals(input)) {
                    deleteUser();
                } else if (DELETE_GOAL.equals(input)) {
                    deleteGoal();
                } else if (GET_ALL_GOALS.equals(input)) {
                    getAllGoals();
                } else if (REDACT_USER.equals(input)) {
                    redactUser();
                } else if (GET_ALL_MARKS_BY_USER.equals(input)) {
                    getAllMarksByUser();
                } else if (SAVE_ALL_MARKS.equals(input)) {
                    saveAllMarks();
                } else if (GET_GOALS_WEIGHT.equals(input)) {
                    List<GoalToWeightDTO> goalToWeight = getGoalToWeight();
                    sout.println(new Gson().toJson(goalToWeight));
                } else if (GET_MAIN_GOAL.equals(input)) {
                    sout.println(task());
                } else if (GET_MARKS_COUNT.equals(input)) {
                    Integer marksCount = getAllMarksCount();
                    sout.println(marksCount);
                } else if (GET_ALL_MARKS_TO_USERS.equals(input)) {
                    List<TaskDTO> allMarksToUsers = getAllMarksToUsers();
                    sout.println(new Gson().toJson(allMarksToUsers));
                }
            }
        } catch (SocketException ex) {
            System.out.println("Клиент отключился");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                sin.close();
                sout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ServerStats.connectionNumber--;
        }
    }

    void userAuthorization() {
        String outline = null;
        try {
            this.resultSplit = new ArrayList<>();
            String get = "";
            get = sin.readLine();
            Gson g = new Gson();
            Type Tip = new TypeToken<Account>() {
            }.getType();
            Account acc = g.fromJson(get, Tip);
            String statementAdminSql = "SELECT DISTINCT * FROM meat_db.user where user.login = '" + acc.getLogin() + "'" +
                    " and user.password = '" + acc.getPassword() + "'";
            openDatabase();
            ResultSet user = getDatabase(statementAdminSql);

            if (user.next()) {
                outline = user.getString("role");
            } else {
                outline = "false";
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        sout.println(outline);
    }

    private void addNewUser() throws IOException {
        String get;
        get = sin.readLine();
        Gson g = new Gson();
        Type Tip = new TypeToken<Account>() {
        }.getType();
        Account acc = g.fromJson(get, Tip);
        String sqlst = "INSERT INTO meat_db.user (login, password, role)" +
                " VALUES ('" + acc.getLogin() + "', '" + acc.getPassword() + "', '" + acc.getRole() + "')";
        openDatabase();
        execute(sqlst);
    }

    private void getAllUsers() throws SQLException {
        String sqlst = "SELECT * FROM meat_db.user";
        openDatabase();
        ResultSet resultSet = getDatabase(sqlst);
        ArrayList<Account> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            arrayList.add(new Account(resultSet.getString("login"), resultSet.getString("password"), resultSet.getString("role")));
        }
        String sent = new Gson().toJson(arrayList);
        sout.println(sent);
    }

    private void deleteUser() throws IOException, SQLException {
        String get;
        get = sin.readLine();
        String sqlst = "DELETE FROM meat_db.user WHERE (login = '" + get + "')";
        String sqlstId = "SELECT * FROM meat_db.user WHERE (login = '" + get  +"')";
        openDatabase();
        ResultSet resultSet = getDatabase(sqlstId);
        resultSet.next();
        String userId = resultSet.getString("id");
        String sqlstMark = "DELETE FROM meat_db.mark WHERE (user_id = '" + userId  +"')";
        execute(sqlstMark);
        execute(sqlst);
    }

    private void deleteGoal() throws IOException, SQLException {
        String get;
        get = sin.readLine();
        String sqlst = "DELETE FROM meat_db.goal WHERE (name = '" + get + "')";
        String sqlstId = "SELECT * FROM meat_db.goal WHERE (name = '" + get  +"')";
        openDatabase();
        ResultSet resultSet = getDatabase(sqlstId);
        resultSet.next();
        String goalId = resultSet.getString("id");
        String sqlstMark = "DELETE FROM meat_db.mark WHERE (goal_id = '" + goalId  +"')";
        execute(sqlstMark);
        execute(sqlst);
    }

    private void getAllGoals() throws SQLException {
        String sqlst = "SELECT * FROM meat_db.goal";
        openDatabase();
        ResultSet resultSet = getDatabase(sqlst);
        ArrayList<Goal> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            arrayList.add(new Goal(resultSet.getString("name")));
        }
        String sent = new Gson().toJson(arrayList);
        sout.println(sent);
    }

    private void redactUser() throws IOException {
        String getl;
        String getp;
        getl = sin.readLine();
        getp = sin.readLine();
        String sqlst = "UPDATE meat_db.user SET password='" + getp + "' WHERE ( login ='" + getl + "')";
        openDatabase();
        execute(sqlst);
    }

    private void addNewGoal() throws IOException {
        String get;
        get = sin.readLine();
        Gson g = new Gson();
        Type Tip = new TypeToken<Goal>() {
        }.getType();
        Goal acc = g.fromJson(get, Tip);
        String sqlst = "INSERT INTO meat_db.goal (name)" +
                " VALUES ('" + acc.getName() + "')";
        openDatabase();
        execute(sqlst);
    }

    private void getAllMarksByUser() throws SQLException, IOException {
        String userLogin = sin.readLine();
        String getUserIdByLogin = "SELECT user.id FROM user where user.login = '" + userLogin + "'";
        openDatabase();
        ResultSet resultSet = getDatabase(getUserIdByLogin);
        resultSet.next();
        Integer userId = resultSet.getInt("id");
        String getMarksByUser = "SELECT * FROM mark, goal WHERE mark.goal_id = goal.id AND mark.user_id = " + userId;
        resultSet = getDatabase(getMarksByUser);
        List<TaskDTO> taskDTOS = new ArrayList<>();
        while (resultSet.next()) {
            Goal goal = new Goal(resultSet.getInt("id"), resultSet.getString("name"));
            TaskDTO taskDTO = new TaskDTO(goal, resultSet.getInt("mark"), userLogin);
            taskDTOS.add(taskDTO);
        }

        String nonMarkedGoals = getNonMarkedGoals(taskDTOS);
        String getAllTasksWithoutMark="";
        if(nonMarkedGoals != null) {
            getAllTasksWithoutMark = "SELECT * FROM goal WHERE id NOT IN (" + nonMarkedGoals + ")";
        }else {
            getAllTasksWithoutMark = "SELECT * FROM goal";
        }
        resultSet = getDatabase(getAllTasksWithoutMark);
        List<TaskDTO> taskWoMark = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.next()) {
                Goal goal = new Goal(resultSet.getInt("id"), resultSet.getString("name"));
                TaskDTO taskDTO = new TaskDTO(goal, userLogin);
                taskWoMark.add(taskDTO);
            }
        }

        taskDTOS.addAll(taskWoMark);

        sout.println(new Gson().toJson(taskDTOS));

    }

    private void saveAllMarks() throws IOException, SQLException {
        openDatabase();
        String request = sin.readLine();
        List<TaskDTO> taskDTOS = new Gson().fromJson(request, new TypeToken<List<TaskDTO>>() {
        }.getType());
        TaskDTO taskDTO = taskDTOS.get(0);
        String userLogin = taskDTO.getUserLogin();
        String findUserByLogin = "SELECT * FROM meat_db.user WHERE login = '" + userLogin + "'";
        ResultSet resultSet = getDatabase(findUserByLogin);
        resultSet.next();
        String userId = resultSet.getString("id");
        for (TaskDTO task : taskDTOS) {
            Integer goalId = task.getGoal().getId();
            String sqlQuery = "SELECT * FROM meat_db.mark WHERE user_id = " + userId + " AND goal_id = " + goalId + "";
            resultSet = getDatabase(sqlQuery);
            if (resultSet.next()) {
                sqlQuery = "UPDATE meat_db.mark SET mark = " + task.getMark() + " WHERE user_id = " + userId + " AND goal_id = " + goalId;
            } else {
                sqlQuery = "INSERT INTO meat_db.mark (user_id, goal_id, mark) VALUES (" + userId + ", " + goalId + ", " + task.getMark() + ")";
            }
            execute(sqlQuery);
        }
    }

    private String getNonMarkedGoals(List<TaskDTO> taskDTOS) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (TaskDTO task : taskDTOS) {
            stringJoiner.add(task.getGoal().getId().toString());
        }
        if(taskDTOS.isEmpty()){
            return null;
        }
        return stringJoiner.toString();
    }

    public String task() throws SQLException {
        openDatabase();
        List<GoalToWeightDTO> goalToWeight = getGoalToWeight();

        List<Float> weights = new ArrayList<>();
        for (GoalToWeightDTO entry : goalToWeight) {
            weights.add(entry.getWeight());
        }

        Collections.sort(weights);
        Float mainGoal = weights.get(weights.size() - 1);
        return getMainGoalName(mainGoal, goalToWeight);

    }

    private String getMainGoalName(Float mainGoal, List<GoalToWeightDTO> goalToWeight) throws SQLException {
        Goal main = new Goal();
        ListIterator<GoalToWeightDTO> iterator = goalToWeight.listIterator();
        while(iterator.hasNext()){
            if(iterator.next().getWeight().equals(mainGoal)){
                main = iterator.previous().getGoal();
                break;
            }
        }
      /*  for (GoalToWeightDTO goalToWeightDTO : goalToWeight) {
            if (goalToWeightDTO.getWeight().equals(mainGoal)) {
                main = goalToWeightDTO.getGoal();
                break;
            }
        }*/
        openDatabase();
        String sqlQuery = "SELECT * FROM meat_db.goal WHERE id = " + main.getId();
        ResultSet database = getDatabase(sqlQuery);
        database.next();
        return database.getString("name");
    }

    public List<GoalToWeightDTO> getGoalToWeight() throws SQLException {
        String sqlQuery = "SELECT DISTINCT m.goal_id FROM mark m " +
                "INNER JOIN goal g ON m.goal_id = g.id";
        ResultSet resultSet = getDatabase(sqlQuery);
        int marksCount = getMarksCount();
        int usersCount = getUsersCount();
        int allMarksSum = getlAllMarksSum(marksCount);
        List<GoalToWeightDTO> goalToWeight = new ArrayList<>();
        while (resultSet.next()) {
            String goal_id = resultSet.getString("goal_id");
            sqlQuery = "SELECT m.user_id, m.goal_id, g.name, m.mark from mark m " +
                    "INNER JOIN goal g ON g.id = m.goal_id " +
                    "WHERE m.goal_id = " + goal_id;
            ResultSet marksResult = getDatabase(sqlQuery);
            int[] goalMarks = new int[usersCount];
            Arrays.fill(goalMarks, 0);
            int j = 0;
            while (marksResult.next()) {
                goalMarks[j] = marksResult.getInt("mark");
                j++;
            }
            for (int k = 0; k < usersCount; k++) {
                goalMarks[k] = marksCount - goalMarks[k];
            }
            int sum = 0;

            for (int k = 0; k < usersCount; k++) {
                sum += goalMarks[k];
            }

            sqlQuery = "SELECT * FROM meat_db.goal WHERE id = " + goal_id;
            ResultSet database = getDatabase(sqlQuery);
            database.next();

            GoalToWeightDTO toWeightDTO = new GoalToWeightDTO(new Goal(database.getInt("id"),
                    database.getString("name")), (float) sum / allMarksSum);
            goalToWeight.add(toWeightDTO);
        }
        return goalToWeight;
    }

    private int getMarksCount() throws SQLException {
        String sqlQuery = "SELECT count(m.goal_id) count FROM mark m " +
                "INNER JOIN goal g ON m.goal_id = g.id;";
        ResultSet resultSet = getDatabase(sqlQuery);
        resultSet.next();
        return resultSet.getInt("count");
    }

    private int getAllMarksCount() throws SQLException {
        String sqlQuery = "SELECT count(distinct g.id) count FROM goal g";
        ResultSet resultSet = getDatabase(sqlQuery);
        resultSet.next();
        return resultSet.getInt("count");
    }

    private int getUsersCount() throws SQLException {
        String sqlQuery = "SELECT count(distinct m.user_id) count FROM mark m " +
                "INNER JOIN user u ON m.user_id = u.id " +
                "WHERE u.role = 'user'";
        ResultSet resultSet = getDatabase(sqlQuery);
        resultSet.next();
        return resultSet.getInt("count");
    }

    private List<TaskDTO> getAllMarksToUsers() throws SQLException {
        openDatabase();
        String sqlQuery = "SELECT * FROM meat_db.mark";
        ResultSet resultSet = getDatabase(sqlQuery);
        List<TaskDTO> taskDTOList = new ArrayList<>();
        while (resultSet.next()) {
            TaskDTO taskDTO = new TaskDTO();
            String user_id = resultSet.getString("user_id");
            String goal_id = resultSet.getString("goal_id");
            String mark = resultSet.getString("mark");

            String userQuery = "SELECT * FROM meat_db.user WHERE id = " + user_id;
            ResultSet userResult = getDatabase(userQuery);
            userResult.next();
            taskDTO.setUserLogin(userResult.getString("login"));

            String goalQuery = "SELECT * FROM meat_db.goal WHERE id = " + goal_id;
            ResultSet goalResult = getDatabase(goalQuery);
            goalResult.next();
            taskDTO.setGoal(new Goal(goalResult.getInt("id"), goalResult.getString("name")));

            taskDTO.setMark(Integer.valueOf(mark));

            taskDTOList.add(taskDTO);
        }
        return taskDTOList;
    }

    private int getlAllMarksSum(int allMarksCount) throws SQLException {
        openDatabase();
        String sqlQuery = "SELECT * FROM meat_db.mark";
        ResultSet resultSet = getDatabase(sqlQuery);
        int[] marks = new int[allMarksCount];
        Arrays.fill(marks, 0);
        int i = 0;
        while (resultSet.next()) {
            marks[i] = resultSet.getInt("mark");
            i++;
        }

        int sum = 0;
        for(int j = 0; j < marks.length; j++) {
            marks[j] = allMarksCount - marks[j];
            sum += marks[j];
        }

        return sum;
    }
}
