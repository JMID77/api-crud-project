package com.api.crud.apiCrudProject.domain.entity;



import com.api.crud.apiCrudProject.domain.entity.enumeration.ActionStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "actions")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String actionName;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionStatus actionStatus;
}
