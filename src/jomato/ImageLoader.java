package jomato;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageLoader {
    public Image loadImage(String fileName) {
        URL url = getClass().getClassLoader().getResource(fileName);
        return new ImageIcon(url).getImage();
    }
}
