import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Minesweeper extends JFrame{
    //private JFrame gameFrame;
    private JPanel gamePanel;
    private final int gameWidth = 24;
    private final int gameHeight = 24;
    private GridLayout grid = new GridLayout(gameHeight, gameWidth);
    private final int amountOfMines = 80;
    private int flags = 0;
    private boolean gameIsOver = false;
    private Map<Integer, MinesweeperButton> allPlaces = new HashMap<>();
    private Map<Integer, Boolean> mines = new HashMap<>();
    private Queue<MinesweeperButton> toBeChecked = new LinkedList<>();

    public static void main(String[] args){
        Minesweeper game = new Minesweeper();
        game.runGame();
    }

    public void runGame(){
        this.setTitle("Minesweeper");
        gamePanel = new JPanel();
        //gamePanel.addKeyListener(new resetClickListener());
        gamePanel.setLayout(grid);
        gamePanel.setSize(gameWidth*25, gameHeight*25);
        populateBoard();
        this.add(gamePanel);
        this.setSize(gameWidth*25,gameHeight*25);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        processKeys();
        this.setVisible(true);
    }

    private void populateBoard(){
        for(int i = 0; i < amountOfMines; i++){
            boolean uniquePlacementNotFound = true;
            while(uniquePlacementNotFound){
                int newRandomPlacement = getRandomPlacement();
                if(mines.get(newRandomPlacement) == null){
                    mines.put(newRandomPlacement, true);
                    uniquePlacementNotFound = false;
                }
            }
        }
        for(int i = 1; i <= (gameWidth*gameHeight); i++){
            BoardPlacement bp = new BoardPlacement(i, gameWidth, gameHeight);
            MinesweeperButton newPlace;
            if(mines.get(i) == null){
                newPlace = new MinesweeperButton(false, bp);
            }
            else if(mines.get(i)){
                newPlace = new MinesweeperButton(true, bp);
            }else{
                newPlace = new MinesweeperButton(false, bp);
            }
            newPlace.addMouseListener(new placeClickListener());
            allPlaces.put(i, newPlace);
            gamePanel.add(newPlace);
        }
    }

    public int getRandomPlacement(){
        int maxInt = (gameWidth * gameHeight);
        Random random = new Random();
        return random.nextInt(maxInt) + 1;
    }

    private int checkNeighbourBombs(MinesweeperButton place){
        int neighbouringBombs = 0;
        boolean isNotLeftMost = true;
        boolean isNotRightMost = true;
        LinkedList<MinesweeperButton> addToQueueInCaseOfNoBombs = new LinkedList<>();

        if(place.getPlacement().getX() != 1){
            MinesweeperButton left = allPlaces.get(place.getPlacement().getOneDimensionalPosition()-1);
            if(!left.isClicked()){
                addToQueueInCaseOfNoBombs.add(left);
            }
            if(left.isBomb()){
                neighbouringBombs++;
            }
        }else{
            isNotLeftMost = false;
        }
        if(place.getPlacement().getX() != gameWidth){
            MinesweeperButton right = allPlaces.get(place.getPlacement().getOneDimensionalPosition()+1);
            if(!right.isClicked()){
                addToQueueInCaseOfNoBombs.add(right);
            }
            if(right.isBomb()){
                neighbouringBombs++;
            }
        }else{
            isNotRightMost = false;
        }
        if(place.getPlacement().getY() != 1){
            MinesweeperButton top = allPlaces.get(place.getPlacement().getOneDimensionalPosition()-gameWidth);
            if(!top.isClicked()){
                addToQueueInCaseOfNoBombs.add(top);
            }
            if(top.isBomb()){
                neighbouringBombs++;
            }
            if(isNotLeftMost){
                MinesweeperButton topLeft = allPlaces.get(place.getPlacement().getOneDimensionalPosition()-gameWidth-1);
                if(!topLeft.isClicked()){
                    addToQueueInCaseOfNoBombs.add(topLeft);
                }
                if(topLeft.isBomb()){
                    neighbouringBombs++;
                }
            }
            if(isNotRightMost){
                MinesweeperButton topRight = allPlaces.get(place.getPlacement().getOneDimensionalPosition()-gameWidth+1);
                if(!topRight.isClicked()){
                    addToQueueInCaseOfNoBombs.add(topRight);
                }
                if(topRight.isBomb()){
                    neighbouringBombs++;
                }
            }
        }
        if(place.getPlacement().getY() != gameHeight){
            MinesweeperButton bottom = allPlaces.get(place.getPlacement().getOneDimensionalPosition()+gameWidth);
            if(!bottom.isClicked()){
                addToQueueInCaseOfNoBombs.add(bottom);
            }
            if(bottom.isBomb()){
                neighbouringBombs++;
            }
            if(isNotLeftMost){
                MinesweeperButton bottomLeft = allPlaces.get(place.getPlacement().getOneDimensionalPosition()+gameWidth-1);
                if(!bottomLeft.isClicked()){
                    addToQueueInCaseOfNoBombs.add(bottomLeft);
                }
                if(bottomLeft.isBomb()){
                    neighbouringBombs++;
                }
            }
            if(isNotRightMost){
                MinesweeperButton bottomRight = allPlaces.get(place.getPlacement().getOneDimensionalPosition()+gameWidth+1);
                if(!bottomRight.isClicked()){
                    addToQueueInCaseOfNoBombs.add(bottomRight);
                }
                if(bottomRight.isBomb()){
                    neighbouringBombs++;
                }
            }
        }
        if(neighbouringBombs == 0){
            if(!place.isClicked()){
                place.setClicked();
            }
            toBeChecked.addAll(addToQueueInCaseOfNoBombs);
        }
        return neighbouringBombs;
    }

    private void resetGame(){
        flags = 0;
        gameIsOver = false;
        allPlaces = new HashMap<>();
        mines = new HashMap<>();
        toBeChecked = new LinkedList<>();
        gamePanel.removeAll();
        populateBoard();
    }

    private void processKeys(){
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
                new KeyEventDispatcher()  {
                    public boolean dispatchKeyEvent(KeyEvent e){
                        if(e.getID() == KeyEvent.KEY_RELEASED){
                            if(e.getKeyCode() == KeyEvent.VK_R){
                                resetGame();
                            }
                        }
                        return false;
                    }
                });
    }

    private class placeClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            MinesweeperButton place = (MinesweeperButton) e.getSource();
            if(SwingUtilities.isRightMouseButton(e) && !gameIsOver && !place.isClicked()){
                boolean executeToggle = true;
                if(flags == amountOfMines){
                    if(place.isFlagged()){
                        //do nothing
                    }else{
                        executeToggle = false;
                    }
                }
                if(executeToggle){
                    if(place.isFlagged()){
                        flags --;
                    }else{
                        flags++;
                    }
                    place.toggleFlag();
                }
            }else if(SwingUtilities.isLeftMouseButton(e) && !gameIsOver && !place.isClicked() && !place.isFlagged()){
                place.setClicked();
                if(place.isBomb()){
                    place.triggerBomb();
                    gameIsOver = true;
                }else{
                    toBeChecked.add(place);
                    while(!toBeChecked.isEmpty()){
                        MinesweeperButton current = toBeChecked.poll();
                        int amountOfNeighbourBombs = checkNeighbourBombs(current);
                        current.setNumberedIcon(amountOfNeighbourBombs);
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

}
