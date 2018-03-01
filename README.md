# EDAF75, project report

This is the report for

 + Ennio Mara, `en0067ma-s`
 + Christian Bilevits, `ch8466bi-s`

We solved this project on our own, except for:

 + The Peer-review meeting


## ER-design - <span style="color:red">NOT FINISHED</span>

The model is in the file [`er-model.png`](er-model.png):

<center>
    <img src="er-model.png" width="100%">
</center>

## Relations - <span style="color:red">NOT FINISHED</span>

The ER-model above gives the following relations (neither
[Markdown](https://docs.gitlab.com/ee/user/markdown.html)
nor [HTML5](https://en.wikipedia.org/wiki/HTML5) handles
underlining withtout resorting to
[CSS](https://en.wikipedia.org/wiki/Cascading_Style_Sheets),
so we use bold face for primary keys, italicized face for
foreign keys, and bold italicized face for attributes which
are both primary keys and foreign keys):

+ authors(**author_name**, nationality)
+ books(**isbn**, book_title, published_year, publisher)
+ authored_books(**_author_name_**, **_isbn_**)
+ copies(**copy_barcode**, _isbn_, _library_name_, shelf)
+ ...

(this should be replaced with your own relations, of course,
but use the same way of marking primary keys and foreign
keys).


## Scripts to set up database - <span style="color:red">NOT FINISHED</span>

The scripts used to set up and populate the database are in:

 + [`create-schema.sql`](create-schema.sql) (defines the tables), and
 + [`initial-data.sql`](initial-data.sql) (inserts data).

So, to create and initialize the database, we run:

```shell
sqlite3 krusty-db.sqlite < create-schema.sql
sqlite3 krusty-db.sqlite < initial-data.sql
```

(or whatever you call your database file).

## How to compile and run the program - <span style="color:red">NOT FINISHED</span>

This section should give a few simple commands to type to
compile and run the program from the command line, such as:

```shell
./gradlew run
```

or

```shell
javac -d bin/ -cp src src/krusty/Main.java
java -cp bin:sqlite-jdbc.jar krusty.Main
```

or, if you put your commands into a `Makefile`:

```shell
make compile
make run
```

The next section shows how this can be done.


<hr>

# How to actually set up and run the program

This section should not be in your report, it just gives
some instructions on how to compile and run your program
from the command line.

First of all, we don't want git to keep track of generated
files, such as `.class` files -- so put this in a file
`.gitignore` in the top directory of the repository:

``` text
*.class
```

(alternatively, we could put `bin/` here, depending on how
you set up your project below).

In the first few courses, you have probably used an
[IDE](https://en.wikipedia.org/wiki/Integrated_development_environment),
such as Eclipse or Intellij, to compile and run your
programs. 

Using an IDE can be nice, but having to start up Eclipse or
Intellij only to test or run the program is time consuming,
and its difficult to automate things this way -- in 'real'
projects we typically want to use scripts with simple
commands to test and deploy our programs, and we definitely
don't want to use the mouse to do it (especially if we use
[continuous
integration](https://en.wikipedia.org/wiki/Continuous_integration)).

So, we want a way to compile and run the program using a few
simple commands in a _command line interpreter_ (CLI).

If you use a build tool like
[`gradle`](https://gradle.org/), it is very simple to
compile and run the program from the CLI:

```shell
./gradlew run
```

Gradle knows when it has to recompile files, and does it
automatically when the source code has changed.

If you use Eclipse, Intellij, or some other IDE, it is
perfectly fine if you keep compiling and running your
program by clicking the 'run'-button, but it is pretty
straightforward to also enable compiling and running from
the CLI, and we want you to do it, so we can test your
program.

As an example of how it works, we can take the swing code
distributed for lab 3:

```text
.
├── bin
├── sqlite-jdbc.jar
└── src
    └── dbtLab3
        ├── BasicPane.java
        ├── BookingPane.java
        ├── ButtonAndMessagePanel.java
        ├── CurrentUser.java
        ├── Database.java
        ├── InputPanel.java
        ├── MovieBooking.java
        ├── MovieGUI.java
        └── UserLoginPane.java
```

To compile the main program (i.e., the class with the
`public static void main...`, which is in
`MovieBooking.java`), and put the resulting program in the
`bin` directory, we can write:

```shell
javac -d bin/ -cp src src/dbtLab3/MovieBooking.java
```

The options and arguments to this compilations are:

+ `-d bin/` tells the compiler to put generated files in the
  `bin` directory (we don't want to clutter our source code
  directory wich `.class` files)

+ `-cp src` tells the compiler that the 'root' of the
  source code is in the `src` directory (all package names
  are names of directories in the hierarchy starting at the
  'root' directory)

+ `src/dbtLab3/MovieBooking.java` is the file with the
  'main' class -- compiling it will make `javac` compile all
  other `.java` files which it depends on (transitively)

So, the command above will compile everything needed to run
`MovieBooking`, and put the generated `.class` files in the
`bin` directory -- to run the program, we write:

```shell
java -cp bin:sqlite-jdbc.jar dbtLab3.MovieBooking
```

where the options and arguments are:

+ `-cp bin:sqlite-jdbc.jar` tells the compiler to look for
  `.class` files in the `bin` directory, and in the
  `sqlite-jdbc.jar` archive (needed only if we use
  'Sqlite3')
  
+ `dbtLab3.MovieBooking` is the fully qualified name of the
  `MovieBooking` class (it's in the `dbtLab3` package)

To make sure that our program can read the database, we can
put the database file (in this case `movie-db.sqlite`), and
the files used to generate it, in the top directory of the
repository:

```text
.
├── bin
├── create-schema.sql
├── initial-data.sql
├── movie-db.sqlite
├── sqlite-jdbc.jar
└── src
    └── dbtLab3
        ├── BasicPane.java
        ├── ...
```

If we start our program from a CLI which has this top
directory as its 'current working directory' (i.e., we've
`cd`-ed into it), we can open `movie-db.sqlite` from our
Java code with:

```java
Database db = new Database();
db.openConnection("movie-db.sqlite");
```

If you want to, we can have a QA session about this at some
lunch (in that case, just go to Moodle and sign up).

To make things really simple, I personally often add simple
commands to a `Makefile` (here we put the `Makefile` in the
top directory of our repository):

```text
compile:
	javac -d bin/ -cp src src/dbtLab3/MovieBooking.java

run:
	java -cp bin:sqlite-jdbc.jar dbtLab3.MovieBooking

generate-db:
	rm -f movie-db.sqlite
	sqlite3 movie-db.sqlite < create-schema.sql
	sqlite3 movie-db.sqlite < initial-data.sql
```

**Observe:** in this file, the indentation _must_ be TABs,
it's one of the very few cases where we use TABs in our
'source code' (beware that the tabs probably will be
replaced by spaces if you copy this file, so you may have to
manually insert TABs in your own `Makefile`).

When we have this `Makefile`, we can compile our program by
just running:

```shell
make compile
```

To create the database, compile our program, and then run it
we type:

```shell
make generate-db
make compile
make run
```

Using `make` this way is almost like playing baseball with a
Stradivarius (it's capable of sooo much more!), but we get
our Strad for free!
