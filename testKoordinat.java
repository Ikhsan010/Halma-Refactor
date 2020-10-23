public class testKoordinat {
    // to enable java assertion
    // java -ea [filename]

    // to disable it
    // java -da [filename]
    public static void main(String[] args) {
        Koordinat now = new Koordinat(5, "e");

        assert (now.getAbsis() == 5) : "fungsi getAbsis() salah\n";
        assert (now.getOrdinat().equalsIgnoreCase("e") == true)
                : "fungsi getOrdinat() salah\n";

        Koordinat now1 = new Koordinat(5, "e");
        Koordinat now2 = new Koordinat(6, "e");
        assert (now.isKoordinatEQ(now1) == true) : "fungsi isKoordinatEQ() salah\n";
        assert (now.isKoordinatEQ(now2) == false) : "fungsi isKoordinatEQ() salah\n";

        Koordinat north = new Koordinat(4, "e");
        Koordinat east = new Koordinat(5, "f");
        Koordinat south = new Koordinat(6, "e");
        Koordinat west = new Koordinat(5, "d");
        assert (now.north().isKoordinatEQ(north) == true) : "fungsi north() bermasalah\n";
        assert (now.north().isKoordinatEQ(east) == false) : "fungsi north() bermasalah\n";
        assert (now.east().isKoordinatEQ(east) == true) : "fungsi east() bermasalah\n";
        assert (now.east().isKoordinatEQ(south) == false) : "fungsi east() bermasalah\n";
        assert (now.south().isKoordinatEQ(south) == true) : "fungsi south() bermasalah\n";
        assert (now.south().isKoordinatEQ(west) == false) : "fungsi south() bermasalah\n";
        assert (now.west().isKoordinatEQ(west) == true) : "fungsi west() bermasalah\n";
        assert (now.west().isKoordinatEQ(north) == false) : "fungsi west() bermasalah\n";

        Koordinat northEast = new Koordinat(4, "f");
        Koordinat southEast = new Koordinat(6, "f");
        Koordinat southWest = new Koordinat(6, "d");
        Koordinat northWest = new Koordinat(4, "d");
        assert (now.northEast().isKoordinatEQ(northEast) == true) : "fungsi northEast() bermasalah\n";
        assert (now.northEast().isKoordinatEQ(northWest) == false) : "fungsi northEast() bermasalah\n";
        assert (now.southEast().isKoordinatEQ(southEast) == true) : "fungsi southEast() bermasalah\n";
        assert (now.southEast().isKoordinatEQ(southWest) == false) : "fungsi southEast() bermasalah\n";
        assert (now.southWest().isKoordinatEQ(southWest) == true) : "fungsi southWest() bermasalah\n";
        assert (now.southWest().isKoordinatEQ(north) == false) : "fungsi southWest() bermasalah\n";
        assert (now.northWest().isKoordinatEQ(northWest) == true) : "fungsi northWest() bermasalah\n";
        assert (now.northWest().isKoordinatEQ(south) == false) : "fungsi northWest() bermasalah\n";

        System.out.println("Modul Koordinat.java lolos uji!");
    }
}
