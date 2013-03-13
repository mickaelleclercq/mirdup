/*
 * Run tests
 */
package paper;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import miRdup.Features;
import miRdup.Main;
import miRdup.RNAfold;
import miRdup.WekaModule;

/**
 *
 * @author fyrox
 */
public class Run_tests {
    public static String rnafold="/home/mycky/tools/ViennaRNA-2.0.6/Progs/";
//    public static String rnafold="/ibrixfs1/Data/mik/tools/ViennaRNA-2.0.7/Progs/";
    public static void main(String[] args) {   
//        tests();
//        batchTrain();
//        AttributeSelection();
//        validationSpecies();
//        batchTest();
//        predictmiRNAposition();
//        predictmiRNApositionByFile();
//        newRNAfold();
//        getmirnastar();
        validate();

    }
    
    public static void tests() {
        String [] args;
//        // use already downloaded miRbase, only aestivum
//        args=new String[]{"-k","aestivum","-m","mirbase.matures.fasta",
//        "-h","mirbase.hairpins.fasta",
//        "-o","organisms.txt"};
//        
//        // Validate a predicted file with a model already created
//        args=new String[]{"-c","all.AFAdaboostRF.model","-v","PredictedDatasets\\all_lib.pred.mirnas.hairpins.txt.folded","-b",
//        "PredictedDatasets\\all_lib.pred.mirnas.hairpins.txt.folded.arff"};        
//                      
//        //"-r","/ibrixfs1/Data/mik/tools/ViennaRNA-2.0.5/Progs/","-k",
//        args = new String[]{"-k","Viridiplantae",
//        "-v","PredictedDatasets\\all_lib.pred.mirnas.hairpins.txt.folded","-b",
//        "PredictedDatasets\\all_lib.pred.mirnas.hairpins.txt.folded.arff"};
//
//        args=new String[]{"-c","all.AFAdaboostRF.model","-v","PredictedDatasets\\SRR029124.mirdeep2.toPredict.txt.folded",
//        "-b","PredictedDatasets\\SRR029124.mirdeep2.toPredict.txt.folded.arff"};
        
        args=new String[]{"-k","all","-s","tmpfold-133348316.folded","-o","organisms.txt",
            "-e","miRNA.dat","-a","all.arff"};
        Main.bestFeatures=true;
        Main.modelExtension=".BF.SVMmodel";
        Main.main(args);

    }
        
        
    public static void validationSpecies(){
        
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("validationSpecies.txt"));
            RunvalidationSpecies("all",pw);
            pw.flush();
            RunvalidationSpecies("mammal",pw);
            pw.flush();
            RunvalidationSpecies("Viridiplantae",pw);
            pw.flush();
            RunvalidationSpecies("Nematoda",pw);
            pw.flush();
            RunvalidationSpecies("Arthropoda",pw);
            pw.flush();
            RunvalidationSpecies("Pisces",pw);
            pw.flush();pw.close();
                    
                   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
            
    public static void RunvalidationSpecies(String model, PrintWriter pw){
        String speciestab[]={"all",        
        "Nematoda",
        "Arthropoda",
        "Pisces",
        "mammal", 
        "Viridiplantae",                
        };
        for (String sp : speciestab) {
            String m = model+".AFAdaboostRF.model";
            File arff = new File(sp+".arff");            
            String out="model:"+model+"\tspecies:"+sp;
            System.out.println(out);
            pw.println(out);
            pw.println(WekaModule.testModel(arff, m));
        }
        
    }
    
    public static void AttributeSelection(){
        RunAttributeSelection("all");
        RunAttributeSelection("Viridiplantae");
        RunAttributeSelection("mammal");
        RunAttributeSelection("Nematoda");
        RunAttributeSelection("Arthropoda");
        RunAttributeSelection("Pisces");

    }
    
    public static void RunAttributeSelection(String keyword){
        File arff = new File(keyword+".arff");     
        System.out.println("Attribute selection for "+keyword);
        WekaModule.attributeSelection(arff,keyword+".AttribSel.txt");
    }
    
