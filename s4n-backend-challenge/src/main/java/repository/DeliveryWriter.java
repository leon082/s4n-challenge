package repository;

import utils.Constants;

import java.io.*;

public class DeliveryWriter {


    public void writeFile(String fileName, String log) {

        File file = new File(Constants.PATH_FILES + fileName);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)))) {
            writer.write(log + "\n");
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}
