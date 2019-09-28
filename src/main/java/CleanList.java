import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CleanList {

    HashMap<String, String> hmap;
    HashMap<String, String> hmapApostrofe;

    public CleanList(){
        hmap = new HashMap<String, String>();
        hmapApostrofe = new HashMap<String, String>();
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
//                    writer.println(value+","+sortedKeys.get(i));
//                    j++;
                }else{
                    hmap.remove(sortedKeys.get(i));
                }

            }

            ArrayList<Integer> noApos = new ArrayList<>();
            for (int i = 0; i < sortedKeys.size() ; i++) {

                if(sortedKeys.get(i).toString().contains("\'")){
                    String[] sp =  sortedKeys.get(i).toString().split("\'");
                    for (int k = 0; k < sortedKeys.size() ; k++) {
                        if(k != i && sp[0].equals(sortedKeys.get(k).toString()) && hmap.containsKey(sortedKeys.get(i)) && hmap.containsKey(sortedKeys.get(k))){


                            String value = hmap.get(sortedKeys.get(i));

                            Integer value1 = Integer.valueOf(value);
                            Integer value2 = Integer.valueOf(hmap.get(sortedKeys.get(k)));

                            if(sp[0].equals("dec")){
                                System.out.println(value+ " - "+value2) ;
                            }

                            hmap.put(sortedKeys.get(k).toString(),value1+value2+"");
                            noApos.add(i);
                        }
                    }
                }

            }

            j=0;
            for (int i = 0; i < sortedKeys.size() ; i++) {

                if(!noApos.contains(i) && hmap.containsKey(sortedKeys.get(i))){
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

    public HashMap<String, String> getHmap() {
        return hmap;
    }

    public boolean isValid(String word){
        if(word.length() < 3)
            return false;
        if(NumberUtils.isCreatable(word.trim()) || NumberUtils.isCreatable(word.replaceAll(" ", "")))
            return false;
        if(word.split("\\.").length > 1)
            return false;
        if(word.split(";").length > 1)
            return false;
        if(word.matches("[0-9]*x['0-9]*") || word.matches("([0-9]+[,|.]*)*") || word.matches(" [0-9]*[a-z][a-z]?[0-9]?")|| word.matches(" ([0-9]+([\\_]*[a-z]*[\\']*[:]*)*)*") || word.matches("[a-z]+[0-9]+") || word.matches(" [\\_]+([\\_]*[0-9]*[:]*[a-z]*)*")|| word.matches("[ ]*[a-z]*([0-9]+[\\_]*[a-z]*)+") || word.matches("[ ]*[a-z]*([\\_]+[0-9]+[a-z]*)+"))
            return false;
        if(word.contains("_"))
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
