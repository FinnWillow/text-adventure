package studio.paperwing.text_adventure.JavaSimpleContainer;

import java.util.ArrayList;
import java.util.List;

public class JscGroup {
    private String ID;
    private JscGroup parent = null;
    private List<JscGroup> children = new ArrayList<>();
    private List<JscPair> pairs = new ArrayList<>();

    /* CTORS --------------------------------------- */

    public JscGroup(String ID) {
        this.ID = ID;
    }

    public JscGroup copy() {
        JscGroup copy = new JscGroup(ID);
        copy.setPairs(pairs);

        return copy;
    }

    /* METHODS -------------------------------------- */

    public int parse(String[] data, int start) {
        StringBuffer build = new StringBuffer();
        int wordIndex = start;
        while (wordIndex < data.length) {
            // if reached the end of file, stop everything.
            if (wordIndex >= data.length) { 
                break;
            }

            // get formatted word.
            String word = data[wordIndex];

            if (word.equals("=")) {
                wordIndex++; // skip = sign

                // make the pair with no value yet
                JscPair pair = new JscPair(build.toString().strip(), ""); 

                // until the next ; collect value.
                wordIndex = pair.parse(data, wordIndex);

                // add it to the group pairs
                pairs.add(pair);

                // clear string build.
                build = new StringBuffer();

                // otherwise keep going.
                continue;
            }

            if (word.equals("{")) {
                wordIndex++; // skip starting {

                // make a new group
                JscGroup group = new JscGroup(build.toString().strip());
                
                // parse inner groups and pairs
                wordIndex = group.parse(data, wordIndex);

                // parent the new group
                group.parent = this;

                // add the group to the children list.
                children.add(group);

                // clear string build.
                build = new StringBuffer();

                // otherwise keep going.
                continue;
            }

            if (word.equals("}")) {
                wordIndex++; // skip ending }

                // break early to return the word index.
                break;
            }

            build.append(word + " ");
            
            wordIndex++;
        }

        return wordIndex;
    }

    /* GETTERS -------------------------------------- */

    public String getID() {
        return ID;
    }

    public List<JscPair> getPairs() {
        return pairs;
    }

    public JscPair getPair(String key) {
        for (JscPair pair : pairs) {
            if (pair.getKey().equals(key)) {
                return pair;
            }
        }

        return null;
    }

    public JscGroup getParent() {
        return parent;
    }

    public List<JscGroup> getChildren() {
        return children;
    }

    public JscGroup getChild(String ID) {
        for (JscGroup jscGroup : children) {
            if (jscGroup.getID().equals(ID)) {
                return jscGroup;
            }
        }

        return null;
    }

    /* SETTERS -------------------------------------- */

    public JscGroup setPairs(List<JscPair> pairs) {
        this.pairs = pairs;
        return this;
    }
}