    public static void testFeatures(){
        String mirna="UGUUUUCCUUUCUUUCCCCUC";
        String prec=  "GUGAGUGCAGGCUGUUUAUUGCGGCGCUGUUUACUGUUUACGAUCGCACUGGAAGUGCGCUUUCCGGCGCCUUUGGAAGCCAGCACUCAAGUUUCCUUCAUUGUUUUCCUUUCUUUCCCCUCUCUCUCUCCCCGCAG";
        String struct=".(((((((.((((.((((....(((((((.....((....))..(((((.....))))).....)))))))..)))))))).)))))))................................................";
        Features f = new Features(mirna, prec, struct, true);
        f.toStringBestAttributes();
    }
    
    
    public static void batchTrain(){
        String[] args;        
        
        args = new String[]{};
//        setOptions(args);miRdupExecutionEMBL();
        //"-s","C:\\tmpfold1394845326.folded","-o","organisms.txt","-e","miRNA.dat"
        args = new String[]{"-k","Pisces","-s","tmpfold-133348316.folded","-o","organisms.txt","-e","miRNA.dat"};
        setOptions(args);miRdupExecutionEMBL();
        
        args = new String[]{"-k","Arthropoda","-s","tmpfold-133348316.folded","-o","organisms.txt","-e","miRNA.dat"};
        setOptions(args);miRdupExecutionEMBL();
        
        args = new String[]{"-k","Nematoda","-s","tmpfold-133348316.folded","-o","organisms.txt","-e","miRNA.dat"};
        setOptions(args);miRdupExecutionEMBL();
                
        args = new String[]{"-k","mammal","-s","tmpfold-133348316.folded","-o","organisms.txt","-e","miRNA.dat"};
        setOptions(args);miRdupExecutionEMBL();
        
        args = new String[]{"-k","Viridiplantae","-s","tmpfold-133348316.folded","-o","organisms.txt","-e","miRNA.dat"};
        setOptions(args);miRdupExecutionEMBL();
    }
    
    
    public static void batchTest(){
        String[] args;
        
//        args=new String[]{"-c","all.AFAdaboostRF.model","-v","PredictedDatasets"+File.separator+"mirdeep2.ToPredict.txt.folded"};
//        setOptions(args);miRdupExecutionEMBL();
//		
//        args=new String[]{"-c","all.AFAdaboostRF.model","-v","PredictedDatasets"+File.separator+"miRbase_exp.ToPredict.folded"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFAdaboostRF.model","-v","PredictedDatasets"+File.separator+"miRbase_notexp.ToPredict.folded"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFAdaboostRF.model","-v","PredictedDatasets"+File.separator+"all.pmrd.topredict.txt.folded"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFAdaboostRF.model","-v","PredictedDatasets"+File.separator+"mircheck.topredict.txt.folded"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFAdaboostRF.model","-v","PredictedDatasets"+File.separator+"miRdeep.ToPredict.txt.folded"};
//        setOptions(args);miRdupExecutionEMBL();
                
//        args=new String[]{"-c","mammal.AFAdaboostRF.model","-v","PredictedDatasets"+File.separator+"SRR029124.mirdeep2.toPredict.txt.folded","-p"};
//        setOptions(args);miRdupExecutionEMBL();
        
        
//        args=new String[]{"-c","all.AFAdaboostJ48.model","-v","PredictedDatasets\\miRbase_exp.ToPredict.folded","-b","PredictedDatasets\\miRbase_exp.ToPredict.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFAdaboostJ48.model","-v","PredictedDatasets\\miRbase_notexp.ToPredict.folded","-b","PredictedDatasets\\miRbase_notexp.ToPredict.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        
//        args=new String[]{"-c","all.AFAdaboostJ48.model","-v","PredictedDatasets\\all.mirbase.topredict.txt.folded","-b","PredictedDatasets\\all.mirbase.topredict.txt.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
        
//        args=new String[]{"-c","all.AFAdaboostJ48.model","-v","PredictedDatasets\\all.pmrd.topredict.txt.folded","-b","PredictedDatasets\\all.pmrd.topredict.txt.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFAdaboostJ48.model","-v","PredictedDatasets\\mircheck.topredict.txt.folded","-b","PredictedDatasets\\mircheck.topredict.txt.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFAdaboostJ48.model","-v","PredictedDatasets\\miRdeep.ToPredict.txt.folded","-b","PredictedDatasets\\miRdeep.ToPredict.txt.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFAdaboostJ48.model","-v","PredictedDatasets\\miRdeep2.ToPredict.txt.folded","-b","PredictedDatasets\\miRdeep2.ToPredict.txt.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFAdaboostJ48.model","-v","PredictedDatasets\\SRR029124.mirdeep2.toPredict.txt.folded","-b","PredictedDatasets\\SRR029124.mirdeep2.toPredict.txt.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
        
        
//        args=new String[]{"-c","all.AFSVM.model","-v","PredictedDatasets\\miRbase_exp.ToPredict.folded","-b","PredictedDatasets\\miRbase_exp.ToPredict.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFSVM.model","-v","PredictedDatasets\\miRbase_notexp.ToPredict.folded","-b","PredictedDatasets\\miRbase_notexp.ToPredict.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        
//        args=new String[]{"-c","all.AFSVM.model","-v","PredictedDatasets\\all.mirbase.topredict.txt.folded","-b","PredictedDatasets\\all.mirbase.topredict.txt.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
        
//        args=new String[]{"-c","all.AFSVM.model","-v","PredictedDatasets\\all.pmrd.topredict.txt.folded","-b","PredictedDatasets\\all.pmrd.topredict.txt.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFSVM.model","-v","PredictedDatasets\\mircheck.topredict.txt.folded","-b","PredictedDatasets\\mircheck.topredict.txt.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFSVM.model","-v","PredictedDatasets\\miRdeep.ToPredict.txt.folded","-b","PredictedDatasets\\miRdeep.ToPredict.txt.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFSVM.model","-v","PredictedDatasets\\miRdeep2.ToPredict.txt.folded","-b","PredictedDatasets\\miRdeep2.ToPredict.txt.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
//        args=new String[]{"-c","all.AFSVM.model","-v","PredictedDatasets\\SRR029124.mirdeep2.toPredict.txt.folded","-b","PredictedDatasets\\SRR029124.mirdeep2.toPredict.txt.folded.arff"};
//        setOptions(args);miRdupExecutionEMBL();
//        
        
    }

