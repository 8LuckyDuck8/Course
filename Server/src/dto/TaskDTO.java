package dto;

import model.Goal;

public class TaskDTO {
    private Goal goal;
    private Integer mark;
    private String userLogin;

    public TaskDTO() {
    }

    public TaskDTO(Goal goal, String userLogin) {
        this.goal = goal;
        this.userLogin = userLogin;
    }

    public TaskDTO(Goal goal, Integer mark, String userLogin) {
        this.goal = goal;
        this.mark = mark;
        this.userLogin = userLogin;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
