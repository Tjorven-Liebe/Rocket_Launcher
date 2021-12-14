package de.tjorven.gui.login;

import de.tjorven.RocketLauncher;
import de.tjorven.frame.RocketFrame;
import de.tjorven.frame.RocketPanel;
import de.tjorven.gui.ChooseLogin;
import de.tjorven.util.auth.model.microsoft.MicrosoftAuthenticator;
import de.tjorven.util.auth.model.microsoft.MicrosoftToken;
import de.tjorven.util.auth.model.microsoft.XboxLiveToken;
import de.tjorven.util.auth.model.microsoft.XboxToken;
import de.tjorven.util.auth.model.mojang.MinecraftAuthenticator;
import de.tjorven.util.auth.model.mojang.MinecraftToken;
import de.tjorven.util.auth.model.mojang.profile.MinecraftProfile;
import de.tjorven.util.configuration.file.FileConfiguration;
import de.tjorven.util.configuration.file.YamlConfiguration;
import de.tjorven.util.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Microsoft extends RocketPanel {

    RocketFrame rocketFrame = RocketLauncher.getFrame();
    Microsoft microsoft = this;
    JButton back;
    JLabel loginType;

    Font title = new Font(getFont().getName(), Font.PLAIN, 25);
    Font text = new Font(getFont().getName(), Font.PLAIN, 14);

    JLabel password;
    JPasswordField passwordField;
    JLabel email;
    JTextField textField;
    JButton login;

    public Microsoft() {
        setLayout(null);
        setComponents();
        addToPanel();
        addActionListener();
        Logger.getLogger().info("Open: " + rocketFrame.getRTitle());
        rocketFrame.setVisible(true);
    }

    public void setComponents() {
        back = new JButton("back");
        loginType = new JLabel("Microsoft Login");
        rocketFrame.setRTitle("Rocket Launcher | Microsoft");
        password = new JLabel("Password");
        login = new JButton("Login");
        passwordField = new JPasswordField();
        textField = new JTextField();
        email = new JLabel("E-Mail");
    }

    public void addToPanel() {
        add(back);
        add(loginType);
        add(password);
        add(passwordField);
        add(textField);
        add(email);
        add(login);

        back.setBounds(25, 50, 100, 30);
        loginType.setBounds(200, 45, 200, 40);

        email.setBounds(50, 150, 100, 50);
        textField.setBounds(150, 150, 300, 50);
        password.setBounds(50, 210, 100, 50);
        passwordField.setBounds(150, 210, 300, 50);

        login.setBounds(15, 450, 450, 50);

        loginType.setFont(title);
        password.setFont(text);
        email.setFont(text);
    }

    public void addActionListener() {
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rocketFrame.remove(microsoft);
                rocketFrame.add(new ChooseLogin());
                rocketFrame.setVisible(true);
            }
        });
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textField.getText() != null && passwordField.getText() != null) {
                        login(textField.getText(), passwordField.getText());

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void login(String username, String password) throws IOException {
        MinecraftAuthenticator minecraftAuthenticator = new MinecraftAuthenticator();
        MinecraftToken minecraftToken = minecraftAuthenticator.loginWithXbox(textField.getText(), passwordField.getText());
        MinecraftProfile minecraftProfile = minecraftAuthenticator.checkOwnership(minecraftToken);

        Logger.getLogger().info("Get: Token");

        File file = new File("user/", username + ".yml");
        File file1 = new File("users.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        FileConfiguration configuration1 = YamlConfiguration.loadConfiguration(file1);

        List<String> users = configuration1.getStringList("users");

        if (!users.contains(username)) {
            users.add(username);
        }

        Logger.getLogger().info("write To Config:");

        configuration.set("accessToken", minecraftToken.getAccessToken());
        configuration.set("email", username);
        Logger.getLogger().info(username);
        configuration.set("uuid", minecraftProfile.getUuid().toString());
        Logger.getLogger().info(minecraftProfile.getUuid().toString());
        configuration.set("username", minecraftProfile.getUsername());
        Logger.getLogger().info(minecraftProfile.getUsername());

        configuration1.set("users", users);
        configuration.save(file);
        configuration1.save(file1);

        Logger.getLogger().info("Set: Config");

        rocketFrame.remove(microsoft);
        rocketFrame.add(new StartFrame());
        rocketFrame.setVisible(true);
    }

}
