package de.tjorven.frame;

import de.tjorven.util.logger.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RocketFrame extends JFrame {

    String version;
    String title;

    public RocketFrame() {
        this("", "");
    }

    public RocketFrame(String version) {
        this(version, "");
    }

    public RocketFrame(String version, String title) {
        this(version, title, null);
    }

    public RocketFrame(String version, String title, File icon) {
        setRVersion(version);
        setRTitle(title);
        try {
            File imageIcon = new File("src/main/resources/" + icon.getPath());
            Image image = ImageIO.read(imageIcon);
            //ImageIcon imageIcon = new ImageIcon(image);
            setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRTitle() {
        return title;
    }

    public void setRTitle(String title) {
        this.title = title;
        super.setTitle(title + " " + getRVersion());
    }

    public String getRVersion() {
        return version;
    }

    public void setRVersion(String version) {
        this.version = version;
    }
}
