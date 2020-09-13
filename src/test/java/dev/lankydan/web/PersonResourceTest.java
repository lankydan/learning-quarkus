package dev.lankydan.web;

import dev.lankydan.people.model.Person;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@QuarkusTest
public class PersonResourceTest {

  @Test
  @Disabled
  public void testPostAndGet() {
    // test doesn't really work sine its connecting to the postgres container im running
    Person person = new Person(UUID.randomUUID(), "dan", 26);
    given()
        .when()
        .contentType(ContentType.JSON)
        .body(person)
        .post("/people")
        .then()
        .statusCode(200);
    Person fromResponse = given().when().get("/people").then().statusCode(200).extract().as(Person.class);
    assertNotEquals(person.getId(), fromResponse.getId());
    assertEquals(person.getName(), fromResponse.getName());
    assertEquals(person.getAge(), fromResponse.getAge());
  }
}
