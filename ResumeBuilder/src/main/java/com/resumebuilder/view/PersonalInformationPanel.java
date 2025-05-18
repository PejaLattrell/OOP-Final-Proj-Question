package com.resumebuilder.view;

import com.resumebuilder.model.PersonalInformation;
import javax.swing.*;
import java.awt.*;

public class PersonalInformationPanel extends JPanel {
    private PersonalInformation data;
    private JTextField[] fields;

    public PersonalInformationPanel(PersonalInformation data) {
        this.data = data;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("PERSONAL INFORMATION:");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(label, gbc);

        String[] fieldLabels = {
            "FULL NAME:", "AGE:", "SEX:", "DATE OF BIRTH:", 
            "PLACE OF BIRTH:", "CITIZENSHIP:", "HEIGHT:", "WEIGHT:",
            "RELIGION:", "LANGUAGES:"
        };
        fields = new JTextField[fieldLabels.length];

        for (int i = 0; i < fieldLabels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            add(new JLabel(fieldLabels[i]), gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            fields[i] = new JTextField(15);
            add(fields[i], gbc);
        }
    }

    public void updateData() {
        for (int i = 0; i < fields.length; i++) {
            data.getFields().set(i, fields[i].getText());
        }
    }
}