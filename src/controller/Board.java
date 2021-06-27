package controller;

import model.Stone;

import java.util.List;

public class Board {

    private List<Stone> unUsedStones;
    private Stone[][] board;
    private Stone root;

    public Board() {
        this.unUsedStones = Stone.shuffle();
        this.board = new Stone[56][56];
    }

}
