package com.dala.mapz.gamebase.factories.room;

public enum RoomFactoryEnum {
    OGRESS(new OgressAbstractRoomFactory()),
    SANTA(new SantaAbstractRoomFactory()),
    GOLDEN(new GoldenAbstractRoomFactory());

    public final AbstractRoomFactory roomFactory;
    RoomFactoryEnum(AbstractRoomFactory roomFactory) {
        this.roomFactory = roomFactory;
    }
}
