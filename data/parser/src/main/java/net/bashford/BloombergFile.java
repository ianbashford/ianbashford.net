package net.bashford;

public class BloombergFile {

    private String filename;
    private String filedate;
    private String filetype;
    private String region;
    private String assetclass;

    public final static String START_OF_FILE = "START-OF-FILE";
    public final static String END_OF_FILE = "END-OF-FILE";

    public final static String START_OF_FIELDS = "START-OF-FIELDS";
    public final static String END_OF_FIELDS = "END-OF-FIELDS";

    public final static String START_OF_DATA = "START-OF-DATA";
    public final static String END_OF_DATA = "END-OF-DATA";


    /*
        example filename equity_namr.out.20180101
     */
    public BloombergFile(String filename) {
        this.filename = filename;
        String[] parts = filename.split("\\.");
        if (parts.length != 3) {
            throw new RuntimeException("don't understand that filename format: " + filename + " " + parts.length);
        }
        String[] asset_region = parts[0].split("_");
        if (asset_region.length != 2) {
            throw new RuntimeException("can't get region and assetclass from filename");
        }
        this.assetclass = asset_region[0];
        this.region = asset_region[1];
        this.filedate = parts[2];
        this.filetype = parts[1];
    }

    public String getFilename() {
        return filename;
    }

    public String getFiledate() {
        return filedate;
    }

    public String getFiletype() {
        return filetype;
    }

    public String getRegion() {
        return region;
    }

    public String getAssetclass() {
        return assetclass;
    }

    @Override
    public String toString() {
        return "BloombergFile{" +
                "filename='" + filename + '\'' +
                ", filedate='" + filedate + '\'' +
                ", filetype='" + filetype + '\'' +
                ", region='" + region + '\'' +
                ", assetclass='" + assetclass + '\'' +
                '}';
    }
}
