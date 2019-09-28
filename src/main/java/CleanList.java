import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CleanList {

    HashMap<String, String> hmap;

    public CleanList(){
        hmap = new HashMap<String, String>();
    }



    public void readFile(String filename){
        File file = new File(getClass().getClassLoader().getResource(filename).getFile());

        try{

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null){
//                System.out.println(st);
                if(st.contains(",")){
                    String[] wordFrequency = st.split(",");

                        // word - frequence
                        hmap.put(wordFrequency[1],wordFrequency[0]);


                }

            }

        }catch (IOException ex){

        }


    }

    public void writeNewFile(String filename){
        PrintWriter writer = null;
        try {
            // Change Path for output
            writer = new PrintWriter("/Users/javierflores/Documents/UPC/MIRI/3/IR/IR_clean/src/main/resources/clean"+filename);

            List sortedKeys=new ArrayList(hmap.keySet());
            Collections.sort(sortedKeys);
            System.out.println("Original size: "+hmap.size());
            int j = 0;
            for (int i = 0; i < sortedKeys.size() ; i++) {

                if(isValid(sortedKeys.get(i).toString())){
                    String value = hmap.get(sortedKeys.get(i));
                    writer.println(value+","+sortedKeys.get(i));
                    j++;
                }

            }
            System.out.println("New size: "+j);

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean isValid(String word){
//        String[] unproperWords = new String ["pm", "am","mg","hz","fps","pp","ug"];
        if(word.length() < 3)
            return false;
        if(NumberUtils.isCreatable(word.trim()) || NumberUtils.isCreatable(word.replaceAll(" ", "")))
            return false;
        if(word.split("\\.").length > 1)
            return false;
        if(word.split(";").length > 1)
            return false;
        if(word.matches("[0-9]*x['0-9]*") || word.matches(" [0-9]*[a-z][a-z]?[0-9]?")|| word.matches(" ([0-9]+([\\_]*[a-z]*[\\']*[:]*)*)*") || word.matches("[a-z]+[0-9]+") || word.matches(" [\\_]+([\\_]*[0-9]*[:]*[a-z]*)*")|| word.matches("[ ]*[a-z]*([0-9]+[\\_]*[a-z]*)+") || word.matches("[ ]*[a-z]*([\\_]+[0-9]+[a-z]*)+"))
            return false;
        if(word.contains("â") ||  word.contains("º") || word.contains("ã") ||
                word.contains("µ") || word.contains("î") ||  word.contains(":") ||
                word.contains("í") || word.contains("å") || word.contains("ª") )
            return false;
        return true;
    }

    public static boolean isNumeric(String strNum) {
        try {
            Double.parseDouble(strNum);
            Integer.parseInt(strNum);
            Float.parseFloat(strNum);
            Double.parseDouble(strNum);
            Long.parseLong(strNum);
            new BigInteger(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public void ProcessFile(String filename){
        readFile(filename);
        writeNewFile(filename);
    }

    public static void main(String[] args) {
        String filename = "vicent.txt";
        CleanList cl = new CleanList();
        cl.ProcessFile(filename);
    }

}
