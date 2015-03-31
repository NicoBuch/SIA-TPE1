# SIA-TPE1


### Installation ###

Clone the repository, make sure you have maven installed

```bash

   > git clone git@bitbucket.org:itba/sia-2015-07.git
   > sudo apt-get install maven

```

### Compiling ###

There is a copy of the executable binary in '/Executable' but if you want to make any change in the project and recompile you can run:

```bash

  > mvn clean package

```

  Then look for the binary executable in target/tp1-0.0.1-SNAPSHOT.jar

### Running ###

Run with a specific board, strategy and heuristic (optional).

```bash

   > cd < path to /SIA-TPE1/Executable >
   > java -jar tp1-0.0.1-SNAPSHOT.jar <boardX> <strategy> [heuristic]

```

  The options for board are:
    board1, board2, board4, board5, board16, board18, board20, board21, board29, board32

  The options for strategy are:
    bfs, dfs, deepiteration, greedy, astar

  The options for heuristic (Optional) are:
    mindistance1, mindistance2, mindistance3, inpath, admissibleinpath, admissiblemindistance, admissiblecombination, notadmissiblecombination



### Solutions ###

 To check how the algorithm worked, you can take a look at '/Solutions' where you will find all of the trees created in each run (you will need to have installed some tool to read .dot files). With blue is painted the solution branch, and with red are painted the branches that were pruned with the Default Heuristic if running an informed algorithm.

### Maintainer ###

This project is maintained by [Nicolás Buchhalter](https://github.com/NicoBuch), [Agustina Fainguersch](https://github.com/agusfagus) and [Francisco Depascuali](https://github.com/frandepa).

