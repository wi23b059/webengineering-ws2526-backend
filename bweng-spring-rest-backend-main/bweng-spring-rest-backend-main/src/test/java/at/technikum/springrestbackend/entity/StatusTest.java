package at.technikum.springrestbackend.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void shouldContainAllStatuses() {
        Status[] values = Status.values();

        assertEquals(4, values.length);
        assertArrayEquals(
                new Status[]{
                        Status.ACTIVE,
                        Status.INACTIVE,
                        Status.BANNED,
                        Status.DELETED
                },
                values
        );
    }

    @Test
    void toString_shouldReturnEnumName() {
        assertEquals("ACTIVE", Status.ACTIVE.toString());
        assertEquals("INACTIVE", Status.INACTIVE.toString());
        assertEquals("BANNED", Status.BANNED.toString());
        assertEquals("DELETED", Status.DELETED.toString());
    }

    @Test
    void valueOf_shouldReturnCorrectEnum() {
        assertEquals(Status.ACTIVE, Status.valueOf("ACTIVE"));
        assertEquals(Status.BANNED, Status.valueOf("BANNED"));
    }

    @Test
    void valueOf_shouldThrowExceptionForInvalidValue() {
        assertThrows(IllegalArgumentException.class, () ->
                Status.valueOf("SUSPENDED")
        );
    }
}
