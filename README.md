# Chartool
Quick and simple data visualization tool.

# The Rules
Chartool should always remain simple to use - a request to a URL should return and render a chart or dashboard that can provide value to someone, without requiring additional front-end development.  Chartool is a visualisation tool in it's own right - it's *not* just a web service for querying databases that then requires a front-end developer to build charts from Json (or whichever other format).  Anyone should be able to start up Chartool get value from it!

# Feature Wishlist 
- User authentication/authorisation.
- ~~Templating engine for prettier dashboards.~~
- ~~Charts that refresh periodically.~~
- Proper integration tests.
- Support for additional chart types.
- ~~Prettier charting tool - perhaps JS charts on the front-end rather than images from the back-end.~~
- User-configurable charts/dashboards that don't require redeploys.
- Support for multiple data sources.
- ~~Support for Chart.js charts containing multiple datasets~~.
- 'Big' and 'Small' chart options in XML to change size of charts on UI (either full page width or half of the page).

# Instructions
This section provides instructions for building and running Chartool.

1.  Configure database connection properties in src\main\resources\application.yml.  The default connection connects to MySQL instance on localhost using test user credentials.
2.  Configure the charts that you wish to display in src\main\resources\charts.xml.  The existing config defines examples for all supported chart types.  For line-, pie-, and bar charts, the SQL query should return three columns - a data set label, a data item label and a numeric value.  For scatter charts, the query should return a data set label and two numeric values - one for each axis.
3.  Run `mvn clean install` in the root of the project directory.  The relevant config files will be included in the resulting JAR file.
4.  Run the JAR file in the target/ directory using `java -jar chartool-0.1.jar`.  You can also copy the file to another location and run if from there if you wish.
5.  Navigate to http://localhost:8080/dashboard?id=sakila (replace 'sakila' with the name of any of your own dashboards, if you've configured anything).

That's it!

# Contributors
If you do something, you can add your name - but never remove someone else's name.

- Riaan Nel <<https://github.com/rfnel>>
