package controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 05/01/2015.
 */
public class Picture {

    public List<String> getPictures(String user) throws IOException {
        List<String> files = new ArrayList<>();
        Files.walk(Paths.get("/uploads/"+user),2).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                files.add(filePath.toString());
            }
        });
        return files;
    }
}
