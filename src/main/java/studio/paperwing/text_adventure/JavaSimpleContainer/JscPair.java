package studio.paperwing.text_adventure.JavaSimpleContainer;

public class JscPair {
    private String key;
    private String value;

    /* CTORS --------------------------------------- */
    
    public JscPair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public JscPair copy() {
        return new JscPair(key, value);
    }

    /* METHODS -------------------------------------- */

    public int parse(String[] data, int start) {
        StringBuffer build = new StringBuffer();
        int wordIndex = start;
        while (wordIndex < data.length) {
            // get stripped word
            String word = data[wordIndex].strip();
            
            // if word is ; skip it, build the value, and continue.
            if (word.equals(";")) {
                wordIndex++; // skip ;
                
                // break early to return wordIndex
                break;
            }

            if (word.equals("\\n")) {
                build.append("\n");
                wordIndex++;
                continue;
            }

            build.append(word + " ");
            wordIndex++;
        }

        value = build.toString().strip();

        return wordIndex;
    }

    /* GETTERS -------------------------------------- */

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    /* SETTERS -------------------------------------- */

    public JscPair setKey(String key) {
        this.key = key;
        return this;
    }

    public JscPair setValue(String value) {
        this.value = value;
        return this;
    }
}
