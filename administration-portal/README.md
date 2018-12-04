# Admin Portal

This README outlines the details of collaborating on this angular application.

## Installation

[install node](https://nodejs.org/)

Then run the following commands in the console
 * `npm install -g bower` **Install bower globally**
 * `npm install -g grunt` **Install grunt globally**
 * `npm install -g jasmine` **Install jasmine globally**
 * `npm install -g karma` **Install karma globally**
 * `npm install` **Install all dev dependencies**
 * `bower install` **Install all runtime dependencies**

## Running

* `grunt dev`
* Visit your app at http://localhost:8888
* The app will automatically rebuild whenever you change a file. You will then have to refresh the browser page.

## Testing

1. Install Protractor

    `npm install -g protractor`

2. Update the WebDriver Manager. Type following command in command line.

    `webdriver-manager update` 

6. Open a command line window and type following.

	`webdriver-manager start`

7. Open another command line window and go to project folder.
    
    eg. `cd \projects\protractor-demo`

8. For testing on your local environment use
    
    `grunt test:p`
    
9. For testing on other environments use

    `grunt protractor --env=[prod|staging|dev|local]`

## Deployment

* QA Deploy -  run 'grunt build --env=qa'
* Prod Deploy - take the qa build and replace the env.js in the build folder from required environment located in /config