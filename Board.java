package com.mar.main2048;

import java.util.ArrayList;
import java.util.List;


public class Board {

    private int size;
    private int score;
    private int emptyTiles;
    private int initTiles = 2;
    private boolean gameover = false;
    private String wonOrLost;
    private boolean genNewTile = false;
    private List<List<Tile>> tiles;


    public Board(int size) {
        super();
        this.size = size;
        this.emptyTiles = this.size * this.size;
        this.tiles = new ArrayList<>();

        start();
    }

    private void initialize() {
        for (int row = 0; row < this.size; row++) {
            tiles.add(new ArrayList<Tile>());
            for (int col = 0; col < this.size; col++) {
                tiles.get(row).add(new Tile());
            }
        }
    }

    private void start() {
        Game.CONTROLS.bind();
        initialize();
        genInitTiles();

    }

    public Tile getTileAt(int row, int col) {
        return tiles.get(row).get(col);
    }

    public void setTileAt(int row, int col, Tile t) {
        tiles.get(row).set(col, t);
    }



    public int getScore() {
        return score;
    }


    private List<Tile> mergeTiles(List<Tile> sequence) {
        for (int l = 0; l < sequence.size() - 1; l++) {
            if (sequence.get(l).getValue() == sequence.get(l + 1).getValue()) {
                int value;
                if ((value = sequence.get(l).merging()) == 2048) {
                    gameover = true;
                }
                score += value;
                sequence.remove(l + 1);
                genNewTile = true;
                emptyTiles++;
            }
        }
        return sequence;
    }


    private List<Tile> addEmptyTilesFirst(List<Tile> merged) {
        for (int k = merged.size(); k < size; k++) {
            merged.add(0, new Tile());
        }
        return merged;
    }


    private List<Tile> addEmptyTilesLast(List<Tile> merged) {
        for (int k = merged.size(); k < size; k++) {
            merged.add(k, new Tile());
        }
        return merged;
    }

    private List<Tile> removeEmptyTilesRows(int row) {

        List<Tile> moved = new ArrayList<>();

        for (int col = 0; col < size; col++) {
            if (!getTileAt(row, col).isEmpty()) {
                moved.add(getTileAt(row, col));
            }
        }

        return moved;
    }

    private List<Tile> removeEmptyTilesCols(int row) {

        List<Tile> moved = new ArrayList<>();

        for (int col = 0; col < size; col++) {
            if (!getTileAt(col, row).isEmpty()) {
                moved.add(getTileAt(col, row));
            }
        }

        return moved;
    }

    private List<Tile> setRowToBoard(List<Tile> moved, int row) {
        for (int col = 0; col < tiles.size(); col++) {
            if (moved.get(col).hasMoved(row, col)) {
                genNewTile = true;
            }
            setTileAt(row, col, moved.get(col));
        }

        return moved;
    }

    private List<Tile> setColToBoard(List<Tile> moved, int row) {
        for (int col = 0; col < tiles.size(); col++) {
            if (moved.get(col).hasMoved(col, row)) {
                genNewTile = true;
            }
            setTileAt(col, row, moved.get(col));
        }

        return moved;
    }

    public void moveUp() {

        List<Tile> moved;

        for (int row = 0; row < size; row++) {

            moved = removeEmptyTilesCols(row);
            moved = mergeTiles(moved);
            moved = addEmptyTilesLast(moved);
            moved = setColToBoard(moved, row);

        }

    }

    public void moveDown() {

        List<Tile> moved;

        for (int row = 0; row < size; row++) {

            moved = removeEmptyTilesCols(row);
            moved = mergeTiles(moved);
            moved = addEmptyTilesFirst(moved);
            moved = setColToBoard(moved, row);

        }

    }

    public void moveLeft() {

        List<Tile> moved;

        for (int row = 0; row < size; row++) {

            moved = removeEmptyTilesRows(row);
            moved = mergeTiles(moved);
            moved = addEmptyTilesLast(moved);
            moved = setRowToBoard(moved, row);

        }

    }

    public void moveRight() {

        List<Tile> moved;

        for (int row = 0; row < size; row++) {

            moved = removeEmptyTilesRows(row);
            moved = mergeTiles(moved);
            moved = addEmptyTilesFirst(moved);
            moved = setRowToBoard(moved, row);

        }

    }

    public void isGameOver() {

        if (gameover) {
            setWonOrLost("выиграл");
        } else {
            if (isFull()) {
                if (!isMovePossible()) {

                    setWonOrLost("проиграл");
                }

            } else {
                newRandomTile();
            }
        }
    }

    private boolean isFull() {
        return emptyTiles == 0;
    }

    private boolean isMovePossible() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size - 1; col++) {
                if (getTileAt(row, col).getValue() == getTileAt(row, col + 1).getValue()) {
                    return true;
                }
            }
        }

        for (int row = 0; row < size - 1; row++) {
            for (int col = 0; col < size; col++) {
                if (getTileAt(col, row).getValue() == getTileAt(col, row + 1).getValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void genInitTiles() {
        for (int i = 0; i < initTiles; i++) {
            genNewTile = true;
            newRandomTile();
        }
    }

    private void newRandomTile() {
        if (genNewTile) {
            int row;
            int col;
            int value = Math.random() < 0.9 ? 2 : 4;
            do {
                row = (int) (Math.random () * 4);
                col = (int) (Math.random () * 4);
            } while (getTileAt(row, col).getValue() != 0);
            setTileAt(row, col, new Tile(value, row, col));
            emptyTiles--;
            genNewTile = false;
        }
    }



    public String getWonOrLost() {

        return wonOrLost;
    }

    public void setWonOrLost(String wonOrLost) {
        this.wonOrLost = wonOrLost;
    }

}
