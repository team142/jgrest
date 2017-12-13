# JGrest - Java PostGrest client

[![Build Status](https://travis-ci.org/team142/jgrest.svg?branch=master)](https://travis-ci.org/team142/jgrest)
[![Pending Pull-Requests](http://githubbadges.herokuapp.com/team142/jgrest/pulls.svg?style=flat)](https://github.com/team142/jgrest/pulls)
[![Github Issues](http://githubbadges.herokuapp.com/team142/jgrest/issues.svg?style=flat)](https://github.com/team142/jgrest/issues)
[![License](http://img.shields.io/:license-mit-blue.svg?style=flat)](http://badges.mit-license.org)


The goal of this project is to build a simple HTTP client for consuming a Postgrest endpoint. 


## Development

This project was uses maven to handle dependencies.

Run `mvn clean install`. Maven will retrieve dependencies. The project does require Lombok and Jacksonin the project to run. 

We do plan on adding the project to Maven central once the project reaches a certain level of maturity.

## Usage - Maven

Coming soon!

## Features


### Complete & tested

- Get one row
- Get many rows by condition
- Simple query
- Complex query
- Get all rows in a table


### Completed but not fully tested

- Insert a row
- Update a row
- Delete a row


### Planned

- Offsets & limits
- IteratorAbstractor
- Selected columns
- Security token
