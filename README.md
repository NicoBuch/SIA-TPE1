# SIA-TPE1


### Installation ###

Clone the repository, make sure you have maven installed
 ```bash
   > git clone git@bitbucket.org:itba/sia-2015-07.git
   > sudo apt-get install maven
 ```

## Running ##

Compile and run with a specific board, strategy and heuristic (optional)
 ```bash
   > cd < path to /SIA-TPE1 >
   > mvn clean package
   > java -jar target/tp1-0.0.1-SNAPSHOT.jar <boardX> <strategy> [heuristic]

  The options for board are:
    board1, board2, board4, board5, board16, board18, board20, board21, board29, board32

  The options for strategy are:
    bfs, dfs, deepiteration, greedy, astar

  The options for heuristic (Optional) are:
    mindistance1, mindistance2, mindistance3, inpath, admissibleinpath, admissiblemindistance, admissiblecombination, notadmissiblecombination
 ```

## Maintainer ##

This project is maintained by [Nicolás Buchhalter](https://github.com/NicoBuch), [Agustina Fainguersch](https://github.com/agusfagus) and [Francisco Depascuali](https://github.com/frandepa).

