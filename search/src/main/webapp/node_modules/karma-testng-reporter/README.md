# karma-testng-reporter

> Reporter for the TestNG XML format.

## Installation

The easiest way is to keep `karma-testng-reporter` as a devDependency in your `package.json`.
```json
{
  "devDependencies": {
    "karma": "~0.10",
    "karma-testng-reporter": "0.1"
  }
}
```

You can simple do it by:
```bash
npm install karma-testng-reporter --save-dev
```

## Configuration
```js
// karma.conf.js
module.exports = function(config) {
  config.set({
    reporters: ['progress', 'testng'],

    // the default configuration
    testngReporter: {
      outputFile: 'unit/testng-results.xml',
      suite: ''
    }
  });
};
```
If you want to tag your test cases you can simply put the tag names separated with '|' in the describe section .
e.g. describe('Logger Module|UT|CT',function(){}); 
This will tag the specs present inside the describe with two tags UT and CT. As many number of tags can be added.
You can pass list of reporters as a CLI argument too:
```bash
karma start --reporters testng,dots
```

----

For more information on Karma see the [homepage].


[homepage]: http://karma-runner.github.com
