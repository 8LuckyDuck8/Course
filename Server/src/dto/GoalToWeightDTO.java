package dto;

import model.Goal;

public class GoalToWeightDTO {
    private Goal goal;
    private Float weight;

    public GoalToWeightDTO(Goal goal, Float weight) {
        this.goal = goal;
        this.weight = weight;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
