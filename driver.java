import java.util.Scanner;

public class driver {
    static void updateProgress(double progressPercentage) {
        final int width = 25; // progress bar width in chars

        System.out.print("\r[");
        int i = 0;
        for (; i <= (int)(progressPercentage*width); i++) {
            System.out.print(".");
        }
        for (; i < width; i++) {
            System.out.print(" ");
        }

        System.out.print("]");
    }

    public static void main(String[] args){
        // Pembaca masukan dari keyboard
        Scanner sc = new Scanner(System.in);
        int absis, x;
        String ordinat, y;
        Koordinat pion, destinasi;

        Player p = new Player(true);
        Player q = new Player(false);
        Board b = new Board(8, p, q);
        System.out.println("\nMenginisialisasi permainan");

        try {
            for (double progressPercentage = 0.0; progressPercentage < 1.0; progressPercentage += 0.01) {
                updateProgress(progressPercentage);
                Thread.sleep(20);
            }
            System.out.println("\n");
        } catch (InterruptedException e) {}

        // Human vs Human
        // System.out.println(b.getPlayer1().isWin(q));
        while(!b.getPlayer1().isWin(q) && !b.getPlayer2().isWin(p)){
            b.writeInfo();

            // Player 1 main phase
            if(b.getPlayer1().getPlayerState()){
                System.out.println("\nGiliran pemain pertama");
                System.out.println("Masukkan koordinat pion ");
                do {
                    absis = sc.nextInt();
                    ordinat = sc.next();
                    pion = new Koordinat(absis, ordinat);
                    if(!b.getPlayer1().isPionExist(pion)){
                        System.out.println("Pion tidak ditemukan!");
                        System.out.println("Masukkan koordinat pion yang baru");
                    }
                } while (!b.getPlayer1().isPionExist(pion));
                System.out.println("Masukkan koordinat tujuan");
                int idxpion = b.getPlayer1().indexOfPion(pion);
                do {
                    x = sc.nextInt();
                    y = sc.next();
                    destinasi = new Koordinat(x, y);
                    if(!b.isValidMovement(pion, destinasi)){
                        System.out.println("Destinasi tidak terdefinisi!");
                        System.out.println("Masukkan destinasi baru");
                    }
                } while (!b.isValidMovement(pion, destinasi));
                b.getPlayer1().getPlayerPion().set(idxpion, destinasi);
            }

            // Player 2 main phase
            if(b.getPlayer2().getPlayerState()){
                System.out.println("\nGiliran pemain kedua");
                System.out.println("Masukkan koordinat pion ");
                do {
                    absis = sc.nextInt();
                    ordinat = sc.next();
                    pion = new Koordinat(absis, ordinat);
                    if(!b.getPlayer2().isPionExist(pion)){
                        System.out.println("Pion tidak ditemukan!");
                        System.out.println("Masukkan koordinat pion yang baru");
                    }
                } while (!b.getPlayer2().isPionExist(pion));
                System.out.println("Masukkan koordinat tujuan");
                int idxpion = b.getPlayer2().indexOfPion(pion);
                do {
                    x = sc.nextInt();
                    y = sc.next();
                    destinasi = new Koordinat(x, y);
                    if(!b.isValidMovement(pion, destinasi)){
                        System.out.println("Destinasi tidak terdefinisi!");
                        System.out.println("Masukkan destinasi baru");
                    }
                } while (!b.isValidMovement(pion, destinasi));
                b.getPlayer2().getPlayerPion().set(idxpion, destinasi);
            }

            // Pergantian pemain
            if(b.getPlayer1().getPlayerState()){
                b.getPlayer1().setPlayerState(false);
                b.getPlayer2().setPlayerState(true);
            }
            else{
                b.getPlayer1().setPlayerState(true);
                b.getPlayer2().setPlayerState(false);
            }

            // Fungsi kemenangan
            System.out.println();
            if(b.getPlayer1().isWin(q)){
                System.out.println("Player 1 menang!");
            }
            if(b.getPlayer2().isWin(p)){
                System.out.println("Player 2 menang!");
            };
        }
        b.writeBoard();
        sc.close();
    }
}
