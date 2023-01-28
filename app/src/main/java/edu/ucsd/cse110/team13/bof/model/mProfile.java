package edu.ucsd.cse110.team13.bof.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class mProfile implements IProfile {
    private String   name;
    private String   headShotUrl;
    private IClass[] pastClasses;

    public  boolean  favorite = false;

    public mProfile(String name, String headShotUrl) {
        this.name = name;
        this.headShotUrl = headShotUrl;
        this.pastClasses = new IClass[0];
    }

    public mProfile(String name, String headShotUrl, IClass[] pastClasses) {
        this.name = name;
        this.headShotUrl = headShotUrl;
        this.pastClasses = new IClass[0];

        ArrayList<IClass> result = new ArrayList<>();
        for(IClass c : pastClasses) { if(!result.contains(c)) { result.add(c); } }
        this.pastClasses = result.toArray(this.pastClasses);
    }

    public String getFirstName()     { return name; }
    public String getHeadshotUrl()   { return headShotUrl; }
    public List<IClass> getClasses() { return Arrays.asList(pastClasses); }
    public boolean isFavorite()      { return favorite; }

    public List<IClass> getCommonClasses(IProfile profile) {
        if(profile == null) { return new ArrayList<>(); }

        ArrayList<IClass> result    = new ArrayList<>();
        ArrayList<IClass> myClasses = new ArrayList<>(Arrays.asList(pastClasses));
        for(IClass c : profile.getClasses()) {
            if(myClasses.contains(c)) { result.add(c); }
        }
        return result;
    }

    public boolean hasCommonClasses(IProfile profile) {
        if(profile == null) { return false; }

        ArrayList<IClass> myClasses = new ArrayList<>(Arrays.asList(pastClasses));
        for(IClass c : profile.getClasses()) {
            if(myClasses.contains(c)) { return true; }
        }
        return false;
    }

    public boolean addClass(IClass c) {
        List<IClass> temp = new ArrayList<>(Arrays.asList(pastClasses));

        if(temp.contains(c)) { return false; }

        temp.add(c);
        pastClasses = temp.toArray(pastClasses);
        return true;
    }

    public int getMostRecentScore(IProfile profile){
        if(profile == null) { return 0; }

        int result=0;
        List<IClass> myClasses    = this.getCommonClasses(profile);
        for(int i=0;i<myClasses.size();i++) {
            if(myClasses.get(i).getQuarter()==Quarter.FA&&myClasses.get(i).getYear()==2021) { result=result+5; }
            else if(myClasses.get(i).getQuarter()==Quarter.SS2&&myClasses.get(i).getYear()==2021) { result=result+4; }
            else if(myClasses.get(i).getQuarter()==Quarter.SS1&&myClasses.get(i).getYear()==2021) { result=result+4; }
            else if(myClasses.get(i).getQuarter()==Quarter.SSS&&myClasses.get(i).getYear()==2021) { result=result+4; }
            else if(myClasses.get(i).getQuarter()==Quarter.SP&&myClasses.get(i).getYear()==2021) { result=result+3; }
            else if(myClasses.get(i).getQuarter()==Quarter.WI&&myClasses.get(i).getYear()==2021) { result=result+2; }
            else  { result=result+1; }
        }
        return result;
    }

    public boolean hasCommonClassesThisQuarter(IProfile profile) {
        if(profile == null) { return false; }
        for(int i=0;i<profile.getClasses().size();i++) {
            if(profile.getClasses().get(i).getQuarter()==Quarter.WI&&profile.getClasses().get(i).getYear()==2022) {
                return true;
            }

        }
        return false;
    }

    public float getWeight(IProfile profile){
        if(profile == null) { return 0; }

        float result=0;
        List<IClass> myClasses    = this.getCommonClasses(profile);
        for(int i=0;i<myClasses.size();i++) {
            result=myClasses.get(i).getSize().getSizeWeight()+result;
        }
        return result;
    }
}
