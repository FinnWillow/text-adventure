package studio.paperwing.text_adventure.core;

public class Action {
    private String value;
    
    public Action(String value) {
        this.value = value;
    }

    public void run() {
        String key = value.split(" ")[0];
        String val = value.substring(value.indexOf(key) + key.length()).strip();

        switch (key) {
            case "CLOSE": {
                Game.closeGame();
                break;
            }

            case "SAY": {
                System.out.println(val);
                break;
            }

            case "GOTO": {
                if (!Game.goTo(val)) {
                    System.out.println("The decision " + val + " does not exist yet.");
                }
                break;
            }

            case "WAIT": {
                try {
                    Thread.sleep(Long.parseLong(val));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                break;
            }
        
            default: {
                System.out.println("The key " + key + " does not exist yet.");
                break;
            }
        }
    }
}
