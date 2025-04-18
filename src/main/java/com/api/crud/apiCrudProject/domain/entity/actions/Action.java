package com.api.crud.apiCrudProject.domain.entity.actions;

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

    private String actionName;
    
    @Enumerated(EnumType.STRING)
    private ActionStatus actionStatus;
}
