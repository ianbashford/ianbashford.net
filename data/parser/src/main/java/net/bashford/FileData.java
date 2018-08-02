package net.bashford;

import java.util.ArrayList;

public class FileData {
    private ArrayList<String> headers;
    private ArrayList<String> filedata;

    public FileData() {
        this.headers = new ArrayList<>();
        /*
            Add for the three fields
         */
        addHeader("secdes");
        addHeader("num");
        addHeader("numtwo");
        this.filedata = new ArrayList<>();
    }

    public void addHeader(String header) {
        this.headers.add(header);
    }

    public void addFileData(String data) {
        this.filedata.add(data);
    }

    public void printHeaders() {
        for (String tmp : headers) {
            System.out.println("Header:" + tmp);
        }
    }
}
