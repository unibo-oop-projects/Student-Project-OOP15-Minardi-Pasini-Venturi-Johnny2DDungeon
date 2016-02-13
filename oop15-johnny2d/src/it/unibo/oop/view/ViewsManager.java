package it.unibo.oop.view;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import it.unibo.oop.controller.Controller;
import it.unibo.oop.controller.State;
import it.unibo.oop.controller.StateObserver;
import it.unibo.oop.utilities.KeysManager;

public class ViewsManager implements StateObserver {

    private static final ViewsManager SINGLETON = new ViewsManager();
    private final Set<MenuEnum> menu;
    private final LevelInterface level;
    private Controller ctrl;
    
    private ViewsManager() {
        this.menu = new HashSet<>(Arrays.asList(MenuEnum.values()));
        this.menu.forEach(e -> e.getView().addObserver(this));
        this.level = new Level();
        level.addObserver(KeysManager.getKeysManager());     
    }
    
    public static ViewsManager getViewsManager() {
        return SINGLETON;
    }
    
    public void setController(final Controller ctrl) {
        this.ctrl = ctrl;
    }
    
    public void show(final MenuEnum menu) {
        menu.getView().showIt();
    }
    
    @Override 
    public void stateAction(final State state) {
        this.hideAll();
        switch (state) {
        case PLAY:
            this.level.showIt();
           // this.ctrl.start();
            break;
        case EXIT:
            System.exit(0);
            break;
        case OPTIONS:
            MenuEnum.OPTIONS.getView().showIt();
            break;
        default:
        }
    }
    
    private void hideAll() {
        for (final MenuEnum menu: MenuEnum.values()) {
            menu.getView().hideIt();
        }
    }

}
