package org.liamb.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Magazine extends Item{
    private String issueNumber;
    private String publisher;

    public Magazine(String id, ItemStatus status, String title, String issueNumber, String publisher) {
        super(id, status, title);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }
}
