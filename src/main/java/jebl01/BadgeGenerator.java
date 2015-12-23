package jebl01;

import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;

/**
 * Created by jesblo on 15-12-23.
 */
public class BadgeGenerator {
    private static final int MAX_WIDTH = 200;
    private static final int HEIGHT = 20;
    private static final int TEXT_MARGIN = 2;
    private static final int ROUNDED_ARC = 7;

    private static final Color GREEN = new Color(36, 188, 0);
    private static final Color RED = new Color(222, 99, 71);
    private static final Color GRAY = new Color(94, 94, 94);

    public static BufferedImage generate(String left, String right, boolean success) {
        final BufferedImage image = new BufferedImage(MAX_WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics = image.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        allignFont(graphics, left + right, HEIGHT, TEXT_MARGIN);
        final int leftWidth = graphics.getFontMetrics().stringWidth(left);
        final int rightWidth = graphics.getFontMetrics().stringWidth(right);

        graphics.setPaint(GRAY);
        graphics.fillRoundRect(0, 0, ROUNDED_ARC * 2, HEIGHT, ROUNDED_ARC, ROUNDED_ARC);
        graphics.fillRect(ROUNDED_ARC, 0, leftWidth + (2 * TEXT_MARGIN) - ROUNDED_ARC, HEIGHT);

        if(success) {
            graphics.setPaint(GREEN);
        } else {
            graphics.setPaint(RED);
        }

        graphics.fillRoundRect(leftWidth + (2 * TEXT_MARGIN), 0, rightWidth + (2 * TEXT_MARGIN), HEIGHT, ROUNDED_ARC, ROUNDED_ARC);
        graphics.fillRect(leftWidth + (2 * TEXT_MARGIN), 0, ROUNDED_ARC, HEIGHT);

        //draw text
        LineMetrics lineMetrics = graphics.getFont().getLineMetrics(left, graphics.getFontRenderContext());

        int y = (int)lineMetrics.getAscent() + TEXT_MARGIN;
        graphics.setPaint(Color.WHITE);
        graphics.drawString(left, TEXT_MARGIN, y);
        graphics.drawString(right, leftWidth + (3 * TEXT_MARGIN), y);

        graphics.dispose();

        int imageWidth = leftWidth + rightWidth + (4 * TEXT_MARGIN);
        return image.getSubimage(0, 0, imageWidth < MAX_WIDTH ? imageWidth : MAX_WIDTH, HEIGHT);
    }


    private static void allignFont(Graphics2D graphics, String text, int height, int margin) {
        final Font font = graphics.getFont();
        final float diff = (height - (2 * margin)) / font.getLineMetrics(text, graphics.getFontRenderContext()).getHeight();

        graphics.setFont(font.deriveFont(font.getSize() * diff));
    }
}
