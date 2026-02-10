package app.collage.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

public class CollageBuilder {

    private final List<BufferedImage> images;
    private final int tileSize;

    public CollageBuilder(List<BufferedImage> images, int tileSize) {
        this.images = images;
        this.tileSize = tileSize;
    }

    public BufferedImage createCollage() {
        List<BufferedImage> resizedImages = this.images
                .stream().map(image -> this.resize(image, tileSize)).collect(Collectors.toList());

        return this.combineIntoSquare(resizedImages);
    }

    private BufferedImage resize(BufferedImage original, int tileSize) {
        BufferedImage resized =
                new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = resized.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.drawImage(original, 0, 0, tileSize, tileSize, null);
        } finally {
            g.dispose();
        }

        return resized;
    }

    private BufferedImage combineIntoSquare(List<BufferedImage> images) {
        if (images == null || images.isEmpty()) {
            throw new IllegalArgumentException("No images provided");
        }

        int n = images.size();
        int tileSize = images.get(0).getWidth(); // assume square & uniform

        int cols = (int) Math.ceil(Math.sqrt(n));
        int rows = (int) Math.ceil((double) n / cols);

        BufferedImage result = new BufferedImage(
                cols * tileSize,
                rows * tileSize,
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = result.createGraphics();
        try {
            for (int i = 0; i < n; i++) {
                int x = (i % cols) * tileSize;
                int y = (i / cols) * tileSize;
                g.drawImage(images.get(i), x, y, null);
            }
        } finally {
            g.dispose();
        }

        return result;
    }
}
