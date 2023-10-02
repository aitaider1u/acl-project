package com.dala.mapz.gamebase.factories;

import com.dala.mapz.gamebase.Room;
import com.dala.mapz.utils.Orientation;

import java.util.*;

public class RoomTree implements Iterable<RoomTree>
{
    private Room room;
    private Orientation orientation;
    private final List<RoomTree> nexts = new ArrayList<>(3);

    public RoomTree(Room room, Orientation orientation) {
        this.room = room;
        this.orientation = orientation;
    }

    public int size() {
        if(nexts.isEmpty())
            return 1;
        int size = 0;
        for(RoomTree next : nexts) {
            size += next.size();
        }
        return size+1;
    }

    public void addNexts(RoomTree ... nexts) {
        this.nexts.addAll(Arrays.asList(nexts));
    }

    public Room getRoom() {
        return room;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public Iterator<RoomTree> iterator() {
        Collections.shuffle(nexts);
        return nexts.iterator();
    }

    private String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t".repeat(Math.max(0, depth))).append(orientation).append("{\n");
        for(RoomTree next : nexts) {
            sb.append("\t".repeat(Math.max(0, depth))).append(next.toString(depth+1)).append("\n");
        }
        sb.append("\t".repeat(Math.max(0, depth))).append("}\n");
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(orientation).append("{\n");
        for(RoomTree next : nexts) {
            sb.append(next.toString(1)).append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }
}
