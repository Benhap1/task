package com.em.tms.service;

import com.em.tms.DTO.TaskCreateDTO;
import com.em.tms.DTO.TaskDTO;
import com.em.tms.DTO.TaskUpdateDTO;

public interface TaskService {

    TaskDTO createTask(TaskCreateDTO taskCreateDTO);
    TaskDTO updateTask(int taskId, TaskUpdateDTO taskUpdateDTO);

}
