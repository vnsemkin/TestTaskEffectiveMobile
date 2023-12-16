package org.vnsemkin.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.vnsemkin.taskmanagementsystem.AbstractControllerTest;
import org.vnsemkin.taskmanagementsystem.dto.TaskDto;
import org.vnsemkin.taskmanagementsystem.dto.TaskDtoUpdate;
import org.vnsemkin.taskmanagementsystem.dto.UserDto;
import org.vnsemkin.taskmanagementsystem.model.TaskPriority;
import org.vnsemkin.taskmanagementsystem.model.TaskStatus;
import org.vnsemkin.taskmanagementsystem.repository.TaskRepository;
import org.vnsemkin.taskmanagementsystem.service.repo.TaskRepoInterfaceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.vnsemkin.taskmanagementsystem.configuration.constants.TMSConstants.TASK_URL;

@Transactional
public class TaskControllerTests extends AbstractControllerTest {
    @Autowired
    TaskRepoInterfaceImpl taskRepo;
    @Autowired
    TaskRepository taskRepository;

    @Test
    @WithUserDetails("user@gmail.com")
    public void shouldReturnAllTask() throws Exception {
        //GIVEN
        ObjectMapper mapper = new ObjectMapper();
        Pageable page =
                PageRequest.of(0,10);
        //WHEN
        MvcResult mvcResult = perform(MockMvcRequestBuilders.get(TASK_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //THEN
        String actual = mvcResult.getResponse().getContentAsString();
        String expected = mapper.writeValueAsString(taskRepo.findAllTask(page));
        assertEquals(actual, expected);
    }

    @Test
    @WithUserDetails("user@gmail.com")
    public void shouldReturnFirstPageWithSize1() throws Exception {
        //GIVEN
        //WHEN
        MvcResult mvcResult = perform(MockMvcRequestBuilders.get(TASK_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "1")
        )
                .andExpect(status().isOk())
                .andReturn();
        //THEN
        assertTrue(mvcResult.getResponse().getContentAsString().contains("pageable"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("\"pageNumber\":0"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("\"pageSize\":1"));
    }

    @Test
    @WithUserDetails("user@gmail.com")
    @Transactional
    @Sql(scripts = "classpath:test_db/delete_task.sql")
    public void shouldCreateNewTask() throws Exception {
        //GIVEN
        String taskName  = "TestTask";
        ObjectMapper mapper = new ObjectMapper();

        UserDto userDto = new UserDto();
        userDto.setUsername("user");
        userDto.setEmail("user@gmail.com");

        TaskDto taskDtoExpected = new TaskDto();
        taskDtoExpected.setTaskName(taskName);
        taskDtoExpected.setAuthor(userDto);
        taskDtoExpected.setAssignee(userDto);
        taskDtoExpected.setPriority(TaskPriority.LOW);
        taskDtoExpected.setStatus(TaskStatus.PENDING);

        //WHEN
        MvcResult mvcResult = perform(MockMvcRequestBuilders.post(TASK_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taskDtoExpected))
        )
                .andExpect(status().isOk())
                .andReturn();
        //THEN
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Task created"));
    }

    @Test
    @WithUserDetails("user@gmail.com")
    @Transactional
    public void shouldUpdateTask() throws Exception {
        //GIVEN
        String taskName  = "TestTask";
        ObjectMapper mapper = new ObjectMapper();

        UserDto userDto = new UserDto();
        userDto.setUsername("user");
        userDto.setEmail("user@gmail.com");

        TaskDtoUpdate taskDtoUpdate = new TaskDtoUpdate();
        taskDtoUpdate.setDescription("test description");
        taskDtoUpdate.setTaskName(taskName);
        taskDtoUpdate.setAssignee(userDto);
        taskDtoUpdate.setPriority(TaskPriority.LOW);
        taskDtoUpdate.setStatus(TaskStatus.PENDING);

        //WHEN
        MvcResult mvcResult = perform(MockMvcRequestBuilders.put(TASK_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taskDtoUpdate))
        )
                .andExpect(status().isOk())
                .andReturn();
        //THEN
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Task updated"));
    }

    @Test
    @WithUserDetails("user@gmail.com")
    public void shouldDeleteTaskById() throws Exception {
        //GIVEN
        long id = 1L;
        //WHEN
        MvcResult mvcResult = perform(MockMvcRequestBuilders.delete(TASK_URL+"/" + id)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        //THEN
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Task with id: " + id+" was deleted."));
    }

    @Test
    @WithUserDetails("user@gmail.com")
    public void shouldOnDeleteTaskByIdWithTaskNotFound() throws Exception {
        //GIVEN
        long id = Long.MAX_VALUE;
        //WHEN
        MvcResult mvcResult = perform(MockMvcRequestBuilders
                .delete(TASK_URL+"/" + id)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andReturn();
        //THEN
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString()
                .contains("Task with id: " + id + " not found."));
    }
}
