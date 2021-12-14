package de.tjorven.gui.login;

import de.tjorven.RocketLauncher;
import de.tjorven.frame.RocketFrame;
import de.tjorven.frame.RocketPanel;
import de.tjorven.gui.ChooseLogin;
import de.tjorven.util.auth.model.mojang.MinecraftAuthenticator;
import de.tjorven.util.auth.model.mojang.MinecraftToken;
import de.tjorven.util.auth.model.mojang.profile.MinecraftProfile;
import de.tjorven.util.configuration.file.FileConfiguration;
import de.tjorven.util.configuration.file.YamlConfiguration;
import de.tjorven.util.json.Json;
import de.tjorven.util.json.JsonParser;
import de.tjorven.util.json.model.JsonObject;
import de.tjorven.util.json.model.JsonString;
import de.tjorven.util.logger.Logger;
import org.apache.http.HttpResponse;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class Mojang extends RocketPanel {

    RocketFrame rocketFrame = RocketLauncher.getFrame();
    Mojang mojang = this;
    JLabel loginType;
    JButton back;

    Font title = new Font(getFont().getName(), Font.PLAIN, 25);
    Font text = new Font(getFont().getName(), Font.PLAIN, 14);

    JLabel password;
    JPasswordField passwordField;
    JLabel email;
    JTextField textField;
    JButton login;

    public Mojang() {
        setLayout(null);
        setComponents();
        addToPanel();
        addActionListener();
        Logger.getLogger().info("Open: " + rocketFrame.getRTitle());
        rocketFrame.setVisible(true);
    }

    public void setComponents() {
        loginType = new JLabel("Mojang Login");
        back = new JButton("back");
        rocketFrame.setRTitle("Rocket Launcher | Mojang");
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
                rocketFrame.remove(mojang);
                rocketFrame.add(new ChooseLogin());
                rocketFrame.setVisible(true);
            }
        });
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    load(textField.getText(), passwordField.getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void load(String username, String password) throws IOException {
        MinecraftAuthenticator minecraftAuthenticator = new MinecraftAuthenticator();
        MinecraftToken token = minecraftAuthenticator.login(username, password);
        MinecraftProfile minecraftProfile = minecraftAuthenticator.checkOwnership(token);

        Logger.getLogger().info("Get: Token");

        File file = new File("user/", username + ".yml");
        File file1 = new File("users.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        FileConfiguration configuration1 = YamlConfiguration.loadConfiguration(file1);

        List<String> users = configuration1.getStringList("users");

        if (!users.contains(username)) {
            users.add(username);
        }

        minecraftAuthenticator.checkOwnership(token);

        configuration.set("accessToken", token.getAccessToken());
        configuration.set("username", minecraftProfile.getUsername());
        configuration.set("uuid", minecraftProfile.getUuid().toString());
        configuration.set("mail", textField.getText());
        configuration1.set("users", users);
        configuration.save(file);
        configuration1.save(file1);

        Logger.getLogger().info("Set: Config");

        //Logger.getLogger().warn(minecraftAuthenticator.getUuid().toString());

        rocketFrame.remove(mojang);
        rocketFrame.add(new StartFrame());
        rocketFrame.setVisible(true);
    }

}
