package de.tjorven.gui;

import de.tjorven.RocketLauncher;
import de.tjorven.frame.RocketFrame;
import de.tjorven.frame.RocketPanel;
import de.tjorven.gui.login.Microsoft;
import de.tjorven.gui.login.Mojang;
import de.tjorven.gui.login.StartFrame;
import de.tjorven.util.configuration.file.FileConfiguration;
import de.tjorven.util.configuration.file.YamlConfiguration;
import de.tjorven.util.logger.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ChooseLogin extends RocketPanel {

    RocketFrame rocketFrame = RocketLauncher.getFrame();
    ChooseLogin chooseLogin;
    JButton microsoft;
    JButton mojang;

    public ChooseLogin() {
        setLayout(null);

        File file1 = new File("users.yml");
        FileConfiguration configuration1 = YamlConfiguration.loadConfiguration(file1);

        if (configuration1.getStringList("users").isEmpty()) {
            setContent();
            addToPanel();
            chooseLogin = this;
            addActionListener();
            Logger.getLogger().info("Open: " + rocketFrame.getRTitle());
            repaint();
        }else {
            rocketFrame.remove(this);
            rocketFrame.add(new StartFrame());
            rocketFrame.setVisible(true);
        }
    }

    public void addActionListener() {
        microsoft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rocketFrame.remove(chooseLogin);
                rocketFrame.add(new Microsoft());
                rocketFrame.setVisible(true);
            }
        });
        mojang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rocketFrame.remove(chooseLogin);
                rocketFrame.add(new Mojang());
                rocketFrame.setVisible(true);
            }
        });
    }

    public void setContent() {
        microsoft = new JButton("Microsoft");
        mojang = new JButton("Mojang");
        rocketFrame.setRTitle("Rocket Launcher | Choose Login");
    }

    public void addToPanel() {
        add(microsoft);
        add(mojang);
        int marginTop = (rocketFrame.getHeight() / 2) - 110;
        int width = rocketFrame.getWidth() - 100;
        int marginSides = 50;
        microsoft.setBounds(marginSides, marginTop, width, 50);
        mojang.setBounds(marginSides, marginTop + 110, width, 50);
    }

}