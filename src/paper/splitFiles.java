/*
 * To parralise the calculs
 */
package paper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author Mickael
 */
public class splitFiles {
    
    public static void main(String[] args) {
        String infile="all.txt";
        String infileName=infile.substring(0, infile.lastIndexOf("."));
        int linesPerFile=100;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(infile));
            PrintWriter pw = null ;
            int cpt=0;
            while (br.ready()){                
                if (cpt%linesPerFile==0){
                    if (cpt>0){
                        pw.flush();pw.close();
                    }
                    pw = new PrintWriter(new FileWriter("/ibrixfs1/Data/mik/mirdup/miRdup/meanMethod.noExp/"+infileName+"_"+cpt+".splitted.txt"));
                    
                }
                cpt++;
                pw.println(br.readLine());
            }
            pw.flush();pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
