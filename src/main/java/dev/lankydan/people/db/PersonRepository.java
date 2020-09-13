package dev.lankydan.people.db;

import dev.lankydan.people.model.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
// @Singleton
public class PersonRepository {

  private static final String FIND_ALL = "select * from people";
  private static final String FIND_ALL_BY_ID = "select * from people where id = ?";
  private static final String INSERT = "insert into people (id, name, age) values (?, ?, ?)";
  private static final String UPDATE = "update people set name = ?, age = ? where id = ?";
  private static final String DELETE = "delete from people where id = ?";

  private final DataSource dataSource;

  public PersonRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public List<Person> findAll() {
    List<Person> result = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_ALL);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        result.add(
            new Person(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getInt("age")));
      }
    } catch (Exception e) {
      throw new RuntimeException("boom", e.getCause());
    }
    return result;
  }

  public Person findById(UUID id) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_ID)) {
      statement.setObject(1, id);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return new Person(
              UUID.fromString(resultSet.getString("id")),
              resultSet.getString("name"),
              resultSet.getInt("age"));
          }
        }
    } catch (Exception e) {
      throw new RuntimeException("boom", e.getCause());
    }
    return null;
  }

  public Person insert(Person person) {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement(INSERT)) {
      statement.setObject(1, person.getId());
      statement.setString(2, person.getName());
      statement.setInt(3,person.getAge());
      statement.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e.getCause());
    }
    return person;
  }

  public Person update(Person person) {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement(UPDATE)) {
      statement.setString(1, person.getName());
      statement.setInt(2,person.getAge());
      statement.setObject(3, person.getId());
      statement.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e.getCause());
    }
    return person;
  }

  public boolean deleteById(UUID id) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE)) {
      statement.setObject(1, id);
      return statement.executeUpdate() == 1;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e.getCause());
    }
  }
}
