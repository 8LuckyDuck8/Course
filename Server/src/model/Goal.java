package model;

public class Goal {
    private Integer id;
    private String name;

    public Goal() {
    }

    public Goal(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Goal(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
