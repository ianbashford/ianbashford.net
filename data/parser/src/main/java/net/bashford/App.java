package net.bashford;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if (args.length != 1) {
            System.out.println("only one arg please");
    }
        App a = new App();
        a.run(args);
    }

    public void run(String[] args) {
        BloombergFile bloombergFile = new BloombergFile(args[0]);
        System.out.println(bloombergFile.toString());

        Parser p = new Parser();
        FileData f = p.parse(bloombergFile.getFilename());
        f.printHeaders();
    }
}
