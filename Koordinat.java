public class Koordinat{
    private int x;
    private String y;

    /*
    Konstruktor pembentuk Koordinat
    @param x --> absis koordinat
    @param y --> ordinat koordinat
    */
    public Koordinat(int x, String y){
        this.x = x;
        this.y = y;
    }

    /*
    Getter absis koordinat
    @return --> integer
    */
    public int getAbsis(){
        return this.x;
    }

    /*
    Getter ordinat koordinat
    @return --> String
    */
    public String getOrdinat(){
        return this.y;
    }

    /*
    Setter absis koordinat
    @param x --> absis koordinat yang diganti
    */
    public void setAbsis(int x){
        this.x = x;
    }

    /*
    Setter ordinat koordinat
    @param y --> ordinat koordinat yang diganti
    */
    public void setOrdinat(String y){
        this.y = y;
    }

    /*
    Mengembalikan true jika this.koordinat = koordinat
    @param koordinat --> koordinat yang dibandingkan
    */
    public boolean isKoordinatEQ(Koordinat koordinat){
        return ((this.x == koordinat.getAbsis()) &&
        (this.getOrdinat().equalsIgnoreCase(koordinat.getOrdinat())));
    }

    /*
    Mencetak koordinat ke layar dengan format (absis, ordinat)
    */
    public void writeKoordinat(){
        System.out.printf("(%d, %s) ", this.getAbsis(), this.getOrdinat());
    }

    /*
    Mengembalikan koordinat hasil perpindahan this.koordinat ke UTARA
    @return --> Koordinat
    */
    public Koordinat north(){
        return new Koordinat(this.getAbsis()-1, this.getOrdinat());
    }

    /*
    Mengembalikan koordinat hasil perpindahan this.koordinat ke TIMUR LAUT
    @return --> Koordinat
    */
    public Koordinat northEast(){
        int temp = (int)this.getOrdinat().charAt(0);
        return new Koordinat(this.getAbsis()-1, Character.toString((char) (temp+1)));
    }

    /*
    Mengembalikan koordinat hasil perpindahan this.koordinat ke TIMUR
    @return --> Koordinat
    */
    public Koordinat east(){
        int temp = (int)this.getOrdinat().charAt(0);
        return new Koordinat(this.getAbsis(), Character.toString((char) (temp+1)));
    }

    /*
    Mengembalikan koordinat hasil perpindahan this.koordinat ke TENGGARA
    @return --> Koordinat
    */
    public Koordinat southEast(){
        int temp = (int)this.getOrdinat().charAt(0);
        return new Koordinat(this.getAbsis()+1, Character.toString((char) (temp+1)));
    }

    /*
    Mengembalikan koordinat hasil perpindahan this.koordinat ke SELATAN
    @return --> Koordinat
    */
    public Koordinat south(){
        return new Koordinat(this.getAbsis()+1, this.getOrdinat());
    }

    /*
    Mengembalikan koordinat hasil perpindahan this.koordinat ke BARAT DAYA
    @return --> Koordinat
    */
    public Koordinat southWest(){
        int temp = (int)this.getOrdinat().charAt(0);
        return new Koordinat(this.getAbsis()+1, Character.toString((char) (temp-1)));
    }

    /*
    Mengembalikan koordinat hasil perpindahan this.koordinat ke BARAT
    @return --> Koordinat
    */
    public Koordinat west(){
        int temp = (int)this.getOrdinat().charAt(0);
        return new Koordinat(this.getAbsis(), Character.toString((char) (temp-1)));
    }

    /*
    Mengembalikan koordinat hasil perpindahan this.koordinat ke BARAT LAUT
    @return --> Koordinat
    */
    public Koordinat northWest(){
        int temp = (int)this.getOrdinat().charAt(0);
        return new Koordinat(this.getAbsis()-1, Character.toString((char) (temp-1)));
    }

    public Koordinat jumpToNorth(){
        return new Koordinat(this.north().getAbsis()-1, this.north().getOrdinat());
    }

    public Koordinat jumpToNorthEast(){
        int temp = (int)this.northEast().getOrdinat().charAt(0);
        return new Koordinat(this.northEast().getAbsis()-1, Character.toString((char) (temp+1)));
    }

    public Koordinat jumpToEast(){
        int temp = (int)this.east().getOrdinat().charAt(0);
        return new Koordinat(this.east().getAbsis(), Character.toString((char) (temp+1)));
    }

    public Koordinat jumpToSouthEast(){
        int temp = (int)this.southEast().getOrdinat().charAt(0);
        return new Koordinat(this.southEast().getAbsis()+1, Character.toString((char) (temp+1)));
    }

    public Koordinat jumpToSouth(){
        return new Koordinat(this.south().getAbsis()+1, this.south().getOrdinat());
    }

    public Koordinat jumpToSouthWest(){
        int temp = (int)this.southWest().getOrdinat().charAt(0);
        return new Koordinat(this.southWest().getAbsis()+1, Character.toString((char) (temp-1)));
    }

    public Koordinat jumpToWest(){
        int temp = (int)this.west().getOrdinat().charAt(0);
        return new Koordinat(this.west().getAbsis(), Character.toString((char) (temp-1)));
    }

    public Koordinat jumpToNorthWest(){
        int temp = (int)this.northWest().getOrdinat().charAt(0);
        return new Koordinat(this.northWest().getAbsis()-1, Character.toString((char) (temp-1)));
    }

    /*** BERIKUT INI MERUPAKAN FUNGSI PENGECEKAN PERPINDAHAN KOORDINAT ***/
    public boolean isMovingToNorth(Koordinat destinasi){
        return destinasi.isKoordinatEQ(this.north());
    }

    public boolean isMovingToNorthEast(Koordinat destinasi){
        return destinasi.isKoordinatEQ(this.northEast());
    }

    public boolean isMovingToEast(Koordinat destinasi){
        return destinasi.isKoordinatEQ(this.east());
    }

    public boolean isMovingToSouthEast(Koordinat destinasi){
        return destinasi.isKoordinatEQ(this.southEast());
    }

    public boolean isMovingToSouth(Koordinat destinasi){
        return destinasi.isKoordinatEQ(this.south());
    }

    public boolean isMovingToSouthWest(Koordinat destinasi){
        return destinasi.isKoordinatEQ(this.southWest());
    }

    public boolean isMovingToWest(Koordinat destinasi){
        return destinasi.isKoordinatEQ(this.west());
    }

    public boolean isMovingToNorthWest(Koordinat destinasi){
        return destinasi.isKoordinatEQ(this.northWest());
    }
}