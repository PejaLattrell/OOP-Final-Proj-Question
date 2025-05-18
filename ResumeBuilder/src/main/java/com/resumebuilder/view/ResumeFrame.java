package com.resumebuilder.view;

import com.resumebuilder.controller.InputValidator;
import com.resumebuilder.controller.ResumeController;
import com.resumebuilder.model.ResumeData;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ResumeFrame extends JFrame {
    private ResumeData data;
    private PersonalInformationPanel personalInfoPanel;
    private ContactInformationPanel contactInfoPanel;
    private ObjectivePanel objectivePanel;
    private WorkExperiencePanel workExperiencePanel;
    private SkillsPanel skillsPanel;
    private EducationPanel educationPanel;

    public ResumeFrame(ResumeController controller) {
        setTitle("Resume Builder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        data = new ResumeData();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("RESUME BUILDER", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        mainPanel.add(titleLabel, gbc);

        // Left Column: Personal Information, Work Experience, Skills
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.insets = new Insets(5, 5, 5, 5);
        leftGbc.fill = GridBagConstraints.HORIZONTAL;

        personalInfoPanel = new PersonalInformationPanel(data.getPersonalInformation());
        leftGbc.gridx = 0;
        leftGbc.gridy = 0;
        leftPanel.add(personalInfoPanel, leftGbc);

        workExperiencePanel = new WorkExperiencePanel(data.getWorkExperience());
        leftGbc.gridy = 1;
        leftPanel.add(workExperiencePanel, leftGbc);

        skillsPanel = new SkillsPanel(data.getSkills());
        leftGbc.gridy = 2;
        leftPanel.add(skillsPanel, leftGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.33;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(leftPanel, gbc);

        // Middle Column: Contact Information, Objective
        JPanel middlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints midGbc = new GridBagConstraints();
        midGbc.insets = new Insets(5, 5, 5, 5);
        midGbc.fill = GridBagConstraints.HORIZONTAL;

        contactInfoPanel = new ContactInformationPanel(data.getContactInformation());
        midGbc.gridx = 0;
        midGbc.gridy = 0;
        middlePanel.add(contactInfoPanel, midGbc);

        objectivePanel = new ObjectivePanel(data.getObjective());
        midGbc.gridy = 1;
        middlePanel.add(objectivePanel, midGbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.33;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(middlePanel, gbc);

        // Right Column: Education
        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(5, 5, 5, 5);
        rightGbc.fill = GridBagConstraints.HORIZONTAL;

        educationPanel = new EducationPanel(data.getEducation());
        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightPanel.add(educationPanel, rightGbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.33;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(rightPanel, gbc);

        // Bottom Section: Template Selection and Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        String[] templateOptions = {"Default", "Two-Column"};
        JComboBox<String> templateCombo = new JComboBox<>(templateOptions);
        bottomPanel.add(new JLabel("SELECT TEMPLATE:"));
        bottomPanel.add(templateCombo);

        JButton previewButton = new JButton("Preview");
        previewButton.addActionListener(e -> {
            updateData();
            List<String> errors = InputValidator.validateResumeData(data);
            if (!errors.isEmpty()) {
                showValidationErrors(errors);
            } else {
                controller.handlePreview(templateCombo.getSelectedItem().toString());
            }
        });
        bottomPanel.add(previewButton);

        JButton generateButton = new JButton("GENERATE RESUME!");
        generateButton.addActionListener(e -> {
            updateData();
            List<String> errors = InputValidator.validateResumeData(data);
            if (!errors.isEmpty()) {
                showValidationErrors(errors);
            } else {
                controller.handleGenerate(templateCombo.getSelectedItem().toString());
            }
        });
        bottomPanel.add(generateButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(bottomPanel, gbc);

        add(mainPanel);
        setVisible(true);
    }

    public ResumeData getData() {
        updateData();
        return data;
    }

    private void updateData() {
        personalInfoPanel.updateData();
        contactInfoPanel.updateData();
        objectivePanel.updateData();
        workExperiencePanel.updateData();
        skillsPanel.updateData();
        educationPanel.updateData();
    }

    private void showValidationErrors(List<String> errors) {
        StringBuilder message = new StringBuilder("Please fix the following errors:\n");
        for (String error : errors) {
            message.append("- ").append(error).append("\n");
        }
        JOptionPane.showMessageDialog(this, message.toString(), "Validation Errors", JOptionPane.ERROR_MESSAGE);
    }
}