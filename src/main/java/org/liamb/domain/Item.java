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

    public Item(String id, String title) {
        this.id = id;
        this.status = ItemStatus.IN_STORE;
        this.title = title;
    }

    public enum ItemStatus {
        BORROWED, IN_STORE, LOST
    }
}
