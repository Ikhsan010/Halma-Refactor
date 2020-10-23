import java.util.ArrayList;
import java.util.List;

public class Board {
    private Player player1, player2;
    private List<ArrayList<String>> board;

    /*
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

    /*
    Getter pemain satu
    @return --> this.player1
    */
    public Player getPlayer1(){
        return this.player1;
    }

    /*
    Getter pemain dua
    @return --> this.player2
    */
    public Player getPlayer2(){
        return this.player2;
    }

    /*
    Menginisiasi permainan: board, player1, player2
    param: int size --> ukuran board
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

    /*
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

    /*
    Menampilkan informasi board dan pion pemain ke layar
    */
    public void writeInfo(){
        this.writeBoard();
        System.out.printf("P1: ");
        this.getPlayer1().writeKoordinatPion();
        System.out.printf("P2: ");
        this.getPlayer2().writePlayerPion();
    }

    /*
    Return true jika destinasi yang dituju oleh pion masih
    berada di dalam board
    param: Koordinat destinasi --> destinasi pion yang dituju
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

    /*
    Mengembalikan true jika destinasi yang dituju pada koordinat pion saat
    ini merupakan destinasi yang terdefinisi dan valid
    @param pion --> koordinat pion yang dipilih (diasumsikan selalu valid)
    @param destinasi --> destinasi tujuan pion yang dituju
    */
    public boolean isValidMovement(Koordinat pion, Koordinat destinasi){
        return
        (isInsideBoard(destinasi) &&
        (isMovingOneBlock(pion, destinasi) || canJumpOverPion(pion, destinasi)) &&
        noPionAtDestinasi(destinasi));
    }

    /*
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

    /*
    Mengembalikan true jika tidak ada pion pada koordinat destinasi
    @param destinasi --> koordinat destinasi yang akan dituju
    */
    public boolean noPionAtDestinasi(Koordinat destinasi){
        return
        (!this.getPlayer1().isPionExist(destinasi) &&
        !this.getPlayer2().isPionExist(destinasi));
    }
}
