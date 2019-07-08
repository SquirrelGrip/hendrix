
### Make your cucumber more potent with a dash of Hendrix 

Examples
========
features/example.feature
```
Feature:

  Scenario Outline: add
    Given <x> plus <y> equals <result>

  Examples:
      | x   | y   | result |
      | 1   | 2   | 3      |
      | 100 | 200 | 300    |

```

ExampleGlue.java
```java
public class ExampleGlue {

    private Scenario scenario;

    @BeforeScenario
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
    }

    @Step("(.*) plus (.*) equals (.*)")
    public void add(int a, int b, int c) {
        int result = a + b;
        assertEquals(c, result);
    }
}
```

SampleTest.java
```java
public class SampleTest {

    @Test
    public void sample() throws Exception {
        Runner runner = new RunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/simple.feature"))
                .glue(SimpleStepdefs.class)
                .create();

        runner.run();
    }

}
```

Upcoming Features
=======================
- Support for existing Cucumber Tags. Useful because adopting the new framework will require changes.
- Support after scenario.
- Java8 Support, mainly support for lambda step definitions.
