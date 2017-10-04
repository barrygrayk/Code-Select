package com.child.Model;

/**
 *
 * @author efsan1
 */
public class MedicalExam {
    private int medicalExamId;
    private String examTitle;
    private String resultComments;

    public int getMedicalExamId() {
        return medicalExamId;
    }

    public void setMedicalExamId(int medicalExamId) {
        this.medicalExamId = medicalExamId;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getResultComments() {
        return resultComments;
    }

    public void setResultComments(String resultComments) {
        this.resultComments = resultComments;
    }
    
}
