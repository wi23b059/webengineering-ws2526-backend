package at.technikum.springrestbackend.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalutationTest {

    @Test
    void shouldContainAllSalutations() {
        Salutation[] values = Salutation.values();

        assertEquals(4, values.length);
        assertArrayEquals(
                new Salutation[]{Salutation.MR, Salutation.MS, Salutation.MRS, Salutation.MX},
                values
        );
    }

    @Test
    void getDisplayName_shouldReturnHumanReadableValue() {
        assertEquals("Mr.", Salutation.MR.getDisplayName());
        assertEquals("Ms.", Salutation.MS.getDisplayName());
        assertEquals("Mrs.", Salutation.MRS.getDisplayName());
        assertEquals("Mx.", Salutation.MX.getDisplayName());
    }

    @Test
    void toString_shouldReturnEnumName_forDatabaseStorage() {
        assertEquals("MR", Salutation.MR.toString());
        assertEquals("MS", Salutation.MS.toString());
        assertEquals("MRS", Salutation.MRS.toString());
        assertEquals("MX", Salutation.MX.toString());
    }

    @Test
    void valueOf_shouldReturnCorrectEnum() {
        assertEquals(Salutation.MR, Salutation.valueOf("MR"));
        assertEquals(Salutation.MX, Salutation.valueOf("MX"));
    }

    @Test
    void valueOf_shouldThrowExceptionForInvalidValue() {
        assertThrows(IllegalArgumentException.class, () ->
                Salutation.valueOf("SIR")
        );
    }
}
