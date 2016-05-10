import org.newdawn.slick.*;

public class SetupTest extends BasicGame {
    public SetupTest(String title) {
        super(title);

    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

    }

    public static void main(String args[]) throws SlickException {
        AppGameContainer app = new AppGameContainer(new SetupTest("Setup Test"));
        app.setDisplayMode(400,100,false);
        app.setShowFPS(false);
        app.start();
    }
}
