/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tester;
import Parser.*;
import Validator.PropertyValueMatch;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MainParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PropertyValueMatch.initList();
        List<Rule> rules = new ArrayList<Rule>();
        int l=args.length;
        if(l==0){
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new ParserGUI().setVisible(true);
                }
            });
        }
        else if(args[0].equalsIgnoreCase("-GUI")){
            if(l>2) 
            {
                System.out.println("Enter only one file for GUI"); System.exit(0); 
            }
            if(l==1){
                System.out.println("Enter one file to open with GUI");
            }
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    ParserGUI window=new ParserGUI();
                    window.initialize(args[1]);
                    window.setVisible(true);
                }
            });
            
        }
        else{
            for(int i=0;i<l;i++){
                System.out.println("\nFile: "+args[i]);
                String temp;
                StringBuilder fileText=new StringBuilder();
                try{
                    
                    System.out.println("Checking syntax...");
                    File file = new File(args[i]);
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    while((temp=bufferedReader.readLine())!=null){
                        fileText.append(temp+"\n");
                    }
                    rules=Parser.CSSParser.parse(fileText.toString());
                    System.out.println("Syntax: Valid\n\nChecking validity...");
                    
                    List<PropertyValue> allProps = new ArrayList<>();
                    Boolean valid = true;
                    for(Rule r:rules){
                        allProps.addAll(r.getPropertyValues());
                    }
                    for(PropertyValue pv:allProps){
                        if(!pv.isValidPVPair()){
                            valid = false;
                            String error = pv.getLineNo()+": The value \""+pv.getValue()+"\" is not applicable to property \""+pv.getProperty()+"\"\n";
                            System.out.println(error);
                        }
                    }
                    if(valid){
                        System.out.println("Validity: Valid");
                    }
                    else{
                        System.out.println("Validity: Invalid");
                    }
                }
                catch(IncorrectFormatException e)
                {
                    System.out.println("Result: Invalid syntax \n\n\tStatus: "+e.getErrorMessage()+"\n\tMessage: "+e.getEMessage()+"\n\tLine No: "+e.getLineNo()+"\n\tColumn: "+e.getColumn());
                }
                catch(Exception e){
                    System.out.println("Unknown Exception: "+e.toString());
                    e.printStackTrace();
                }
                System.out.println("--------------------------------------------");
            }
        }
        
    }
    
}