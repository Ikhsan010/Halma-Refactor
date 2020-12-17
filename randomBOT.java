import java.util.Random;

public class randomBOT {
    public static void main(String[] args) {
        Player p = new Player(true);
        Player q = new Player(false);
        // Note: this bot is using RANDOM MOVES heuristic
        // So the more size you set to the board, it will increase
        // the time to find the goal state...
        // It's actually effective iff the board size is small, e.g = 4

        // I don't know but there is still a bug when the board size >= 6
        Board b = new Board(Integer.parseInt(args[0]), p, q);

        Koordinat randomPion, randomDestinasi;
        int randomIdx;
        int iterateP1 = 0;
        int iterateP2 = 0;

        randomPion = new Koordinat(5, "a");

        while(!b.getPlayer1().isWin(q) && !b.getPlayer2().isWin(p)){
            // b.writeInfo();
            if(b.getPlayer1().getPlayerState()){
                iterateP1++;
                do{
                    randomIdx = new Random().nextInt(b.getPlayer1().getPlayerPion().size());
                    randomPion = b.getPlayer1().getPlayerPion().get(randomIdx);
                } while (!b.getPlayer1().isPionExist(randomPion));
                // ASUMSI randomPion yang terpilih pasti milik player 1
                randomDestinasi = b.moveRandomly(randomPion);
                b.getPlayer1().getPlayerPion().set(randomIdx, randomDestinasi);
            }

            if(b.getPlayer2().getPlayerState()){
                iterateP2++;
                do {
                    randomIdx = new Random().nextInt(b.getPlayer2().getPlayerPion().size());
                    randomPion = b.getPlayer2().getPlayerPion().get(randomIdx);
                } while (!b.getPlayer2().isPionExist(randomPion));
                randomDestinasi = b.moveRandomly(randomPion);
                b.getPlayer2().getPlayerPion().set(randomIdx, randomDestinasi);
            }

            if(b.getPlayer1().getPlayerState()){
                b.getPlayer1().setPlayerState(false);
                b.getPlayer2().setPlayerState(true);
                // System.out.println("Player 1 turn");
                // randomPion.writeKoordinat(); System.out.println();
                // b.writeInfo();
            }
            else{
                b.getPlayer1().setPlayerState(true);
                b.getPlayer2().setPlayerState(false);
                // System.out.println("Player 2 turn");
                // randomPion.writeKoordinat(); System.out.println();
                // b.writeInfo();
            }
            // try{Thread.sleep(2000);} catch (Exception e) {e.printStackTrace();}
        }
        b.writeBoard();
        System.out.println("Butuh sebanyak " + iterateP1 + " langkah untuk P1");
        System.out.println("Butuh sebanyak " + iterateP2 + " langkah untuk P2");
    }
}
