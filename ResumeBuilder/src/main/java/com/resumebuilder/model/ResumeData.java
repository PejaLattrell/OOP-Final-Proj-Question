package com.resumebuilder.model;

public class ResumeData {
    private PersonalInformation personalInformation;
    private ContactInformation contactInformation;
    private Objective objective;
    private WorkExperience workExperience;
    private Skills skills;
    private Education education;

    public ResumeData() {
        this.personalInformation = new PersonalInformation();
        this.contactInformation = new ContactInformation();
        this.objective = new Objective();
        this.workExperience = new WorkExperience();
        this.skills = new Skills();
        this.education = new Education();
    }

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public WorkExperience getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(WorkExperience workExperience) {
        this.workExperience = workExperience;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }
}