package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
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
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import util.SPILocator;

public class Game
        implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private List<IGamePluginService> entityPlugins = new ArrayList<>();
    private List<IPostEntityProcessingService> postEntityProcessors = new ArrayList<>();
    private World world = new World();
    
    private SpriteBatch sBatch;
    private TextureRegion backgroundTexture;

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        shapeRenderer = new ShapeRenderer();
        sBatch = new SpriteBatch(600);
        backgroundTexture = new TextureRegion(new Texture("/home/ccl/Downloads/tumblr_inline_n258q4r8c01qhwjx8.png"), 0, 0, 500, 400);
        //backgroundTexture = new TextureRegion(new Texture("/home/ccl/Downloads/25352259_10155514660336865_369656534154241243_o_0.jpg"), 0, 0, 500, 400);
        
        
        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }
        
        // Lookup all IEntityProcessors using ServiceLoader
        for (IEntityProcessingService iEntity : getEntityProcessingServices()) {
            entityProcessors.add(iEntity);
        }
        
        // Lookup all IPostEntityProcessors using ServiceLoader
        for (IPostEntityProcessingService iPostEntity : getPostEntityProcessingServices()) {
            postEntityProcessors.add(iPostEntity);
        }        
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
        
        // 1. Update the game state
        update();
        
        // 2. Post process the game state
        postProcess();

        // 3. Draw the game state
        draw();
        
        

        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessors) {
            entityProcessorService.process(gameData, world);
        }
    }
    
    private void postProcess() {
        for (IPostEntityProcessingService postProcessorService : postEntityProcessors) {
            postProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {

            float[] color = entity.getColor();
            shapeRenderer.setColor(color[0], color[1], color[2], color[3]);
            

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {

                shapeRenderer.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }
            
            shapeRenderer.end();
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
    
    private Collection<? extends IGamePluginService> getPluginServices() {
        return SPILocator.locateAll(IGamePluginService.class);
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }
    
    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return SPILocator.locateAll(IPostEntityProcessingService.class);
    }
}
