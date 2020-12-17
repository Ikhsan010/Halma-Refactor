public enum Color {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    RESET("\u001B[0m");

    public final String color;

    /**
    Konstruktor pembentuk warna (pallete)
    @param color --> warna yang diinginkan
    */
    private Color(String color){
        this.color = color;
    }

    /**
    Getter warna yang dipilih
    @return --> warna yang dipilih
    */
    public String getColor(){
        return this.color;
    }
}
