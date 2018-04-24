package pcsetgui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PCSet
{
    
    private ArrayList<Integer> pcSet = new ArrayList<>();
    private ArrayList<Integer> normalForm = new ArrayList<>();
    private ArrayList<Integer> t0Inversion = new ArrayList<>();
    private ArrayList<Integer> primeForm = new ArrayList<>(0);
    private final String cardinality;
    private final String intervalVector;
    
    //constructor
    public PCSet(ArrayList<Integer> inSet)
    {
        //fill pcSet with inSet
        inSet.forEach((pitch) -> {
            this.pcSet.add(pitch);
        });
        
        //gets rid of duplicaates
        Set<Integer> deDuper = new HashSet<>();
        deDuper.addAll(pcSet);        
        
        ArrayList<Integer> workingCopy = new ArrayList<>(deDuper);
        Collections.sort(workingCopy);
        this.normalForm = new ArrayList<>(toNormalForm(workingCopy));
        this.t0Inversion = new ArrayList<>(fillInversion(this.normalForm));
        this.cardinality = setCardinality(this.normalForm.size());
        this.intervalVector = setIntervalVector(this.normalForm);
        this.primeForm = new ArrayList<>(fillPrimeForm(this.normalForm, this.t0Inversion));
    }
    
    public ArrayList<Integer> getNormalForm()
    {
        return this.normalForm;
    }
    public ArrayList<Integer> getT0Inversion()
    {
        return this.t0Inversion;
    }
    public String getCardinality()
    {
        return this.cardinality;
    }
    public ArrayList<Integer> getPrimeForm()
    {
        return this.primeForm;
    }
    private String setCardinality(int size)
    {
        StringBuilder card = new StringBuilder();
        
        switch(size)
        {
            case 3:
                card.append("Trichord");
                break;
            case 4:
                card.append("Tetrachord");
                break;
            case 5: 
                card.append("Pentachord");
                break;
            case 6:
                card.append("Hexachord");
                break;
            case 7:
                card.append("Septachord");
                break;
            case 8:
                card.append("Octochord");
                break;
            case 9:
                card.append("Nonachord");
                break;
            default:
                card.append(Integer.toString(size));
                break;
        }
        
        return card.toString();
    }
    public String getIntervalVector()
    {
        return this.intervalVector;
    }
    @Override
    public String toString()
    {
        StringBuilder thisThangIsAStrang = new StringBuilder();
        
        //first the original input
        thisThangIsAStrang.append("\n\nOriginal pcset: { ");
        this.pcSet.forEach( (pitch) -> {
            thisThangIsAStrang.append(pitch).append(" ");
        });
        thisThangIsAStrang.append("}");
        
        // normal form
        thisThangIsAStrang.append("\n\nNormalForm: { ");
        this.normalForm.forEach( (pitch) -> {
            thisThangIsAStrang.append(pitch).append(" ");
        });
        thisThangIsAStrang.append("}");
        
        //t0 inversion
        thisThangIsAStrang.append("\n\nT0Inversion: { ");
        this.t0Inversion.forEach( (pitch) -> {
            thisThangIsAStrang.append(pitch).append(" ");
        });
        thisThangIsAStrang.append("}");
        
        //prime form
        thisThangIsAStrang.append("\n\nPrime Form: { ");
        this.primeForm.forEach( (pitch) -> {
            thisThangIsAStrang.append(pitch).append(" ");
        });
        thisThangIsAStrang.append("}");
        
        //cardinality
        thisThangIsAStrang.append("\n\nCardinality: ").append(this.cardinality);
        
        //interval vector
        thisThangIsAStrang.append("\n\nInterval Vector: ").append(this.intervalVector).append("\n\n");
        
        return thisThangIsAStrang.toString();
    }
    
    //this method fills this.normalForm as part of the constructor
    private ArrayList<Integer> toNormalForm(ArrayList<Integer> inputSet)
    {
        //normalForm starts as inputForm by default
        ArrayList<Integer> normalFormFound = new ArrayList<>(inputSet);
        
        //loop checks each form of inputForm
        for(int i = 0; i < inputSet.size(); i++)
        {
            //ArrayList to hold current test form. Each iteration iterates up one form of inputSet
            ArrayList<Integer> testForm = new ArrayList<>(getDifferentForm(inputSet, i));
            //check normal form vs form to test. If normalForm is more normal than test form it should just return itself back, otherwise normalForm will now be testForm.
            normalFormFound = new ArrayList<>(checkFormNormality(normalFormFound, testForm, inputSet.size() - 1));
        }
        
        return normalFormFound;
    }
    
    //gets pitch difference between first pitch in form and pitch at the specified index
    private int getPitchDifference(ArrayList<Integer> inputForm, int lowIndexToCheck, int highIndexToCheck)
    {
        //first gets the first pitch in the form
        int first = inputForm.get(lowIndexToCheck);
        int last;
        try
        {
            //last gets the pitch at the specified index
            last = inputForm.get(highIndexToCheck);
        } catch(IndexOutOfBoundsException e)
        {
            //in case indexToCheck is too big, print this message and return the original index.
            System.out.println("Error:"
                    + "\nWhere: PCSet.getPtichDifference()"
                    + "\nWhy: parameter \"indexToCheck\" is out of range in inputForm"
                    + "\nThe bad index was " + highIndexToCheck + "\nIt has been returned to its caller.");
            return highIndexToCheck;
        }
        
        //get the differenc between last and first. If the difference goes below 0 we mod12 it to keep it within the musical aggragate.
        int difference = Math.floorMod(last - first, 12);
        
        return difference;
    }
    
    //this method should only take in the original form and return the nth form where n is formChange-1
    private ArrayList<Integer> getDifferentForm(ArrayList<Integer> inputForm, int formChange)
    {
        ArrayList<Integer> outForm = new ArrayList<>(inputForm);
        
        //foreach to fill outForm
        for(int element : inputForm)
        {
            //add the current element at index formChange
            outForm.set(formChange, element);
            //iterate formChange
            formChange++;
            //make sure formChange stays between 0 and the size of the original ArrayList
            formChange = Math.floorMod(formChange, inputForm.size());
        }
        
        return outForm;
    }
    
    //this method helps toNormalForm()'s main loop check which form is most "normal", the current most normal form or the next form in its iteration
    //this method is recursive
    private ArrayList<Integer> checkFormNormality(ArrayList<Integer> inputForm1, ArrayList<Integer> inputForm2, int indexToCheck)
    {
        //if there's only one element left then the forms are equal and the first one is returned by default.
        if(indexToCheck == 1)
            return inputForm1;
        //check which one's difference is smaller
        if(getPitchDifference(inputForm1, 0, indexToCheck) < getPitchDifference(inputForm2, 0, indexToCheck))
        {
            //if the difference between the first and last pitches of inputForm1 is smaller, then inputForm1 is more normal so we return it
            return inputForm1;
        } else if(getPitchDifference(inputForm1, 0, indexToCheck) > getPitchDifference(inputForm2, 0, indexToCheck))
        {
            //if the difference between the first and last pitches of inputForm2 is smaller, then inputForm2 is more normal so we return it
            return inputForm2;
        } else
        {
            //so, if indexToCheck is not 1 and the calculated differences are the same between both forms, make recursive call, iterating down to the index below indexToCheck
            return checkFormNormality(inputForm1, inputForm2, indexToCheck - 1);
        }
    }
    
    //method to fill t0Inversion for constructor
    private ArrayList<Integer> fillInversion(ArrayList<Integer> inSet)
    {
        //arraylist to return
        ArrayList<Integer> set = new ArrayList<>();
        ArrayList<Integer> outSet;
        inSet.forEach((element) -> {
            //fill outset with the mod12 equivilent of 0 - the current element, which is the definition of the inverted ptich.
            set.add(Math.floorMod(12 - element, 12));
        });
        
        Collections.sort(set);
        
        outSet = new ArrayList<>(toNormalForm(set));
        
        return outSet;
    }
    
    //finds the stringIntervalVector and fills the intervalVector class field
    private String setIntervalVector(ArrayList<Integer> inSet)
    {
        //6 elements, one for each of the interval classes
        int[] intervalVectorFound = {0, 0, 0, 0, 0, 0};
        
        //iterate through each element of inSet
        for(int i = 0; i < inSet.size() - 1; i++)
        {
            
            for(int j = i + 1; j < inSet.size(); j++)
            {
                int difference = getPitchDifference(inSet, i, j);
                switch(difference)
                {
                case 1: case 11:
                    intervalVectorFound[0]++;
                    break;
                case 2: case 10:
                    intervalVectorFound[1]++;
                    break;
                case 3: case 9:
                    intervalVectorFound[2]++;
                    break;
                case 4: case 8:
                    intervalVectorFound[3]++;
                    break;
                case 5: case 7:
                    intervalVectorFound[4]++;
                    break;
                case 6:
                    intervalVectorFound[5]++;
                    break;
                default:
                    break;
                }
            }
        }
        StringBuilder stringBuild = new StringBuilder();
        //build string from intervalVector array
        for(int element : intervalVectorFound)
        {
            switch (element)
            {
                case 10:
                    stringBuild.append("t");
                    break;
                case 11:
                    stringBuild.append("e");
                    break;
                default:
                    stringBuild.append(element);
                    break;
            }
            
        }
        return stringBuild.toString();
    }
    
    //fill prime form
    private ArrayList<Integer> fillPrimeForm(ArrayList<Integer> normFormIn, ArrayList<Integer> t0InvIn)
    {
        ArrayList<Integer> outList;
        
        ArrayList<Integer> transNorm = new ArrayList<>(transpose(normFormIn, 12 - normFormIn.get(0)));
        ArrayList<Integer> transT0Inv = new ArrayList<>(transpose(t0InvIn, 12 - t0InvIn.get(0)));
        
        outList = new ArrayList<>(checkFormNormality(transNorm, transT0Inv, normFormIn.size() - 1));
        
        return outList;
    }
    
    //transposes set
    public ArrayList<Integer> transpose(ArrayList<Integer> inSet, int transDeg)
    {
        ArrayList<Integer> outSet = new ArrayList<>(inSet);
        
        int i = 0;
        for(int element : inSet)
        {
            int newPitch = Math.floorMod(element + transDeg, 12);
            outSet.set(i, newPitch);
            i++;
        }
        
        return outSet;
    }
}