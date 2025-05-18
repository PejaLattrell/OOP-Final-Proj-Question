package com.resumebuilder.view;

import com.resumebuilder.model.Education;
import javax.swing.*;
import java.awt.*;

public class EducationPanel extends JPanel {
    private Education data;
    private JTextField collegeNameField;
    private JTextField programField;
    private JTextField collegeYearField;
    private JTextField shsNameField;
    private JTextField strandField;
    private JTextField shsYearField;
    private JTextField jhsNameField;
    private JTextField jhsYearField;

    public EducationPanel(Education data) {
        this.data = data;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("EDUCATION:");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(label, gbc);

        // College
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("COLLEGE SCHOOL:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        collegeNameField = new JTextField(15);
        add(collegeNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("NAME OF PROGRAM:"), gbc);
        gbc.gridx = 1;
        programField = new JTextField(15);
        add(programField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("ACADEMIC YEAR:"), gbc);
        gbc.gridx = 1;
        collegeYearField = new JTextField(15);
        add(collegeYearField, gbc);

        // Senior High School
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("SENIOR HIGH SCHOOL:"), gbc);
        gbc.gridx = 1;
        shsNameField = new JTextField(15);
        add(shsNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("NAME OF STRAND:"), gbc);
        gbc.gridx = 1;
        strandField = new JTextField(15);
        add(strandField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("ACADEMIC YEAR:"), gbc);
        gbc.gridx = 1;
        shsYearField = new JTextField(15);
        add(shsYearField, gbc);

        // Junior High School
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("JUNIOR HIGH SCHOOL:"), gbc);
        gbc.gridx = 1;
        jhsNameField = new JTextField(15);
        add(jhsNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JLabel("ACADEMIC YEAR:"), gbc);
        gbc.gridx = 1;
        jhsYearField = new JTextField(15);
        add(jhsYearField, gbc);
    }

    public void updateData() {
        data.setCollegeName(collegeNameField.getText());
        data.setProgram(programField.getText());
        data.setCollegeYear(collegeYearField.getText());
        data.setShsName(shsNameField.getText());
        data.setStrand(strandField.getText());
        data.setShsYear(shsYearField.getText());
        data.setJhsName(jhsNameField.getText());
        data.setJhsYear(jhsYearField.getText());
    }
}