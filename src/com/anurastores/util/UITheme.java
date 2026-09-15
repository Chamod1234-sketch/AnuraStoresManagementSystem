package com.anurastores.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;

public class UITheme {

    // =========================================
    // COLORS
    // =========================================
    public static final Color PRIMARY = new Color(34, 112, 147);
    public static final Color PRIMARY_DARK = new Color(25, 85, 112);
    public static final Color SECONDARY = new Color(46, 204, 113);
    public static final Color WARNING = new Color(241, 196, 15);
    public static final Color DANGER = new Color(231, 76, 60);
    public static final Color LIGHT_BG = new Color(245, 247, 250);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color TEXT_DARK = new Color(44, 62, 80);
    public static final Color TEXT_LIGHT = Color.WHITE;
    public static final Color BORDER = new Color(210, 215, 223);

    // =========================================
    // FONTS
    // =========================================
    public static final Font TITLE_FONT =
            new Font("Segoe UI", Font.BOLD, 24);

    public static final Font SUBTITLE_FONT =
            new Font("Segoe UI", Font.BOLD, 16);

    public static final Font NORMAL_FONT =
            new Font("Segoe UI", Font.PLAIN, 14);

    public static final Font BUTTON_FONT =
            new Font("Segoe UI", Font.BOLD, 14);

    public static final Font TABLE_FONT =
            new Font("Segoe UI", Font.PLAIN, 13);

    public static final Font TABLE_HEADER_FONT =
            new Font("Segoe UI", Font.BOLD, 13);


    // =========================================
    // GENERAL FRAME / PANEL
    // =========================================
    public static void stylePanel(JPanel panel) {
        panel.setBackground(LIGHT_BG);
    }

    public static void styleCardPanel(JPanel panel) {
        panel.setBackground(CARD_BG);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER, 1),
                        BorderFactory.createEmptyBorder(12, 12, 12, 12)
                )
        );
    }

    public static void styleTitleLabel(JLabel label) {
        label.setFont(TITLE_FONT);
        label.setForeground(TEXT_DARK);
    }

    public static void styleSubtitleLabel(JLabel label) {
        label.setFont(SUBTITLE_FONT);
        label.setForeground(TEXT_DARK);
    }

    public static void styleNormalLabel(JLabel label) {
        label.setFont(NORMAL_FONT);
        label.setForeground(TEXT_DARK);
    }


    // =========================================
    // TEXT FIELDS / COMBO
    // =========================================
    public static void styleTextField(JTextField field) {
        field.setFont(NORMAL_FONT);
        field.setPreferredSize(new Dimension(180, 32));
        field.setBorder(createRoundedBorder());
    }

    public static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(NORMAL_FONT);
        comboBox.setPreferredSize(new Dimension(180, 32));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(createRoundedBorder());
    }

    private static Border createRoundedBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        );
    }


    // =========================================
    // BUTTONS
    // =========================================
    public static void stylePrimaryButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(PRIMARY);
        button.setForeground(TEXT_LIGHT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    public static void styleSuccessButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(SECONDARY);
        button.setForeground(TEXT_LIGHT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    public static void styleWarningButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(WARNING);
        button.setForeground(TEXT_DARK);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    public static void styleDangerButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(DANGER);
        button.setForeground(TEXT_LIGHT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    public static void styleSecondaryButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(Color.WHITE);
        button.setForeground(TEXT_DARK);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(BORDER, 1));
    }


    // =========================================
    // TABLE
    // =========================================
    public static void styleTable(JTable table) {
        table.setFont(TABLE_FONT);
        table.setRowHeight(24);
        table.setGridColor(BORDER);
        table.setSelectionBackground(new Color(204, 229, 255));
        table.setSelectionForeground(TEXT_DARK);

        JTableHeader header = table.getTableHeader();
        header.setFont(TABLE_HEADER_FONT);
        header.setBackground(PRIMARY);
        header.setForeground(TEXT_LIGHT);
    }


    // =========================================
    // TITLED BORDER
    // =========================================
    public static TitledBorder createSectionBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                title
        );
        border.setTitleFont(SUBTITLE_FONT);
        border.setTitleColor(TEXT_DARK);
        return border;
    }


    // =========================================
    // APPLY DEFAULT LABEL STYLE
    // =========================================
    public static void styleAllLabels(Component component) {
        if (component instanceof JLabel) {
            styleNormalLabel((JLabel) component);
        }

        if (component instanceof JPanel) {
            Component[] components = ((JPanel) component).getComponents();
            for (Component child : components) {
                styleAllLabels(child);
            }
        }
    }


    // =========================================
    // GENERIC COMPONENT BACKGROUND
    // =========================================
    public static void setWhiteBackground(JComponent component) {
        component.setBackground(Color.WHITE);
    }
}