    private static void setOptions(String[] args) {
        Main.setOptions(args);
    }

    private static void miRdupExecutionEMBL() {
        Main.miRdupExecutionEMBL();
    }

    private static void predictmiRNAposition() {
        String prec=  "CCACGUCUACCCUGUAGAUCCGAAUUUGUUUUAUACUAGCUUUAAGGACAAAUUCGGUUCUAGAGAGGUUUGUGUGG";
        miRdupPredictor.Predictor.predictionBySequence(prec, "Arthropoda.model","test.txt");
    }

    private static void predictmiRNApositionByFile() {
        miRdupPredictor.Predictor.predictionByFile("test.txt", "Pisces.model", "test.predictions");
    }

    private static void newRNAfold() {
        String s=RNAfold.GetSecondaryStructure("CAGUUUGUUGUGAUGUGCUCCAAGCCGAGAAGCUGCAGAUGGAGGUUC");
        System.out.println(s);
    }

    private static void getmirnastar() {
        String mirna="ACAAAUUCGGUUCUAGAGAGGUU";
        String precursor="CCACGUCUACCCUGUAGAUCCGAAUUUGUUUUAUACUAGCUUUAAGGACAAAUUCGGUUCUAGAGAGGUUUGUGUGG";
        String structure="(((((...(((((.((((.(((((((((((((............))))))))))))).)))).)).)))...)))))";
        Features f = new Features(mirna, precursor, structure, true);
        String star=f.getMirnaStar();
        System.out.println(star);
    }
    
    private static void validate(){
        String[] args=new String[]{"-c","mammal.model","-v","PredictedDatasets"+File.separator+"all.mirna.5p.3p.precursors.from.aln.anc.txt"};
        setOptions(args);miRdupExecutionEMBL();
        
    }
}