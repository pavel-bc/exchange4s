# exchange4s

[![Scala CI](https://github.com/pavel-bc/exchange4s/actions/workflows/scala.yml/badge.svg)](https://github.com/pavel-bc/exchange4s/actions/workflows/scala.yml) [![scaladoc](https://img.shields.io/badge/scaladoc-1.0.0-green?style=flat&logo=scala)](https://pavel-bc.github.io/exchange4s)

[Blockchain.com Exchange API](https://exchange.blockchain.com/api) client implementation in Scala.

Features:

- Create market and limit orders
- Cancel an order
- Subscribe to balances
- Subscribe to market data
- Subscribe to symbol reference data

## Building

To build a JAR, execute:

```sh
$ sbt package
```

## Examples

Check [`examples`](src/example/scala) directory. To run one of examples, execute:

```sh
$ sbt example:run
`````

## Showcase

[![asciicast](https://asciinema.org/a/323363.svg)](https://asciinema.org/a/323363)
