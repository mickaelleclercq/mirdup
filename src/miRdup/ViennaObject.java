/*
 * Object holding Vienna package tools informations
 */
package miRdup;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author mycky
 */
public class ViennaObject {
    public String mirna5p;
    public String mirna3p;
    public String bothmirnas;
    public String RNAcofoldStructure;
    public String RNAcofoldMfe;
    public String RNAcofoldStructureEnsemble;
    public String RNAcofoldMfeEnsemble;
    public String RNAcofoldFrequency;
    public String RNAcofoldDeltaG;
    public String RNAcofoldAB;
    public String RNAcofoldAA;
    public String RNAcofoldBB;
    public String RNAcofoldA;
    public String RNAcofoldB;
    
    public Double BPprobGlobal=0.0; // sum of probabilities of ALL possible pairs
    public Double BPprobTmpStructure=0.0; // sum of probabilities of possible pairs
    public Double BPprobFinalStructure=0.0; // sum of probabilities of the final structure
    
    public Double BPprobFinalStructureGC=0.0; //canonical
    public Double BPprobFinalStructureGU=0.0; //canonical
    public Double BPprobFinalStructureGA=0.0;
    public Double BPprobFinalStructureGG=0.0;
    
    public Double BPprobFinalStructureCC=0.0;
    public Double BPprobFinalStructureCU=0.0;
    public Double BPprobFinalStructureCA=0.0;
    
    public Double BPprobFinalStructureAU=0.0; //canonical
    public Double BPprobFinalStructureAA=0.0;
    
    public Double BPprobFinalStructureUU=0.0;
    
    public Boolean error=false;
    

    public void parseRNAcofold(String output) {
        String tab[]= output.split("\n");
        mirna5p=tab[0].split("&")[0];
        mirna3p=tab[0].split("&")[1];
        bothmirnas=mirna5p+mirna3p;
        RNAcofoldStructure=tab[1].substring(0,tab[1].lastIndexOf("("));
        RNAcofoldMfe=tab[1].substring(tab[1].lastIndexOf("(")+1, tab[1].lastIndexOf(")")).trim();
        RNAcofoldStructureEnsemble=tab[2].substring(0,tab[1].lastIndexOf("("));
        RNAcofoldMfeEnsemble=tab[2].substring(tab[2].indexOf("[")+1, tab[2].indexOf("]")).trim();
        RNAcofoldFrequency=tab[3].substring(tab[3].indexOf("ble")+3, tab[3].indexOf(",")-1).trim();
        RNAcofoldDeltaG=tab[3].substring(tab[3].indexOf("=")+1).trim();
        String tab2[]=tab[6].split("\t");
        RNAcofoldAB=tab2[0];
        RNAcofoldAA=tab2[1];
        RNAcofoldBB=tab2[2];
        RNAcofoldA=tab2[3];
        RNAcofoldB=tab2[4];
        getBasePairsProbabilities("ABdot5.ps");
        System.out.print("");
    }
    
    
    public void parseRNAcofoldShell(String output) {
        String tab[]= output.split("\n");
        RNAcofoldMfe=tab[4].substring(tab[4].indexOf("=")+1, tab[4].indexOf("kcal")-1).trim();
        RNAcofoldMfeEnsemble=tab[6].substring(tab[6].indexOf("=")+1, tab[6].indexOf("kcal")-1).trim();
        RNAcofoldFrequency=tab[7].substring(tab[7].indexOf("ble")+3, tab[7].indexOf(",")-1).trim();
        RNAcofoldDeltaG=tab[8].substring(tab[8].indexOf("=")+1).trim();
        String tab2[]=tab[10].split("\t");
        RNAcofoldAB=tab2[0];
        RNAcofoldAA=tab2[1];
        RNAcofoldBB=tab2[2];
        RNAcofoldA=tab2[3];
        RNAcofoldB=tab2[4];
        getBasePairsProbabilities("ABdot5.ps");
    }

    
    public String toStringHeaderRNAcofold(){
        String s = ""
                + "RNAcofoldMfe"+"\t"
                + "RNAcofoldMfeEnsemble"+"\t"
                + "RNAcofoldFrequency"+"\t"
                + "RNAcofoldDeltaG"+"\t"
                + "RNAcofoldAB"+"\t"
                + "RNAcofoldAA"+"\t"
                + "RNAcofoldBB"+"\t"
                + "RNAcofoldA"+"\t"
                + "RNAcofoldB"+"\t"
                + "";
        return s;
        
    }
    
