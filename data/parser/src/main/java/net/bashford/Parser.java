package net.bashford;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    private int linecount;
    private int state;
    private FileData filedata;

    public FileData parse(String filename) {
        this.linecount = 0;
        this.state = 0;
        this.filedata = new FileData();

        try (BufferedReader br = new BufferedReader(new FileReader("S:\\development\\java\\parser\\data\\" + filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                handleLine(line);
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        System.out.println(this.linecount);
        return this.filedata;
    }

    private void handleLine(String line) {
        linecount ++;
        /*
            If the line is a comment or blank, throw it away
            If the line is one of the file markers switch state
            Otherwise process
         */
        if (line.startsWith("#") || line.length() == 0)
            return;
        else if (line.startsWith("END-OF")) {
            this.state = 0;
            System.out.println(line + " " + state);
        }
        else if (line.startsWith(BloombergFile.START_OF_FIELDS) ) {
            this.state = 1;
            System.out.println(line + " " + state);
        }
        else if (line.startsWith(BloombergFile.START_OF_DATA)) {
            this.state = 2;
            System.out.println(line + " " + state);
        }
        else
            switch (state) {
                case 0:
                    break;
                case 1:
                    processHeader(line);
                    break;
                case 2:
                    processData(line);
                    break;
                default:
                    System.out.println(line + " " + state);
                    throw new RuntimeException("Processing data in invalid state " + linecount);
            }

    }

    private void processHeader(String line) {
        this.filedata.addHeader(line);
    }

    private void processData(String line) {
        this.filedata.addFileData(line);
    }

}
