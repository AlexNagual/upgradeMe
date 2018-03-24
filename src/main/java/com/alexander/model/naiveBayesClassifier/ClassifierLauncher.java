package com.alexander.model.naiveBayesClassifier;

import com.alexander.model.config.Config;
import com.alexander.model.naiveBayesClassifier.base.BayesClassifier;
import com.alexander.model.naiveBayesClassifier.base.Classifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 15.12.2017.
 */
public class ClassifierLauncher {
    public ArrayList<String> reader(String filepath){
        ArrayList<String> result = new ArrayList<String>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            String currentString;
            while ((currentString = bufferedReader.readLine()) != null){
                result.addAll(Arrays.asList(currentString.split("\\s")));
                //result.add(currentString.split("\\s"));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public Classifier<String, String> getTrainingClassifier(){
        Map<String, ArrayList<String>> datasForTraining = new HashMap<String, ArrayList<String>>();
        datasForTraining.put("animals", reader(Config.FILEPATH_ANIMALS));
        datasForTraining.put("people", reader(Config.FILEPATH_PEOPLE));
        datasForTraining.put("countries", reader(Config.FILEPATH_COUNTRIES));
        datasForTraining.put("architecture", reader(Config.FILEPATH_ARCHITECTURE));
        datasForTraining.put("food", reader(Config.FILEPATH_FOOD));
        datasForTraining.put("musical instruments", reader(Config.FILEPATH_MUSICALINSTRUMENTS));
        datasForTraining.put("nature", reader(Config.FILEPATH_NATURE));
        datasForTraining.put("fashion", reader(Config.FILEPATH_FASHION));
        datasForTraining.put("construction details", reader(Config.FILEPATH_DETAILS));
        datasForTraining.put("other", reader(Config.FILEPATH_OTHER));
        datasForTraining.put("sport", reader(Config.FILEPATH_SPORT));
        datasForTraining.put("toys" +
                "", reader(Config.FILEPATH_TOYS));
        Classifier<String, String> bayes = new BayesClassifier<String, String>();
        for (Map.Entry<String, ArrayList<String>> entry: datasForTraining.entrySet()){
            bayes.learn(entry.getKey(), entry.getValue());
        }
        return bayes;
    }

    public String getClassifyResult(Classifier<String, String> bayes, String inputText){
        if (inputText!=""){
            String[] textForClassify = inputText.split("\\s");
            return bayes.classify(Arrays.asList(textForClassify)).getCategory();
        } else {
           return  "tag list empty";
        }
    }

    public static void main(String[] args) {
        ClassifierLauncher classifierLauncher = new ClassifierLauncher();
        Classifier<String, String> classifier = classifierLauncher.getTrainingClassifier();
        String unknownText = "Russia ds people boy green tree man  girl";
        System.out.println(classifierLauncher.getClassifyResult(classifier, unknownText));

    }
}
