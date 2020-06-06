package dto;

import Account.Goal;

public class TaskDTO {
    private Goal goal;
    private String mark;
    private String userLogin;

    public TaskDTO(Goal goal, String mark, String userLogin) {
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
