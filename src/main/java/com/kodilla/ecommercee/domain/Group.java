package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
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
    @OneToMany(mappedBy = "group")
    private List<Product> products;
    @NotNull
    @Column(name = "CREATE_DATE")
    private LocalDateTime createdDate;
}
