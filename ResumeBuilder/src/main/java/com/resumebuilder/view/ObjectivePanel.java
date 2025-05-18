package com.resumebuilder.view;

import com.resumebuilder.model.Objective;
import javax.swing.*;
import java.awt.*;

public class ObjectivePanel extends JPanel {
    private Objective data;
    private JTextArea textArea;

    public ObjectivePanel(Objective data) {
        this.data = data;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("OBJECTIVE:");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(label, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        textArea = new JTextArea(5, 25);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, gbc);
    }

    public void updateData() {
        data.setText(textArea.getText());
    }
}