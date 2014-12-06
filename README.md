myRunningShoes
==============

Sample service.  Keep track of mileage on a user's running shoes.

This service will provide user/shoe data in json. Persistence is done
with a Mysql database with a spring JDBC connection.  Eventually the front
ends will be browser MVC support using angularjs, and an iOS app.  Maybe
eventually a map using location services to track the mileage on the iOS app.

Services provided so far:

<url>/user?userId=<#>
returns the user in json

<url>/shoe?userId=<#>&shoeId=<#>&miles=<#>
adds # miles to the given shoe in the DB
