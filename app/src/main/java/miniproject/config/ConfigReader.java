package miniproject.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;




public final class ConfigReader {
    private List<List<String>> farmersParams;
    private List<List<String>> mallsParams;
    
    public ConfigReader(){
        Pattern commaDelimiter = Pattern.compile(",");
        try {
            this.farmersParams = readCSV(new File(Config.FARMER_DB_PATH), commaDelimiter);
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        try {
            this.mallsParams = readCSV(new File(Config.MALL_DB_PATH), commaDelimiter);
            
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }        
    }

    private List<List<String>> readCSV(File file, Pattern commaDelimiter) throws FileNotFoundException, IllegalArgumentException {
        List<List<String>> records = new ArrayList<>();
        
        int rowLength = -1;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                List<String> row = getRecordFromLine(scanner.nextLine(), commaDelimiter);
                if(rowLength == -1) {
                    rowLength = row.size();
                } else if(row.size() != rowLength) {
                    throw new IllegalArgumentException("Row length mismatch");
                }
                records.add(row);
                }
            }
        return records;
    }


    private List<String> getRecordFromLine(String line, Pattern commaDelimiter) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(commaDelimiter);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }


    public List<List<String>> getFarmersParams() {
        return this.farmersParams;

    }


    public List<List<String>> getMallsParams() {
        return this.mallsParams;
    }
}
