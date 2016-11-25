# spring-jpa-lazy-projections
[ ![Codeship Status for mefernandez/spring-jpa-lazy-projections](https://app.codeship.com/projects/8f6f7cb0-9054-0134-93d5-6e4574ccc4bb/status?branch=master)](https://app.codeship.com/projects/185759)

A test case project to dive into JPA fetch types and projections and its impact on a Spring boot application performance.

**Work in progress**

# Intro

Spring and JPA makes it easy to build CRUD-like REST services.
A basic feature to implement in this scenario is to show tables of data, with pages support.

The goal of this project is to find the best configuration to show high volumes of paged data with minimum code and maximum performance.

# The setup

I'll be testing this using [Spring Boot 1.4.2](http://docs.spring.io/spring-boot/docs/1.4.2.RELEASE/reference/htmlsingle/), which in turn uses:

1. [Spring framework 4.3.4](http://docs.spring.io/spring/docs/4.3.4.RELEASE/spring-framework-reference/htmlsingle/)
2. [JPA's Hibernate 5.0.11](http://hibernate.org/orm/documentation/5.0/)
3. [Jackson 2.8.3](https://github.com/FasterXML/jackson-docs)

I'm using [Eclipse Neon](http://www.eclipse.org/) to develop this scenario.

# The model

To test how different configurations work we'll use the tried-and-true Employee-Department scenario.

![Employee-Department Class Diagram](http://yuml.me/6a340d30)

http://yuml.me/edit/6a340d30

In this model, an Employee is in a Department and has a boss, who is also an Employee.

In terms of Java and JPA, there are only two classes, Employee and Department, and two JPA @ManyToOne relationships.

```java
@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne(fetch=FetchType.LAZY)
	private Department department;

	@ManyToOne(fetch=FetchType.LAZY)
	private Employee boss;
}

@Entity
public class Department {
	
	@Id
	@GeneratedValue
	private Long id;

	private String name;
}
```

# The goal

The goal is to code an HTTP service that returns a JSON listing all Employees by pages.
In Spring, that's a @RestController.

We want to show the Employee's name and Department, and **not** the boss.

|Name|Department|
|----|----------|
|Joe |Management|

Page 1 of 10, Next >>

We will also try to find the best implementation in terms of performance.
Here's what to watch for:

1. SQL queries
2. Serialization
3. Overall time spent

# Eager loading

Upon defining a relationship between two entities, JPA provides two fetch types: Eager and Lazy.

Eager is the easy one. Upon loading an Employee, it will also fetch its boss and Department immediately.

```java
@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne(fetch=FetchType.EAGER)
	private Department department;

	@ManyToOne(fetch=FetchType.EAGER)
	private Employee boss;
```

So, how does it perform? Here's how in terms of our criteria:

1. SQL queries:
2. Serialization:
3. Overall time spent:

As easy as it is, egaer loading will fetch unnecessary information, causing SQL and serialization overhead.

# Lazy loading v1: Defaults

It's time for the other JPA fetch type: Lazy loading. 
Lazy will only fetch related objects on demand.

```java
@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne(fetch=FetchType.LAZY)
	private Department department;

	@ManyToOne(fetch=FetchType.LAZY)
	private Employee boss;
```

In this case, I'm using Spring Boot's defaults.
I'm only adding Jackson's Hibernate4Module to get Spring Data's `Page` class serialized.

```java
@Configuration
public class SpringApplicationConfiguration {

	/**
	 * You need this to get Page class serialized by Jackson.
	 * @return
	 */
	@Bean
	public Module jacksonHibernate4Module() {
		Hibernate4Module module = new Hibernate4Module();
		return module;
	}
}
```

This minimum configuration results in Department not being serialized.
I would expect the same thing with the boss. However, boss relationship is serialized.

# Lazy loading v2: FORCE_LAZY_LOADING

Jackson's `Hibernate4Module` has a feature named `FORCE_LAZY_LOADING` that works as expected.
```java
@Configuration
public class SpringApplicationConfiguration {

	@Bean
	public Module jacksonHibernate4Module() {
		Hibernate4Module module = new Hibernate4Module();
		module.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
		return module;
	}
}
```

The problem with `FORCE_LAZY_LOADING` is that it behaves like Eager.
Let's move on to find a better way.

# Lazy loading v3: @JsonView

@JsonView lets you define which attributes will get serialized in an HTTP response.
First, you need to annotate @RestController method handling the request with @JsonView.

```java
	@JsonView(SummaryView.class)
	@RequestMapping(method = RequestMethod.GET, value = "/lazy/employees")
	public Page<Employee> search(Pageable pageable) {
		Page<Employee> page = (Page<Employee>) employeeRepository.findAll(pageable);
		PageWithJsonView<Employee> myPage = new PageWithJsonView(page);
		return myPage;
	}
```

This piece of code tells Jackson to serialize only attributes marked with `@JsonView(SummaryView.class)`.

```java
@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;

	@JsonView(SummaryView.class)
	private String name;

	@JsonView(SummaryView.class)
	@ManyToOne(fetch=FetchType.LAZY)
	private Department department;

	@ManyToOne(fetch=FetchType.LAZY)
	private Employee boss;

}
```

In the class above, only `name` and `department` will be serialized.
The attribute `boss` will not be serialized, since it is not annotated with `@JsonView(SummaryView.class)`.

For the same reason, the class `Page` also need to be annotated.
Since this class is part of Spring Data library and comes with no annotation, I created a new subclass with all its attributes annotated with `@JsonView(SummaryView.class)`. **Otherwise, the `@RestController` would not serialize anything**.

```java
public class PageWithJsonView<T> extends PageImpl<T> {

	public PageWithJsonView(Page<T> page) {
		super(page.getContent());
	}

	@JsonView(SummaryView.class)
	@Override
	public int getTotalPages() {
		// TODO Auto-generated method stub
		return super.getTotalPages();
	}

	@JsonView(SummaryView.class)
	@Override
	public long getTotalElements() {
		// TODO Auto-generated method stub
		return super.getTotalElements();
	}
	
	@JsonView(SummaryView.class)
	@Override
	public int getNumberOfElements() {
		// TODO Auto-generated method stub
		return super.getNumberOfElements();
	}
	
	@JsonView(SummaryView.class)
	@Override
	public List<T> getContent() {
		// TODO Auto-generated method stub
		return super.getContent();
	}

}
```


# Lazy loading v4: JPA Projections
