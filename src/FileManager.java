import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

    public static File getImageFromDesktop() {
        File selectedFile = null;
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        } else {
            System.out.println("didn't find the image.");
        }
        return selectedFile;
    }


    public static void saveImagesToDirectory(ArrayList<BufferedImage> images, String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                System.out.println("Failed to create directory: " + directoryPath);
                return;
            }
        }
        clearDirectory(directoryPath);
        for (int i = 0; i < images.size(); i++) {
            BufferedImage image = images.get(i);
            File outputFile = new File(directoryPath + File.separator + "image_" + (i + 1) + ".png");
            try {
                ImageIO.write(image, "png", outputFile);
            } catch (IOException e) {
                System.out.println("Failed to save image " + (i + 1) + ": " + e.getMessage());
            }
        }
        System.out.println("saved images to directory" + directoryPath);
    }

    private static void clearDirectory(String directoryPath) {
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("The specified path is invalid or not a directory.");
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("Failed to list files in the directory.");
            return;
        }

        for (File file : files) {
            deleteFileOrDirectory(file);
        }

    }

    private static void deleteFileOrDirectory(File file) {
        if (file.isDirectory()) {
            // Recursively delete contents of the directory
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteFileOrDirectory(child);
                }
            }
        }
        // Delete the file or empty directory
        if (!file.delete()) {
            System.out.println("Failed to delete: " + file.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        // Example usage
        String directoryPath = "C:/example/path/to/directory";
        clearDirectory(directoryPath);
    }

}





