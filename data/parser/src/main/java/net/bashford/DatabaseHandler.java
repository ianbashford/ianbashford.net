package net.bashford;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    private static final String connectionUrl;

    public DatabaseHandler() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (ClassNotFoundException cnf) {
            throw new RuntimeException("can't find sql driver class")
        }
    }

    public void bulkUpload (FileData data) {

        String tableNameBulkCopyAPI = "bbg_desc";

        try (Connection con = DriverManager.getConnection(connectionUrl + ";useBulkCopyForBatchInsert=true"); // useBulkCopyForBatchInsert connection property set to true.
             Statement stmt = con.createStatement();
             PreparedStatement pstmt = con.prepareStatement("insert into " + tableNameBulkCopyAPI + " values (?, ?)");) {

            //String dropSql = "if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[" + tableNameBulkCopyAPI + "]') and OBJECTPROPERTY(id, N'IsUserTable') = 1) DROP TABLE [" + tableNameBulkCopyAPI + "]";
            //stmt.execute(dropSql);

            //String createSql = "create table " + tableNameBulkCopyAPI + " (c1 int, c2 varchar(20))";
            //stmt.execute(createSql);

            System.out.println("Starting batch operation using Bulk Copy API.");
            int batchsize = 1000;

            long start = System.currentTimeMillis();

            int attribute = 0;

            for (int i = 0; i < 1000; i++) {
                pstmt.setInt(1, i);
                pstmt.setString(2, "test" + i);
                pstmt.addBatch();
            }
            pstmt.executeBatch();

            long end = System.currentTimeMillis();

            System.out.println("Finished. Time taken : " + (end - start) + " milliseconds.");
        }
        catch (SQLException se) {
            //TODO
        }

    }


}
