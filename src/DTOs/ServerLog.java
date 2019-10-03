/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.logging.FileHandler;

/**
 *
 * @author conor
 */
public class ServerLog
{

    private File f = null;
    private FileReader file = null;
    private LineNumberReader line = null;
    private int lineNumber = 0;

    /**
     *
     */
    public ServerLog()
    {
    }

    /**
     *
     * @param command
     * @param clientNumber
     * @throws FileNotFoundException
     */
    public ServerLog(String command, int clientNumber) throws FileNotFoundException
    {
        try
        {
            String fileName = "./logFile.txt";

            f = new File(fileName);

            file = new FileReader(fileName);

            line = new LineNumberReader(file);

            try (BufferedWriter writeTo = new BufferedWriter(new FileWriter(f, true)))
            {
                while (line.readLine() != null)
                {
                    lineNumber++;
                }
                lineNumber += 1;

                String toWrite = "Protocol entered: (" + command + "),"
                        + "ClientNumber is (" + clientNumber + ")";

                writeTo.newLine();
                writeTo.write(toWrite);

                writeTo.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
