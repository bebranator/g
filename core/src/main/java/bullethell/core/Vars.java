package bullethell.core;

import bullethell.entity.Arena;
import bullethell.entity.Entities;
import bullethell.entity.EntityGroup;
import bullethell.entity.type.Bullet;
import bullethell.entity.type.Laser;
import bullethell.entity.type.Player;
import bullethell.entity.type.PlayerBullet;
import bullethell.game.Ev;
import bullethell.game.GameState;
import bullethell.game.State;
import bullethell.game.dialog.DialogueManager;
import bullethell.module.*;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class Vars {
    private static State state;
    public static UI ui;
    public static Renderer renderer;
    public static Control control;
    public static SoundControl sounds;

    public static Entities entities;
    public static Arena arena;
    public static GameState game;

    public static EntityGroup<Bullet> enemyBullets;
    public static EntityGroup<PlayerBullet> playerBullets;
    public static EntityGroup<Player> playerGroup;
    public static EntityGroup<Laser> lasers;
    // why we do other group for player lmao
    public static Player player;

    public static DialogueManager dialogue;

    public static void init() {
        setState(State.menu);
        entities = new Entities();
        arena = new Arena(40, 40, Client.WIDTH / 1.5f - 80, Client.HEIGHT - 80f);
        game = new GameState();
        sounds = new SoundControl();

        dialogue = new DialogueManager();

        enemyBullets = entities.getGroup(Bullet.class);
        playerBullets = entities.getGroup(PlayerBullet.class);
        playerGroup = entities.getGroup(Player.class);
        lasers = entities.getGroup(Laser.class);
        player = new Player();
        player.add();
    }

    public static void setState(State newState) {
        Events.fire(Ev.StateChange.get(state, newState));
        state = newState;
        // todo: events
    }

    public static boolean state(State state) {
        // fun
        return state.state(state);
    }

    public static boolean menu() {
        return state.menu();
    }
    public static boolean inGame() {
        return state.inGame();
    }
    public static boolean paused() {
        return state.pause();
    }
}
