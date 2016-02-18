package it.unibo.oop.controller;

import static it.unibo.oop.utilities.Direction.NONE;
import it.unibo.oop.utilities.Direction;
import it.unibo.oop.utilities.KeysManager;
import it.unibo.oop.view.ViewsManager;

public class GameLoopAgent implements AgentInterface {

    private final static double FPS = 10;
    private final static int TO_SECONDS = 1000;
    private final static int SLEEPING_TIME = (int)(1/FPS * TO_SECONDS);
    private final KeysManager keysMan = KeysManager.getInstance();
    private final ViewsManager viewsMan = ViewsManager.getInstance();
    private volatile Direction pgDir;
    private volatile boolean pgIsShooting;
    private volatile boolean loop; /* default false */

    public GameLoopAgent() {
        this.loop = true;
    }

    public synchronized void play() {
        this.loop = true;
        this.notify();
    }

    @Override
    public synchronized void run() {
        /* GAME LOOP */
        while (true) {
            while(!loop) {
                try {
                    this.viewsMan.stateAction(State.PAUSE);
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /* valutare l'aggiunta di un ulteriore wait per far eseguire eventuali eventi di key releasing (onde evitare che un tasto sia processato
             * pi� volte in caso di un tasso di FPS elevato)
             */

            /* CHECK GIOCO FINITO/DA INIZIARE */
            this.processKeys();
            this.dbgKeysMan();        /* per debugging */
            /* chiamo C passandogli la direzione del pg e l'azione (spara o no) */
            /* chiamo V che si aggiorna e disegna frame*/
            this.viewsMan.getLevel().showIt();
            try {
                Thread.sleep(SLEEPING_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }        
    }

    private void processKeys() {
        this.loop = !this.keysMan.isAKeyPressed(KeyCommands.ESC);
        this.pgIsShooting = this.keysMan.isAKeyPressed(KeyCommands.SPACE);
        this.pgDir = this.keysMan.getDirection(); // rimuovo le KeysTyped.
    }

    /* per debug */
    private void dbgKeysMan() {
        if (this.pgDir != NONE) {
            System.out.println("Dir 1: " + this.pgDir);
        }
        System.out.println(this.pgIsShooting ? "SHOOT!" : "");
    }
}