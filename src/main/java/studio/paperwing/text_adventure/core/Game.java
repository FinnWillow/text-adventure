package studio.paperwing.text_adventure.core;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import studio.paperwing.text_adventure.JavaSimpleContainer.JscGroup;
import studio.paperwing.text_adventure.JavaSimpleContainer.JscPair;
import studio.paperwing.text_adventure.JavaSimpleContainer.JscReader;

public class Game {
    private static HashMap<String, Decision> decisionMap = new HashMap<>();
    private static HashMap<String, Item> itemMap = new HashMap<>();
    private static HashMap<String, Entity> entityMap = new HashMap<>();

    private static Scanner scan;
    private static JscReader reader = new JscReader("/src/main/resources/gameData", ".jsc");
    // private static JscWriter writer = new JscWriter("/text-adventure", ".jsc");
    
    private static boolean created = false;
    private static boolean closeGame = false;
    private static Decision dec = null;

    public static void create() {
        if (created) {
            throw new IllegalStateException("Game already exists.");
        }

        // initialize the game
        scan = new Scanner(System.in);

        // TEMP: parse decisions
        parseDecisions();

        // raise created flag.
        created = true;
    }

    private static void parseDecisions() {
        // get files in the /decisions folder.
        List<File> files = reader.parseDirectory("/decisions");
        for (File file : files) {
            // get data from the file
            JscGroup root = reader.parseData(file);

            // create the decision
            Decision decision = new Decision(root.getID());

            // parse the description tag and put its value into the description.
            decision.setDescription(root.getPair("description").getValue());

            // parse the response
            JscGroup grResponses = root.getChild("response");
            for (JscPair prResponse : grResponses.getPairs()) {
                decision.inserResponse(prResponse.getKey(), prResponse.getValue());
            }

            // parse the action
            JscGroup grActions = root.getChild("action");
            for (JscPair prAction : grActions.getPairs()) {
                Action action = new Action(prAction.getValue());
                decision.insertAction(prAction.getKey(), action);
            }

            // put decision in the map
            decisionMap.put(decision.getId(), decision);
        }
    }

    public static void destroy() {
        if (!created) {
            throw new IllegalStateException("No game exists that can be destroyed.");
        }

        // destroy everything
        decisionMap.clear();
        itemMap.clear();
        entityMap.clear();
        scan.close();

        // lower the created flag, so another game can start after it.
        created = false;
    }

    public static void closeGame() {
        if (!created) {
            System.out.println("There is no game to close.");
        }
        closeGame = true;
    }

    public static boolean goTo(String decID) {
        boolean contains = decisionMap.containsKey(decID);
        
        if (contains) {
            dec = decisionMap.get(decID);
        }

        return contains;
    }

    public static void start(String startingDec) {
        if (!created) {
            create();
        } else {
            System.out.println("A running game already exists.");
        }

        goTo(startingDec);

        while (closeGame == false) {
            System.out.println(dec.getDescription());

            System.out.println("-".repeat(16));
        
            for (String rid : dec.getResponses().keySet()) {
                System.out.println(rid + " = " + dec.getResponse(rid));
            }

            System.out.println("-".repeat(16));
            System.out.print("> ");

            String rid = scan.nextLine().strip();
            if (rid.equals("exit")) {
                closeGame();
                break;
            }

            if (!dec.runActions(rid)) {
                System.out.printf("The action \"%s\" is unknown.\n", rid);
            }
        }

        System.out.println("Bye bye!");
        destroy();
    }
}
