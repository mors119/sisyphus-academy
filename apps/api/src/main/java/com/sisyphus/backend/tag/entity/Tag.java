package com.sisyphus.backend.tag.entity;

import com.sisyphus.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "tag",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_tag_user", columnNames = {"name","user_id"}))
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    /* Tag 측에도 orphanRemoval=true 적용 */
    @OneToMany(mappedBy = "tag",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<NoteTag> noteTags = new HashSet<>();

    /* ---------- 도메인 로직 ---------- */
    public void changeName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("태그 이름은 비어 있을 수 없습니다.");
        }
        this.name = newName.trim().toLowerCase();
    }

    public static Tag of(String name, User user) {
        if (user == null) throw new IllegalArgumentException("owner must not be null");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must not be blank");

        Tag t = new Tag();
        t.name = name.trim().toLowerCase();
        t.user = user;
        return t;
    }

    public static List<Tag> of(User user, List<String> names) {
        if (user == null) throw new IllegalArgumentException("user must not be null");
        if (names == null || names.isEmpty()) return List.of();

        List<Tag> tags = new ArrayList<>();

        for (String n : names) {
            if (n == null || n.isBlank()) continue;
            tags.add(Tag.of(n, user));
        }

        return tags;
    }

}