package com.ratelimiter.util.rule;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties (prefix = "rules")
public class RuleProperties {
    private List<Rule> keyList; //key
    private List<Rule> epList; //endpoint
    private Map<String, Rule> defaults;

    public Map<String, Rule> getDefaults() {
        return defaults;
    }

    public void setDefaults(Map<String, Rule> defaults) {
        this.defaults = defaults;
    }

    public List<Rule> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<Rule> keyList) {
        this.keyList = keyList;
    }

    public List<Rule> getEpList() {
        return epList;
    }

    public void setEpList(List<Rule> epList) {
        this.epList = epList;
    }

    @Override
    public String toString() {
        return "Rules{" +
                "keyList=" + keyList +
                ", epList=" + epList +
                ", defaults=" + defaults +
                '}';
    }
}
