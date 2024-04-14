package com.cev.finalproyect.proyectservices.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.domain.Task;
import com.cev.finalproyect.proyectservices.repository.TaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskPersistanceService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskPersistanceService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTask(UUID id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));
    }

    public Task createTask(Task newTask) {
    	 Task task = new Task();
		 task.setDescription(newTask.getDescription());
	     task.setDateCreated(new java.util.Date());
	     task.setUser(newTask.getUser());
	     return taskRepository.save(task);
    }

    public Task updateTask(UUID id, Task updatedTask) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));
        task.setDescription(updatedTask.getDescription());
        task.setDateCreated(new java.util.Date());
        task.setUser(updatedTask.getUser());
        return taskRepository.save(task);
    }

    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}