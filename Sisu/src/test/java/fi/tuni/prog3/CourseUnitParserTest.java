package fi.tuni.prog3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import fi.tuni.prog3.model.CourseUnit;
import fi.tuni.prog3.model.Credits;
import fi.tuni.prog3.model.LanguageStringTuple;
import fi.tuni.prog3.parser.CourseUnitParser;

public class CourseUnitParserTest {
    @Test
    public void testFromJSON() {
        System.out.println("fromJSON");
        String jsonFile = "src/test/resources/courseUnit.json";
        CourseUnit expectedCourseUnit = new CourseUnit(
                "MATH.APP.220",
                new LanguageStringTuple("Usean muuttujan funktiot", "Multivariable Calculus"),
                "otm-bbb0137a-353b-4d70-b398-91c558e084c9",
                "tut-cu-g-47510",
                new Credits(5, 5),
                new LanguageStringTuple(
                        "<p>Opiskelija osaa tutkia ja havannollistaa kahden muuttujan reaaliarvoisen funktion käyttäytymistä kuvaajan ja tasa-arvokäyrien avulla.</p><p>Opiskelija ymmärtää usean muuttujan funktion raja-arvon.</p><p>Opiskelija osaa laskea ensimmäisen ja korkeamman kertaluvun osittaisderivaatat, gradientin ja suunnatun derivaatan sekä hakea lokaaleja ja globaaleja ääriarvoja. </p><p>Opiskelija osaa muodostaa vektoriarvoisen funktion derivaattamatriisin ja käyttää ketjusääntöä. </p><p>Opiskelija osaa laskea taso- ja avaruusintegraaleja projisoituvissa joukoissa ja käyttää integroinnissa napa-, sylinteri- ja pallokoordinaatteja. </p><p>Opiskelija osaa esittää ratkaisunsa sekä suullisesti että kirjallisesti.</p>",
                        "<p>On this course the students learn to study and visualize the behaviour \nof a real valued function of two variables using its graphs and contour \nplots, compute limits of multivariable functions, compute first \nand higher order partial derivatives, gradients and directed \nderivatives, and to find local and global maxima of functions. The \nstudents learn to form the derivative matrix of a vector-valued function\n and to use the chain rule of differentiation. The students learn how to \ncompute integrals of real-value functions of two and three variables \nusing cartesian, polar, cylinder and spherical coordinates. The students\n learn how to justify their claims using mathematical methods and to \npresent their solutions orally as well as in written form.</p>"));
        try {
            CourseUnit courseUnit = CourseUnitParser.fromJSON(jsonFile);
            assertEquals(expectedCourseUnit.getCode(), courseUnit.getCode());
            assertEquals(expectedCourseUnit.getName(), courseUnit.getName());
            assertEquals(expectedCourseUnit.getDescription(), courseUnit.getDescription());
            assertEquals(expectedCourseUnit.getCredits().getMinCredits(), courseUnit.getCredits().getMinCredits());
            assertEquals(expectedCourseUnit.getCredits().getMaxCredits(), courseUnit.getCredits().getMaxCredits());
            assertEquals(expectedCourseUnit.getId(), courseUnit.getId());
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
    }
}
