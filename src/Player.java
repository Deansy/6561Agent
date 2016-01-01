/**
 * Created by camsh on 01/01/2016.
 */
public interface Player {

    void handlePlaceTurn(String moveInfo);
    void handleMoveTurn(String moveInfo);

    void performPlaceTurn();
    void performMoveTurn();


}
