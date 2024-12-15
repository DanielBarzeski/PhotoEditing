import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


class ImageManipulation {
    public enum EModify{pixel,noise,gray_scale,poster,contrast,brighter,darker,negative,solarize,sepia,vintage}

    public static BufferedImage fix(BufferedImage image, int width, int height) {
        if (image != null) {
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.dispose();
            return resizedImage;
        }
        return null;
    }
        public static BufferedImage applyEffect(EModify modify, BufferedImage image) {
            if (modify == EModify.pixel) {
               return applyPixelateEffect(image,5);
            }
            BufferedImage result = deepCopy(image);

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    if (Data.getPOLYGON().contains(x, y)) {
                        int rgb = image.getRGB(x, y);
                        Color originalColor = new Color(rgb, true);
                        Color modifiedColor = originalColor;
                        if (modify == EModify.gray_scale) {
                            modifiedColor = applyGrayScaleEffect(originalColor);
                        }else if (modify == EModify.darker) {
                            modifiedColor = applyDarkerEffect(originalColor);
                        }else if (modify == EModify.brighter) {
                            modifiedColor = applyBrighterEffect(originalColor);
                        }else if (modify == EModify.poster) {
                            modifiedColor = applyPosterEffect(originalColor);
                        }else if (modify == EModify.contrast) {
                            modifiedColor = applyContrastEffect(originalColor);
                        }else if (modify == EModify.negative) {
                            modifiedColor = applyNegativeEffect(originalColor);
                        }else if (modify == EModify.solarize) {
                            modifiedColor = applySolarizeEffect(originalColor);
                        }else if (modify == EModify.noise) {
                            modifiedColor = applyNoiseEffect(originalColor);
                        }else if (modify == EModify.sepia) {
                            modifiedColor = applySepiaEffect(originalColor);
                        }else if (modify == EModify.vintage) {
                            modifiedColor = applyVintageEffect(originalColor);
                        }
                        result.setRGB(x, y, modifiedColor.getRGB());
                    }
                }
            }

            return result;
        }

        private static Color applyPosterEffect(Color color) {
            int r = (color.getRed() / 64) * 64;
            int g = (color.getGreen() / 64) * 64;
            int b = (color.getBlue() / 64) * 64;
            return new Color(r, g, b);
        }

        private static Color applySepiaEffect(Color color) {
            int r = (int) Math.min(255, (color.getRed() * 0.393) + (color.getGreen() * 0.769) + (color.getBlue() * 0.189));
            int g = (int) Math.min(255, (color.getRed() * 0.349) + (color.getGreen() * 0.686) + (color.getBlue() * 0.168));
            int b = (int) Math.min(255, (color.getRed() * 0.272) + (color.getGreen() * 0.534) + (color.getBlue() * 0.131));
            return new Color(r, g, b);
        }

        private static Color applyContrastEffect(Color color) {
            int r = Math.min(255, Math.max(0, (int) ((color.getRed() - 128) * 1.5 + 128)));
            int g = Math.min(255, Math.max(0, (int) ((color.getGreen() - 128) * 1.5 + 128)));
            int b = Math.min(255, Math.max(0, (int) ((color.getBlue() - 128) * 1.5 + 128)));
            return new Color(r, g, b);
        }

        private static Color applyNegativeEffect(Color color) {
            return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
        }

        private static Color applyBrighterEffect(Color color) {
            return color.brighter();
        }

        private static Color applyDarkerEffect(Color color) {
            return color.darker();
        }

        private static Color applySolarizeEffect(Color color) {
            int r = color.getRed() > 127 ? 255 - color.getRed() : color.getRed();
            int g = color.getGreen() > 127 ? 255 - color.getGreen() : color.getGreen();
            int b = color.getBlue() > 127 ? 255 - color.getBlue() : color.getBlue();
            return new Color(r, g, b);
        }

        private static Color applyVintageEffect(Color color) {
            int r = (int) (color.getRed() * 0.9);
            int g = (int) (color.getGreen() * 0.7);
            int b = (int) (color.getBlue() * 0.5);
            return new Color(Math.min(255, r), Math.min(255, g), Math.min(255, b));
        }

        private static Color applyGrayScaleEffect(Color color) {
            int gray = (int) (color.getRed() * 0.3 + color.getGreen() * 0.59 + color.getBlue() * 0.11);
            return new Color(gray, gray, gray);
        }

        private static Color applyNoiseEffect(Color color) {
            Random random = new Random();
            int noise = random.nextInt(50) - 25; // רעש חיובי או שלילי
            int r = Math.min(255, Math.max(0, color.getRed() + noise));
            int g = Math.min(255, Math.max(0, color.getGreen() + noise));
            int b = Math.min(255, Math.max(0, color.getBlue() + noise));
            return new Color(r, g, b);
        }

        private static BufferedImage deepCopy(BufferedImage bi) {
            BufferedImage copy = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
            Graphics g = copy.getGraphics();
            g.drawImage(bi, 0, 0, null);
            g.dispose();
            return copy;
        }

        public static BufferedImage applyPixelateEffect(BufferedImage image, int pixelSize) {
            // Create a copy of the original image to modify
            BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = result.createGraphics();

            // Draw the original image onto the result
            g2d.drawImage(image, 0, 0, null);

            // Clip the graphics to the polygon
            g2d.setClip(Data.getPOLYGON());

            // Iterate over the image in pixel-sized steps
            for (int y = 0; y < image.getHeight(); y += pixelSize) {
                for (int x = 0; x < image.getWidth(); x += pixelSize) {
                    // Check if the current pixel block is inside the polygon
                    if (Data.getPOLYGON().contains(x, y)) {
                        // Calculate the average color in the pixel block
                        Color avgColor = calculateAverageColor(image, x, y, pixelSize);
                        // Fill the pixel block with the average color
                        g2d.setColor(avgColor);
                        g2d.fillRect(x, y, pixelSize, pixelSize);
                    }
                }
            }

            g2d.dispose();
            return result;
        }

        private static Color calculateAverageColor(BufferedImage image, int startX, int startY, int pixelSize) {
            int red = 0, green = 0, blue = 0, alpha = 0;
            int count = 0;

            // Iterate over the pixel block
            for (int y = startY; y < startY + pixelSize && y < image.getHeight(); y++) {
                for (int x = startX; x < startX + pixelSize && x < image.getWidth(); x++) {
                    // Get the color of the pixel
                    Color color = new Color(image.getRGB(x, y), true);
                    red += color.getRed();
                    green += color.getGreen();
                    blue += color.getBlue();
                    alpha += color.getAlpha();
                    count++;
                }
            }

            // Calculate the average color
            return new Color(red / count, green / count, blue / count, alpha / count);
        }
}