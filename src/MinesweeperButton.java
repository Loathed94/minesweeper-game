import javax.swing.*;

public class MinesweeperButton extends JButton {

    private final boolean isBomb;
    private final BoardPlacement placement;
    private boolean flagged = false;
    private boolean clicked = false;

    public MinesweeperButton(boolean isBomb, BoardPlacement placement) {
        this.isBomb = isBomb;
        this.placement = placement;
        super.setBounds(placement.getX()*15, placement.getY()*15, 15, 15);
    }

    public boolean isBomb() {
        return isBomb;
    }

    public BoardPlacement getPlacement() {
        return placement;
    }

    public boolean isFlagged(){
        return flagged;
    }

    public void triggerBomb(){
        setIcon(new ImageIcon("C:\\Users\\CNeij1\\Pictures\\Minesweeper\\mine2.png"));
    }

    public boolean isClicked(){
        return clicked;
    }

    public void setClicked(){
        clicked = true;
    }

    public void toggleFlag(){
        flagged = !flagged;
        if(flagged){
            setIcon(new ImageIcon("C:\\Users\\CNeij1\\Pictures\\Minesweeper\\flag.png"));
        }else{
            setIcon(new ImageIcon());
        }
    }

    public void setNumberedIcon(int number){
        String filepath = "C:\\Users\\CNeij1\\Pictures\\Minesweeper\\numbers\\";
        switch(number){
            case 0:
                setIcon(new ImageIcon(filepath+"0.png"));
                break;
            case 1:
                setIcon(new ImageIcon(filepath+"1.png"));
                break;
            case 2:
                setIcon(new ImageIcon(filepath+"2.png"));
                break;
            case 3:
                setIcon(new ImageIcon(filepath+"3.png"));
                break;
            case 4:
                setIcon(new ImageIcon(filepath+"4.png"));
                break;
            case 5:
                setIcon(new ImageIcon(filepath+"5.png"));
                break;
            case 6:
                setIcon(new ImageIcon(filepath+"6.png"));
                break;
            case 7:
                setIcon(new ImageIcon(filepath+"7.png"));
                break;
            case 8:
                setIcon(new ImageIcon(filepath+"8.png"));
                break;
        }
    }

    @Override
    public String toString(){
        return isBomb+" "+flagged+" "+placement;
    }
}
