package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.osgi.framework.BundleContext;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private static final GameData gameData = new GameData();
    private static World world = new World();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    private static BundleContext bundleContext;

    private SpriteBatch sBatch;
    private TextureRegion backgroundTexture;
    
    public Game() {
        System.out.println("Game class constructor... " + this);
        init();
    }
    
    private void init() {

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Asteroids";
        cfg.width = 500;
        cfg.height = 400;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }
    
    @Override
    public void create() {
        System.out.println("Game class create() method... " + this);
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        
        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();        
        
        sBatch = new SpriteBatch(600);
        backgroundTexture = new TextureRegion(new Texture("/home/ccl/Downloads/tumblr_inline_n258q4r8c01qhwjx8.png"), 0, 0, 500, 400);

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
        
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        sBatch.begin();
        sBatch.draw(backgroundTexture, 0, 0);
        sBatch.end();

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();

        update();
        draw();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            
            float[] color = entity.getColor();
            sr.setColor(color[0], color[1], color[2], color[3]);

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    public void addEntityProcessingService(IEntityProcessingService eps) {
        System.out.println("Added EntityProcessing service " + eps + " " + this);
        this.entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService(IEntityProcessingService eps) {
        System.out.println("Removed EntityProcessing service " + eps + " " + this);
        this.entityProcessorList.remove(eps);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService eps) {
        System.out.println("Added PostEntityProcessingService " + eps + " " + this);
        postEntityProcessorList.add(eps);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService eps) {
        System.out.println("Removed PostEntityProcessingService " + eps + " " + this);
        postEntityProcessorList.remove(eps);
    }

    public void addGamePluginService(IGamePluginService plugin) {
        System.out.println("Added GamePluginService " + plugin + " " + this);
        this.gamePluginList.add(plugin);
        plugin.start(gameData, world);

    }

    public void removeGamePluginService(IGamePluginService plugin) {
        System.out.println("Removed GamePluginService " + plugin + " " + this);
        this.gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }
}
