import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.FileReader;

public class FileHandler {
    String inputFile;
    String dataFile;
    String gameWorldFile;

    public FileHandler() {
        inputFile = "simple_1.dat";
        dataFile = "player.dat";
        gameWorldFile = "world.dat";
    }

    public void createFile(String fileName) {
        try {
            // create a new file 
            File myFile = new File(fileName);
            myFile.createNewFile();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String readFile(String fileName) {
        String data = "";
        try {
            if(checkFileExists(fileName)){
                try {
                    FileReader myFile = new FileReader(fileName);
                    Scanner myReader = new Scanner(myFile);
                    while (myReader.hasNextLine()) {
                        data += myReader.nextLine() + "\n";
                    }
                    myFile.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (GameLevelNotFoundException e) {
            System.out.println(e);
        }
        return data;
    }

    public void writeFile(String fileName, String data) {
        try {
            // write data to a file
                FileWriter myWriter = new FileWriter(fileName);
                myWriter.write(data);
                myWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    } 

    public boolean checkFileExists(String fileName) throws GameLevelNotFoundException {
        File file = new File(fileName);
        if(file.exists() == false){
            throw new GameLevelNotFoundException("Map not found");
        }
        else {
            return true;
        }
    }

    public boolean checkPlayerFileExists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if(file.exists() == false){
            throw new FileNotFoundException("No player data found.");
        }
        else {
            return true;
        }
    }

    public String loadPlayerFile(String fileName){
        String data = "";
        try {
            FileReader myFile = new FileReader(fileName);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine() + "\n";
            }
            myFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }
}
