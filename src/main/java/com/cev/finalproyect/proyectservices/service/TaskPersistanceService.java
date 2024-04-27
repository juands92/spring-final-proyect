package com.cev.finalproyect.proyectservices.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.domain.Task;
import com.cev.finalproyect.proyectservices.repository.TaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskPersistanceService {

    private final TaskRepository taskRepository;

    public TaskPersistanceService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));
    }

    public Task createTask(Task newTask) {
        Task task = new Task();
        task.setDescription(newTask.getDescription());
        task.setDateCreated(new java.util.Date());
        task.setUser(newTask.getUser());
        task.setCompleted(false); // filtro de busqueda por tareas completadas.
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));
        task.setDescription(updatedTask.getDescription());
        task.setDateCreated(new java.util.Date());
        task.setUser(updatedTask.getUser());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getPendingTasks() {
        return taskRepository.findByCompletedFalse();
    }

    public Task completeTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));
        task.setCompleted(true); // Cambia el estado a completado
        return taskRepository.save(task); // Guarda la tarea actualizada en la base de datos
    }
//Cambios

}