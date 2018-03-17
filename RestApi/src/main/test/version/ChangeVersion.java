package version;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeVersion {
    private final String REGEX_VERSION = "private final String VERSION = \"(\\d+).(\\d+)";
    private final String REGEX_REPLACE = "private final String VERSION = \"";
    String fileName;

    @Before
    public void setup() {
        fileName = "src\\main\\java\\com\\apon\\service\\VersionService.java";
    }

    @Test
    public void nextSubVersion() {
        writeNewVersionToFile(fileName, false);
    }

    @Test
    public void nextVersion() {
        writeNewVersionToFile(fileName, true);
    }

    private void writeNewVersionToFile(String fileName, boolean updateVersion) {
        // Variables for files.
        String tmpFileName = fileName + "_tmp";
        BufferedReader br = null;
        BufferedWriter bw = null;

        // Variable for regex.
        Pattern pattern = Pattern.compile(REGEX_VERSION);
        Matcher matcher;

        try {
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(tmpFileName));
            String line;

            while ((line = br.readLine()) != null) {
                if (line.matches(".*" + REGEX_VERSION + ".*")) {
                    // We do our magic
                    Integer version = null, subVersion = null;
                    matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        version = Integer.valueOf(matcher.group(1));
                        subVersion = Integer.valueOf(matcher.group(2));
                    }

                    if (updateVersion) {
                        subVersion = 0;
                        version++;
                    } else {
                        subVersion++;
                    }

                    // Replace the content of the line.
                    String newVersion = version.toString() + "." + subVersion.toString();
                    line = line.replaceAll(REGEX_VERSION, REGEX_REPLACE + newVersion);
                    System.out.println("New version: " + newVersion);
                }

                bw.write(line+"\n");
            }
        } catch (Exception e) {
            return;
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException e) {
                //
            }
            try {
                if(bw != null)
                    bw.close();
            } catch (IOException e) {
                //
            }
        }
        // Once everything is complete, delete old file..
        File oldFile = new File(fileName);
        oldFile.delete();

        // And rename tmp file's name to old file name
        File newFile = new File(tmpFileName);
        newFile.renameTo(oldFile);
    }

}
