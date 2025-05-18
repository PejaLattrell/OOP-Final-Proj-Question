package com.resumebuilder.model;

public class Education {
    private String collegeName;
    private String program;
    private String collegeYear;
    private String shsName;
    private String strand;
    private String shsYear;
    private String jhsName;
    private String jhsYear;

    public Education() {
        this.collegeName = "";
        this.program = "";
        this.collegeYear = "";
        this.shsName = "";
        this.strand = "";
        this.shsYear = "";
        this.jhsName = "";
        this.jhsYear = "";
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getCollegeYear() {
        return collegeYear;
    }

    public void setCollegeYear(String collegeYear) {
        this.collegeYear = collegeYear;
    }

    public String getShsName() {
        return shsName;
    }

    public void setShsName(String shsName) {
        this.shsName = shsName;
    }

    public String getStrand() {
        return strand;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    public String getShsYear() {
        return shsYear;
    }

    public void setShsYear(String shsYear) {
        this.shsYear = shsYear;
    }

    public String getJhsName() {
        return jhsName;
    }

    public void setJhsName(String jhsName) {
        this.jhsName = jhsName;
    }

    public String getJhsYear() {
        return jhsYear;
    }

    public void setJhsYear(String jhsYear) {
        this.jhsYear = jhsYear;
    }
}