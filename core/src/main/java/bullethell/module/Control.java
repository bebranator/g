package bullethell.module;

import bullethell.core.Vars;
import bullethell.game.Ev;
import bullethell.game.GameStats;
import bullethell.game.GameTime;
import bullethell.game.State;
import bullethell.game.spell.SpellCard;
import bullethell.game.stage.Stage;
import bullethell.game.stage6.Stage6;
import bullethell.game.stagebad.StageBadApple;
import bullethell.graphics.Shortcuts;
import bullethell.log.Log;
import bullethell.utils.Time;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Timer;

import static bullethell.core.Core.*;
import static bullethell.core.Vars.*;

public class Control implements IModule {
    @Override
    public void render() {
        Time.update();

        if(inGame()) {
            if(!paused()) {
                updateGame();
                GameTime.update();
            }
        }
    }

    public void updateGame() {
        if(cinput.isJustPressed(Input.Keys.ESCAPE)) ui.pauseDialog.show();

        enemyBullets.update();
        playerBullets.update();
        lasers.update();
        healthEntities.update();
        player.update();

        if(game.level == null) {
            Log.info("Something went wrong with stage!");
            app.exit();
        }

        game.levelUpdate();

        arena.updateGroup(playerBullets);
        GameStats.update();
    }
    // exit the stage
    // todo: show loading screen
    public void menu() {
        sounds.stopMusic();

        player.defaultLocation();
        Vars.setState(State.menu);
//        ui.menuFragment.showLabels();
        game.setLevel(null);
        ui.menu();
        GameTime.reset();
    }

    public void playStage(Stage stage) {
        reset();
        game.setLevel(stage);
    }

    public void card(SpellCard card) {
//        game.spell(card);
    }

    // clean every entity
    public void reset() {
        playerBullets.clean();
        lasers.clean();
        healthEntities.clean();
        enemyBullets.clean();
        player.defaultLocation();
    }

    public void gamePause() {
        Vars.setState(State.pause);
    }
    public void gameResume() {
        Vars.setState(State.inGame);
    }

    Stage stage6 = new Stage6();
    // switch state to gaming
    public void game() {
        ui.menuFragment.setMenu(null);
        GameStats.reset();
        playStage(stage6);
        player.defaultLocation();

//        sounds.playMusic(audio.newMusic(files.internal("music/bad_apple_extract.mp3")), false);
//        Shortcuts.arenaNotification("BGM - Bad Apple!!!");

        Vars.setState(State.inGame);
//        ui.menuFragment.hideLabels();
        ui.game();

    }
}
