import net.tobszarny.ssl.java6.provider.BouncyCastleSSLProvider;

import java.security.Provider;
import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    static {
        System.out.println("wtf");
    }

    public static void main(String args[]) {
        try {
            Security.insertProviderAt(new BouncyCastleSSLProvider(), 2);
            Security.insertProviderAt(new org.bouncycastle.jce.provider.BouncyCastleProvider(), 3);
            for (Provider provider : Security.getProviders()) {
                System.out.println(provider);
            }

//            Security.addProvider(new BouncyCastleSSLProvider());
            // Load the SQLServerDriver class, build the
            // connection string, and get a connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://ki11:1433;database=rasplus;"
                    + "user=FR08552;"
                    + "password=techuser!";
            Connection con = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected.");

            // Create and execute an SQL statement that returns some data.
            String SQL = "SELECT * FROM dbo.tblCustomer";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
