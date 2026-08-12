package studio.paperwing.text_adventure.JavaSimpleContainer;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class JscReader {
    private String LOCAL_DATA;
    private String ending;

    public JscReader(String dataPackage, String ending) {
        LOCAL_DATA = System.getProperty("user.dir") + dataPackage;
        this.ending = ending;
    }

    public List<File> parseDirectory(String dir) {
        try {
            // get the folder 
            File directory = new File(LOCAL_DATA + dir);
            
            // list the directory files
            File[] files = directory.listFiles();
            
            // find the files with the specified ending
            List<File> jscFiles = new ArrayList<>();
            for (File file : files) {
                if (file.getName().endsWith(ending)) {
                    jscFiles.add(file);
                }
            }

            // if no file was found return null
            if (jscFiles.size() == 0) {
                return null;
            }

            // otherwise return the files.
            return jscFiles;
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        } catch (SecurityException e) {
            throw new SecurityException(e);
        }
    }

    public JscGroup parseData(File file) {
        // make group ID
        String groupID = file.getName().substring(0, file.getName().lastIndexOf("."));

        // create a JSCGroup with the groupID
        JscGroup group = new JscGroup(groupID);
        
        // parse the file then format the returned string.
        String[] data = parseFile(file)
            .strip()
            .replaceAll("[\r\n]+", " ")
            .replaceAll("( )+", " ")
            .split(" ");

        // parse the data into the group, and return it.
        group.parse(data, 0);
        return group;
    }

    private String parseFile(File file) {
        try (FileInputStream input = new FileInputStream(file)) {
            // make a buffer to store the data
            StringBuffer sBuffer = new StringBuffer();
            
            // build string data.
            int i;
            while ((i = input.read()) != -1) {
                char chara = (char)i;
                
                if (chara == '{' || chara == '}' || chara == '=' || chara == ';') {
                    // place some spaces around special chars so they get seen as separate words
                    sBuffer.append(" " + chara + " ");
                } else if (chara == '\\') {
                    // also pad out all escape characters.
                    sBuffer.append(" \\");
                    i = input.read();
                    chara = (char)i;
                    sBuffer.append(chara + " ");
                } else {
                    // otherwise build the text as is.
                    sBuffer.append(chara);
                }
            }

            // once done return the buffer as a string.
            return sBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // if any error occurs it returns null.
        return null;
    }
}
