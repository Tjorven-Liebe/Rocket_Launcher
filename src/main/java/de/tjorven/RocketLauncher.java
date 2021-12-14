package de.tjorven;

import de.tjorven.frame.RocketFrame;
import de.tjorven.gui.ChooseLogin;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme;

import javax.swing.*;
import java.io.File;
import de.tjorven.util.logger.*;

public class RocketLauncher extends RocketFrame {

    private static RocketFrame rocketFrame;

    public RocketLauncher() {
        super("#Dev1.1", "RocketLauncher", new File("icon/rocket.png"));
        Logger.getLogger().info("Starting RocketLauncher version: " + getRVersion());
        rocketFrame = this;
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new ChooseLogin());
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        FlatMaterialDeepOceanIJTheme.setup();
        UIManager.setLookAndFeel(new FlatMaterialDeepOceanIJTheme());
        new RocketLauncher();
    }

    public static RocketFrame getFrame() {
        return rocketFrame;
    }

}
