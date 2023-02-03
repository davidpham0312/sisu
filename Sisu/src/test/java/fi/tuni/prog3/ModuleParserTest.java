package fi.tuni.prog3;

import org.junit.jupiter.api.Test;

import fi.tuni.prog3.exception.InvalidDataException;
import fi.tuni.prog3.model.CourseUnit;
import fi.tuni.prog3.model.Credits;
import fi.tuni.prog3.model.DegreeMetadata;
import fi.tuni.prog3.model.DegreeProgramme;
import fi.tuni.prog3.model.GroupingModule;
import fi.tuni.prog3.model.LanguageStringTuple;
import fi.tuni.prog3.model.StudyModule;
import fi.tuni.prog3.parser.ModuleParser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ModuleParserTest {
        private boolean isOffline = true;

        @Test
        public void testStudyModuleParser() {
                System.out.println("StudyModule from JSON");
                String jsonFile = "src/test/resources/studyModule.json";
                CourseUnit expectedCourseUnit = new CourseUnit(
                                "MATH.APP.220",
                                new LanguageStringTuple("Usean muuttujan funktiot", "Multivariable Calculus"),
                                "otm-bbb0137a-353b-4d70-b398-91c558e084c9",
                                "tut-cu-g-47510",
                                new Credits(5, 5),
                                new LanguageStringTuple(
                                                "<p>Opiskelija osaa tutkia ja havannollistaa kahden muuttujan reaaliarvoisen funktion käyttäytymistä kuvaajan ja tasa-arvokäyrien avulla.</p><p>Opiskelija ymmärtää usean muuttujan funktion raja-arvon.</p><p>Opiskelija osaa laskea ensimmäisen ja korkeamman kertaluvun osittaisderivaatat, gradientin ja suunnatun derivaatan sekä hakea lokaaleja ja globaaleja ääriarvoja. </p><p>Opiskelija osaa muodostaa vektoriarvoisen funktion derivaattamatriisin ja käyttää ketjusääntöä. </p><p>Opiskelija osaa laskea taso- ja avaruusintegraaleja projisoituvissa joukoissa ja käyttää integroinnissa napa-, sylinteri- ja pallokoordinaatteja. </p><p>Opiskelija osaa esittää ratkaisunsa sekä suullisesti että kirjallisesti.</p>",
                                                "<p>On this course the students learn to study and visualize the behaviour \nof a real valued function of two variables using its graphs and contour \nplots, compute limits of multivariable functions, compute first \nand higher order partial derivatives, gradients and directed \nderivatives, and to find local and global maxima of functions. The \nstudents learn to form the derivative matrix of a vector-valued function\n and to use the chain rule of differentiation. The students learn how to \ncompute integrals of real-value functions of two and three variables \nusing cartesian, polar, cylinder and spherical coordinates. The students\n learn how to justify their claims using mathematical methods and to \npresent their solutions orally as well as in written form.</p>"));

                StudyModule expectedStudyModule = new StudyModule("otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf",
                                "tut-sm-g-3662",
                                new LanguageStringTuple("Tietotekniikan yhteiset opinnot",
                                                "Joint Studies in Information Technology"),
                                new Credits(15));

                try {
                        StudyModule studyModule = (StudyModule) ModuleParser.fromJSON(jsonFile);
                        assertEquals(expectedStudyModule.getName(), studyModule.getName());
                        assertEquals(expectedStudyModule.getCredits().getMinCredits(),
                                        studyModule.getCredits().getMinCredits());
                        assertEquals(expectedStudyModule.getCredits().getMaxCredits(),
                                        studyModule.getCredits().getMaxCredits());
                        assertEquals(expectedStudyModule.getId(), studyModule.getId());

                        assertEquals(expectedCourseUnit.getName(),
                                        studyModule.getCourseByGroupId("tut-cu-g-47510", isOffline).getName());
                        assertEquals(expectedCourseUnit.getCredits().getMinCredits(),
                                        studyModule.getCourseByGroupId("tut-cu-g-47510", isOffline).getCredits()
                                                        .getMinCredits());
                        assertEquals(expectedCourseUnit.getCredits().getMaxCredits(),
                                        studyModule.getCourseByGroupId("tut-cu-g-47510", isOffline).getCredits()
                                                        .getMaxCredits());
                        assertEquals(expectedCourseUnit.getId(),
                                        studyModule.getCourseByGroupId("tut-cu-g-47510", isOffline).getId());
                        assertEquals(expectedCourseUnit.getCode(),
                                        studyModule.getCourseByGroupId("tut-cu-g-47510", isOffline).getCode());
                } catch (FileNotFoundException e) {
                        return;
                } catch (IOException ex) {
                        fail(ex.getMessage());
                } catch (InvalidDataException ex) {
                        fail(ex.getMessage());
                }
        }

        @Test
        public void testGroupingModuleParser() {
                System.out.println("GroupingModule from JSON");
                String jsonFile = "src/test/resources/groupingModule.json";

                GroupingModule expectedModule = new GroupingModule("otm-dfcd98b1-4f28-4095-874f-178413c11869",
                                "otm-dfcd98b1-4f28-4095-874f-178413c11869",
                                new LanguageStringTuple("Vapaasti valittavat opintokokonaisuudet",
                                                "Free Choice Study Modules"));

                GroupingModule expectedGroupingModule = new GroupingModule("otm-73c44ab7-259c-4ba3-9247-27597af07443",
                                "otm-73c44ab7-259c-4ba3-9247-27597af07443",
                                new LanguageStringTuple("Valitse syventävät opinnot", "Choose your Advanced Studies"));

                try {
                        GroupingModule groupingModule = (GroupingModule) ModuleParser.fromJSON(jsonFile);
                        assertEquals(expectedGroupingModule.getName(),
                                        groupingModule.getName());
                        assertEquals(expectedGroupingModule.getId(), groupingModule.getId());
                        assertEquals(expectedGroupingModule.getGroupId(), groupingModule.getGroupId());

                        assertEquals(expectedModule.getName(), groupingModule
                                        .getModuleByGroupId("otm-dfcd98b1-4f28-4095-874f-178413c11869", isOffline)
                                        .getName());
                        assertEquals(expectedModule.getId(),
                                        groupingModule.getModuleByGroupId("otm-dfcd98b1-4f28-4095-874f-178413c11869",
                                                        isOffline)
                                                        .getId());
                        assertEquals(expectedModule.getGroupId(),
                                        groupingModule.getModuleByGroupId("otm-dfcd98b1-4f28-4095-874f-178413c11869",
                                                        isOffline)
                                                        .getGroupId());
                } catch (FileNotFoundException e) {
                        return;
                } catch (IOException ex) {
                        fail(ex.getMessage());
                } catch (InvalidDataException ex) {
                        fail(ex.getMessage());
                }
        }

        @Test
        public void testDegreeProgramParser() {
                System.out.println("GroupingModule from JSON");
                String jsonFile = "src/test/resources/degreeProgram.json";

                DegreeProgramme expectedDegreeProgram = new DegreeProgramme("otm-3990be25-c9fd-4dae-904c-547ac11e8302",
                                "tut-dp-g-1180",
                                new LanguageStringTuple("Tietotekniikan DI-ohjelma",
                                                "Master's Programme in Information Technology"),
                                "TTEM", new Credits(120));

                StudyModule expectedStudyModule = new StudyModule("otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf",
                                "tut-sm-g-3662",
                                new LanguageStringTuple("Tietotekniikan yhteiset opinnot",
                                                "Joint Studies in Information Technology"),
                                new Credits(15));

                try {
                        DegreeProgramme degreeProgramme = (DegreeProgramme) ModuleParser.fromJSON(jsonFile);
                        assertEquals(expectedDegreeProgram.getName(),
                                        expectedDegreeProgram.getName());
                        assertEquals(expectedDegreeProgram.getId(), expectedDegreeProgram.getId());
                        assertEquals(expectedDegreeProgram.getGroupId(), expectedDegreeProgram.getGroupId());
                        assertEquals(expectedDegreeProgram.getCode(), expectedDegreeProgram.getCode());
                        assertEquals(expectedDegreeProgram.getCredits().getMinCredits(),
                                        expectedDegreeProgram.getCredits().getMinCredits());
                        assertEquals(expectedDegreeProgram.getCredits().getMaxCredits(),
                                        expectedDegreeProgram.getCredits().getMaxCredits());

                        assertEquals(expectedStudyModule.getName(), degreeProgramme
                                        .getModuleByGroupId("tut-sm-g-3662", isOffline).getName());
                        assertEquals(expectedStudyModule.getId(),
                                        degreeProgramme.getModuleByGroupId("tut-sm-g-3662", isOffline).getId());
                        assertEquals(expectedStudyModule.getGroupId(),
                                        degreeProgramme.getModuleByGroupId("tut-sm-g-3662", isOffline).getGroupId());
                } catch (IOException ex) {
                        fail(ex.getMessage());
                } catch (InvalidDataException ex) {
                        fail(ex.getMessage());
                }
        }

        @Test
        public void testGetAllDegreeMetadata() {
                System.out.println("Get all degree metadata");
                // REQUIRES INTERNET CONNECTION
                try {
                        ArrayList<DegreeMetadata> degreeMetadata = ModuleParser.getAllDegreeMetadata();
                        assert (degreeMetadata.size() > 0);
                } catch (IOException ex) {
                        fail("Exception thrown. Are you connected to the Internet?");
                } catch (InvalidDataException ex) {
                        fail(ex.getMessage());
                }
        }

        @Test
        public void testFromURL() {
                System.out.println("From URL");
                // REQUIRES INTERNET CONNECTION
                try {
                        StudyModule studyModule = (StudyModule) ModuleParser.fromURL(
                                        "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId=uta-ok-ykoodi-41176&universityId=tuni-university-root-id");
                        assertEquals("Basic Studies in Computer Sciences", studyModule.getName());
                        assertEquals("uta-ok-ykoodi-41176", studyModule.getGroupId());
                        assertEquals("otm-af70be28-9bf5-49f7-b8fc-41a2bafbf2f2", studyModule.getId());
                } catch (IOException ex) {
                        fail("Exception thrown. Are you connected to the Internet?");
                } catch (InvalidDataException ex) {
                        fail(ex.getMessage());
                }
        }
}
