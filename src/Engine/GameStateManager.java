package Engine;

import Engine.EngineExceptions.StateNotFoundException;
import com.sun.istack.internal.NotNull;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Created by Michał Pawłowicz on 03.02.17.
 * Generic class for managing states in game.
 * Parametr T is a key class to identify game states.
 */
public class GameStateManager<T> {

    private HashMap<T, GameState> gameStateSet;
    private T currentState;

    public GameStateManager(){
        gameStateSet = new HashMap<>();
    }

    /**
     * @param currentStateKey - key to identify game state.
     * @throws StateNotFoundException if could not find specified key.
     */
    public void setCurrentState(T currentStateKey) throws StateNotFoundException{
        try {
            gameStateSet.get(currentStateKey).init();
            this.currentState = currentStateKey;
        } catch (NullPointerException e){
            throw new StateNotFoundException();
        }
    }

    /**
     * Add new game state.
     * @param gameState
     * @param key to uniquely identify game state objects in set
     * @return Returns false if set already contain that key
     */
    public boolean addState(GameState gameState, @NotNull T key){
        if(gameStateSet.containsKey(key)){
            return false;
        }
        gameStateSet.put(key, gameState);
        return true;
    }

    /**
     * Update current state
     */
    public void update(){
        try{
            gameStateSet.get(currentState).update();
        } catch (NullPointerException e){
//            throw new StateNotFoundException();
        }
    }

    public void draw(Graphics2D g){
        try{
            gameStateSet.get(currentState).draw(g);
        } catch (NullPointerException e){
            throw new StateNotFoundException();
        }
    }

    /**
     * Getter used to get key of current game state.
     * @return T
     */
    public T getCurrentStateKey(){ return currentState; }
    public GameState getCurrentState(){ return gameStateSet.get(currentState); }

    public void keyTyped(KeyEvent k) {
        try{
            gameStateSet.get(currentState).keyTyped(k);
        } catch (NullPointerException e){
            ///??
        }
    }
    public void keyPressed(KeyEvent k) {
        try{
            gameStateSet.get(currentState).keyPressed(k);
        } catch (NullPointerException e){
            throw new StateNotFoundException();
        }
    }
    public void keyReleased(KeyEvent k) {
        try{
            gameStateSet.get(currentState).keyReleased(k);
        } catch (NullPointerException e){
            throw new StateNotFoundException();
        }
    }
}
