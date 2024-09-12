package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`GROUPS`")
public class Group {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "GROUP_ID", unique = true)
    private Long id;
    @NotNull
    @Column(name = "GROUP_NAME")
    private String name;
    @ManyToOne
    @JoinColumn(name = "PARENT_GROUP_ID")
    private Group parentGroup;
    @OneToMany(mappedBy = "parentGroup", cascade = CascadeType.ALL)
    private List<Group> subGroups;
    @OneToMany(mappedBy = "group")
    private List<Product> products;
    @NotNull
    @Column(name = "CREATE_DATE")
    private LocalDateTime createdDate;
}
