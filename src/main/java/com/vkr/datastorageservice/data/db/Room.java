package com.vkr.datastorageservice.data.db;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Room {

    @Id
    @Column(name = "room_id")
    private UUID id;

    private String name;

    @ManyToOne
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "room_members",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<User> members;
}
