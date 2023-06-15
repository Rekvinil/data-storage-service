package com.vkr.datastorageservice.data.db;

import com.vkr.datastorageservice.data.db.Role;
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
@Table(name="\"user\"")
public class User {

    @Id
    @Column(name = "user_id")
    private UUID id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String initialFolder;

    @ManyToOne
    private Role role;

    @ManyToMany(mappedBy = "members")
    private List<Room> rooms;
}
