package studio.paperwing.text_adventure.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Decision {
    private String id;
    private String description; // gets printed to the console when decision called.
    
    // responses that the user can make where key = what to write and value = what is written as info to the user.
    private HashMap<String, String> responses = new HashMap<>();

    // actions that each response does when taken, where key = what the user wrote and value = what it does.
    private HashMap<String, List<Action>> actionMap = new HashMap<>();

    public Decision(String id) {
        this.id = id;
    }

    public Decision copy() {
        Decision copy = new Decision(id);
        copy.description = description;
        copy.actionMap = new HashMap<>(actionMap);
        copy.responses = new HashMap<>(responses);
        return copy;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void insertAction(String rid, Action act) {
        if (actionMap.get(rid) == null) {
            List<Action> actions = new ArrayList<>();
            actions.add(act);
            actionMap.put(rid, actions);
        } else {
            actionMap.get(rid).add(act);
        }
    }

    public boolean runActions(String rid) {
        for (String mapRid : actionMap.keySet()) {
            if (mapRid.equals(rid)) {
                for (Action act : actionMap.get(rid)) {
                    act.run();
                }

                return true;
            }
        }

        return false;
    }

    public void inserResponse(String rid, String tell) {
        responses.put(rid, tell);
    }

    public List<Action> getActions(String rid) {
        return actionMap.get(rid);
    }

    public HashMap<String, List<Action>> getActionMap() {
        return actionMap;
    }

    public String getResponse(String rid) {
        return responses.get(rid);
    }

    public HashMap<String, String> getResponses() {
        return responses;
    }

    public Decision setDescription(String description) {
        this.description = description;
        return this;
    }

    public Decision setActionMap(HashMap<String, List<Action>> actions) {
        this.actionMap = actions;
        return this;
    }

    public Decision setResponses(HashMap<String, String> responses) {
        this.responses = responses;
        return this;
    }
}
