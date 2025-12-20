package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.CategoryRequestDto;
import at.technikum.springrestbackend.dto.CategoryResponseDto;
import at.technikum.springrestbackend.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller tests for {@link CategoryController}.
 * Tests web layer, validation and security behaviour.
 */
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * CategoryService is mocked to isolate controller behaviour.
     */
    @MockitoBean
    private CategoryService categoryService;

    // -------------------------------------------------------------------------
    // Security
    // -------------------------------------------------------------------------

    @Test
    void requestsAreUnauthorizedWhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isUnauthorized());
    }

    // -------------------------------------------------------------------------
    // GET all categories
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void getAllCategoriesReturnsListForAuthenticatedUser() throws Exception {
        CategoryResponseDto category = new CategoryResponseDto();
        // optional: set fields if needed (id, name, ...)

        when(categoryService.getAllCategories())
                .thenReturn(List.of(category));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // -------------------------------------------------------------------------
    // GET single category
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void getCategoryReturnsCategoryById() throws Exception {
        CategoryResponseDto category = new CategoryResponseDto();

        when(categoryService.getCategory(1))
                .thenReturn(category);

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk());
    }

    // -------------------------------------------------------------------------
    // POST create category
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void createCategoryReturnsCreatedStatus() throws Exception {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        // Beispiel – anpassen an euer echtes DTO
        requestDto.setName("Electronics");

        CategoryResponseDto responseDto = new CategoryResponseDto();

        when(categoryService.createCategory(any(CategoryRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void createCategoryReturnsBadRequestWhenRequestIsInvalid() throws Exception {
        CategoryRequestDto invalidRequest = new CategoryRequestDto();
        // leer → verletzt @Valid

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    // -------------------------------------------------------------------------
    // PUT update category
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void updateCategoryReturnsUpdatedCategory() throws Exception {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Updated Category");

        CategoryResponseDto responseDto = new CategoryResponseDto();

        when(categoryService.updateCategory(
                eq(1),
                any(CategoryRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    // -------------------------------------------------------------------------
    // DELETE category
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void deleteCategoryReturnsNoContent() throws Exception {
        doNothing().when(categoryService)
                .deleteCategory(1);

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNoContent());
    }
}
