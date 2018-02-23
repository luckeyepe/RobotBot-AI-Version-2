package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class Main
{
	static Scanner console = new Scanner(System.in);
    private static final double priorProbabilityPositive = 1;
    private static final double priorProbabilityNegative = 1;
    private static final double priorProbabilityNeutral = 1;

    public static void main(String[] args)
    {
        System.out.print("Please enter the sentence you want to judge: ");
        Input(console.nextLine());
    }

    static void Input(String input)
	{
		String[] inputArray;
		
		inputArray = input.split(" ");
		Conclusion(UniqueWordCount(RemoveSuffix(RemovePrefix(RemoveThreeLetterWords(RemoveConjucntions(ChangeVowels(RemoveEnglishWords(RemoveNonLetters(inputArray)))))))));
	}
	
	static void Display(ArrayList<String> list)
	{
		for (String string : list) {
			System.out.println(string);
		}
	}
	
	static ArrayList<String> RemoveNonLetters(String[] input)
	{
		ArrayList<String> treatedWords = new ArrayList<String>(1);
		char[] word;
		String st = "";
		
		for(int i = 0; i<input.length; i++)
		{
			treatedWords.add(input[i].toLowerCase());//lower case words
		}
		
		
		for (int i = 0; i < treatedWords.size(); i++)
		{
			word = treatedWords.get(i).toCharArray();
			for(int j= 0; j<word.length; j++)
			{
				if(Character.isLetter(word[j]))
				{
					st+=word[j];
				}
			}
			treatedWords.set(i, st);
			st = "";
		}
		
		treatedWords.remove("");//removes null words from the arrayList
		return treatedWords;
	}
	
	//removing English words
	static ArrayList<String> RemoveEnglishWords(ArrayList<String> input)
	{
		String csvFileDirectory = "D:\\Users\\Mickey\\eclipse-workspace\\RobotBot v0.0.2\\src\\EnglishDictionary.csv";
        String englishWord;
        
        Collections.sort(input);//sorts input arrayList to alphabetical order to make it easier to find the matching English words

        try (BufferedReader br = new BufferedReader(new FileReader(csvFileDirectory)))
        {
            while (null != (englishWord = br.readLine()))
            {
                for (int i = 0; i < input.size(); i++)
                {
                    if (englishWord.equals(input.get(i)))
                    {
                       input.remove(i);
                       i--;
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		return input;
    }
	
	//change vowels
	 static ArrayList<String > ChangeVowels(ArrayList<String> input)
	 {
		 for (int i = 0; i < input.size(); i++)
	     {
			 if (input.get(i).contains("e") || input.get(i).contains("o"))
	         {
				 input.set(i, input.get(i).replace('e', 'i'));
				 input.set(i, input.get(i).replace('o', 'u'));
	         }
	     }

	        return input;
	 }
	 
	 static ArrayList<String> RemoveConjucntions(ArrayList<String> input)
	 {
		 String csvFileDirectory = "D:\\Users\\Mickey\\eclipse-workspace\\RobotBot v0.0.2\\src\\ConjunctionWords.csv";
		 String conjunction;

		 try (BufferedReader br = new BufferedReader(new FileReader(csvFileDirectory)))
        {
            while (null != (conjunction = br.readLine()))
            {
                for (int i = 0; i < input.size(); i++)
                {
                    if (conjunction.equals(input.get(i)))
                    {
                       input.remove(i);
                       i--;
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		 return input;
	 }
	 
	 static ArrayList<String> RemovePrefix(ArrayList<String> input)
	 {
		String csvFileDirectory = "D:\\Users\\Mickey\\eclipse-workspace\\RobotBot v0.0.2\\src\\Prefix.csv";
        String prefix;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFileDirectory)))
        {
            while (null != (prefix = br.readLine()))
            {
            	for (int i = 0; i < input.size(); i++)
            	{
	            	if (input.get(i).startsWith(prefix))
	                {
	            		input.set(i, input.get(i).replaceFirst(prefix, ""));
	                }
            	}
        	}
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
		return input;
	 }
	 
	 static ArrayList<String> RemoveSuffix(ArrayList<String> input)
	 {
		String csvFileDirectory = "D:\\Users\\Mickey\\eclipse-workspace\\RobotBot v0.0.2\\src\\Suffix.csv";
        String suffix;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFileDirectory)))
        {
        	while (null != (suffix = br.readLine()))
            {
            	for (int i = 0; i < input.size(); i++)
            	{
            		if (input.get(i).endsWith(suffix))
                    {
	            		input.set(i, ReplaceLast(input.get(i), suffix, ""));
                    }
            	}
        	}
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
		return input;
	}
	
	 
	static String ReplaceLast(String string, String substring, String replacement)
    {
        int index = string.lastIndexOf(substring);

        if (index == -1)
        {
            return string;
        }

        return string.substring(0, index) + replacement;
    }
	
	static ArrayList<String> RemoveThreeLetterWords(ArrayList<String> input)
	{
		for (int i = 0; i < input.size(); i++)
        {
            if (input.get(i).length()<=3)
            {
                input.remove(i);
                i--;
            }
        }
		
		return input;
	}
	
	
	static Hashtable<String, Integer> UniqueWordCount(ArrayList<String> treatedWords)
    {
        Hashtable<String, Integer> uniqueWordTable = new Hashtable<String, Integer>();
        ArrayList<String> uniqueWords;

        uniqueWords = new ArrayList<>(1);

        for (int i = 0; i < treatedWords.size(); i++)
        {
            if (!uniqueWords.contains(treatedWords.get(i)))
            {
            	uniqueWords.add(treatedWords.get(i));
            	uniqueWordTable.put(treatedWords.get(i), 1);//value of 1 is placed since the times that this word has repeated is 1
            }
            else
            {
            	//increases value of the number of times that the word has repeated
				uniqueWordTable.put(treatedWords.get(i), uniqueWordTable.get(treatedWords.get(i)) + 1);
            }
        }
        
        return uniqueWordTable;
    }

    public static void Conclusion(Hashtable<String, Integer> uniqueWordTable)
    {
        String csvFileDirectory = "D:\\Users\\Mickey\\eclipse-workspace\\RobotBot v0.0.2\\src\\Library.csv";
        String line;
        double positive = 1, negative = 1, neutral = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFileDirectory)))
        {
            while (null != (line = br.readLine()))
            {
                String[] featureSelectionLine = line.split(",");
                
                for (Map.Entry m: uniqueWordTable.entrySet())
                {
                    if (featureSelectionLine[0].equals(m.getKey()))
                    {
                        positive*=(Double.parseDouble(featureSelectionLine[2])*Integer.parseInt(m.getValue().toString()));
                        neutral*=(Double.parseDouble(featureSelectionLine[3])*Integer.parseInt(m.getValue().toString()));
                        negative*=(Double.parseDouble(featureSelectionLine[4])*Integer.parseInt(m.getValue().toString()));
                    }
                }
            }

            positive*=priorProbabilityPositive;
            negative*=priorProbabilityNegative;
            neutral*=priorProbabilityNeutral;

//            System.out.println("Positive Value: "+ positive);
//            System.out.println("Neutral Value: "+ neutral);
//            System.out.println("Negative Value: "+ negative);

            if (positive>negative && positive>neutral)
            {
                System.out.println("Positive Value: "+ positive);
            }

            if (negative>positive && negative>neutral)
            {
                System.out.println("Negative Value: "+ negative);
            }

            if (neutral>negative && neutral>positive)
            {
                System.out.println("Neutral Value: "+ neutral);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
