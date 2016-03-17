History
=======================
This is not cucumber as you know it. This is better.

I love cucumber. I really do. 
I love the simple way it binds steps in feature files to step definitions in code and then executes against the system under test.
I love how you can write your own reporters and formatters and extend the functionality.
I love how it incorporates IoC containers and how it just works.

What I didn't like was that it was slow.
Don't get me wrong, the code is quick, just that the tests I was writing were slow and I wanted to speed them up.

So I studied cucumber and dove into the code and worked out a number of ways to speed things up. And I improved the performance a lot.
Only now the tests were really, REALLY, ugly and confusing to others. It didn't feel like the elegant cucumber I loved, but I had created a monster.

So I sat down with a friend and talked about writing a new test framework. One that ran each test in its own thread. One that had all the features of cucumber, but just ran everything in parallel.

We both said we would never do it. "Who in this day and age writes a test framework from scratch? There has to be one out there that does exactly that. Right?"

So I did more research. There is parallelism but at a class level, not at a test level. That means all the test within a feature run in serial, whilst features can be tested in parallel.
Not good enough. 

So I started writing. Within a few hours I had the basic solution. An extremely basic solution. It proved it could be done.

I told my friend. He told me I was mad. After a week, he and I were convinced. After 2 weeks I had it working.

This is it.

It needs some more work, but basically, it does run each scenario in parallel.

You will need to write your tests to handle a multithreaded runner.

Any questions, let me know.

Examples
=======================


Upcoming Features
=======================
Support for existing Cucumber Tags. Useful because adopting the new framework will require changes.
Support after scenario.
Java8 Support, mainly support for lambda step definitions.
