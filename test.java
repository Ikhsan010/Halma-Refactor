public class test {
    public static void main(String[] args) {
        Player p = new Player(true);
        // p.addKoordinatToBase(new Koordinat(1, "a"));
        // p.addKoordinatToBase(new Koordinat(2, "b"));
        // p.addKoordinatToBase(new Koordinat(3, "c"));

        Player q = new Player(false);
        // q.addKoordinatToPlayer(new Koordinat(1, "a"));
        // q.addKoordinatToPlayer(new Koordinat(4, "b"));
        // q.addKoordinatToPlayer(new Koordinat(3, "c"));

        // System.out.println(q.isWin(p));
        // Koordinat pion = new Koordinat(5, "a");
        // Koordinat destinasi = new Koordinat(5, "c");
        // boolean keTimur = pion.isMovingToEast(destinasi.west());
        // System.out.println(keTimur);
        Board b = new Board(4, p, q);
        b.writeInfo();
        // System.out.println(b.countFreedomPion(new Koordinat(1, "b")));
        for (Koordinat destinasi : b.possibleMoveOfPion(new Koordinat(1, "a"))) {
            destinasi.writeKoordinat();
        }
    }
}
