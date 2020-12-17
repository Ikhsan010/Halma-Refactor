import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private Player player1, player2;
    private List<ArrayList<String>> board;

    /**
    Konstruktor pembentuk Board (papan permainan)
    Terdiri dari 2 player, yaitu player1 dan player2
    @param size --> ukuran board yang dipilih (Asumsi selalu genap)
    @param player1 --> pemain 1
    @param player2 --> pemain 2
    */
    public Board(int size, Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        this.board = new ArrayList<ArrayList<String>>(size+1);
        initBoard(size);
    }

    /**
    Getter pemain satu
    @return --> this.player1
    */
    public Player getPlayer1(){
        return this.player1;
    }

    /**
    Getter pemain dua
    @return --> this.player2
    */
    public Player getPlayer2(){
        return this.player2;
    }

    /**
    Menginisiasi permainan: board, player1, player2
    @param size --> ukuran board
    */
    public void initBoard(int size){
        int count = 1;
        int alphabet = (int)('a');

        // Initial board
        for(int row=0; row<size+1; row++){
            ArrayList<String> temp = new ArrayList<String>(size+1);
            for(int col=0; col<size+1; col++){
                if((row == 0) && (col == 0)){
                    temp.add(" ");
                }
                else if((row != 0) && (col == 0)){
                    temp.add(Integer.toString(count));
                    count++;
                }
                else if((row == 0) && (col != 0)){
                    temp.add(Character.toString((char) alphabet));
                    alphabet++;
                }
                else{
                    temp.add("x");
                }
            }
            this.board.add(temp);
        }

        // Init player 1's base
        int maksCol = (size+1)/2;
        for(int row=1; row<=(size+1)/2; row++){
            for(int col=1; col<=maksCol; col++){
                ArrayList<String> temp = this.board.get(0);
                Koordinat koordinat = new Koordinat(row, temp.get(col));
                this.getPlayer1().addKoordinatToPlayer(koordinat);
                this.getPlayer1().addKoordinatToBase(koordinat);
            }
            maksCol--;
        }

        // Init player 2's base
        int minCol = (size+1)/2;
        for(int row=size; row>(size+1)/2; row--){
            for(int col=minCol+1; col<size+1; col++){
                ArrayList<String> temp = this.board.get(0);
                Koordinat koordinat = new Koordinat(row, temp.get(col));
                this.getPlayer2().addKoordinatToPlayer(koordinat);
                this.getPlayer2().addKoordinatToBase(koordinat);
            }
            minCol++;
        }
    }

    /**
    Mencetak papan permainan halma ke layar
    */
    public void writeBoard(){
        for(int row=0; row<this.board.size(); row++){
            ArrayList<String> temp = this.board.get(row);
            ArrayList<String> alphabet = this.board.get(0);
            for(int col=0; col<temp.size()-1; col++){
                Koordinat pion = new Koordinat(row, alphabet.get(col));
                if(row < 10){
                    if(col == 0){
                        System.out.printf("%s  ", temp.get(col));
                    }
                    else{
                        if(this.getPlayer1().isPionExist(pion)){
                            System.out.printf("%so%s  ", Color.GREEN.getColor(), Color.RESET.getColor());
                        }
                        else if(this.getPlayer2().isPionExist(pion)){
                            System.out.printf("%so%s  ", Color.RED.getColor(), Color.RESET.getColor());
                        }
                        else{
                            System.out.printf("%s  ", temp.get(col));
                        }
                    }
                }
                else{
                    if(col == 0){
                        System.out.printf("%s ", temp.get(col));
                    }
                    else{
                        if(this.getPlayer1().isPionExist(pion)){
                            System.out.printf("%so%s  ", Color.GREEN.getColor(), Color.RESET.getColor());
                        }
                        else if(this.getPlayer2().isPionExist(pion)){
                            System.out.printf("%so%s  ", Color.RED.getColor(), Color.RESET.getColor());
                        }
                        else{
                            System.out.printf("%s  ", temp.get(col));
                        }
                    }
                }
            }
            Koordinat pion = new Koordinat(row, alphabet.get(temp.size()-1));
            if(this.getPlayer1().isPionExist(pion)){
                System.out.printf("%so%s\n", Color.GREEN.getColor(), Color.RESET.getColor());
            }
            else if(this.getPlayer2().isPionExist(pion)){
                System.out.printf("%so%s\n", Color.RED.getColor(), Color.RESET.getColor());
            }
            else{
                System.out.printf("%s\n", temp.get(temp.size()-1));
            }
        }
    }

    /**
    Menampilkan informasi board dan pion pemain ke layar
    */
    public void writeInfo(){
        this.writeBoard();
        System.out.printf("P1: ");
        this.getPlayer1().writeKoordinatPion();
        System.out.printf("P2: ");
        this.getPlayer2().writePlayerPion();
    }

    /**
    Return true jika destinasi yang dituju oleh pion masih
    berada di dalam board
    @param Koordinat destinasi --> destinasi pion yang dituju
    @return boolean
    */
    public boolean isInsideBoard(Koordinat destinasi){
        int idx = 0;
        ArrayList<String> alphabet = this.board.get(0);
        for(int i=0; i<alphabet.size(); i++){
            if(alphabet.get(i).equalsIgnoreCase(destinasi.getOrdinat())){
                idx = i;
                break;
            }
        }
        return ((destinasi.getAbsis() >= 1) && (destinasi.getAbsis() < this.board.size())
        && (idx >= 1) && (idx < this.board.size()));
    }

    /**
    Mengembalikan true jika destinasi yang dituju pada koordinat pion saat
    ini merupakan destinasi yang terdefinisi dan valid
    @param pion --> koordinat pion yang dipilih (diasumsikan selalu valid)
    @param destinasi --> destinasi tujuan pion yang dituju
    @return boolean
    */
    public boolean isValidMovement(Koordinat pion, Koordinat destinasi){
        return
        (isInsideBoard(destinasi) &&
        (isMovingOneBlock(pion, destinasi) || canJumpOverPion(pion, destinasi)) &&
        noPionAtDestinasi(destinasi));
    }

    /**
    Mengembalikan true jika koordinat destinasi yang dituju berjarak satu
    blok dari koordinat pion saat ini
    @param pion --> koordinat pion yang dipilih (diasumsikan selalu valid)
    @param destinasi --> destinasi tujuan pion yang dituju
    */
    public boolean isMovingOneBlock(Koordinat pion, Koordinat destinasi){
        return
        (pion.isMovingToNorth(destinasi) || pion.isMovingToNorthEast(destinasi) ||
        pion.isMovingToEast(destinasi) || pion.isMovingToSouthEast(destinasi) ||
        pion.isMovingToSouth(destinasi) || pion.isMovingToSouthWest(destinasi) ||
        pion.isMovingToWest(destinasi) || pion.isMovingToNorthWest(destinasi));
    }

    /**
    Mengembalikan true jika pion dapat menuju destinasi dengan cara melompati
    pion yang berada diantara koordinat pion dengan koordinat destinasi
    Ide: Cek apakah terdapat pion (sendiri atau musuh) yang berada diantara
    koordinat pion dengan koordinat destinasi. Jika ditemukan sebuah pion maka
    destinasi dapat dituju
    @param pion --> pion yang dipilih
    @param destinasi --> destinasi yang dituju
    */
    public boolean canJumpOverPion(Koordinat pion, Koordinat destinasi){
        Player p1 = this.getPlayer1();
        Player p2 = this.getPlayer2();
        if(pion.isMovingToNorth(destinasi.south())){
            return (p1.isPionExist(pion.north()) || p2.isPionExist(pion.north()));
        }
        else if(pion.isMovingToNorthEast(destinasi.southWest())){
            return (p1.isPionExist(pion.northEast()) || p2.isPionExist(pion.northEast()));
        }
        else if(pion.isMovingToEast(destinasi.west())){
            return (p1.isPionExist(pion.east()) || p2.isPionExist(pion.east()));
        }
        else if(pion.isMovingToSouthEast(destinasi.northWest())){
            return (p1.isPionExist(pion.southEast()) || p2.isPionExist(pion.southEast()));
        }
        else if(pion.isMovingToSouth(destinasi.north())){
            return (p1.isPionExist(pion.south()) || p2.isPionExist(pion.south()));
        }
        else if(pion.isMovingToSouthWest(destinasi.northEast())){
            return (p1.isPionExist(pion.southWest()) || p2.isPionExist(pion.southWest()));
        }
        else if(pion.isMovingToWest(destinasi.east())){
            return (p1.isPionExist(pion.west()) || p2.isPionExist(pion.west()));
        }
        else if(pion.isMovingToNorthWest(destinasi.southEast())){
            return (p1.isPionExist(pion.northWest()) || p2.isPionExist(pion.northWest()));
        }
        return false;
    }

    /**
    Mengembalikan true jika tidak ada pion pada koordinat destinasi
    @param destinasi --> koordinat destinasi yang akan dituju
    */
    public boolean noPionAtDestinasi(Koordinat destinasi){
        return
        (!this.getPlayer1().isPionExist(destinasi) &&
        !this.getPlayer2().isPionExist(destinasi));
    }

    /**
    Mengembalikan true jika pion sudah berada diluar koordinat
    base sendiri
    @param pion --> koordinat pion yang dipilih
    @return boolean
    */
    public boolean isOutFromBase(Koordinat pion){
        boolean found = false;
        int i = 0;
        if(this.getPlayer1().isPionExist(pion)){
            while(!found && i<this.getPlayer1().getPlayerBase().size()){
                if(pion.isKoordinatEQ(this.getPlayer1().getPlayerBase().get(i))){
                    found = true;
                }
                else{
                    i++;
                }
            }
        }
        if(this.getPlayer2().isPionExist(pion)){
            while(!found && i<this.getPlayer2().getPlayerBase().size()){
                if(pion.isKoordinatEQ(this.getPlayer2().getPlayerBase().get(i))){
                    found = true;
                }
                else{
                    i++;
                }
            }
        }
        return found;
    }

    public boolean isDestinasiBase(Koordinat pion, Koordinat destinasi){
        boolean base = false;
        int i = 0;
        if(this.getPlayer1().isPionExist(pion)){
            while(!base && i<this.getPlayer1().getPlayerBase().size()){
                if(destinasi.isKoordinatEQ(this.getPlayer1().getPlayerBase().get(i))){
                    base = true;
                }
                else{
                    i++;
                }
            }
        }
        if(this.getPlayer2().isPionExist(pion)){
            while(!base && i<this.getPlayer2().getPlayerBase().size()){
                if(destinasi.isKoordinatEQ(this.getPlayer2().getPlayerBase().get(i))){
                    base = true;
                }
                else{
                    i++;
                }
            }
        }
        return base;
    }

    // public boolean wantToGoInsideBase(Koordinat pion, Koordinat destinasi){
    //     if(isDestinasiBase(pion, destinasi)){
    //         if(!isOutFromBase(pion)){
                
    //         }
    //     }
    // }

    /***** HEURISTIC APPROACH *****/
    // 1. Butuh fungsi yang dapat menentukan pion mana yang memiliki gerakan paling banyak
    // 2. Setelah pion telah ditentukan, butuh fungsi yang dapat memetakan "possible moves"
    // 3. ...?
    // PERLU FUNGSI UNTUK MENGHITUNG NILAI DARI SUATU STATE BOARD

    /**
    Menghitung heuristic pion yang dipilih
    Mungkin idenya seperti menghitung nilai kebebasan dari
    pion yang dipilih. Semakin banyak koordinat yang mampu dicapai
    oleh pion, maka semakin bebas pion tersebut bergerak.
    NAMUN, nanti pada implementasinya, akan dipilih pion yang
    memiliki kebebesan bergerak PALING KECIL/PALING BANYAK?????
    @param pion --> koordinat pion yang dipilih
    @return --> pion's freedom point
    **/
    public int countFreedomPion(Koordinat pion){
        int point = 0;

        // Jika pion milik player 1
        // Untuk player 1 gunakan hanya gerakan east, southeast, south
        // dan juga gerakan melompat east, southeast, south
        if(this.getPlayer1().isPionExist(pion)){
            if(this.isValidMovement(pion, pion.east())){
                point++;
            }
            if(this.isValidMovement(pion, pion.southEast())){
                point += 2;
            }
            if(this.isValidMovement(pion, pion.south())){
                point++;
            }
            if(this.isValidMovement(pion, pion.jumpToEast())){
                point += 2;
            }
            if(this.isValidMovement(pion, pion.jumpToSouthEast())){
                point += 4;
            }
            if(this.isValidMovement(pion, pion.jumpToSouth())){
                point += 2;
            }
        }

        // Jika pion milik player 2
        // Gunakan hanya untuk gerakan west, northwest, north
        // dan juga gerakan melompat west, northwest, north
        if(this.getPlayer2().isPionExist(pion)){
            if(this.isValidMovement(pion, pion.west())){
                point++;
            }
            if(this.isValidMovement(pion, pion.northWest())){
                point += 2;
            }
            if(this.isValidMovement(pion, pion.north())){
                point++;
            }
            if(this.isValidMovement(pion, pion.jumpToWest())){
                point += 2;
            }
            if(this.isValidMovement(pion, pion.jumpToNorthWest())){
                point += 4;
            }
            if(this.isValidMovement(pion, pion.jumpToNorth())){
                point += 2;
            }
        }

        return point;
    }

    /**** RANDOM MOVES ALGORITHM *****/

    /**
    Mengembalikan true jika pion yang dipilih sudah berada pada
    base musuh. ASUMSI pion merupakan milik salah satu dari player
    @param pion --> Koordinat pion yang dipilih
    **/
    public boolean isInsideEnemyBase(Koordinat pion){
        boolean found = false;
        int i = 0;
        // Cek milik siapa pion yang dimasukkan
        // Jika pion milik player 1
        if(this.getPlayer1().isPionExist(pion)){
            while(!found && i < this.getPlayer2().getPlayerBase().size()){
                if(pion.isKoordinatEQ(this.getPlayer2().getPlayerBase().get(i))){
                    found = true;
                }
                else{
                    i++;
                }
            }
            return found;
        }
        if(this.getPlayer2().isPionExist(pion)){
            while(!found && i < this.getPlayer1().getPlayerBase().size()){
                if(pion.isKoordinatEQ(this.getPlayer1().getPlayerBase().get(i))){
                    found = true;
                }
                else{
                    i++;
                }
            }
            return found;
        }
        return false;
    }

    /**
    Mengembalikan list koordinat destinasi yang dapat dipilih oleh pion
    @param pion --> koordinat pion yang dipilih
    @return list of possible move from pion
    **/
    public List<Koordinat> possibleMoveOfPion(Koordinat pion){
        List<Koordinat> result = new ArrayList<Koordinat>();
        if(isValidMovement(pion, pion.north())){
            result.add(pion.north());
        }
        if(isValidMovement(pion, pion.northEast())){
            result.add(pion.northEast());
        }
        if(isValidMovement(pion, pion.east())){
            result.add(pion.east());
        }
        if(isValidMovement(pion, pion.southEast())){
            result.add(pion.southEast());
        }
        if(isValidMovement(pion, pion.south())){
            result.add(pion.south());
        }
        if(isValidMovement(pion, pion.southWest())){
            result.add(pion.southWest());
        }
        if(isValidMovement(pion, pion.west())){
            result.add(pion.west());
        }
        if(isValidMovement(pion, pion.northWest())){
            result.add(pion.northWest());
        }

        if(isValidMovement(pion, pion.jumpToNorth())){
            result.add(pion.jumpToNorth());
        }
        if(isValidMovement(pion, pion.jumpToNorthEast())){
            result.add(pion.jumpToNorthEast());
        }
        if(isValidMovement(pion, pion.jumpToEast())){
            result.add(pion.jumpToEast());
        }
        if(isValidMovement(pion, pion.jumpToSouthEast())){
            result.add(pion.jumpToSouthEast());
        }
        if(isValidMovement(pion, pion.jumpToSouth())){
            result.add(pion.jumpToSouth());
        }
        if(isValidMovement(pion, pion.jumpToSouthWest())){
            result.add(pion.jumpToSouthWest());
        }
        if(isValidMovement(pion, pion.jumpToWest())){
            result.add(pion.jumpToWest());
        }
        if(isValidMovement(pion, pion.jumpToNorthWest())){
            result.add(pion.jumpToNorthWest());
        }
        return result;
    }

    /**
    @param randomPion --> pion yang dipilih untuk bergerak
    @return destinasi koordinat yang dituju
    */
    public Koordinat moveRandomly(Koordinat randomPion){
        List<Koordinat> moves = possibleMoveOfPion(randomPion);
        // System.out.println("size: " + moves.size());
        int randomIdx = new Random().nextInt(moves.size());
        // System.out.println("random idx: " + randomIdx);
        // System.out.println("size: " + moves.size());
        try {
            moves.get(randomIdx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moves.get(randomIdx);
    }
}
