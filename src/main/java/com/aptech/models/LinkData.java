package com.aptech.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkData {
    private Integer ID;
    private String link;
    private String title;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkData)) return false;
        LinkData linkData = (LinkData) o;
        return getID().equals(linkData.getID()) &&
                getLink().equals(linkData.getLink()) &&
                getTitle().equals(linkData.getTitle()) &&
                Objects.equals(getContent(), linkData.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getLink(), getTitle(), getContent());
    }
}
