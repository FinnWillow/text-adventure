package studio.paperwing.text_adventure.JavaSimpleContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class JscWriter {
    private String LOCAL_DATA;
    private String ending;

    public JscWriter(String dataPackage, String ending) {
        LOCAL_DATA = System.getProperty("user.home") + dataPackage;
        this.ending = ending;
    }

    private String buildData(JscGroup parent, int depth) {
        StringBuffer buffer = new StringBuffer();
        String tab = " ".repeat(depth * 4);
        for (JscPair pair : parent.getPairs()) {
            buffer.append(tab + pair.getKey() + " = " + pair.getValue().replaceAll("\\n", "\\\\n\n") + ";\n");
        }

        for (JscGroup child : parent.getChildren()) {
            buffer.append("\n");
            buffer.append(tab + child.getID() + " {\n");
            buffer.append(buildData(child, depth + 1));
            buffer.append(tab + "}\n");
        }

        return buffer.toString();
    }

    public boolean parseGroup(String directory, JscGroup root) {
        File dir = new File(LOCAL_DATA + directory);
        File file = new File(LOCAL_DATA + directory + "/" + root.getID() + ending);
        try {
            if (!file.exists()) {
                dir.mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileOutputStream output = new FileOutputStream(file)) {
            String data = buildData(root, 0);
            output.write(data.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // if any error occurs it returns null.
        return false;
    }
}