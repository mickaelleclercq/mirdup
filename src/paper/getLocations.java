/*
 * from conserved miRNAs in mirbase after mirdeep2, find their genomic location
 */
package paper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 *
 * @author fyrox
 */
public class getLocations {
    public static String organism="";
    public static HashMap<String,gffObject> hm = new HashMap<String, gffObject>();
    
    public static void main(String[] args) {
        process();
    }

    private static void process() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("SRR0239124.mirdeep2.conserved.txt"));
            PrintWriter pw = new PrintWriter(new FileWriter("SRR0239124.mirdeep2.conserved.coordinates.txt"));
            String line ="";
            while (br.ready()){
                line=br.readLine();
                String actualOrganism=line.split("-")[0];
                if (!actualOrganism.equals(organism)){
                    System.out.println(actualOrganism);
                    addgfftoHM(actualOrganism);
                    organism=actualOrganism;
                }                
                gffObject go = hm.get(line.trim());
                String out=line+"\t"+go.chromosom+"\t"+go.strand+"\t"+go.mirnaStart+"\t"+go.mirnaEnd;
                System.out.println(out);
                pw.println(out);
                pw.flush();
            }
            pw.flush();pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addgfftoHM(String actualOrganism) {
        try {
            String infile=actualOrganism+".gff3";            
            BufferedReader br = new BufferedReader(new FileReader(infile));
            String line=br.readLine();
            while (br.ready()){
                if (!line.startsWith("#")){
                    gffObject go = new gffObject();
                    //read precursor informations
                    String t[]=line.split("\t");
                    go.chromosom=t[0];
                    go.precursorStart=Integer.valueOf(t[3]);
                    go.precursorEnd=Integer.valueOf(t[4]);
                    go.strand=t[6];
                    go.precName=line.substring(line.indexOf("Name=")+5);
                    //read mirna informations
                    line=br.readLine();
                    t=line.split("\t");
                    go.mirnaStart=t[3];
                    go.mirnaEnd=t[4];
                    go.mirnaName=line.substring(line.indexOf("Name=")+5);
                    
                    try {
                        go.mirnaName = go.mirnaName.substring(0, go.mirnaName.indexOf(";"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    hm.put(go.mirnaName, go);
                    //read miRNA star information if exist
                    line=br.readLine();
                    
                    if(line!=null&&!line.contains("miRNA_primary_transcript")){
                        go = new gffObject();
                        go.chromosom=t[0];
                        go.precursorStart=Integer.valueOf(t[3]);
                        go.precursorEnd=Integer.valueOf(t[4]);
                        go.strand=t[6];
                        go.precName=line.substring(line.indexOf("Name=")+5);
                        t=line.split("\t");
                        go.mirnaStart=t[3];
                        go.mirnaEnd=t[4];
                        go.mirnaName=line.substring(line.indexOf("Name=")+5);
                        go.mirnaName=go.mirnaName.substring(0, go.mirnaName.indexOf(";"));
                        hm.put(go.mirnaName, go);
                        line=br.readLine();
                        if(line!=null&&!line.contains("miRNA_primary_transcript")){
                            go = new gffObject();
                            go.chromosom=t[0];
                            go.precursorStart=Integer.valueOf(t[3]);
                            go.precursorEnd=Integer.valueOf(t[4]);
                            go.strand=t[6];
                            go.precName=line.substring(line.indexOf("Name=")+5);
                            t=line.split("\t");
                            go.mirnaStart=t[3];
                            go.mirnaEnd=t[4];
                            go.mirnaName=line.substring(line.indexOf("Name=")+5);
                            go.mirnaName=go.mirnaName.substring(0, go.mirnaName.indexOf(";"));
                            hm.put(go.mirnaName, go);
                            line=br.readLine();
                            if(line!=null&&!line.contains("miRNA_primary_transcript")){
                                go = new gffObject();
                                go.chromosom=t[0];
                                go.precursorStart=Integer.valueOf(t[3]);
                                go.precursorEnd=Integer.valueOf(t[4]);
                                go.strand=t[6];
                                go.precName=line.substring(line.indexOf("Name=")+5);
                                t=line.split("\t");
                                go.mirnaStart=t[3];
                                go.mirnaEnd=t[4];
                                go.mirnaName=line.substring(line.indexOf("Name=")+5);
                                go.mirnaName=go.mirnaName.substring(0, go.mirnaName.indexOf(";"));
                                hm.put(go.mirnaName, go);
                                line=br.readLine();
                            }
                        }
                    }
                    
                    
                } else {
                    line=br.readLine();
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
