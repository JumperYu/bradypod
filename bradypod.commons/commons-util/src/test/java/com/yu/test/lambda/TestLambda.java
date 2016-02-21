package com.yu.test.lambda;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * jdk 1.8 开始了 lambda 表达式
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月19日
 */
public class TestLambda {

	@Test
	public void testThread() {
		new Thread(() -> {
			System.out.println("hello!");
		}).start();
	}

	@Test
	public void testFunction() {
		// ()为方法的形参, -> {..}为方法体的内容

	}

	@Test
	public void testArray() {
		String[] arr = { "JavaScript", "C", "Java", "C++", "C#" };
		Arrays.sort(arr, (m, n) -> m.length() - n.length());
		System.out.println(Arrays.toString(arr));
		Stream<String> stream = Stream.of(arr);
		stream.forEach(x -> System.out.println(x));
	}

	@Test
	public void testList() {
		// stream的其他终端API
		// forEach, toArray, collect, reduce, count, min, max
		// findFirst, findAny, anyMatch, allMatch, noneMatch

		List<String> list = Lists.newArrayList("ABC", "AB", "A", "B", "C");
		long count = list.stream().filter(string -> string.length() >= 2)
				.count();
		System.out.println("ret: " + count);
		list.forEach(str -> System.out.println(str));
	}

	@Test
	public void testMap() {
		Map<String, String> map = Maps.newHashMap();
		map.put("A", "B");

	}

	@Test
	public void testStream() {
		Stream<String> stream = Stream.generate(() -> "test").limit(10);
		String[] strArr = stream.toArray(String[]::new);
		System.out.println(Arrays.toString(strArr));

		String sentence = "Program creek is a Java site.";
		Stream<String> wordStream = Pattern.compile("\\W").splitAsStream(
				sentence);
		String[] wordArr = wordStream.toArray(String[]::new);
		System.out.println(Arrays.toString(wordArr));
	}

	@Test
	public void testFiles() throws IOException {
		Path path = Paths.get("E:\\abc.txt");
		Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8);
		Stream<String> words = lines.flatMap(line -> Stream.of(line
				.split("\\W+")));
		System.out.println(Arrays.toString(words.toArray()));
		lines.close();
	}

	@Test
	public void testDog() {

		Dog d1 = new Dog("Max", 2, 50);
		Dog d2 = new Dog("Rocky", 1, 30);
		Dog d3 = new Dog("Bear", 3, 40);
		Dog[] dogArray = { d1, d2, d3 };

		// printDogs(dogArray);

		Arrays.sort(dogArray, new Comparator<Dog>() {
			@Override
			public int compare(Dog o1, Dog o2) {
				return o1.getWeight() - o2.getWeight();
			}
		});

		// printDogs(dogArray);

		// 使用lambda
		dogArray = new Dog[] { d1, d2, d3 };

		// printDogs(dogArray);

		// Arrays.sort(dogArray, (Dog m, Dog n) -> m.getWeight() -
		// n.getWeight());

		// printDogs(dogArray);

		Stream<Dog> dogStream = Stream.of(dogArray);

		Stream<Dog> sortedDogStream = dogStream
				.sorted((Dog m, Dog n) -> Integer.compare(m.getHeight(),
						n.getHeight()));

		sortedDogStream.forEach(d -> System.out.print(d));
	}

	public static void printDogs(Dog[] dogs) {
		System.out.println("--Dog List--");
		for (Dog d : dogs)
			System.out.print(d);
		System.out.println();
	}

}

class Dog {

	String name;
	int height;
	int weight;

	public Dog(String n, int s, int w) {
		this.name = n;
		this.height = s;
		this.weight = w;
	}

	public String toString() {
		return getName() + ": size=" + getHeight() + " weight=" + getWeight()
				+ " \n";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}