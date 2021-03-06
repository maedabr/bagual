package io.maeda.apps.bagual.models;

import io.maeda.apps.bagual.validators.NotAShortUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "urls")
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotAShortUrl
    @NonNull
    @Column(name = "url_original", nullable = false)
    private String originalUrl;

    @Setter
    @Column(name = "suspect", nullable = false)
    private boolean suspect;

    @Builder.Default
    @Column(name = "shortcut_count", nullable = false, length = 11)
    private int shortcutCount = 1;

    @EqualsAndHashCode.Exclude
    @NonNull
    @Builder.Default
    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    @EqualsAndHashCode.Exclude
    @NonNull
    @Builder.Default
    @Column(name = "modified", nullable = false)
    private LocalDateTime modified = LocalDateTime.now();

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        created = created == null ? now : created;
        modified = modified == null ? now : modified;
    }

    @PreUpdate
    void preUpdate() {
        modified = LocalDateTime.now();
    }

    public UrlBuilder copy() {
        return Url.builder()
                .id(this.id)
                .originalUrl(this.originalUrl)
                .suspect(this.suspect)
                .shortcutCount(this.shortcutCount)
                .created(this.created)
                .modified(this.modified);
    }
}
