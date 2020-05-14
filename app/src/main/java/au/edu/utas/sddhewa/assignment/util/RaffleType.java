package au.edu.utas.sddhewa.assignment.util;

public enum RaffleType {

    NORMAL_RAFFLE(1, "Normal Raffle"),
    MARGIN_RAFFLE(2, "Margin Raffle");

    public int id;
    public String name;

    private RaffleType(int id, String name) {

        this.id = id;
        this.name = name;
    }
}
