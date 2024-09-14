package com.kodilla.ecommercee.controller;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.kodilla.ecommercee.controller.exception.ParentGroupNotFoundException;
import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.service.GroupDbService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DisplayName("Tests for GroupController class")
@SpringJUnitWebConfig
@WebMvcTest(GroupController.class)
public class GroupControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroupDbService groupDbService;
    @MockBean
    private GroupMapper groupMapper;

    @DisplayName("Test case for no groups available")
    @Test
    void shouldFetchEmptyList() throws Exception {
        // Given
        Mockito.when(groupDbService.getAllGroups()).thenReturn(List.of());
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/groups"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @DisplayName("Test case for fetching groups")
    @Test
    void shouldFetchGroups() throws Exception {
        // Given
        Group parentGroup = new Group(1L, "Parent group name", null, new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2024, 9, 11, 12, 0, 0));

        Group childGroup1 = new Group(2L, "Child group 1 name", parentGroup, new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2024, 9, 11, 12, 0, 0));
        Group childGroup2 = new Group(3L, "Child group 2 name", parentGroup, new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2024, 9, 11, 12, 0, 0));

        parentGroup.getSubGroups().addAll(List.of(childGroup1, childGroup2));

        Product product1 = new Product(1L, "Product 1 name", "Product 1 description", new BigDecimal("100.00"), 1, parentGroup, LocalDate.of(2024, 9, 11));

        parentGroup.getProducts().add(product1);

        GroupDto parentGroupDto = new GroupDto(1L, "Parent group name", null, new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2024, 9, 11, 12, 0, 0));

        parentGroupDto.getSubGroupIds().addAll(List.of(2L, 3L));

        ProductDto productDto1 = new ProductDto(1L, "Product 1 name", "Product 1 description", new BigDecimal("100.00"), 1, 1L, LocalDate.of(2024, 9, 11));

        parentGroupDto.getProducts().add(productDto1);

        List<Group> groups = List.of(parentGroup);
        List<GroupDto> groupDtos = List.of(parentGroupDto);

        Mockito.when(groupDbService.getAllGroups()).thenReturn(groups);
        Mockito.when(groupMapper.mapToGroupDtoList(groups)).thenReturn(groupDtos);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/groups"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Parent group name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].parentGroupId", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].subGroupIds", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].subGroupIds[0]", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].subGroupIds[1]", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].products", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].products[0].productId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].products[0].productName", Matchers.is("Product 1 name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].products[0].description", Matchers.is("Product 1 description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].products[0].price", Matchers.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].products[0].quantity", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].products[0].productGroupId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].products[0].creationDate", Matchers.is("2024-09-11")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].creationDate", Matchers.is("2024-09-11T12:00:00")));
    }

    @DisplayName("Test case for creating a group")
    @Test
    void shouldCreateGroup() throws Exception {
        // Given
        Group parentGroup = new Group(1L, "Parent group name", null, null, null, LocalDateTime.of(2024, 9, 11, 12, 0, 0));
        Group createGroup = new Group(null, "Group name", parentGroup, null, null, null);
        Group savedGroup = new Group(null, "Group name", parentGroup, null, null, LocalDateTime.of(2024, 9, 11, 12, 0, 0));

        CreateGroupDto createGroupDto = new CreateGroupDto("Group name", 1L);

        Mockito.when(groupMapper.createGroupDtoToGroup(createGroupDto)).thenReturn(createGroup);
        Mockito.when(groupDbService.saveGroup(createGroup)).thenReturn(savedGroup);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(createGroupDto);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @DisplayName("Test case for creating a group when parent group does not exist")
    @Test
    void shouldReturnParentGroupNotFoundExceptionWhenCreateGroup() throws Exception {
        CreateGroupDto createGroupDto = new CreateGroupDto("Created Group", 0L);

        Mockito.when(groupMapper.createGroupDtoToGroup(Mockito.any())).thenThrow(new ParentGroupNotFoundException(0L));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(createGroupDto);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is("ERROR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is("parent.group.does.not.exist")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Parent group with ID 0 does not exist.")));
    }

    @DisplayName("Test case for getting a group by ID")
    @Test
    void shouldReturnGroupWhenGroupExists() throws Exception {
        // Given
        Long groupId = 1L;
        Group parentGroup = new Group(groupId, "Parent group name", null, new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2024, 9, 11, 12, 0, 0));

        Group childGroup1 = new Group(2L, "Child group 1 name", parentGroup, new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2024, 9, 11, 12, 0, 0));
        Group childGroup2 = new Group(3L, "Child group 2 name", parentGroup, new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2024, 9, 11, 12, 0, 0));

        parentGroup.getSubGroups().addAll(List.of(childGroup1, childGroup2));

        Product product1 = new Product(1L, "Product 1 name", "Product 1 description", new BigDecimal("100.00"), 1, parentGroup, LocalDate.of(2024, 9, 11));

        parentGroup.getProducts().add(product1);

        GroupDto parentGroupDto = new GroupDto(1L, "Parent group name", null, new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2024, 9, 11, 12, 0, 0));

        parentGroupDto.getSubGroupIds().addAll(List.of(2L, 3L));

        ProductDto productDto1 = new ProductDto(1L, "Product 1 name", "Product 1 description", new BigDecimal("100.00"), 1, 1L, LocalDate.of(2024, 9, 11));

        parentGroupDto.getProducts().add(productDto1);

        Mockito.when(groupDbService.getGroup(groupId)).thenReturn(Optional.of(parentGroup));
        Mockito.when(groupMapper.groupToGroupDto(parentGroup)).thenReturn(parentGroupDto);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/groups/{groupId}", groupId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Parent group name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parentGroupId", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subGroupIds", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subGroupIds[0]", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subGroupIds[1]", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].productId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].productName", Matchers.is("Product 1 name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].description", Matchers.is("Product 1 description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].price", Matchers.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].quantity", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].productGroupId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].creationDate", Matchers.is("2024-09-11")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.is("2024-09-11T12:00:00")));
    }

    @DisplayName("Test case for getting a group by ID when group does not exist")
    @Test
    void shouldReturnGroupNotFoundException() throws Exception {
        // Given
        Long groupId = 1L;

        Mockito.when(groupDbService.getGroup(groupId)).thenReturn(Optional.empty());
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/groups/{groupId}", groupId))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is("ERROR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is("group.does.not.exist")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Group with ID 1 does not exist.")));
    }

    @DisplayName("Test case for updating group")
    @Test
    void shouldUpdateGroup() throws Exception {
        Long groupId = 3L;
        UpdateGroupDto updateGroupDto = new UpdateGroupDto("Updated Group", 1L);

        Long parentGroupId = 1L;
        Group parentGroup = new Group(parentGroupId, "Parent Group", null, null, null, LocalDateTime.of(2024, 9, 11, 12, 0, 0));
        Group group = new Group(groupId, "Updated Group", parentGroup, null, null, null);
        Group updatedGroup = new Group(groupId, "Updated Group", parentGroup, null, null, LocalDateTime.of(2024, 9, 11, 12, 0, 0));
        GroupDto groupDto = new GroupDto(groupId, "Updated Group", parentGroupId, null, null, LocalDateTime.of(2024, 9, 11, 12, 0, 0));

        Mockito.when(groupDbService.getGroup(groupId)).thenReturn(Optional.of(updatedGroup));
        Mockito.when(groupDbService.getGroup(parentGroupId)).thenReturn(Optional.of(parentGroup));
        Mockito.when(groupMapper.updateGroupDtoToGroup(Mockito.any(), Mockito.any())).thenReturn(group);
        Mockito.when(groupDbService.updateGroup(group)).thenReturn(updatedGroup);
        Mockito.when(groupMapper.groupToGroupDto(updatedGroup)).thenReturn(groupDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updateGroupDto);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/groups/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Updated Group")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parentGroupId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.is("2024-09-11T12:00:00")));
    }

    @DisplayName("Test case for updating group when group does not exist")
    @Test
    void shouldReturnGroupNotFoundExceptionWhenUpdateGroup() throws Exception {
        Long groupId = 3L;
        UpdateGroupDto updateGroupDto = new UpdateGroupDto("Updated Group", 1L);

        Mockito.when(groupDbService.getGroup(groupId)).thenReturn(Optional.empty());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updateGroupDto);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/groups/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is("ERROR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is("group.does.not.exist")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Group with ID 3 does not exist.")));
    }

    @DisplayName("Test case for updating group when parent group does not exist")
    @Test
    void shouldReturnParentGroupNotFoundExceptionWhenUpdateGroup() throws Exception {
        Long groupId = 3L;
        UpdateGroupDto updateGroupDto = new UpdateGroupDto("Updated Group", 0L);

        Long parentGroupId = 1L;
        Group parentGroup = new Group(parentGroupId, "Parent Group", null, null, null, LocalDateTime.of(2024, 9, 11, 12, 0, 0));
        Group updatedGroup = new Group(groupId, "Updated Group", parentGroup, null, null, LocalDateTime.of(2024, 9, 11, 12, 0, 0));

        Mockito.when(groupDbService.getGroup(groupId)).thenReturn(Optional.of(updatedGroup));
        Mockito.when(groupMapper.updateGroupDtoToGroup(Mockito.any(), Mockito.any())).thenThrow(new ParentGroupNotFoundException(0L));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updateGroupDto);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/groups/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is("ERROR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is("parent.group.does.not.exist")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Parent group with ID 0 does not exist.")));
    }
}
