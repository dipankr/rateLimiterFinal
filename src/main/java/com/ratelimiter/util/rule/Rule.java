package com.ratelimiter.util.rule;

//POJO to hold a rule object
public class Rule {
    private String name;
    private String on;
    private Integer time;
    private String unit;
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "name='" + name + '\'' +
                ", on='" + on + '\'' +
                ", time=" + time +
                ", unit='" + unit + '\'' +
                ", value=" + value +
                '}';
    }
}
