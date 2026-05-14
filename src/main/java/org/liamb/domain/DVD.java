package org.liamb.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DVD extends Item{
    private String director;
    private int duration;

    public DVD(String id, ItemStatus status, String title, String director, int duration) {
        super(id, status, title);
        this.director = director;
        this.duration = duration;
    }
}
