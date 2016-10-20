package com.example.kashishgrover.mttn;

/**
 * Created by Kashish Grover on 10/21/2016.
 */
public class ListItem {

    private String subjectName;
    private String subjectParameters;

    public ListItem(String subjectName, String subjectParameters) {
        super();
        this.subjectName = subjectName;
        this.subjectParameters = subjectParameters;
    }
    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public String getSubjectParameters() {
        return subjectParameters;
    }
    public void setSubjectParameters(String subjectParameters) {
        this.subjectParameters = subjectParameters;
    }
}