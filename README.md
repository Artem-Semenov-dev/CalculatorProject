<h1>BazaScript language</h1>

<h3> Project consist of 3 modules: </h3>

<ul>
<li><h4>FSM</h4></li>
<li><h4>Calculator</h4></li>
<li><h4>BazaScript</h4></li>
</ul>

<h2>FSM</h2>

<h3> Universal module that implements the concept of
<a href = "https://en.wikipedia.org/wiki/Finite-state_machine">Finite state machine.</a></h3>
<h4> For transitions between states that define in transition matrix we used transducers.</h4>
<h4> FSM used a universal mechanism for throwing exceptions.</h4>
<h5> Also this module has an implementation of Identifier machine. 
Identifier machine used to read symbols and collect them.
</h5>

<h2>Calculator</h2>

<h3> Module that implement concept of calculator for mathematical expressions.</h3>

<h4> Calculator created with usage of finite state machines</h4>
<h4> This calculator can solve expressions that include:</h4>

<ul>
<li>Integer or float numbers</li>
<li>Priority binary operators like +, -, /, *, ^, etc.</li>
<li>Brackets</li>
<li>Functions like sum, avg, min, max</li>
</ul>

<h2>BazaScript language</h2>

<h3>Simple programming language that used interpreter for execution of the code.</h3>
<h4>Work of interpreter for BazaScript based on Finite state machines.</h4>
<h4> This language can:</h4>
<ul>
<li>Initialize variables of Double type or with expression that solve double values.</li>
<li>Resolve math operations between variables like +, -, /, *, ^, etc.</li>
<li>Print variables</li>
</ul>