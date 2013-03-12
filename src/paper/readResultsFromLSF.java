/*
 * read results after lsf
 */
package paper;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author fyrox
 */
public class readResultsFromLSF {
    
    public static File folder= new File("/ibrixfs1/Data/mik/mirdup/miRdup/");
    
    public static void main(String[] args) {
        process("Arthropoda");
        process("mammal");
        process("Nematoda");
        process("Pisces");
        process("Viridiplantae");
        
        folder=new File("/ibrixfs1/Data/mik/mirdup/miRdup/all/");
        process("all");
    }
    
    public static void process(String species) {
        
        
        
        
        ArrayList<Integer> diffStarts = new ArrayList<Integer>();
        ArrayList<Integer> diffEnds = new ArrayList<Integer>();
        
        // full lists with 0
        for (int i = 0; i <= 2000; i++) {
            diffStarts.add(0);
            diffEnds.add(0);
        }
        
        // read results
        for (int i = 0; i <= 20000; i+=100) {
            //mammal_6300.splitted.txt.mammal.results.txt
            File f = new File(folder+"/"+species+"_"+i+".splitted.txt."+species+".results.txt");
            System.out.println(f.toString());
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line=br.readLine();
                while(br.ready()){
                    if (line.startsWith("Start")){
                        line=br.readLine();
                        while(!line.startsWith("End")){                            
                            int index=Integer.valueOf(line.split("\t")[0]);
                            int value=Integer.valueOf(line.split("\t")[1]);
                            int tmp=diffStarts.get(index);
                            value+=tmp;
                            diffStarts.set(index, value);
                            line=br.readLine();
                        }
                    }
                    line=br.readLine();
                    int index=Integer.valueOf(line.split("\t")[0]);
                    int value=Integer.valueOf(line.split("\t")[1]);
                    int tmp=diffEnds.get(index);
                    value+=tmp;
                    diffEnds.set(index, value);
                    
                }
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        
        // read lists
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(folder+"/results."+species+".txt"));
            pw.println("Start");
            int flagStart = 0;
            for (int i = diffStarts.size() - 1; i >= 0; i--) {
                if (diffStarts.get(i) != 0) {
                    flagStart = i;
                    break;
                }                
            }
            for (int i = 0; i <= flagStart; i++) {
                pw.println(i + "\t" + diffStarts.get(i));                
            }
            
            pw.println("End");
            int flagEnd = 0;
            for (int i = diffEnds.size() - 1; i >= 0; i--) {
                if (diffEnds.get(i) != 0) {
                    flagEnd = i;
                    break;
                }                
            }
            for (int i = 0; i <= flagEnd; i++) {
                pw.println(i + "\t" + diffEnds.get(i));                
            }
            
            pw.flush();pw.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
