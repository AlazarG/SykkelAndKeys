package net.mahber.sykkelandkeys.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Station.
 */
@Entity
@Table(name = "station")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "number_of_locks")
    private Long numberOfLocks;

    @OneToOne
    @JoinColumn(unique = true)
    private Availability availability;

    @OneToOne
    @JoinColumn(unique = true)
    private Center center;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Station title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Station subtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Long getNumberOfLocks() {
        return numberOfLocks;
    }

    public Station numberOfLocks(Long numberOfLocks) {
        this.numberOfLocks = numberOfLocks;
        return this;
    }

    public void setNumberOfLocks(Long numberOfLocks) {
        this.numberOfLocks = numberOfLocks;
    }

    public Availability getAvailability() {
        return availability;
    }

    public Station availability(Availability availability) {
        this.availability = availability;
        return this;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public Center getCenter() {
        return center;
    }

    public Station center(Center center) {
        this.center = center;
        return this;
    }

    public void setCenter(Center center) {
        this.center = center;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Station station = (Station) o;
        if (station.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), station.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Station{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", subtitle='" + getSubtitle() + "'" +
            ", numberOfLocks=" + getNumberOfLocks() +
            "}";
    }
}
