package com.bradypod.neo4j.repositories;

import com.bradypod.neo4j.domain.Movie;
import com.bradypod.neo4j.domain.Person;
import com.bradypod.neo4j.domain.Role;
import com.bradypod.neo4j.repositories.MovieRepository;
import com.bradypod.neo4j.repositories.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MovieRepositoryTest {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private PersonRepository personRepository;

	@Before
	public void setUp() {
		Movie matrix = new Movie("The Matrix", 1999, "Welcome to the Real World");

		movieRepository.save(matrix);

		Person keanu = new Person("Keanu Reeves", 1964);

		personRepository.save(keanu);

		Role neo = new Role(matrix, keanu);
		neo.addRoleName("Neo");

		matrix.addRole(neo);

		movieRepository.save(matrix);
	}

	/**
	 * Test of findByTitle method, of class MovieRepository.
	 */
	@Test
	public void testFindByTitle() {

		String title = "The Matrix";
		Movie result = movieRepository.findByTitle(title);
		assertNotNull(result);
		assertEquals(1999, result.getReleased());
	}

	/**
	 * Test of findByTitleContaining method, of class MovieRepository.
	 */
	@Test
	public void testFindByTitleContaining() {
		String title = "*Matrix*";
		Collection<Movie> result = movieRepository.findByTitleLike(title);
		assertNotNull(result);
		assertEquals(1, result.size());
	}

	/**
	 * Test of graph method, of class MovieRepository.
	 */
	@Test
	public void testGraph() {
		int limit = 5;

		Collection<Movie> graph = movieRepository.graph(limit);

		System.out.println(graph);

		assertEquals(1, graph.size());

		Movie movie = graph.iterator().next();

		assertEquals(1, movie.getRoles().size());

		assertEquals("The Matrix", movie.getTitle());
		assertEquals("Keanu Reeves", movie.getRoles().iterator().next().getPerson().getName());
	}

	@Test
	public void testSave() {

		Movie movie = new Movie("卧虎苍龙", 2009, "无名剑客");
		movieRepository.save(movie);

		Person person = new Person("李连杰", 1964);
		personRepository.save(person);

		Role role = new Role(movie, person);
		role.addRoleName("无名");

		movie.addRole(role);

		movieRepository.save(movie);

	}
}