# spring-jpa-lazy-projections
[ ![Codeship Status for mefernandez/spring-jpa-lazy-projections](https://app.codeship.com/projects/8f6f7cb0-9054-0134-93d5-6e4574ccc4bb/status?branch=master)](https://app.codeship.com/projects/185759)

A test case project to dive into JPA fetch types and projections and its impact on performance

# Intro

Spring and JPA makes it easy to build CRUD-like REST services.
A basic feature to implement in this scenario is to show tables of data, with pages support.

The goal of this project is to find the best configuration to show high volumes of paged data with minimum code and maximum performance.

# The model

To test how different configurations work we'll use the tried-and-true Employee-Department scenario.

# Eager loading

# Lazy loading v1: Defaults

# Lazy loading v2: FORCE_LAZY_LOADING
