package de.tjorven.gui.login;

import de.tjorven.RocketLauncher;
import de.tjorven.frame.RocketFrame;
import de.tjorven.frame.RocketPanel;
import de.tjorven.util.configuration.file.FileConfiguration;
import de.tjorven.util.configuration.file.YamlConfiguration;
import de.tjorven.util.logger.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class StartFrame extends RocketPanel {

    private static JPanel lastSelected = new JPanel();
    String selectedUniqueId;
    RocketFrame rocketFrame = RocketLauncher.getFrame();
    StartFrame startFrame = this;
    JList<String> list;
    JLabel imageLabel;
    JScrollPane scrollPane;
    JButton start;

    public StartFrame() {
        setComponents();
        setLayout(null);
        addToPanel();
        addActionListener();
        Logger.getLogger().info("Open: " + rocketFrame.getRTitle());
        rocketFrame.setVisible(true);
    }

    public void setComponents() {
        start = new JButton("Start");
        rocketFrame.setRTitle("Rocket Launcher");

        addToList();

        imageLabel = new JLabel();
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);

    }

    public void addActionListener() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.getLogger().info(list.getSelectedValue());
            }
        });

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    File file = new File("user/", list.getSelectedValue() + ".yml");
                    FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

                    Image image = ImageIO.read(new URL("http://mc-heads.net/body/" + configuration.getString("username") + ".png"));
                    imageLabel.setIcon(new ImageIcon(image));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public void addToPanel() {
        add(start);
        add(scrollPane);
        add(imageLabel);

        imageLabel.setBounds(330, 15, 300, 300);
        rocketFrame.setSize(700, 600);
        scrollPane.setBounds(15, 15, 300, 500);
        start.setBounds(350, 500, 315, 50);
    }

    public void addToList() {
        File file1 = new File("users.yml");
        FileConfiguration configuration1 = YamlConfiguration.loadConfiguration(file1);

        ArrayList<String> myList = new ArrayList<>();
        for (String string : configuration1.getStringList("users")) {
            myList.add(string);
        }

        list = new JList<String>(myList.toArray(new String[0]));
    }
}