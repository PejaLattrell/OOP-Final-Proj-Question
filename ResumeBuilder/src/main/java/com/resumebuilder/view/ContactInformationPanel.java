package com.resumebuilder.view;

import com.resumebuilder.model.ContactInformation;
import javax.swing.*;
import java.awt.*;

public class ContactInformationPanel extends JPanel {
    private ContactInformation data;
    private JTextField[] fields;

    public ContactInformationPanel(ContactInformation data) {
        this.data = data;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("CONTACT INFORMATION:");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(label, gbc);

        String[] fieldLabels = {"CONTACT NUMBER:", "EMAIL ADDRESS:", "ADDRESS:"};
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