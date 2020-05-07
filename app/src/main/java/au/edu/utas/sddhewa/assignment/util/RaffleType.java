package au.edu.utas.sddhewa.assignment.util;

public enum RaffleType {

    NORMAL_RAFFLE(1),
    MARGIN_RAFFLE(2);

    public int id;

    private RaffleType(int id) {
        this.id = id;
    }
}