    public String toStringRNAcofold(){
        String s = ""
                + RNAcofoldMfe+","
                + RNAcofoldMfeEnsemble+","
                + RNAcofoldFrequency+","
                + RNAcofoldDeltaG+","
                + RNAcofoldAB+","
                + RNAcofoldAA+","
                + RNAcofoldBB+","
                + RNAcofoldA+","
                + RNAcofoldB+","
                + BPprobGlobal+","
                + BPprobTmpStructure+","
                + BPprobFinalStructure+","
                + BPprobFinalStructureGC+","
                + BPprobFinalStructureGU+","
                + BPprobFinalStructureGA+","
                + BPprobFinalStructureGG+","
                + BPprobFinalStructureCC+","
                + BPprobFinalStructureCU+","
                + BPprobFinalStructureCA+","
                + BPprobFinalStructureAU+","
                + BPprobFinalStructureAA+","
                + BPprobFinalStructureUU+""
                ;
        return s;        
    }

    
    public String toStringError() {
        String s = ""
                + "0"+"\t"
                + "0"+"\t"
                + "0"+"\t"
                + "0"+"\t"
                + "0"+"\t"
                + "0"+"\t"
                + "0"+"\t"
                + "0"+"\t"
                + "0"+"\t"
                
                + "0.0"+"\t"
                + "0.0"+"\t"
                + "0.0"+"\t"
                
                + "0.0"+"\t"
                + "0.0"+"\t"
                + "0.0"+"\t"
                + "0.0"+"\t"
                
                + "0.0"+"\t"
                + "0.0"+"\t"
                + "0.0"+"\t"
                
                + "0.0"+"\t"
                + "0.0"+"\t"
                
                + "0.0"
                
                + "";
        return s;
    }

    /**
     * if submitted sequence contains errors
     * @return 
     */
    public boolean hasError(){
        if (error==true){
            return true;            
        } else {
            return false;
        }
    }

    /**
     * Get probabilities for each pairs stored in the ps file
     * @param aBdot5ps 
     */
    private void getBasePairsProbabilities(String aBdot5ps) {

        boolean finalProbs=false;
        int nucleotide5p=0;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(aBdot5ps));
            String line=br.readLine();
            while(!line.startsWith("%data")){
                line=br.readLine();
            }
            line=br.readLine();
            while (!line.startsWith("showpage")){
                String tab[]=line.split(" ");
                int actual5pnt=Integer.valueOf(tab[0])-1;
                int nucleotide3p=Integer.valueOf(tab[1])-1;
                double score=Double.valueOf(tab[2]);
                if (actual5pnt<nucleotide5p){
                    finalProbs=true;
                }
                nucleotide5p=actual5pnt;
//                System.out.println(line);
                assignBasePairsProbability(bothmirnas.charAt(actual5pnt), 
                        bothmirnas.charAt(nucleotide3p), score);
                
                BPprobGlobal+=score;
                if (finalProbs){
                    BPprobFinalStructure+=score;                    
                    
                }else {
                    BPprobTmpStructure+=score;
                }
                
                line=br.readLine();                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void assignBasePairsProbability(char fiveP,char threeP, Double score) {
         //canonical
        if ((fiveP=='A'&&threeP=='U')||(fiveP=='U'&&threeP=='A')){
            BPprobFinalStructureAU+=score;
        }
        if ((fiveP=='G'&&threeP=='U')||(fiveP=='U'&&threeP=='G')){
            BPprobFinalStructureGU+=score;
        }
        if ((fiveP=='G'&&threeP=='C')||(fiveP=='C'&&threeP=='G')){
            BPprobFinalStructureGC+=score;
        }
        
         //non canonical
        if ((fiveP=='G'&&threeP=='A')||(fiveP=='A'&&threeP=='G')){
            BPprobFinalStructureGA+=score;
        }
        if ((fiveP=='C'&&threeP=='A')||(fiveP=='A'&&threeP=='C')){
            BPprobFinalStructureCA+=score;
        }
        if ((fiveP=='C'&&threeP=='U')||(fiveP=='U'&&threeP=='C')){
            BPprobFinalStructureCU+=score;
        }
        if ((fiveP=='G'&&threeP=='G')||(fiveP=='G'&&threeP=='G')){
            BPprobFinalStructureGG+=score;
        }
        if ((fiveP=='C'&&threeP=='C')||(fiveP=='C'&&threeP=='C')){
            BPprobFinalStructureCC+=score;
        }
        if ((fiveP=='A'&&threeP=='A')||(fiveP=='A'&&threeP=='A')){
            BPprobFinalStructureAA+=score;
        }
        if ((fiveP=='U'&&threeP=='U')||(fiveP=='U'&&threeP=='U')){
            BPprobFinalStructureUU+=score;
        }

    }
    
}
