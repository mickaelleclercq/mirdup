/*
 * Submit sequences to miRalign
 */
package miRalign;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fyrox
 */
public class miRalign {
    
    public static void main(String[] args) {
        
        String infile = "all.txt"; 
        String outfile="predictor\\all.miRalign.txt";
        
//        String infile=args[0];  
//        String outfile=args[1];
        
        ArrayList<Integer> diffStarts= new ArrayList<Integer>();        
        ArrayList<Integer> diffEnds= new ArrayList<Integer>();
        
        for (int i = 0; i < 1000; i++) {
            diffStarts.add(0);
            diffEnds.add(0);
        }
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(infile));
            String line;
            int cpt=0;
            while(br.ready()){
                try {
                    cpt++;
                    line = br.readLine();
                    //System.out.println(line);
                    String konwnMirna = line.split("\t")[2];
                    System.out.print(konwnMirna+"\t");
                    String prec = line.split("\t")[3];
                    String differences = executeMiRalign(konwnMirna, prec);
                    //differences from known mirna
                    int diffstart = Integer.valueOf(differences.split(",")[0]);
                    int diffend = Integer.valueOf(differences.split(",")[1]);

                    int tmp = diffStarts.get(diffstart);
                    tmp += 1;
                    diffStarts.set(diffstart, tmp);
                    
                    tmp = diffEnds.get(diffend);
                    tmp += 1;
                    diffEnds.set(diffend, tmp);                    
                    if (cpt%100==0){
                        printResults(outfile, diffStarts, diffEnds);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } 
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        printResults(outfile, diffStarts, diffEnds);
        
    }
    
    public static void printResults(String outfile,ArrayList<Integer> diffStarts,ArrayList<Integer> diffEnds){
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(outfile));
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
            e.printStackTrace();
        }
    }
    
    private static String executeMiRalign(String konwnMirna, String prec) {
        //execute miRalign and get start and end position of the predicted miRNA
        try {
            Map<String, String> data = new HashMap<String, String>();
            data.put("sequence", prec);
            data.put("type", "1");
            data.put("delta", "15");
            data.put("min_seq_sim", "30");
            data.put("MFE", "20");
            String results = doSubmit(data);
            String code = getCode(results);
            
            int startPosition = code.split("\n")[5].indexOf("*");
            int endPosition = code.split("\n")[5].lastIndexOf("*");

            String miRNA=code.split("\n")[6].substring(startPosition, endPosition);
            System.out.println(miRNA);
            
            // get difference from known miRNA
            int startOfKnownMiRNA = prec.indexOf(konwnMirna);
            int endOfKnownMiRNA = prec.indexOf(konwnMirna) + konwnMirna.length();
            
            int diffStart = Math.abs(startPosition - startOfKnownMiRNA);
            int diffend = Math.abs(endPosition - endOfKnownMiRNA);
            
            return diffStart + "," + diffend;
        } catch (Exception e) {
            return 100 + "," + 100;
        }
    }
    
    
    
    public static void exemple(){
        Map<String, String> data = new HashMap<String, String>();
        data.put("sequence", "ccagccugcugaagcucagagggcucugauucagaaagaucaucggauccgucugagcuuggcuggucgg");
        data.put("type", "1");
        data.put("delta", "15");
        data.put("min_seq_sim", "30");
        data.put("MFE", "20");
        String results=doSubmit(data);
        String code = getCode(results);
        int mirnastart=code.split("\n")[5].indexOf("*");
        int mirnaend=code.split("\n")[5].lastIndexOf("*");
        String miRNA=code.split("\n")[6].substring(mirnastart, mirnaend);
        System.out.println(miRNA);
    }
    
    /**
     * get link to results
     * @param data
     * @return 
     */
    public static String doSubmit(Map<String, String> data) {
        String link="";
	try{
            URL siteUrl = new URL("http://bioinfo.au.tsinghua.edu.cn/miralign/predict.php");

            HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            Set keys = data.keySet();
            Iterator keyIter = keys.iterator();
            String content = "";
            for(int i=0; keyIter.hasNext(); i++) {
                    Object key = keyIter.next();
                    if(i!=0) {
                            content += "&";
                    }
                    content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
            }
            out.writeBytes(content);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while((line=in.readLine())!=null) {
                if (line.contains("result")){
                    link=line.substring(line.indexOf(".")+1,line.indexOf(">"));
                }                    
            }
            in.close();   
	} catch(Exception e){
             e.printStackTrace();       
        }
        return "http://bioinfo.au.tsinghua.edu.cn/miralign"+link;
    }
    
        /**
     * Get HTML code from an url
     * @param link
     * @return 
     */
    public static String getCode (String link) {
        String sourceCode = null;
        try {
            URL url = new URL(link);
            URLConnection uc = url.openConnection();
            InputStream in = uc.getInputStream();
            int c = in.read();
            StringBuilder build = new StringBuilder();
            while (c != -1) {
                build.append((char) c);
                c = in.read();
            }
            sourceCode = build.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceCode;
    }


}
