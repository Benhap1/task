package com.em.tms.service;

import com.em.tms.DTO.TaskCreateDTO;
import com.em.tms.DTO.TaskDTO;

public interface TaskService {

    TaskDTO createTask(TaskCreateDTO taskCreateDTO);
}
