package vasylchenko.avatar.vertx;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;

public class App extends AbstractVerticle {

    static final Random random = new Random();

    static final int ARMS_COUNT = 14;
    static final int BODY_COUNT = 20;
    static final int EYES_COUNT = 20;
    static final int HAIR_COUNT = 12;
    static final int LEGS_COUNT = 18;
    static final int MOUTH_COUNT = 17;

    final BufferedImage[] arms = new BufferedImage[ARMS_COUNT];
    final BufferedImage[] body = new BufferedImage[BODY_COUNT];
    final BufferedImage[] eyes = new BufferedImage[EYES_COUNT];
    final BufferedImage[] hair = new BufferedImage[HAIR_COUNT];
    final BufferedImage[] legs = new BufferedImage[LEGS_COUNT];
    final BufferedImage[] mouth = new BufferedImage[MOUTH_COUNT];

    final BufferedImage[] arms_old = new BufferedImage[ARMS_COUNT];
    final BufferedImage[] body_old = new BufferedImage[BODY_COUNT];
    final BufferedImage[] eyes_old = new BufferedImage[EYES_COUNT];
    final BufferedImage[] hair_old = new BufferedImage[HAIR_COUNT];
    final BufferedImage[] legs_old = new BufferedImage[LEGS_COUNT];
    final BufferedImage[] mouth_old = new BufferedImage[MOUTH_COUNT];

    public App() {
        loadResources();
    }

    private void loadResources() {
        try {
            // --------------- new resources ------------------------
            for (int i = 0; i < ARMS_COUNT; i++) {
                arms[i] = ImageIO.read(App.class.getResourceAsStream("/imgs/arms_" + (i + 1) + ".png"));
            }
            for (int i = 0; i < BODY_COUNT; i++) {
                body[i] = ImageIO.read(App.class.getResourceAsStream("/imgs/body_" + (i + 1) + ".png"));
            }
            for (int i = 0; i < EYES_COUNT; i++) {
                eyes[i] = ImageIO.read(App.class.getResourceAsStream("/imgs/eyes_" + (i + 1) + ".png"));
            }
            for (int i = 0; i < HAIR_COUNT; i++) {
                hair[i] = ImageIO.read(App.class.getResourceAsStream("/imgs/hair_" + (i + 1) + ".png"));
            }
            for (int i = 0; i < LEGS_COUNT; i++) {
                legs[i] = ImageIO.read(App.class.getResourceAsStream("/imgs/legs_" + (i + 1) + ".png"));
            }
            for (int i = 0; i < MOUTH_COUNT; i++) {
                mouth[i] = ImageIO.read(App.class.getResourceAsStream("/imgs/mouth_" + (i + 1) + ".png"));
            }
            // --------------- old resources ------------------------
            for (int i = 0; i < ARMS_COUNT; i++) {
                arms_old[i] = ImageIO.read(App.class.getResourceAsStream("/imgs/oldarms_" + (i + 1) + ".png"));
            }
            for (int i = 0; i < BODY_COUNT; i++) {
                body_old[i] = ImageIO.read(App.class.getResourceAsStream("/imgs/oldbody_" + (i + 1) + ".png"));
            }
            for (int i = 0; i < EYES_COUNT; i++) {
                eyes_old[i] = ImageIO.read(App.class.getResourceAsStream("/imgs/oldeyes_" + (i + 1) + ".png"));
            }
            for (int i = 0; i < HAIR_COUNT; i++) {
                hair_old[i] = ImageIO.read(App.class.getResourceAsStream("/imgs/oldhair_" + (i + 1) + ".png"));
            }
            for (int i = 0; i < LEGS_COUNT; i++) {
                legs_old[i] = ImageIO.read(App.class.getResourceAsStream("/imgs/oldlegs_" + (i + 1) + ".png"));
            }
            for (int i = 0; i < MOUTH_COUNT; i++) {
                mouth_old[i] = ImageIO.read(App.class.getResourceAsStream("/imgs/oldmouth_" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Launcher.main(new String[]{"run", App.class.getName()});
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            final HttpServerResponse response = req.response();
            response.putHeader("content-type", "image/png");
            if (req.path().startsWith("/old")) {
                response.end(getImageBuffer(false));
            } else {
                response.end(getImageBuffer(true));
            }
        }).listen(8080);
    }

    public Buffer getImageBuffer(Boolean isNew) {
        BufferedImage destinationImage = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);
        //---------------------
        Graphics2D destinationGraphics = destinationImage.createGraphics();
        try {
            configureGraphics(destinationGraphics);
            if (isNew) {
                drawImages(destinationGraphics,
                        arms[random.nextInt(ARMS_COUNT)],
                        hair[random.nextInt(HAIR_COUNT)],
                        legs[random.nextInt(LEGS_COUNT)],
                        body[random.nextInt(BODY_COUNT)],
                        eyes[random.nextInt(EYES_COUNT)],
                        mouth[random.nextInt(MOUTH_COUNT)]);
            } else {
                drawImages(destinationGraphics,
                        arms_old[random.nextInt(ARMS_COUNT)],
                        hair_old[random.nextInt(HAIR_COUNT)],
                        legs_old[random.nextInt(LEGS_COUNT)],
                        body_old[random.nextInt(BODY_COUNT)],
                        eyes_old[random.nextInt(EYES_COUNT)],
                        mouth_old[random.nextInt(MOUTH_COUNT)]);
            }
        } finally {
            destinationGraphics.dispose();
        }
        //---------------------
        byte[] imageInByte = null;
        try {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(destinationImage, "png", baos);
                baos.flush();
                imageInByte = baos.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Buffer.buffer(imageInByte);
    }

    private void drawImages(Graphics2D destinationGraphics, BufferedImage... imgs) {
        for (BufferedImage img : imgs) {
            destinationGraphics.drawImage(img, 0, 0, 120, 120, null);
        }
    }

    private void configureGraphics(Graphics2D destinationGraphics) {
        destinationGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        destinationGraphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        destinationGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        destinationGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        destinationGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
    }
}