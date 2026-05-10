package org.liamb.domain;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public abstract class Item {
    protected String id;
    @Setter protected ItemStatus status;
    protected String title;

    private static int nextId = 1;

    public Item(String title) {
        this.id = String.format("%04d", nextId++);
        this.status = ItemStatus.IN_STORE;
        this.title = title;
    }

    public enum ItemStatus {
        BORROWED, IN_STORE, LOST
    }
}
