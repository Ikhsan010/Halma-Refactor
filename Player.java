import java.util.List;
import java.util.ArrayList;

public class Player {
    private List<Koordinat> base;
    private List<Koordinat> pion;
    private boolean state;

    /*
    Konstruktor pembentuk Player
    @param state --> state untuk pergantian pemain
    */
    public Player(boolean state){
        this.state = state;
        this.base = new ArrayList<Koordinat>();
        this.pion = new ArrayList<Koordinat>();
    }

    /*
    Getter state player
    @return --> boolean
    */
    public boolean getPlayerState(){
        return this.state;
    }

    /*
    Getter player's base
    @return --> List of Koordinat
    */
    public List<Koordinat> getPlayerBase(){
        return this.base;
    }

    /*
    Getter player's pion
    @return --> List of Koordinat
    */
    public List<Koordinat> getPlayerPion(){
        return this.pion;
    }

    /*
    Menambahkan koordinat ke dalam player's base
    @param koordinat --> koordinat base pemain
    */
    public void addKoordinatToBase(Koordinat koordinat){
        this.base.add(koordinat);
    }

    /*
    Menambahkan koordinat ke dalam pion ke dalam list
    @param koordinat --> koordinat pion pemain
    */
    public void addKoordinatToPlayer(Koordinat koordinat){
        this.pion.add(koordinat);
    }

    /*
    Setter state pemain
    @param state --> state pemain
    */
    public void setPlayerState(boolean state){
        this.state = state;
    }

    /*
    Return true jika pion ditemukan this.player
    @param pion --> koordinat pion yang akan dicocokkan
    */
    public boolean isPionExist(Koordinat pion){
        int i=0;
        boolean found = false;
        while(!found && i<this.getPlayerPion().size()){
            if(this.getPlayerPion().get(i).isKoordinatEQ(pion)){
                found = true;
            }
            else{
                i++;
            }
        }
        return found;
    }

    /*
    Menampilkan koordinat pion yang dimiliki pemain ke layar
    */
    public void writePlayerPion(){
        for (Koordinat pion : this.getPlayerPion()) {
            pion.writeKoordinat();
        }
        System.out.println();
    }

    /*
    Return true jika this.pion masih berada pada koordinat base
    @param destinasi --> destinasi tujuan pion yang dituju
    */
    public boolean isInsideBase(Koordinat destinasi){
        boolean found = false;
        int idx = 0;
        while(!found && idx < this.getPlayerBase().size()){
            if(this.getPlayerBase().get(idx).isKoordinatEQ(destinasi)){
                found = true;
            }
            else{
                idx++;
            }
        }
        return found;
    }

    public void writeKoordinatPion(){
        for(int i=0; i<this.getPlayerPion().size(); i++){
            this.getPlayerPion().get(i).writeKoordinat();
        }
        System.out.println();
    }

    /*
    Mengembalikan true jika this.player berhasil memindahkan semua pionnya
    ke base musuh. Algoritma searching dengan boolean
    @param enemy --> untuk mendapatkan base pemain lawan
    */
    public boolean isWin(Player enemy){
        boolean sama = true;
        int i = 0;
        int j = 0;
        while(sama && i < this.getPlayerPion().size()){
            sama = false;
            j =0;
            while(!sama && j < enemy.getPlayerBase().size()){
                if(this.getPlayerPion().get(i).isKoordinatEQ(enemy.getPlayerBase().get(j))){
                    sama = true;
                }
                else{
                    j++;
                }
            }
            i++;
        }
        return sama;
    }

    /**
    Mengembalikan index pion ditemukan pada list
    Jika pion tidak tersedia pada list maka return -1
    @param pion --> koordinat pion yang dicari di list dari this.player
    @return boolean or -1
    **/
    public int indexOfPion(Koordinat pion){
        boolean found = false;
        int i = 0;
        while(!found && i < this.getPlayerPion().size()){
            if(this.getPlayerPion().get(i).isKoordinatEQ(pion)){
                found = true;
            }
            else{
                i++;
            }
        }
        if(found){
            return i;
        }
        else{
            return -1;
        }
    }
}
